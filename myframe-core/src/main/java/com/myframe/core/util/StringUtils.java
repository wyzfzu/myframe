package com.myframe.core.util;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 字符串工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String fromCamelCase(String input, char separator) {
        int length = input.length();
        StringBuilder result = new StringBuilder(length * 2);
        int resultLength = 0;
        boolean prevTranslated = false;
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if (i > 0 || c != separator) {// skip first starting separator
                if (Character.isUpperCase(c)) {
                    if (!prevTranslated && resultLength > 0 && result.charAt(resultLength - 1) != separator) {
                        result.append(separator);
                        resultLength++;
                    }
                    c = Character.toLowerCase(c);
                    prevTranslated = true;
                } else {
                    prevTranslated = false;
                }
                result.append(c);
                resultLength++;
            }
        }
        return resultLength > 0 ? result.toString() : input;
    }

    public static String toCamelCase(String input, boolean firstCharUppercase, char separator) {
        int length = input.length();
        StringBuilder sb = new StringBuilder(length);
        boolean upperCase = firstCharUppercase;

        for (int i = 0; i < length; i++) {
            char ch = input.charAt(i);
            if (ch == separator) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(ch));
                upperCase = false;
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public static List<String> splitToList(String str, String split) {
        return Splitter.on(split)
                .omitEmptyStrings()
                .trimResults()
                .splitToList(str);
    }

    public static List<String> splitToList(String str, char splitChart) {
        return Splitter.on(splitChart)
                .omitEmptyStrings()
                .trimResults()
                .splitToList(str);
    }

    public static List<String> splitToList(String str, Pattern splitPattern) {
        return Splitter.on(splitPattern)
                .omitEmptyStrings()
                .trimResults()
                .splitToList(str);
    }

    public static short toShort(String str) {
        return Short.parseShort(str);
    }

    public static short toShort(String str, short defaultValue) {
        try {
            return toShort(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int toInt(String str) {
        return Integer.parseInt(str);
    }

    public static int toInt(String str, int defaultValue) {
        try {
            return toInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long toLong(String str) {
        return Long.parseLong(str);
    }

    public static long toLong(String str, long defaultValue) {
        try {
            return toLong(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static float toFloat(String str) {
        return Float.parseFloat(str);
    }

    public static float toFloat(String str, float defaultValue) {
        try {
            return toFloat(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static double toDouble(String str) {
        return Double.parseDouble(str);
    }

    public static double toDouble(String str, double defaultValue) {
        try {
            return toDouble(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean toBoolean(String str) {
        return Boolean.parseBoolean(str);
    }

    public static boolean toBoolean(String str, boolean defaultValue) {
        try {
            return toBoolean(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
