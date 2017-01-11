package com.myframe.dao.util;

/**
 * 数据库字段对象。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class Column {
    private String name;
    private String property;
    private String jdbcType;
    private String javaType;
    private String firstUpperProperty;
    private String remark;
    private boolean isPk = false;
    private boolean queryExclude = false;
    private boolean updateExclude = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getFirstUpperProperty() {
        return firstUpperProperty;
    }

    public void setFirstUpperProperty(String firstUpperProperty) {
        this.firstUpperProperty = firstUpperProperty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isPk() {
        return isPk;
    }

    public void setPk(boolean isPk) {
        this.isPk = isPk;
    }

    public boolean isQueryExclude() {
        return queryExclude;
    }

    public void setQueryExclude(boolean queryExclude) {
        this.queryExclude = queryExclude;
    }

    public boolean isUpdateExclude() {
        return updateExclude;
    }

    public void setUpdateExclude(boolean updateExclude) {
        this.updateExclude = updateExclude;
    }
}
