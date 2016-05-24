package com.myframe.dao.util;

import com.myframe.core.util.CollectUtils;

import java.util.List;

/**
 * 数据库表字段。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class Table {
    private String tableName;
    private String className;
    private String remark;
    private List<Column> pkColumns;
    private List<Column> columns;
    private String pkType;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Column> getPkColumns() {
        return pkColumns;
    }

    public void setPkColumns(List<Column> pkColumns) {
        this.pkColumns = pkColumns;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getPkType() {
        return pkType;
    }

    public void setPkType(String pkType) {
        this.pkType = pkType;
    }

    public boolean isAutoIncrement() {
        if (CollectUtils.isEmpty(pkColumns) || pkColumns.size() > 1) {
            return false;
        }
        Column col = pkColumns.get(0);
        return col.getJavaType().equals("Integer")
                    || col.getJavaType().equals("Long");
    }
}
