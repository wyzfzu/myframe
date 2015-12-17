package com.myframe.core.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.zip.CRC32;

/**
 * 编码工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class CodeUtils {

    public static long crc32(final String str) {
        if (StringUtils.isEmpty(str)) {
            return 0L;
        }
        CRC32 crc = new CRC32();
        crc.update(str.getBytes(Encoding.UTF8));
        return crc.getValue();
    }

    public static String urlEncode(final String url) {
        try {
            return URLEncoder.encode(url, Encoding.S_UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String urlDecode(final String url) {
        try {
            return URLDecoder.decode(url, Encoding.S_UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encodeBase64String(final String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        return encodeBase64String(str.getBytes(Encoding.UTF8));
    }

    public static String encodeBase64String(final byte[] data) {
        if (ArrayUtils.isEmpty(data)) {
            return "";
        }
        return Base64.encodeBase64String(data);
    }

    public static byte[] encodeBase64(final byte[] data) {
        if (ArrayUtils.isEmpty(data)) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        return Base64.encodeBase64(data);
    }

    public static byte[] encodeBase64(final String str) {
        if (StringUtils.isEmpty(str)) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        return encodeBase64(str.getBytes(Encoding.UTF8));
    }

    public static String decodeBase64String(final String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        return decodeBase64String(str.getBytes(Encoding.UTF8));
    }

    public static String decodeBase64String(final byte[] data) {
        if (ArrayUtils.isEmpty(data)) {
            return "";
        }
        return new String(decodeBase64(data), Encoding.UTF8);
    }

    public static byte[] decodeBase64(final byte[] data) {
        if (ArrayUtils.isEmpty(data)) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        return Base64.decodeBase64(data);
    }

    public static byte[] decodeBase64(final String str) {
        if (StringUtils.isEmpty(str)) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        return decodeBase64(str.getBytes(Encoding.UTF8));
    }
}
