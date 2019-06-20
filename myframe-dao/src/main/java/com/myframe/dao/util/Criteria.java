package com.myframe.dao.util;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * 查询对象集合。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class Criteria {
    protected List<Criterion> criteria;

    protected Criteria() {
        criteria = Lists.newArrayList();
    }

    public static Criteria create() {
        return new Criteria();
    }

    public boolean isValid() {
        return !criteria.isEmpty();
    }

    public List<Criterion> getAllCriteria() {
        return criteria;
    }

    protected void addCriterion(String field, Op op) {
        if (field == null) {
            throw new RuntimeException("field for condition cannot be null");
        }
        criteria.add(new Criterion(field, op));
    }

    protected void addCriterion(String field, Op op, Object value) {
        if (value == null) {
            throw new RuntimeException("Value for " + field + " cannot be null");
        }
        criteria.add(new Criterion(field, op, value));
    }

    protected void addCriterion(String field, Op op, Object value, boolean ignoreCase) {
        if (value == null) {
            throw new RuntimeException("Value for " + field + " cannot be null");
        }
        criteria.add(new Criterion(field, op, value, ignoreCase));
    }

    protected void addCriterion(String field, Op op, Object value1, Object value2) {
        if (value1 == null || value2 == null) {
            throw new RuntimeException("Between values for " + field + " cannot be null");
        }
        criteria.add(new Criterion(field, op, value1, value2));
    }

    protected void addCriterion(String field, Op op, Object value1, Object value2, boolean ignoreCase) {
        if (value1 == null || value2 == null) {
            throw new RuntimeException("Between values for " + field + " cannot be null");
        }
        criteria.add(new Criterion(field, op, value1, value2, ignoreCase));
    }

    public Criteria andIsNull(String field) {
        addCriterion(field, Op.IS_NULL);
        return this;
    }

    public Criteria andIsNotNull(String field) {
        addCriterion(field, Op.IS_NOT_NULL);
        return this;
    }

    public Criteria andEQ(String field, Object value) {
        addCriterion(field, Op.EQ, value);
        return this;
    }

    public Criteria andNEQ(String field, Object value) {
        addCriterion(field, Op.NEQ, value);
        return this;
    }

    public Criteria andGT(String field, Object value) {
        addCriterion(field, Op.GT, value);
        return this;
    }

    public Criteria andGE(String field, Object value) {
        addCriterion(field, Op.GE, value);
        return this;
    }

    public Criteria andLT(String field, Object value) {
        addCriterion(field, Op.LT, value);
        return this;
    }

    public Criteria andLE(String field, Object value) {
        addCriterion(field, Op.LE, value);
        return this;
    }

    public Criteria andIn(String field, List<?> values) {
        addCriterion(field, Op.IN, values);
        return this;
    }

    public Criteria andNotIn(String field, List<?> values) {
        addCriterion(field, Op.NIN, values);
        return this;
    }

    public Criteria andIn(String field, Object... values) {
        return andIn(field, Arrays.asList(values));
    }

    public Criteria andNotIn(String field, Object... values) {
        return andNotIn(field, Arrays.asList(values));
    }

    public Criteria andBetween(String field, Object value1, Object value2) {
        addCriterion(field, Op.BETWEEN, value1, value2);
        return this;
    }

    public Criteria andNotBetween(String field, Object value1, Object value2) {
        addCriterion(field, Op.NOT_BETWEEN, value1, value2);
        return this;
    }

    public Criteria andLike(String field, Object value) {
        return andLike(field, value, false);
    }

    public Criteria andLike(String field, Object value, boolean ignoreCase) {
        if (ignoreCase) {
            addCriterion(field, Op.LIKE, value, true);
        } else {
            addCriterion(field, Op.LIKE, value);
        }
        return this;
    }

    public Criteria andNotLike(String field, Object value) {
        return andNotLike(field, value, false);
    }

    public Criteria andNotLike(String field, Object value, boolean ignoreCase) {
        if (ignoreCase) {
            addCriterion(field , Op.NOT_LIKE, value, true);
        } else {
            addCriterion(field, Op.NOT_LIKE, value);
        }
        return this;
    }
}
