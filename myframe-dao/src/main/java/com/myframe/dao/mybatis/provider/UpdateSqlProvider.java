// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.dao.mybatis.provider;

import com.myframe.dao.util.SqlHelper;
import com.myframe.dao.mybatis.MapperHelper;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/1/24 15:31
 */
public class UpdateSqlProvider extends BaseSqlProvider {

    private static final String FOREACH_OPEN = "<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\n\">";
    private static final String FOREACH_CLOSE = "</foreach>";

    public UpdateSqlProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String updateById(MappedStatement ms) {
        return updatePKSql(ms, null, false, false);
    }

    public String updateNotNullById(MappedStatement ms) {
        return updatePKSql(ms, null, true, false);
    }

    public String updateBatchById(MappedStatement ms) {
        StringBuilder sql = new StringBuilder();
        sql.append(FOREACH_OPEN);
        sql.append(updatePKSql(ms, "item", false, false));
        sql.append(FOREACH_CLOSE);
        return sql.toString();
    }

    public String updateNotNullBatchById(MappedStatement ms) {
        StringBuilder sql = new StringBuilder();
        sql.append(FOREACH_OPEN);
        sql.append(updatePKSql(ms, "item", true, false));
        sql.append(FOREACH_CLOSE);
        return sql.toString();
    }

    public String updateByChain(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.UPDATE_CHAIN);
        sql.append(SqlHelper.WHERE_CLAUSE);
        return sql.toString();
    }

    private String updatePKSql(MappedStatement ms, String entityName, boolean notNull, boolean notEmpty) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, entityName, notNull, notEmpty));
        sql.append(SqlHelper.wherePKColumns(entityClass, entityName));
        return sql.toString();
    }
}
