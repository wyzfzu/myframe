package com.myframe.core.util;

import java.io.*;
import java.util.Properties;

/**
 * 属性文件工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class PropUtils {

    public static Properties load(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            throw new IllegalArgumentException("文件[" + fileName + "]不能为空！");
        }
        return load(ResourceUtils.getResourceStream(fileName));
    }

    public static Properties load(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("文件[" + file.getAbsolutePath() + "]不存在！");
        }
        try {
            return load(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Properties load(InputStream is) {
        return load(new InputStreamReader(is, Encoding.UTF8));
    }

    public static Properties load(Reader reader) {
        Properties prop = new Properties();
        try {
            prop.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            StreamUtils.closeQuietly(reader);
        }
        return prop;
    }

    public static String getString(Properties prop, String key) {
        return prop.getProperty(key);
    }

    public static String getString(Properties prop, String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

    public static int getInt(Properties prop, String key) {
        return Integer.parseInt(getString(prop, key));
    }

    public static int getInt(Properties prop, String key, int defaultValue) {
        return Integer.parseInt(getString(prop, key, String.valueOf(defaultValue)));
    }

    public static long getLong(Properties prop, String key) {
        return Long.parseLong(getString(prop, key));
    }

    public static long getLong(Properties prop, String key, long defaultValue) {
        return Long.parseLong(getString(prop, key, String.valueOf(defaultValue)));
    }

    public static short getShort(Properties prop, String key) {
        return Short.parseShort(getString(prop, key));
    }

    public static short getShort(Properties prop, String key, short defaultValue) {
        return Short.parseShort(getString(prop, key, String.valueOf(defaultValue)));
    }

    public static boolean getBoolean(Properties prop, String key) {
        return isBoolean(getString(prop, key));
    }

    public static boolean getBoolean(Properties prop, String key, boolean defaultValue) {
        return isBoolean(getString(prop, key, String.valueOf(defaultValue)));
    }

    private static boolean isBoolean(String val) {
        String lval = val.toLowerCase();
        return "true".equals(lval) || "1".equals(lval) || "yes".equals(lval);
    }

}
