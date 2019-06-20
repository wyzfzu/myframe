
package com.myframe.dao.util;

import com.myframe.dao.dynamic.DynamicTableName;

/**
 * OGNL静态方法
 *
 * @author liuzh
 */
public abstract class OGNL {
    public static boolean isDynamicInterface(Object parameter) {
        return parameter instanceof DynamicTableName;
    }

    public static boolean isDynamicCnd(Object parameter) {
        return parameter instanceof Cnd
                && ((Cnd) parameter).isDynamicTable();
    }

    public static boolean isDynamicUpdateChain(Object parameter) {
        if (parameter instanceof UpdateChain) {
            UpdateChain uc = (UpdateChain) parameter;
            return uc.getCnd().isDynamicTable();
        }
        return false;
    }

    /**
     * 判断参数是否支持动态表名
     *
     * @param parameter
     * @return true支持，false不支持
     */
    public static boolean isDynamicTable(Object parameter) {
        return parameter != null
                && (isDynamicInterface(parameter)
                || isDynamicCnd(parameter)
                || isDynamicUpdateChain(parameter));
    }

    /**
     * 判断参数是否支持动态表名
     *
     * @param parameter
     * @return true支持，false不支持
     */
    public static boolean isNotDynamicTable(Object parameter) {
        return !isDynamicTable(parameter);
    }
}
