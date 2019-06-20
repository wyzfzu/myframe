package com.myframe.dao.util;

import java.util.Arrays;
import java.util.List;

public class Having {
    private Agg agg;
    private Criterion criterion;

    public static Having make(Agg agg) {
        return new Having().agg(agg);
    }

    public Having agg(Agg agg) {
        this.agg = agg;
        return this;
    }

    public Agg getAgg() {
        return agg;
    }

    public Criterion getCriterion() {
        return criterion;
    }

    public Having eq(Object value) {
        return op(Op.EQ, value);
    }

    public Having neq(Object value) {
        return op(Op.NEQ, value);
    }

    public Having gt(Object value) {
        return op(Op.GT, value);
    }

    public Having lt(Object value) {
        return op(Op.LT, value);
    }

    public Having gte(Object value) {
        return op(Op.GE, value);
    }

    public Having lte(Object value) {
        return op(Op.LE, value);
    }

    public Having in(List<?> value) {
        return op(Op.IN, value);
    }

    public Having in(Object... value) {
        return op(Op.IN, Arrays.asList(value));
    }

    public Having notIn(List<?> value) {
        return op(Op.NIN, value);
    }

    public Having notIn(Object... value) {
        return op(Op.NIN, Arrays.asList(value));
    }

    public Having isNull(Object value) {
        return op(Op.IS_NULL, value);
    }

    public Having isNotNull(Object value) {
        return op(Op.IS_NOT_NULL, value);
    }

    public Having between(Object value, Object secondValue) {
        return op(Op.BETWEEN, value, secondValue);
    }

    public Having notBetween(Object value, Object secondValue) {
        return op(Op.NOT_BETWEEN, value, secondValue);
    }

    public Having like(Object value) {
        return op(Op.LIKE, value);
    }

    public Having notLike(Object value) {
        return op(Op.NOT_LIKE, value);
    }

    private Having op(Op op, Object value) {
        checkValue(value);
        criterion = new Criterion(agg.getFieldName(), op, value);

        return this;
    }

    private Having op(Op op, Object value, Object secondValue) {
        checkValue(value);
        checkValue(secondValue);
        criterion = new Criterion(agg.getFieldName(), op, value, secondValue);

        return this;
    }

    private void checkValue(Object value) {
        if (value == null) {
            throw new RuntimeException("Value for having operator cannot be null");
        }
    }
}
