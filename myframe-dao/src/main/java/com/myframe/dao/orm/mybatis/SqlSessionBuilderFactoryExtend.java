// Copyright (C) 2015 Meituan
// All rights reserved
package com.myframe.dao.orm.mybatis;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.myframe.core.util.ClassUtils;
import com.myframe.core.util.LogUtils;
import com.myframe.core.util.StreamUtils;
import com.myframe.core.util.TemplateUtils;
import com.myframe.dao.orm.mybatis.interceptor.PaginationInterceptor;
import com.myframe.dao.util.Column;
import com.myframe.dao.util.JdbcUtils;
import com.myframe.dao.util.Table;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 扩展SqlSessionFactoryBuilder，自动添加数据库表对应的mapper。
 *
 * @author wuyuzhen
 * @version 1.0
 * @created 15/9/29 16:28
 */
public class SqlSessionBuilderFactoryExtend extends SqlSessionFactoryBuilder {
    private static final Logger logger = LogUtils.get();
    private static final String MAPPER_TEMPLATE = "/mapper.xml.tpl";
    private String tablePrefix;
    private DataSource dataSource;
    private Set<String> pojoPackages;

    @Override
    public SqlSessionFactory build(Configuration config) {
        Map<Class<?>, String> existClass = getAllClassNames();
        // 添加
        for (Map.Entry<Class<?>, String> entry : existClass.entrySet()) {
            Table table = JdbcUtils.getTableInfo(getDataSource(), getTablePrefix(), entry.getValue());
            // 过滤没有加@Column注解的字段
            filterTableColumns(table, entry.getKey());

            Map<String, Object> params = Maps.newHashMap();
            params.put("table", table);
            String tplStr = TemplateUtils.render(MAPPER_TEMPLATE, params);

            if (logger.isDebugEnabled()) {
                logger.debug("mapper generated: \n{}", tplStr);
            }

            InputStream is = new ByteArrayInputStream(tplStr.getBytes(Charsets.UTF_8));
            XMLMapperBuilder builder = new XMLMapperBuilder(
                    is, config, "_inner_" + table.getClassName() + "Mapper",
                    config.getSqlFragments(), table.getClassName());
            builder.parse();
            StreamUtils.closeQuietly(is);
        }

        // 加入分页拦截器
        config.addInterceptor(new PaginationInterceptor());

        return super.build(config);
    }

    private Map<Class<?>, String> getAllClassNames() {
        Map<Class<?>, String> exists = Maps.newHashMap();

        for (String pojo : getPojoPackages()) {
            Set<Class<?>> existClass = ClassUtils.getClasses(pojo, true);
            for (Class<?> clazz : existClass) {
                if (clazz.isAnnotationPresent(com.myframe.dao.orm.annotation.Table.class)) {
                    com.myframe.dao.orm.annotation.Table tz = clazz.getAnnotation(com.myframe.dao.orm.annotation.Table.class);
                    exists.put(clazz, tz.name());
                }
            }
        }
        return exists;
    }

    private void filterTableColumns(Table table, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<Column> columns = table.getColumns();
        List<Column> newColumns = Lists.newArrayList();
        for (Field field : fields) {
            if (field.isAnnotationPresent(com.myframe.dao.orm.annotation.Column.class)
                    || field.isAnnotationPresent(com.myframe.dao.orm.annotation.Id.class)) {
                String fieldName = field.getName();
                for (Column column : columns) {
                    if (fieldName.equals(column.getProperty())) {
                        newColumns.add(column);
                    }
                }
            }
        }
        table.setColumns(newColumns);
    }

    public Set<String> getPojoPackages() {
        return pojoPackages;
    }

    public void setPojoPackages(Set<String> pojoPackages) {
        this.pojoPackages = pojoPackages;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
