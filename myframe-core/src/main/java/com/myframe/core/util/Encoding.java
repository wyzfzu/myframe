package com.myframe.core.util;

import java.nio.charset.Charset;

/**
 * 编码常量。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class Encoding {
    public static final String S_UTF8 = "UTF-8";
    public static final String S_UTF16 = "UTF-16";
    public static final String S_GBK = "GBK";
    public static final String S_GB2312 = "GB2312";
    public static final String S_ISO_88591 = "ISO-8859-1";

    public static final Charset UTF8 = Charset.forName(S_UTF8);
    public static final Charset UTF16 = Charset.forName(S_UTF16);
    public static final Charset GBK = Charset.forName(S_GBK);
    public static final Charset GB2312 = Charset.forName(S_GB2312);
    public static final Charset ISO_8859_1 = Charset.forName(S_ISO_88591);
}
