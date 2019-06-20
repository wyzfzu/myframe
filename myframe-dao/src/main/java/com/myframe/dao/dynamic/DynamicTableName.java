// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.dao.dynamic;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/7 11:43
 */
public interface DynamicTableName {

    default String getDynamicTableSuffix() {
        return StringUtils.trimToNull(getDynamicSuffix());
    }

    /**
     * 获取动态表名的后缀
     *
     * @return
     */
    String getDynamicSuffix();

    /**
     * 获取动态表名与后缀的分隔符
     * @return
     */
    default String getDynamicSuffixSeparator() {
        return "";
    }
}
