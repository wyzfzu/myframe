package com.myframe.dao.util;

/**
 * 聚合类型
 *
 * @author wuyuzhen
 * @version 1.0
 * @created 18/5/11
 */
public enum AggType {
    COUNT("COUNT"),
    MIN("MIN"),
    MAX("MAX"),
    SUM("SUM"),
    AVG("AVG");

    private String name;

    AggType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
