package com.myframe.dao.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * 查询条件工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class Cnd {
    private static final String DEFAULT_DYNAMIC_SUFFIX_SEPARATOR = "";

    private boolean orderBy;
    private boolean distinct;
    private List<Criteria> orderCriteria;
    private Criteria cri;
    private Map<String, Object> extra;
    private List<Pair<String, String>> orders;
    private Cnd innerCnd;
    private Cnd parentCnd = null;
    private Set<String> includeFields;
    private Set<String> excludeFields;
    private String dynamicSuffix = "";
    private String dynamicSuffixSeparator = DEFAULT_DYNAMIC_SUFFIX_SEPARATOR;
    private boolean forceMaster = false;
    private List<Agg> aggs;
    private List<String> groupBys;
    private Having having;
    private static Supplier<String> dynamicTableNameSupplier;

    public Cnd() {
        orderCriteria = Lists.newArrayList();
        cri = Criteria.create();
        orderCriteria.add(cri);
        extra = Maps.newHashMap();
        orders = Lists.newArrayList();
        includeFields = Sets.newHashSet();
        excludeFields = Sets.newHashSet();
        groupBys = Lists.newArrayList();
        aggs = Lists.newArrayList();
    }

    public static void registerDynamicTableNameSuffix(Supplier<String> supplier) {
        if (supplier != null) {
            dynamicTableNameSupplier = supplier;
        }
    }

    public static void unregisterDynamicTableNameSuffix() {
        dynamicTableNameSupplier = null;
    }

    public static Cnd where() {
        return new Cnd();
    }

    public static Cnd whereAnd(Cnd cnd) {
        return where().and(cnd);
    }

    public static Cnd whereIsNull(String field) {
        return where().andIsNull(field);
    }

    public static Cnd whereIsNotNull(String field) {
        return where().andIsNotNull(field);
    }

    public static Cnd whereEQ(String field, Object value) {
        return where().andEQ(field, value);
    }

    public static Cnd whereNEQ(String field, Object value) {
        return where().andNEQ(field, value);
    }

    public static Cnd whereGT(String field, Object value) {
        return where().andGT(field, value);
    }

    public static Cnd whereGE(String field, Object value) {
        return where().andGE(field, value);
    }

    public static Cnd whereLT(String field, Object value) {
        return where().andLT(field, value);
    }

    public static Cnd whereLE(String field, Object value) {
        return where().andLE(field, value);
    }

    public static Cnd whereIn(String field, Object... values) {
        return where().andIn(field, values);
    }

    public static Cnd whereIn(String field, List<?> values) {
        return where().andIn(field, values);
    }

    public static Cnd whereNotIn(String field, Object... values) {
        return where().andNotIn(field, values);
    }

    public static Cnd whereNotIn(String field, List<?> values) {
        return where().andNotIn(field, values);
    }

    public static Cnd whereLike(String field, Object value) {
        return where().andLike(field, value);
    }

    public static Cnd whereNotLike(String field, Object value) {
        return where().andNotLike(field, value);
    }

    public static Cnd whereLike(String field, Object value, boolean ignoreCase) {
        return where().andLike(field, value, ignoreCase);
    }

    public static Cnd whereNotLike(String field, Object value, boolean ignoreCase) {
        return where().andNotLike(field, value, ignoreCase);
    }

    public static Cnd whereBetween(String field, Object value, Object secondValue) {
        return where().andBetween(field, value, secondValue);
    }

    public static Cnd whereNotBetween(String field, Object value, Object secondValue) {
        return where().andNotBetween(field, value, secondValue);
    }

    public static Cnd whereAgg(Agg... agg) {
        return where().agg(agg);
    }

    public static Cnd whereAgg(Collection<Agg> aggs) {
        return where().agg(aggs);
    }

    public Cnd and(Cnd cnd) {
        innerCnd = cnd;
        innerCnd.setParentCnd(this);
        return this;
    }

    public Cnd addExtra(String name, Object value) {
        if (StringUtils.isEmpty(name)) {
            return this;
        }
        extra.put(name, value);
        return this;
    }

    public Cnd addExtra(Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            extra.putAll(params);
        }
        return this;
    }

    public Cnd andIsNull(String field) {
        cri.andIsNull(field);
        return this;
    }

    public Cnd andIsNotNull(String field) {
        cri.andIsNotNull(field);
        return this;
    }

    public Cnd andEQ(String field, Object value) {
        cri.andEQ(field, value);
        return this;
    }

    public Cnd andNEQ(String field, Object value) {
        cri.andNEQ(field, value);
        return this;
    }

    public Cnd andGT(String field, Object value) {
        cri.andGT(field, value);
        return this;
    }

    public Cnd andGE(String field, Object value) {
        cri.andGE(field, value);
        return this;
    }

    public Cnd andLT(String field, Object value) {
        cri.andLT(field, value);
        return this;
    }

    public Cnd andLE(String field, Object value) {
        cri.andLE(field, value);
        return this;
    }

    public Cnd andIn(String field, List<?> values) {
        cri.andIn(field, values);
        return this;
    }

    public Cnd andNotIn(String field, List<?> values) {
        cri.andNotIn(field, values);
        return this;
    }

    public Cnd andIn(String field, Object... values) {
        return andIn(field, Arrays.asList(values));
    }

    public Cnd andNotIn(String field, Object... values) {
        return andNotIn(field, Arrays.asList(values));
    }

    public Cnd andBetween(String field, Object value1, Object value2) {
        cri.andBetween(field, value1, value2);
        return this;
    }

    public Cnd andNotBetween(String field, Object value1, Object value2) {
        cri.andNotBetween(field, value1, value2);
        return this;
    }

    public Cnd andLike(String field, Object value) {
        return andLike(field, value, false);
    }

    public Cnd andLike(String field, Object value, boolean ignoreCase) {
        cri.andLike(field, value, ignoreCase);
        return this;
    }

    public Cnd andNotLike(String field, Object value) {
        return andNotLike(field, value, false);
    }

    public Cnd andNotLike(String field, Object value, boolean ignoreCase) {
        cri.andNotLike(field, value, ignoreCase);
        return this;
    }

    public Cnd includes(String... fields) {
        if (fields != null && fields.length > 0) {
            return includes(Arrays.asList(fields));
        }
        return this;
    }

    public Cnd includes(Collection<String> fields) {
        if (fields != null && !fields.isEmpty()) {
            fields.forEach(field -> includeFields.add(FieldHelper.wrapFromCamelCase(field, '_')));
        }
        return this;
    }

    public Cnd excludes(String... fields) {
        if (fields != null && fields.length > 0) {
            return excludes(Arrays.asList(fields));
        }
        return this;
    }

    public Cnd excludes(Collection<String> fields) {
        if (fields != null && !fields.isEmpty()) {
            fields.forEach(field -> excludeFields.add(FieldHelper.wrapFromCamelCase(field, '_')));
        }
        return this;
    }

    public Cnd desc(String field) {
        return orderBy(field, OrderBy.desc, false);
    }

    /**
     * 指定降序排序字段
     *
     * @param field 字段名
     * @param keepOrigin false: 字段名不会做驼峰到下划线的转化, true: 会做转化, 主要用于聚合函数的别名
     * @return
     */
    public Cnd desc(String field, boolean keepOrigin) {
        return orderBy(field, OrderBy.desc, keepOrigin);
    }

    public Cnd asc(String field) {
        return orderBy(field, OrderBy.asc, false);
    }

    /**
     * 指定升序排序字段
     *
     * @param field 字段名
     * @param keepOrigin false: 字段名不会做驼峰到下划线的转化, true: 会做转化, 主要用于聚合函数的别名
     * @return
     */
    public Cnd asc(String field, boolean keepOrigin) {
        return orderBy(field, OrderBy.asc, keepOrigin);
    }

    /**
     * 指定排序字段, 推荐使用{@link Cnd#asc(String)}与{@link Cnd#desc(String)}
     *
     * @param field 字段名
     * @param ob 排序规则
     * @return
     */
    private Cnd orderBy(String field, OrderBy ob) {
        return orderBy(field, ob, false);
    }

    /**
     * 指定排序字段, 推荐使用{@link Cnd#asc(String, boolean)}与{@link Cnd#desc(String, boolean)}
     *
     * @param field 字段名
     * @param ob 排序规则
     * @return
     */
    private Cnd orderBy(String field, OrderBy ob, boolean keepOrigin) {
        String col = keepOrigin ? field : FieldHelper.wrapFromCamelCase(field, '_');
        orders.add(Pair.create(col, ob.getValue()));
        orderBy = true;
        return this;
    }

    public boolean isOrderBy() {
        return orderBy;
    }

    public Cnd groupBy(String... fields) {
        if (fields != null && fields.length > 0) {
            return groupBy(Arrays.asList(fields));
        }

        return this;
    }

    public Cnd groupBy(Collection<String> fields) {
        if (fields == null || fields.isEmpty()) {
            return this;
        }

        fields.forEach(field -> groupBys.add(FieldHelper.wrapFromCamelCase(field, '_')));

        return this;
    }

    public boolean isGroupBy() {
        return !groupBys.isEmpty();
    }

    public Cnd having(Having having) {
        this.having = having;
        return this;
    }

    public Cnd agg(Agg... agg) {
        return agg(Arrays.asList(agg));
    }

    public Cnd agg(Collection<Agg> aggs) {
        if (aggs != null && !aggs.isEmpty()) {
            this.aggs.addAll(aggs);
        }

        return this;
    }

    public Cnd distinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public Cnd dynamicSuffix(String dynamicSuffix) {
        if (StringUtils.isNotEmpty(dynamicSuffix)) {
            setDynamicSuffix(dynamicSuffix);
        }
        return this;
    }

    public Cnd dynamicSuffixSeparator(String dynamicSuffixSeparator) {
        if (dynamicSuffixSeparator != null) {
            setDynamicSuffixSeparator(dynamicSuffixSeparator);
        }
        return this;
    }

    public String getDynamicSuffix() {
        if (StringUtils.isNotEmpty(dynamicSuffix)) {
            return dynamicSuffix;
        }
        if (dynamicTableNameSupplier != null) {
            dynamicSuffix = dynamicTableNameSupplier.get();
            return dynamicSuffix;
        }
        return null;
    }

    public String getDynamicSuffixSeparator() {
        return StringUtils.defaultString(dynamicSuffixSeparator, DEFAULT_DYNAMIC_SUFFIX_SEPARATOR);
    }

    public boolean isDynamicTable() {
        return StringUtils.isNotEmpty(getDynamicSuffix());
    }

    public void setDynamicSuffix(String dynamicSuffix) {
        this.dynamicSuffix = StringUtils.trimToNull(dynamicSuffix);
    }

    public void setDynamicSuffixSeparator(String dynamicSuffixSeparator) {
        this.dynamicSuffixSeparator = dynamicSuffixSeparator == null ? DEFAULT_DYNAMIC_SUFFIX_SEPARATOR : dynamicSuffixSeparator;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOrderCriteria() {
        return orderCriteria;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public Cnd getInnerCnd() {
        return innerCnd;
    }

    public Cnd getParentCnd() {
        return parentCnd;
    }

    public Set<String> getIncludeFields() {
        return includeFields;
    }

    public void setIncludeFields(Set<String> includeFields) {
        this.includeFields.clear();
        includes(includeFields);
    }

    public Set<String> getExcludeFields() {
        return excludeFields;
    }

    public void setExcludeFields(Set<String> excludeFields) {
        this.excludeFields.clear();
        excludes(excludeFields);
    }

    public List<Agg> getAggs() {
        return aggs;
    }

    public List<String> getGroupBys() {
        return groupBys;
    }

    private void setParentCnd(Cnd cnd) {
        parentCnd = cnd;
    }

    public Cnd or() {
        // 创建一个新的对象，并加入集合
        cri = Criteria.create();
        orderCriteria.add(cri);
        return this;
    }

    public String toCndString() {
        StringBuilder sqlBuf = new StringBuilder();
        if (!aggs.isEmpty()) {
            sqlBuf.append("SELECT ");
            for (Agg agg : aggs) {
                    sqlBuf.append(agg.getType().getName())
                        .append("(").append(agg.getFieldName()).append(")");
                if (StringUtils.isNotEmpty(agg.getAliasName())) {
                    sqlBuf.append(" AS ").append(agg.getAliasName());
                }
                sqlBuf.append(",");
            }
            sqlBuf.deleteCharAt(sqlBuf.length() - 1);

            sqlBuf.append("\nFROM table\nWHERE 1 = 1 ");
        }

        List<Criteria> cris = this.getOrderCriteria();
        for (Criteria cri : cris) {
            if (!cri.isValid()) {
                continue;
            }
            sqlBuf.append("(");
            StringBuilder subSql = new StringBuilder();
            List<Criterion> criterions = cri.getAllCriteria();
            for (Criterion criterion : criterions) {
                subSql.append(" AND ").append(criterion.getField())
                        .append(" ")
                        .append(criterion.getOp());
                if (criterion.isNullExp()) {
                    // 无需
                } else if (criterion.isBetweenExp()) {
                    subSql.append(" ")
                            .append(criterion.getValue())
                            .append(" AND ")
                            .append(criterion.getSecondValue());
                } else if (criterion.isInExp()) {
                    Object val = criterion.getValue();
                    subSql.append(" (");
                    if (val instanceof Iterator) {
                        subSql.append(StringUtils.join((Iterator)val, ','));
                    } else if (val instanceof Iterable) {
                        subSql.append(StringUtils.join((Iterable)val, ','));
                    } else {
                        subSql.append(StringUtils.join((Object[])val, ','));
                    }
                    subSql.append(")");
                } else {
                    subSql.append(" ").append(criterion.getValue());
                }
            }
            if (subSql.length() > 0) {
                sqlBuf.append(subSql.substring(4)).append(") OR ");
            }
        }
        if (sqlBuf.length() > 0 && aggs.isEmpty()) {
            sqlBuf.delete(sqlBuf.length() - 4, sqlBuf.length());
        }
        if (this.getInnerCnd() != null) {
            sqlBuf.append(" AND (");
            sqlBuf.append(this.getInnerCnd().toCndString());
            sqlBuf.append(")");
        }

        if (isGroupBy()) {
            sqlBuf.append("\nGROUP BY ")
                    .append(StringUtils.join(groupBys, ','));
        }
        if (having != null) {
            Criterion havingCri = having.getCriterion();
            sqlBuf.append("\nHAVING ")
                    .append(having.getAgg().getType().getName())
                    .append("(").append(having.getAgg().getFieldName()).append(") ")
                    .append(havingCri.getOp().getOp());
            if (havingCri.isNullExp()) {
                // 无需
            } else if (havingCri.isBetweenExp()) {
                sqlBuf.append(" ")
                        .append(havingCri.getValue())
                        .append(" AND ")
                        .append(havingCri.getSecondValue());
            } else if (havingCri.isInExp()) {
                Object val = havingCri.getValue();
                sqlBuf.append(" (");
                if (val instanceof Iterator) {
                    sqlBuf.append(StringUtils.join((Iterator)val, ','));
                } else if (val instanceof Iterable) {
                    sqlBuf.append(StringUtils.join((Iterable)val, ','));
                } else {
                    sqlBuf.append(StringUtils.join((Object[])val, ','));
                }
                sqlBuf.append(")");
            } else {
                sqlBuf.append(" ").append(havingCri.getValue());
            }
        }
        if (isOrderBy()) {
            sqlBuf.append("\nORDER BY ");
            StringBuilder od = new StringBuilder(",");
            orders.forEach(p -> {
                od.append(p.getKey()).append(" ").append(p.getValue());
            });
            sqlBuf.append(od.substring(1));
        }
        return sqlBuf.toString();
    }
}