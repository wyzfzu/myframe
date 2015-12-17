package com.myframe.core.util;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加解密工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class CryptoUtils {

    private static final String AES = "AES";
    private static final String DES = "DES";
    private static final String DES_CIPHER = "DES/CBC/PKCS5Padding";
    private static final int AES_KEY_SIZE = 128;
    private static final String DEFAULT_SEED = "myframe@crypto!key$";
    private static final String DEFAULT_DES_KEY = "@myframe";

    public static String md5(final String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String sha1(final String str) {
        return DigestUtils.sha1Hex(str);
    }

    public static String sha256(final String str) {
        return DigestUtils.sha256Hex(str);
    }

    public static String sha512(final String str) {
        return DigestUtils.sha512Hex(str);
    }

    private static SecretKeySpec generateAesSecretKey(
            final String algo, final String seed,
            final int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algo);
        keyGenerator.init(keySize, new SecureRandom(seed.getBytes(Encoding.UTF8)));
        SecretKey secretKey = keyGenerator.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), algo);
    }

    private static byte[] aesCrypto(final byte[] data, final String algo,
                                    final int mode, final String seed) {
        try {
            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(mode, generateAesSecretKey(algo, seed, AES_KEY_SIZE));
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String aesEncrypt(final String str, final String seed) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        byte[] result = aesCrypto(str.getBytes(Encoding.UTF8),
                AES, Cipher.ENCRYPT_MODE, seed);
        return byte2Hex(result);
    }

    public static String aesDecrypt(final String str, final String seed) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }

        byte[] result = aesCrypto(hex2Byte(str),AES, Cipher.DECRYPT_MODE, seed);
        return new String(result, Encoding.UTF8);
    }

    public static String aesEncrypt(final String str) {
        return aesEncrypt(str, DEFAULT_SEED);
    }

    public static String aesDecrypt(final String str) {
        return aesDecrypt(str, DEFAULT_SEED);
    }

    public static String desEncrypt(final String str, final String key) {
        byte[] result = desCrypto(str.getBytes(Encoding.UTF8), Cipher.ENCRYPT_MODE, key);
        return byte2Hex(result);
    }

    public static String desDecrypt(final String str, final String key) {
        byte[] result = desCrypto(hex2Byte(str), Cipher.DECRYPT_MODE, key);
        return new String(result, Encoding.UTF8);
    }

    public static String desEncrypt(final String str) {
        return desEncrypt(str, DEFAULT_DES_KEY);
    }

    public static String desDecrypt(final String str) {
        return desDecrypt(str, DEFAULT_DES_KEY);
    }

    private static SecretKey generateDesKey(
            final String algo, final byte[] keyBytes) {
        try {
            DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algo);
            return keyFactory.generateSecret(desKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] desCrypto(final byte[] data, final int mode, final String key) {
        if (ArrayUtils.isEmpty(data)) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        try {
            Cipher cipher = Cipher.getInstance(DES_CIPHER);
            byte[] keyBytes = key.getBytes(Encoding.UTF8);
            cipher.init(mode, generateDesKey(DES, keyBytes),
                    new IvParameterSpec(keyBytes));
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String byte2Hex(byte data[]) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private static byte[] hex2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        int len = hexStr.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i< len; i++) {
            int pos = i * 2;
            int high = Integer.parseInt(hexStr.substring(pos, pos + 1), 16);
            int low = Integer.parseInt(hexStr.substring(pos + 1, pos + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
