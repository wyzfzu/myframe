package com.myframe.core.util;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class RegexUtils {
    // 正则表达式取至网络
    private static final String MOBILE_CM = "^(?:0?1)((?:3[56789]|5[0124789]|8[278])\\d|34[0-8]|47\\d)\\d{7}$";
    private static final String MOBILE_CU = "^(?:0?1)(?:3[012]|4[5]|5[356]|8[356]\\d|349)\\d{7}$";
    private static final String MOBILE_CE = "^(?:0?1)(?:33|53|8[079])\\d{8}$";
    private static final String MOBILE_CN = "^(?:0?1)[3458]\\d{9}$";
    private static final String EMAIL = "^([A-Z0-9]+[_|\\_|\\.]?)*[A-Z0-9]+@([A-Z0-9]+[_|\\_|\\.]?)*[A-Z0-9]+\\.[A-Z]{2,3}$";
    private static final String IPV4 = "^(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";
    private static final String IPV6 = "^((([0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}:([0-9A-Fa-f]{1,4}:)?[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){4}:([0-9A-Fa-f]{1,4}:){0,2}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){3}:([0-9A-Fa-f]{1,4}:){0,3}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){2}:([0-9A-Fa-f]{1,4}:){0,4}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(([0-9A-Fa-f]{1,4}:){0,5}:((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(::([0-9A-Fa-f]{1,4}:){0,5}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|([0-9A-Fa-f]{1,4}::([0-9A-Fa-f]{1,4}:){0,5}[0-9A-Fa-f]{1,4})|(::([0-9A-Fa-f]{1,4}:){0,6}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:))$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL, Pattern.CASE_INSENSITIVE);
    private static final Pattern IPV4_PATTERN = Pattern.compile(IPV4, Pattern.CASE_INSENSITIVE);
    private static final Pattern IPV6_PATTERN = Pattern.compile(IPV6, Pattern.CASE_INSENSITIVE);
    private static final Pattern MOBILE_CM_PATTERN = Pattern.compile(MOBILE_CM, Pattern.CASE_INSENSITIVE);
    private static final Pattern MOBILE_CU_PATTERN = Pattern.compile(MOBILE_CU, Pattern.CASE_INSENSITIVE);
    private static final Pattern MOBILE_CE_PATTERN = Pattern.compile(MOBILE_CE, Pattern.CASE_INSENSITIVE);
    private static final Pattern MOBILE_CN_PATTERN = Pattern.compile(MOBILE_CN, Pattern.CASE_INSENSITIVE);

    public static boolean isEmail(String email) {
        return StringUtils.isNotEmpty(email)
                && EMAIL_PATTERN.matcher(email).find();
    }

    public static boolean isIpv4(String ip) {
        return StringUtils.isNotEmpty(ip)
                && IPV4_PATTERN.matcher(ip).find();
    }

    public static boolean isIpv6(String ip) {
        return StringUtils.isNotEmpty(ip)
                && IPV6_PATTERN.matcher(ip).find();
    }

    public static boolean isMobile(String mobile) {
        return StringUtils.isNotEmpty(mobile)
                && (
                   MOBILE_CM_PATTERN.matcher(mobile).find()
                || MOBILE_CE_PATTERN.matcher(mobile).find()
                || MOBILE_CU_PATTERN.matcher(mobile).find()
                || MOBILE_CN_PATTERN.matcher(mobile).find()
                );
    }

}
