package com.myframe.core.util;

import java.util.*;

/**
 * 集合工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class CollectUtils {

    public static <T> boolean isEmpty(Collection<T> collect) {
        return collect == null || collect.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> collect) {
        return !isEmpty(collect);
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }

    public static <T> T[] toArray(Collection<T> collect, Class<T[]> clazz) {
        if (collect == null) {
            throw new RuntimeException("参数不能为null!");
        }
        if (clazz == null) {
            return null;
        }
        Object[] data = collect.toArray();
        return Arrays.copyOf(data, data.length, clazz);
    }

    public static <T> boolean containsElement(Collection<T> collect, T elem) {
        if (elem == null || isEmpty(collect)) {
            return false;
        }
        return collect.contains(elem);
    }

    public static <K, V> Pair<K, V> firstElement(Map<K, V> map) {
        if (isEmpty(map)) {
            return null;
        }
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        if (it.hasNext()) {
            Map.Entry<K, V> entry = it.next();
            return Pair.create(entry.getKey(), entry.getValue());
        }
        return null;
    }

}
