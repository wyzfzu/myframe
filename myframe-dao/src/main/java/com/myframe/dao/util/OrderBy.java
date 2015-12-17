package com.myframe.dao.util;

/**
 * 排序枚举对象。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public enum OrderBy {
    asc("asc"),
    desc("desc");

    private String value;

    public String getValue() {
        return value;
    }

    OrderBy(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getValue();
    }


}
