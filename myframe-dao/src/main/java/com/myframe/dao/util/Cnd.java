package com.myframe.dao.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.myframe.core.util.CollectUtils;
import com.myframe.core.util.Pair;
import com.myframe.core.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 查询条件工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class Cnd {
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

    public Cnd() {
        orderCriteria = Lists.newArrayList();
        cri = Criteria.create();
        orderCriteria.add(cri);
        extra = Maps.newHashMap();
        orders = Lists.newArrayList();
        includeFields = Sets.newHashSet();
        excludeFields = Sets.newHashSet();
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

    public static Cnd whereIn(String field, List<Object> values) {
        return where().andIn(field, values);
    }

    public static Cnd whereNotIn(String field, Object... values) {
        return where().andNotIn(field, values);
    }

    public static Cnd whereNotIn(String field, List<Object> values) {
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
        return where().andNotBetween(field, value, secondValue);
    }

    public static Cnd whereNotBetween(String field, Object value, Object secondValue) {
        return where().andNotBetween(field, value, secondValue);
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
        if (CollectUtils.isNotEmpty(params)) {
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

    public Cnd andIn(String field, List<Object> values) {
        cri.andIn(field, values);
        return this;
    }

    public Cnd andNotIn(String field, List<Object> values) {
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
        cri.andBetween(field, value1, value2);
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
        if (CollectUtils.isNotEmpty(fields)) {
            fields.forEach(field -> includeFields.add(StringUtils.fromCamelCase(field, '_').toLowerCase()));
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
        if (CollectUtils.isNotEmpty(fields)) {
            fields.forEach(field -> excludeFields.add(StringUtils.fromCamelCase(field, '_').toLowerCase()));
        }
        return this;
    }

    public Cnd desc(String field) {
        return orderBy(field, OrderBy.desc);
    }

    public Cnd asc(String field) {
        return orderBy(field, OrderBy.asc);
    }

    public Cnd orderBy(String field, OrderBy ob) {
        String col = StringUtils.fromCamelCase(field, '_').toLowerCase();
        orders.add(Pair.create(col, ob.getValue()));
        orderBy = true;
        return this;
    }

    public boolean isOrderBy() {
        return orderBy;
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

    public String getDynamicSuffix() {
        return dynamicSuffix;
    }

    public boolean isDynamicTable() {
        return StringUtils.isNoneEmpty(dynamicSuffix);
    }

    public void setDynamicSuffix(String dynamicSuffix) {
        this.dynamicSuffix = dynamicSuffix;
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
        this.includeFields = includeFields;
    }

    public Set<String> getExcludeFields() {
        return excludeFields;
    }

    public void setExcludeFields(Set<String> excludeFields) {
        this.excludeFields = excludeFields;
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

    public String toCndString(Cnd cnd) {
        StringBuilder sqlBuf = new StringBuilder();
        List<Criteria> cris = cnd.getOrderCriteria();
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
        if (sqlBuf.length() > 0) {
            sqlBuf.delete(sqlBuf.length() - 4, sqlBuf.length());
        }
        if (cnd.getInnerCnd() != null) {
            sqlBuf.append(" AND (");
            sqlBuf.append(toCndString(cnd.getInnerCnd()));
            sqlBuf.append(")");
        }

        return sqlBuf.toString();
    }

    /**
     * 只用于方便调试。
     *
     * @return
     */
    public String toCndString() {
        StringBuilder sqlBuf = new StringBuilder("SELECT ");
        if (isDistinct()) {
            sqlBuf.append(" DISTINCT ");
        }
        sqlBuf.append(" * FROM ${TABLE_NAME} WHERE ");

        sqlBuf.append(toCndString(this));

        String sql = sqlBuf.toString();
        if (sql.endsWith(" OR ")) {
            sqlBuf.delete(sqlBuf.length() - 4, sqlBuf.length());
        }
        if (CollectUtils.isNotEmpty(orders)) {
            sqlBuf.append(" order by");
            for (Pair<String, String> od : orders) {
                sqlBuf.append(" ").append(od.getKey())
                        .append(" ").append(od.getValue());
            }
        }
        sqlBuf.append("\n").append(getExtra().toString());
        return sqlBuf.toString();
    }
}