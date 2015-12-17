// Copyright (C) 2015 Meituan
// All rights reserved
package com.myframe.util;

import com.myframe.dao.util.Cnd;
import org.junit.Test;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 15/9/1 10:41
 */
public class CndTest {

    @Test
    public void testToCndString() {
        Cnd idCnd = Cnd.whereIn("id", 1, 2, 3).or().andGT("age", 21).andLike("userName", "'%wyz%'", true);
        System.out.println(idCnd.toCndString());
        Cnd cnd = Cnd.whereGE("createTime", "2015-08-24").and(idCnd);
        System.out.println(cnd.toCndString());
    }
}
