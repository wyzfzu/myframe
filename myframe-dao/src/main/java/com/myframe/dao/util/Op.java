package com.myframe.dao.util;

/**
 * SQL操作枚举。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
enum Op {
    EQ("="),
    NEQ("<>"),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<="),
    IN("IN"),
    NIN("NOT IN"),
    LIKE("LIKE"),
    NOT_LIKE("NOT LIKE"),
    IS_NULL("IS NULL"),
    IS_NOT_NULL("IS NOT NULL"),
    BETWEEN("BETWEEN"),
    NOT_BETWEEN("NOT BETWEEN")
    ;

    private String op;

    public String getOp() {
        return op;
    }

    @Override
    public String toString() {
        return getOp();
    }

    Op(String op) {
        this.op = op;
    }
}
