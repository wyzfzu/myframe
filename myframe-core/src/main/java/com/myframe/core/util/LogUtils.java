package com.myframe.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class LogUtils {

    public static Logger get() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        return getLogger(sts[2].getClassName());
    }

    public static Logger getLogger(String className) {
        return LoggerFactory.getLogger(className);
    }

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }
}
