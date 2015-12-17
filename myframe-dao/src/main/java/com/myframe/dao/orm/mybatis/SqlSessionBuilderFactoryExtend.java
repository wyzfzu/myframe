// Copyright (C) 2015 Meituan
// All rights reserved
package com.myframe.dao.orm.mybatis;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.myframe.core.util.ClassUtils;
import com.myframe.core.util.LogUtils;
import com.myframe.core.util.StreamUtils;
import com.myframe.core.util.TemplateUtils;
import com.myframe.dao.orm.mybatis.interceptor.PaginationInterceptor;
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
import java.lang.annotation.Annotation;
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
    private static final String TABLE_PATTERN = "%";
    private static final String MAPPER_TEMPLATE = "/mapper.xml.tpl";
    private String tablePrefix;
    private DataSource dataSource;
    private Set<String> pojoPackages;

    @Override
    public SqlSessionFactory build(Configuration config) {
        Set<String> existClass = getAllClassNames();
        // 添加
        List<Table> tables = JdbcUtils.getTableInfo(getDataSource(), getTablePrefix(), TABLE_PATTERN);
        for (Table table : tables) {
            if (!existClass.contains(table.getClassName())) {
                continue;
            }
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

    private Set<String> getAllClassNames() {
        Set<String> exists = Sets.newHashSet();

        for (String pojo : getPojoPackages()) {
            Set<Class<?>> existClass = ClassUtils.getClasses(pojo, true);
            for (Class<?> clazz : existClass) {
                if (clazz.isAnnotationPresent(com.myframe.dao.orm.annotation.Table.class)) {
                    exists.add(clazz.getSimpleName());
                }
            }
        }
        return exists;
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
