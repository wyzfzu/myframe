package com.myframe.dao.util;

/**
 * 单个查询条件对象。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
class Criterion {
    private String field;
    private Op op;
    private Object value;
    private Object secondValue;
    private boolean ignoreCase;

    protected Criterion(String field, Op op) {
        this(field, op, null);
    }

    protected Criterion(String field, Op op, Object value) {
        this(field, op, value, null);
    }

    protected Criterion(String field, Op op, Object value, boolean ignoreCase) {
        this(field, op, value, null, ignoreCase);
    }

    protected Criterion(String field, Op op, Object value, Object secondValue) {
        this(field, op, value, secondValue, false);
    }

    protected Criterion(String field, Op op, Object value, Object secondValue, boolean ignoreCase) {
        this.field = FieldHelper.wrapFromCamelCase(field);
        this.op = op;
        this.value = value;
        this.secondValue = secondValue;
        this.ignoreCase = ignoreCase;
    }

    public boolean isBetweenExp() {
        return op == Op.BETWEEN || op == Op.NOT_BETWEEN;
    }

    public boolean isInExp() {
        return op == Op.IN || op == Op.NIN;
    }

    public boolean isNullExp() {
        return op == Op.IS_NULL || op == Op.IS_NOT_NULL;
    }

    public boolean isLikeExp() {
        return op == Op.LIKE || op == Op.NOT_LIKE;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Op getOp() {
        return op;
    }

    public void setOp(Op op) {
        this.op = op;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(Object secondValue) {
        this.secondValue = secondValue;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }
}
