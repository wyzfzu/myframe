package com.myframe.generator.config;

import java.io.Serializable;

/**
 * 数据库连接配置对象。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class JdbcConfig implements Serializable {
    private String driverClass;
    private String connectionUrl;
    private String userName;
    private String password;
    private String schema;

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
