package com.myframe.generator.config;

import java.io.Serializable;

/**
 * 生成器配置文件对象。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class GeneratorConfig implements Serializable {
    private JdbcConfig jdbcConfig;
    private JavaModelConfig javaModelConfig;
    private JavaMapperConfig javaMapperConfig;
    private SqlMapConfig sqlMapConfig;
    private TableConfig tableConfig;

    public JdbcConfig getJdbcConfig() {
        return jdbcConfig;
    }

    public void setJdbcConfig(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    public JavaModelConfig getJavaModelConfig() {
        return javaModelConfig;
    }

    public void setJavaModelConfig(JavaModelConfig javaModelConfig) {
        this.javaModelConfig = javaModelConfig;
    }

    public JavaMapperConfig getJavaMapperConfig() {
        return javaMapperConfig;
    }

    public void setJavaMapperConfig(JavaMapperConfig javaMapperConfig) {
        this.javaMapperConfig = javaMapperConfig;
    }

    public SqlMapConfig getSqlMapConfig() {
        return sqlMapConfig;
    }

    public void setSqlMapConfig(SqlMapConfig sqlMapConfig) {
        this.sqlMapConfig = sqlMapConfig;
    }

    public TableConfig getTableConfig() {
        return tableConfig;
    }

    public void setTableConfig(TableConfig tableConfig) {
        this.tableConfig = tableConfig;
    }
}
