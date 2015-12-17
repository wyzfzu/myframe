package com.myframe.core.util;

import jodd.util.ObjectUtil;

import java.io.*;

/**
 * 对象工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class ObjectUtils {

    public static <T> T clone(T src) {
        try {
            return (T) ObjectUtil.clone(src);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static <T> T deepClone(T src) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(src);

            //将流序列化成对象
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);

            return (T) ois.readObject();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            StreamUtils.closeQuietly(oos);
            StreamUtils.closeQuietly(ois);
        }
    }
}
