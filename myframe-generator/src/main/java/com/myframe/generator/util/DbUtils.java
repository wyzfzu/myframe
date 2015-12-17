package com.myframe.generator.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.myframe.core.util.LogUtils;
import com.myframe.core.util.StringUtils;
import com.myframe.dao.util.Column;
import com.myframe.dao.util.Table;
import com.myframe.generator.config.GeneratorConfig;
import com.myframe.generator.config.JdbcConfig;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 数据库操作工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class DbUtils {
    private static final Logger logger = LogUtils.get();
    private DataSource ds;
    private GeneratorConfig generatorConfig;
    private Set<String> tablePrefix = Sets.newHashSet();

    private Map<String, Set<String>> nonPrimitiveImport = Maps.newHashMap();
    private static final List<Pattern> includePatterns = Lists.newArrayList();
    private static final List<Pattern> excludePatterns = Lists.newArrayList();
    private static final Map<String, String> typeMap = Maps.newHashMap();

    static {
        typeMap.put("BIGINT", "Long");
        typeMap.put("INTEGER", "Integer");
        typeMap.put("TINYINT", "Short");
        typeMap.put("BIT", "Short");
        typeMap.put("SMALLINT", "Integer");
        typeMap.put("FLOAT", "Float");
        typeMap.put("DOUBLE", "Double");
        typeMap.put("DECIMAL", "Double");
        typeMap.put("NUMERIC", "BigDecimal");
        typeMap.put("REAL", "Float");
        typeMap.put("VARCHAR", "String");
        typeMap.put("NVARCHAR", "String");
        typeMap.put("LONGVARCHAR", "String");
        typeMap.put("TEXT", "String");
        typeMap.put("LONGTEXT", "String");
        typeMap.put("TINYTEXT", "String");
        typeMap.put("SMALLTEXT", "String");
        typeMap.put("CHAR", "String");
        typeMap.put("NCHAR", "String");
        typeMap.put("CLOB", "String");
        typeMap.put("NCLOB", "String");
        typeMap.put("BLOB", "byte[]");
        typeMap.put("DATE", "Date");
        typeMap.put("TIMESTAMP", "Date");
        typeMap.put("DATETIME", "Date");
        typeMap.put("TIME", "Date");
    }

    private void initDataSource(JdbcConfig jdbcConfig) {
        try {
            Properties prop = new Properties();
            prop.put("driver", jdbcConfig.getDriverClass());
            prop.put("url", StringUtils.strip(jdbcConfig.getConnectionUrl()));
            prop.put("username", jdbcConfig.getUserName());
            prop.put("password", jdbcConfig.getPassword());

            ds = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initPattern() {
        Set<String> includes = generatorConfig.getTableConfig().getIncludes();
        Set<String> excludes = generatorConfig.getTableConfig().getExcludes();

        for (String inc : includes) {
            if (inc.equals("*")) {
                includePatterns.clear();
                includePatterns.add(Pattern.compile(".*", Pattern.CASE_INSENSITIVE));
                break;
            }
            includePatterns.add(Pattern.compile(inc, Pattern.CASE_INSENSITIVE));
        }

        for (String exc : excludes) {
            if (exc.equals("*")) {
                excludePatterns.clear();
                excludePatterns.add(Pattern.compile(".*", Pattern.CASE_INSENSITIVE));
                break;
            }
            excludePatterns.add(Pattern.compile(exc, Pattern.CASE_INSENSITIVE));
        }
    }

    public DbUtils(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
        initDataSource(this.generatorConfig.getJdbcConfig());
        String prefix = generatorConfig.getTableConfig().getTrimPrefix();
        tablePrefix.addAll(StringUtils.splitToList(prefix, ','));
        initPattern();
    }

    private DataSource getDataSource() {
        return ds;
    }

    public void closeDataSource() {
        if (ds != null) {
            ((DruidDataSource)ds).close();
        }
    }

    private String getProperty(String column) {
        return StringUtils.toCamelCase(column.toLowerCase(), false, '_');
    }

    private String getClassName(String tableName) {
        tableName = tableName.toLowerCase();
        String[] tns = tableName.split("_");
        if (tns.length == 1) {
            return StringUtils.capitalize(tableName);
        }
        StringBuilder sb = new StringBuilder();
        int start = 0;
        if (tablePrefix.contains(tns[0] + '_')) {
            ++start;
        }

        for (int i = start; i < tns.length; ++i) {
            sb.append(StringUtils.capitalize(tns[i]));
        }

        return sb.toString();
    }

    public Map<String, Set<String>> getNonPrimitiveImport() {
        return this.nonPrimitiveImport;
    }

    private boolean filterInclude(String tableName) {
        for (Pattern p : includePatterns) {
            if (p.matcher(tableName).matches()) {
                return true;
            }
        }

        return false;
    }

    private boolean filterExclude(String tableName) {
        for (Pattern p : excludePatterns) {
            if (p.matcher(tableName).matches()) {
                return true;
            }
        }
        return false;
    }

    private boolean filterTable(String tableName) {
        String priority = generatorConfig.getTableConfig().getPriority();
        if (StringUtils.isEmpty(priority)) {
            priority = "include";
            generatorConfig.getTableConfig().setPriority(priority);
        }
        boolean fe = filterExclude(tableName);
        boolean fi = filterInclude(tableName);

        if ("exclude".equals(priority)) {
            return !fi || fe;
        }

        return fe && !fi;
    }

    public List<Table> getTableInfo(String schemaPattern, String tableNamePattern) {
        List<Table> tables = Lists.newArrayList();
        Connection conn = null;
        try {
            conn = getDataSource().getConnection();
            DatabaseMetaData dma = conn.getMetaData();
            ResultSet rs = dma.getTables(null, schemaPattern, tableNamePattern,
                    new String[]{"TABLE"});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                // 过滤被排除的表
                if (filterTable(tableName)) {
                    logger.info(tableName + "被过滤！");
                    continue;
                }

                String schema = rs.getString("TABLE_SCHEM");
                String tableRemark = rs.getString("REMARKS");
                Table table = new Table();
                List<Column> columns = Lists.newArrayList();
                ResultSet crs = dma.getColumns(null, schema, tableName, null);
                crs.getMetaData();
                Set<String> imports = Sets.newHashSet();
                while (crs.next()) {
                    String columnName = crs.getString("COLUMN_NAME");
                    String jdbcType = JdbcType.forCode(crs.getInt("DATA_TYPE")).name();
                    String remark = crs.getString("REMARKS");
                    Column column = new Column();
                    column.setName(columnName);
                    column.setJdbcType(jdbcType.toUpperCase());
                    String javaType = typeMap.get(column.getJdbcType());
                    if (javaType.equals("Date")) {
                        imports.add("java.util.Date");
                    } else if (javaType.equals("BigDecimal")) {
                        imports.add("java.math.BigDecimal");
                    }
                    column.setJavaType(javaType);
                    column.setPk(false);
                    column.setProperty(getProperty(column.getName()));
                    column.setRemark(remark);
                    column.setFirstUpperProperty(StringUtils.capitalize(column.getProperty()));
                    columns.add(column);
                }
                crs.close();
                nonPrimitiveImport.put(tableName, imports);
                ResultSet prs = dma.getPrimaryKeys(null, schema, tableName);
                List<Column> pks = Lists.newArrayList();
                String pkType = "Long";
                while (prs.next()) {
                    String columnName = prs.getString("COLUMN_NAME");

                    Column c = null;
                    for (Column col : columns) {
                        if (col.getName().equalsIgnoreCase(columnName)) {
                            c = col;
                            break;
                        }
                    }
                    if (c != null) {
                        c.setPk(true);
                        Column pk = new Column();
                        pk.setName(c.getName());
                        pk.setJdbcType(c.getJdbcType());
                        pk.setPk(true);
                        pk.setProperty(c.getProperty());
                        pk.setFirstUpperProperty(c.getFirstUpperProperty());
                        pk.setJavaType(c.getJavaType());
                        pks.add(pk);
                        if (c.getJavaType().indexOf(".") > 0) {
                            pkType = StringUtils.substringAfterLast(c.getJavaType(), ".");
                        } else {
                            pkType = c.getJavaType();
                        }
                    }
                }
                prs.close();
                table.setTableName(tableName);
                table.setClassName(getClassName(tableName));
                table.setRemark(tableRemark);
                table.setPkColumns(pks);
                table.setColumns(columns);
                table.setPkType(pkType);

                tables.add(table);
            }
            rs.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                    // ignore
                }
            }
        }
        return tables;
    }

}
