// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.dao.orm.mybatis.provider;

import com.myframe.dao.util.SqlHelper;
import com.myframe.dao.orm.mybatis.MapperHelper;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/1/24 15:31
 */
public class UpdateSqlProvider extends BaseSqlProvider {

    public UpdateSqlProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String updateById(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, null, false, false));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        return sql.toString();
    }

    public String updateNotNullById(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, null, true, false));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        return sql.toString();
    }

    public String updateByChain(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateChain());
        sql.append(SqlHelper.whereClause());
        return sql.toString();
    }
}
