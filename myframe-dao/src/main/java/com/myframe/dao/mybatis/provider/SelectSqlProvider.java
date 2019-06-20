// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.dao.mybatis.provider;

import com.myframe.dao.mybatis.MapperHelper;
import com.myframe.dao.util.SqlHelper;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/1/24 15:31
 */
public class SelectSqlProvider extends BaseSqlProvider {

    public SelectSqlProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String getById(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        return sql.toString();
    }

    public String get(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.getSqlPrefix());
        sql.append(SqlHelper.selectFilterColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.WHERE_CLAUSE);
        return sql.toString();
    }

    public String getMap(MappedStatement ms) {
        return get(ms);
    }

    public String getList(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.getSqlPrefix());
        sql.append(SqlHelper.selectFilterColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.WHERE_CLAUSE);
        sql.append(SqlHelper.ORDER_BY);
        return sql.toString();
    }

    public String getMapList(MappedStatement ms) {
        return getList(ms);
    }

    public String getPageList(MappedStatement ms) {
        return getList(ms);
    }

    public String getAll(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectFilterColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        return sql.toString();
    }

    public String getCount(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.getSqlPrefix());
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.WHERE_CLAUSE);
        return sql.toString();
    }

    public String getAllCount(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        return sql.toString();
    }
}
