package com.myframe.core.util;

import java.util.concurrent.TimeUnit;

/**
 * 线程工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class ThreadUtils {

    public static void sleep(long milliSecends) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliSecends);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public static void sleepSecond(long seconds) {
        sleep(seconds * 1000);
    }

    public static void sleepMinute(long minutes) {
        sleepSecond(minutes * 60);
    }

    public static void sleepHour(long hours) {
        sleepMinute(hours * 24);
    }
}
