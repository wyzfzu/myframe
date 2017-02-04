// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.dao.util;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/3 15:40
 */
public class IdGenerator {
    public static final String JDBC = "JDBC";
    public static final String MYSQL = "SELECT LAST_INSERT_ID()";

    private IdGenerator() {
    }
}
