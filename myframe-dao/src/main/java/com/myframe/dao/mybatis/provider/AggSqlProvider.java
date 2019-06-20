package com.myframe.dao.mybatis.provider;

import com.myframe.dao.mybatis.MapperHelper;
import com.myframe.dao.util.SqlHelper;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 18/5/11
 */
public class AggSqlProvider extends BaseSqlProvider {
    private static final String FROM = " from (";
    private static final char BRACE = ')';
    private static final String AS = " as ";
    private static final String ALIAS_SUFFIX = "_agg";

    public AggSqlProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String getAgg(MappedStatement ms) {
        return aggSql(ms);
    }

    public String getAggCount(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        String tableName = tableName(entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.SELECT_COUNT_CLAUSE);
        sql.append(FROM);
        sql.append(SqlHelper.getSqlPrefix());
        sql.append(SqlHelper.selectAggregation());
        sql.append(SqlHelper.fromTable(entityClass, tableName));
        sql.append(SqlHelper.WHERE_CLAUSE);
        sql.append(SqlHelper.GROUP_BY);
        sql.append(SqlHelper.HAVING_CLAUSE);
        sql.append(BRACE);
        sql.append(AS);
        sql.append(tableName);
        sql.append(ALIAS_SUFFIX);

        return sql.toString();
    }

    public String getAggList(MappedStatement ms) {
        return aggSql(ms);
    }

    public String getAggPageList(MappedStatement ms) {
        return aggSql(ms);
    }

    private String aggSql(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.getSqlPrefix());
        sql.append(SqlHelper.selectAggregation());
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.WHERE_CLAUSE);
        sql.append(SqlHelper.GROUP_BY);
        sql.append(SqlHelper.HAVING_CLAUSE);
        sql.append(SqlHelper.ORDER_BY);

        return sql.toString();
    }
}
