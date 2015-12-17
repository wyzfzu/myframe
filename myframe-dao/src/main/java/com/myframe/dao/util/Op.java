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
    NOTLIKE("NOT LIKE"),
    ISNULL("IS NULL"),
    ISNOTNULL("IS NOT NULL"),
    BETWEEN("BETWEEN"),
    NOTBETWEEN("NOT BETWEEN")
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
