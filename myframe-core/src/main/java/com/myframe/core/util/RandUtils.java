package com.myframe.core.util;

import java.util.Random;

/**
 * 随机工具类。
 *
 * 部分代码摘抄自jodd的<code>RandomStringUtil</code>。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class RandUtils {

    private static final Random rnd = new Random();

    protected static final char[] ALPHA_RANGE = new char[] {'A', 'Z', 'a', 'z'};
    protected static final char[] ALPHA_NUMERIC_RANGE = new char[] {'0', '9', 'A', 'Z', 'a', 'z'};

    public static int randInt() {
        return rnd.nextInt();
    }

    public static int randInt(int max) {
        return rnd.nextInt(max);
    }

    public static long randLong() {
        return rnd.nextLong();
    }

    public static boolean randBoolean() {
        return rnd.nextBoolean();
    }

    public static double randDouble() {
        return rnd.nextDouble();
    }

    public static float randFloat() {
        return rnd.nextFloat();
    }

    public static String randString(int count) {
        return randAlphaNumeric(count);
    }

    public static String randAscii(int count) {
        return random(count, (char)32, (char)126);
    }

    public static String randAlpha(int count) {
        return randRanges(count, ALPHA_RANGE);
    }

    public static String randNumeric(int count) {
        return random(count, '0', '9');
    }

    public static String randAlphaNumeric(int count) {
        return randRanges(count, ALPHA_NUMERIC_RANGE);
    }

    public static String randString(int count, char[] chars) {
        if (count == 0) {
            return StringUtils.EMPTY;
        }
        char[] result = new char[count];
        while (count-- > 0) {
            result[count] = chars[rnd.nextInt(chars.length)];
        }
        return new String(result);
    }

    public static String randString(int count, String chars) {
        return randString(count, chars.toCharArray());
    }

    public static String random(int count, char start, char end) {
        if (count == 0) {
            return StringUtils.EMPTY;
        }
        char[] result = new char[count];
        int len = end - start + 1;
        while (count-- > 0) {
            result[count] = (char) (rnd.nextInt(len) + start);
        }
        return new String(result);
    }

    public static String randRanges(int count, char... ranges) {
        if (count == 0) {
            return StringUtils.EMPTY;
        }
        int i = 0;
        int len = 0;
        int lens[] = new int[ranges.length];
        while (i < ranges.length) {
            int gap = ranges[i + 1] - ranges[i] + 1;
            len += gap;
            lens[i] = len;
            i += 2;
        }

        char[] result = new char[count];
        while (count-- > 0) {
            char c = 0;
            int r = rnd.nextInt(len);
            for (i = 0; i < ranges.length; i += 2) {
                if (r < lens[i]) {
                    r += ranges[i];
                    if (i != 0) {
                        r -= lens[i - 2];
                    }
                    c = (char) r;
                    break;
                }
            }
            result[count] = c;
        }
        return new String(result);
    }
}
