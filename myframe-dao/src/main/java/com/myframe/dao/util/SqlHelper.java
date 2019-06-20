
package com.myframe.dao.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * 拼常用SQL的工具类
 *
 */
public class SqlHelper {

    public static final String ORDER_BY = orderBy();
    public static final String GROUP_BY = groupBy();
    public static final String AGGREGATION = agg();
    public static final String SELECT_COUNT_CLAUSE = selectCount();
    public static final String WHERE_CLAUSE = whereClause();
    public static final String HAVING_CLAUSE = having();
    public static final String UPDATE_CHAIN = updateChain();
    public static final String SQL_PREFIX = "";

    /**
     * <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
     *
     * @param column
     * @return
     */
    public static String getBindCache(EntityColumn column) {
        StringBuilder sql = new StringBuilder();
        sql.append("<bind name=\"");
        sql.append(column.getProperty()).append("_cache\" ");
        sql.append("value=\"").append(column.getProperty()).append("\"/>");
        return sql.toString();
    }

    /**
     * <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
     *
     * @param column
     * @return
     */
    public static String getBindValue(EntityColumn column, String value) {
        StringBuilder sql = new StringBuilder();
        sql.append("<bind name=\"");
        sql.append(column.getProperty()).append("_bind\" ");
        sql.append("value='").append(value).append("'/>");
        return sql.toString();
    }

