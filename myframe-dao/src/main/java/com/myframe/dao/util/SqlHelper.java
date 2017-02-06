/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.myframe.dao.util;

import com.myframe.core.util.StringUtils;

import java.util.Set;

/**
 * 拼常用SQL的工具类
 *
 */
public class SqlHelper {

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
            cols.append(entityColumn.getColumn()).append(',');
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
        sql.append(defaultTableName);
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
        sql.append(defaultTableName);
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
        sql.append(defaultTableName);
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
        sql.append(defaultTableName);
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
     * insert-values()列
     *
     * @param entityClass
     * @param skipId      是否从列中忽略id类型
     * @param notNull     是否判断!=null
     * @param notEmpty    是否判断String类型!=''
     * @return
     */
    public static String insertValuesColumns(Class<?> entityClass, boolean skipId, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\">");
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
                sql.append(SqlHelper.getIfNotNull(column, column.getColumnHolder() + ",", notEmpty));
            } else {
                sql.append(column.getColumnHolder()).append(",");
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
        return "<set><foreach collection=\"params\" item=\"item\" index=\"key\" separator=\",\">\n" +
                "    \\${key} = #{item}\n" +
                "</foreach></set>";
    }
    /**
     * where主键条件
     *
     * @param entityClass
     * @return
     */
    public static String wherePKColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            sql.append(" AND ").append(column.getColumnEqualsHolder());
        }
        sql.append("</where>");
        return sql.toString();
    }

    /**
     * 获取orderBy
     *
     * @param entityClass
     * @return
     */
    public static String orderBy(Class<?> entityClass) {
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

}
