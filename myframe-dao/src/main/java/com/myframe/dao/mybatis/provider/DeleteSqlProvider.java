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
public class DeleteSqlProvider extends BaseSqlProvider {

    public DeleteSqlProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 通过主键删除
     *
     * @param ms
     */
    public String deleteById(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        return sql.toString();
    }

    public String delete(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.WHERE_CLAUSE);
        return sql.toString();
    }
}
