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
    private JavaDaoConfig javaDaoConfig;
    private JavaServiceConfig javaServiceConfig;
    private SqlMapConfig sqlMapConfig;
    private GenerateConfig generateConfig;
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

    public JavaDaoConfig getJavaDaoConfig() {
        return javaDaoConfig;
    }

    public void setJavaDaoConfig(JavaDaoConfig javaDaoConfig) {
        this.javaDaoConfig = javaDaoConfig;
    }

    public JavaServiceConfig getJavaServiceConfig() {
        return javaServiceConfig;
    }

    public void setJavaServiceConfig(JavaServiceConfig javaServiceConfig) {
        this.javaServiceConfig = javaServiceConfig;
    }

    public SqlMapConfig getSqlMapConfig() {
        return sqlMapConfig;
    }

    public void setSqlMapConfig(SqlMapConfig sqlMapConfig) {
        this.sqlMapConfig = sqlMapConfig;
    }

    public GenerateConfig getGenerateConfig() {
        return generateConfig;
    }

    public void setGenerateConfig(GenerateConfig generateConfig) {
        this.generateConfig = generateConfig;
    }

    public TableConfig getTableConfig() {
        return tableConfig;
    }

    public void setTableConfig(TableConfig tableConfig) {
        this.tableConfig = tableConfig;
    }
}