    /**
     * <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
     *
     * @param column
     * @return
     */
    public static String getIfCacheNotNull(EntityColumn column, String contents) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"").append(column.getProperty()).append("_cache != null\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * 如果_cache == null
     *
     * @param column
     * @return
     */
    public static String getIfCacheIsNull(EntityColumn column, String contents) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"").append(column.getProperty()).append("_cache == null\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * 判断自动!=null的条件结构
     *
     * @param column
     * @param contents
     * @param empty
     * @return
     */
    public static String getIfNotNull(EntityColumn column, String contents, boolean empty) {
        return getIfNotNull(null, column, contents, empty);
    }

    /**
     * 判断自动==null的条件结构
     *
     * @param column
     * @param contents
     * @param empty
     * @return
     */
    public static String getIfIsNull(EntityColumn column, String contents, boolean empty) {
        return getIfIsNull(null, column, contents, empty);
    }

    /**
     * 判断自动!=null的条件结构
     *
     * @param entityName
     * @param column
     * @param contents
     * @param empty
     * @return
     */
    public static String getIfNotNull(String entityName, EntityColumn column, String contents, boolean empty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if (StringUtils.isNotEmpty(entityName)) {
            sql.append(entityName).append(".");
        }
        sql.append(column.getProperty()).append(" != null");
        if (empty && column.getJavaType().equals(String.class)) {
            sql.append(" and ");
            if (StringUtils.isNotEmpty(entityName)) {
                sql.append(entityName).append(".");
            }
            sql.append(column.getProperty()).append(" != '' ");
        }
        sql.append("\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * 判断自动==null的条件结构
     *
     * @param entityName
     * @param column
     * @param contents
     * @param empty
     * @return
     */
    public static String getIfIsNull(String entityName, EntityColumn column, String contents, boolean empty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if (StringUtils.isNotEmpty(entityName)) {
            sql.append(entityName).append(".");
        }
        sql.append(column.getProperty()).append(" == null");
        if (empty && column.getJavaType().equals(String.class)) {
            sql.append(" or ");
            if (StringUtils.isNotEmpty(entityName)) {
                sql.append(entityName).append(".");
            }
            sql.append(column.getProperty()).append(" == '' ");
        }
        sql.append("\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    /**
     * 获取所有查询列，如id,name,code...
     *
     * @param entityClass
     * @return
     */
    public static String getAllColumns(Class<?> entityClass) {
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        for (EntityColumn entityColumn : columnList) {
            sql.append(entityColumn.getColumn()).append(" AS ")
                    .append(entityColumn.getProperty()).append(",");
        }
        return sql.substring(0, sql.length() - 1);
    }

    public static String getFilterColumns(Class<?> entityClass) {
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        StringBuilder cols = new StringBuilder(100);
        StringBuilder commaCols = new StringBuilder(100);
        for (EntityColumn entityColumn : columnList) {
            cols.append(entityColumn.getColumn());
            if (!entityColumn.isColumnPropertySame()) {
                cols.append(" AS ").append(entityColumn.getProperty());
            }
            cols.append(',');
            commaCols.append("'").append(entityColumn.getColumn()).append("',");
        }
        sql.append("<trim prefix=\"\" suffixOverrides=\",\">\n")
                .append("<choose>\n")
                .append("    <when test=\"includeFields != null and includeFields.size() > 0\">\n")
                .append("        <foreach item=\"field\" collection=\"includeFields\" open=\"\" close=\"\" separator=\",\">\n")
                .append("             \\${field}\n")
                .append("        </foreach>\n")
                .append("    </when>\n")
                .append("    <when test=\"excludeFields != null and excludeFields.size() > 0\">\n")
                .append("        <foreach item=\"field\" collection=\"{")
                .append(commaCols.substring(0, commaCols.length() - 1))
                .append("}\" open=\"\" close=\"\" separator=\",\">\n")
                .append("            <if test=\"field not in excludeFields\">\n")
                .append("                \\${field}\n")
                .append("            </if>\n")
                .append("        </foreach>\n")
                .append("    </when>\n")
                .append("    <otherwise>")
                .append(cols.substring(0, cols.length() - 1))
                .append("</otherwise>\n")
                .append("</choose>\n</trim>");

        return sql.toString();
    }

    /**
     * select xxx,xxx...
     *
     * @param entityClass
     * @return
     */
    public static String selectAllColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(getAllColumns(entityClass));
        sql.append(" ");
        return sql.toString();
    }

    public static String selectFilterColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("<if test=\"distinct\">DISTINCT</if>");
        sql.append(getFilterColumns(entityClass));
        sql.append(" ");
        return sql.toString();
    }

    /**
     * select count(x)
     *
     * @param entityClass
     * @return
     */
    public static String selectCount(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        Set<EntityColumn> pkColumns = EntityHelper.getPKColumns(entityClass);
        if (pkColumns.size() == 1) {
            sql.append("COUNT(").append(pkColumns.iterator().next().getColumn()).append(") ");
        } else {
            sql.append("COUNT(1) ");
        }
        return sql.toString();
    }

    public static String selectCount() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1) ");
        return sql.toString();
    }

    /**
     * select case when count(x) > 0 then 1 else 0 end
     *
     * @param entityClass
     * @return
     */
    public static String selectCountExists(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE WHEN ");
        Set<EntityColumn> pkColumns = EntityHelper.getPKColumns(entityClass);
        if (pkColumns.size() == 1) {
            sql.append("COUNT(").append(pkColumns.iterator().next().getColumn()).append(") ");
        } else {
            sql.append("COUNT(1) ");
        }
        sql.append(" > 0 THEN 1 ELSE 0 END AS result ");
        return sql.toString();
    }

    public static String selectAggregation() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(AGGREGATION);

        return sql.toString();
    }

    public static String getDynamicTableName(Class<?> entityClass, String tableName) {
        return "<choose>\n" +
                "<when test=\"@com.myframe.dao.util.OGNL@isDynamicInterface(_parameter) and dynamicTableSuffix != null and dynamicTableSuffix != ''\">" + tableName + "${dynamicSuffixSeparator}${dynamicTableSuffix}</when>\n" +
                "<when test=\"@com.myframe.dao.util.OGNL@isDynamicCnd(_parameter) and dynamicSuffix != null and dynamicSuffix != ''\">" + tableName + "${dynamicSuffixSeparator}${dynamicSuffix}</when>\n" +
                "<when test=\"@com.myframe.dao.util.OGNL@isDynamicUpdateChain(_parameter) and cnd.dynamicSuffix != null and cnd.dynamicSuffix != ''\">" + tableName + "${cnd.dynamicSuffixSeparator}${cnd.dynamicSuffix}</when>\n" +
                "<otherwise>`" + tableName + "`</otherwise>\n" +
                "</choose>";
    }

    /**
     * from tableName - 动态表名
     *
     * @param entityClass
     * @param defaultTableName
     * @return
     */
    public static String fromTable(Class<?> entityClass, String defaultTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM ");
        sql.append(getDynamicTableName(entityClass, defaultTableName));
        sql.append(" ");
        return sql.toString();
    }

    /**
     * update tableName - 动态表名
     *
     * @param entityClass
     * @param defaultTableName
     * @return
     */
    public static String updateTable(Class<?> entityClass, String defaultTableName) {
        return updateTable(entityClass, defaultTableName, null);
    }

    /**
     * update tableName - 动态表名
     *
     * @param entityClass
     * @param defaultTableName 默认表名
     * @param entityName       别名
     * @return
     */
    public static String updateTable(Class<?> entityClass, String defaultTableName, String entityName) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(getDynamicTableName(entityClass, defaultTableName));
        sql.append(" ");
        return sql.toString();
    }

    /**
     * delete tableName - 动态表名
     *
     * @param entityClass
     * @param defaultTableName
     * @return
     */
    public static String deleteFromTable(Class<?> entityClass, String defaultTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(getDynamicTableName(entityClass, defaultTableName));
        sql.append(" ");
        return sql.toString();
    }

    /**
     * insert into tableName - 动态表名
     *
     * @param entityClass
     * @param defaultTableName
     * @return
     */
    public static String insertIntoTable(Class<?> entityClass, String defaultTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(getDynamicTableName(entityClass, defaultTableName));
        sql.append(" ");
        return sql.toString();
    }

    /**
     * replace into tableName - 动态表名
     *
     * @param entityClass
     * @param defaultTableName
     * @return
     */
    public static String replaceIntoTable(Class<?> entityClass, String defaultTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("REPLACE INTO ");
        sql.append(getDynamicTableName(entityClass, defaultTableName));
        sql.append(" ");
        return sql.toString();
    }

    /**
     * insert table()列
     *
     * @param entityClass
     * @param skipId      是否从列中忽略id类型
     * @param notNull     是否判断!=null
     * @param notEmpty    是否判断String类型!=''
     * @return
     */
    public static String insertColumns(Class<?> entityClass, boolean skipId, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (!column.isInsertable()) {
                continue;
            }
            if (skipId && column.isId()) {
                continue;
            }
            if (notNull) {
                sql.append(SqlHelper.getIfNotNull(column, column.getColumn() + ",", notEmpty));
            } else {
                sql.append(column.getColumn()).append(",");
            }
        }
        sql.append("</trim>");
        return sql.toString();
    }

    /**
     * update set列
     *
     * @param entityClass
     * @param entityName  实体映射名
     * @param notNull     是否判断!=null
     * @param notEmpty    是否判断String类型!=''
     * @return
     */
    public static String updateSetColumns(Class<?> entityClass, String entityName, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (!column.isId() && column.isUpdatable()) {
                if (notNull) {
                    sql.append(SqlHelper.getIfNotNull(entityName, column, column.getColumnEqualsHolder(entityName) + ",", notEmpty));
                } else {
                    sql.append(column.getColumnEqualsHolder(entityName)).append(",");
                }
            }
        }
        sql.append("</set>");
        return sql.toString();
    }

    public static String updateChain() {
        return "<set>\n" +
                "<foreach collection=\"opList\" item=\"item\" separator=\",\">\n" +
                "    \\${item.left} = " +
                "   <if test='item.middle == \"=\"'>#{item.right}</if>\n" +
                "   <if test='item.middle != \"=\"'>\\${item.left} \\${item.middle} #{item.right}</if>\n" +
                "</foreach>\n" +
                "</set>";
    }
    /**
     * where主键条件
     *
     * @param entityClass
     * @return
     */
    public static String wherePKColumns(Class<?> entityClass) {
        return wherePKColumns(entityClass, "");
    }

    /**
     * where主键条件
     *
     * @param entityClass
     * @return
     */
    public static String wherePKColumns(Class<?> entityClass, String entityName) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            sql.append(" AND ").append(column.getColumnEqualsHolder(entityName));
        }
        sql.append("</where>");
        return sql.toString();
    }

    /**
     * 获取orderBy
     * @return
     */
    public static String orderBy() {
        return "<if test=\"orderBy\">\n" +
                "    order by\n" +
                "    <foreach collection=\"orders\" item=\"od\" separator=\",\">\n" +
                "        \\${od.key} \\${od.value}\n" +
                "    </foreach>\n" +
                "</if>";
    }

    public static String whereClause() {
        return "<where>\n" +
                "    <if test=\"innerCnd != null\">(</if>\n" +
                "    <foreach collection=\"orderCriteria\" item=\"criteria\" separator=\"or\">\n" +
                "        " + innerWhereClause() + "\n" +
                "    </foreach>\n" +
                "    <if test=\"innerCnd != null\">\n" +
                "        ) AND\n" +
                "        <trim prefix=\"(\" suffix=\")\" prefixOverrides=\"and\">\n" +
                "            <foreach collection=\"innerCnd.orderCriteria\" item=\"criteria\" separator=\"or\">\n" +
                "                " + innerWhereClause() + "\n" +
                "            </foreach>\n" +
                "        </trim>\n" +
                "    </if>\n" +
                "</where>";
    }

    /**
     * 内部where结构
     *
     * @return
     */
    public static String innerWhereClause() {
        return "<if test=\"criteria.valid\">\n" +
                "    <trim prefix=\"(\" suffix=\")\" prefixOverrides=\"and\">\n" +
                "        <foreach collection=\"criteria.allCriteria\" item=\"cri\">\n" +
                "            <choose>\n" +
                "                <when test=\"cri.nullExp\">\n" +
                "                    and \\${cri.field} \\${cri.op}\n" +
                "                </when>\n" +
                "                <when test=\"cri.betweenExp\">\n" +
                "                    and \\${cri.field} \\${cri.op} #{cri.value} and #{cri.secondValue}\n" +
                "                </when>\n" +
                "                <when test=\"cri.inExp\">\n" +
                "                    and \\${cri.field} \\${cri.op}\n" +
                "                    <foreach collection=\"cri.value\" item=\"listItem\" open=\"(\" close=\")\" separator=\",\">\n" +
                "                        #{listItem}\n" +
                "                    </foreach>\n" +
                "                </when>\n" +
                "                <when test=\"cri.likeExp\">\n" +
                "                    <if test=\"cri.ignoreCase\">\n" +
                "                        and LOWER(\\${cri.field}) \\${cri.op} LOWER(#{cri.value})\n" +
                "                    </if>\n" +
                "                    <if test=\"!cri.ignoreCase\">\n" +
                "                        and \\${cri.field} \\${cri.op} #{cri.value}\n" +
                "                    </if>\n" +
                "                </when>\n" +
                "                <otherwise>\n" +
                "                    and \\${cri.field} \\${cri.op} #{cri.value}\n" +
                "                </otherwise>\n" +
                "            </choose>\n" +
                "        </foreach>\n" +
                "    </trim>\n" +
                "</if>";
    }

    public static String getSqlPrefix() {
        return SQL_PREFIX;
    }

    public static String groupBy() {
        return "<if test=\"groupBy\">\n" +
                "    group by " +
                "    <foreach collection=\"groupBys\" item=\"gb\" separator=\",\">\n" +
                "        \\${gb}" +
                "    </foreach>\n" +
                "</if>";
    }

    public static String agg() {
        return "<if test=\"aggs.size > 0\">" +
                    " <foreach collection=\"aggs\" item=\"agg\" separator=\",\">" +
                        "<if test=\"agg.extraFields.size > 0\">" +
                            "<foreach collection=\"agg.extraFields\" item=\"ef\" separator=\",\">\n" +
                            "        \\${ef}" +
                            "</foreach>," +
                        "</if>" +
                        "${agg.type.name}(${agg.fieldName})" +
                        "<if test=\"agg.aliasName != null and agg.aliasName != ''\">" +
                            " AS ${agg.aliasName} " +
                        "</if>" +
                    "</foreach>" +
                "</if>";
    }

    public static String having() {
        return "<if test=\"having != null and having.agg != null and having.criterion != null\">" +
                    "having ${having.agg.type.name}(${having.agg.fieldName}) " +
                    "<choose>" +
                        "<when test=\"having.criterion.nullExp\">" +
                            "\\${having.criterion.op}\n" +
                        "</when>\n" +
                        "<when test=\"having.criterion.betweenExp\">\n" +
                            "\\${having.criterion.op} #{having.criterion.value} and #{having.criterion.secondValue}\n" +
                        "</when>\n" +
                        "<when test=\"having.criterion.inExp\">\n" +
                            "\\${having.criterion.op}\n" +
                            "<foreach collection=\"having.criterion.value\" item=\"listItem\" open=\"(\" close=\")\" separator=\",\">\n" +
                                "#{listItem}\n" +
                            "</foreach>\n" +
                        "</when>\n" +
                        "<otherwise>\n" +
                            "\\${having.criterion.op} #{having.criterion.value}\n" +
                        "</otherwise>\n" +
                    "</choose>\n" +
                "</if>";
    }
}
