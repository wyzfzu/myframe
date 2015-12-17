package com.myframe.core.util;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Map工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class MapUtils {

    public static class MapBean<K, V> {
        private Map<K, V> map;

        public static <K, V> MapBean<K, V> create() {
            return new MapBean<K, V>(Maps.<K, V>newHashMap());
        }

        public static <K, V> MapBean<K, V> create(Map<K, V> map) {
            return new MapBean<K, V>(map);
        }

        public MapBean(Map<K, V> map) {
            this.map = map;
        }

        public MapBean<K, V> add(K key, V value) {
            map.put(key, value);
            return this;
        }

        public MapBean<K, V> addAll(Map<K, V> all) {
            if (CollectUtils.isEmpty(all)) {
                return this;
            }
            map.putAll(all);
            return this;
        }

        public MapBean<K, V> remove(K key) {
            map.remove(key);
            return this;
        }

        public MapBean<K, V> clear() {
            map.clear();
            return this;
        }

        public Map<K, V> toMap() {
            return map;
        }
    }

    public static <K, V> MapBean<K, V> makeHashMap(K key, V value) {
        return MapBean.<K, V>create().add(key, value);
    }

    public static <K, V> MapBean<K, V> makeConcurrentMap(K key, V value) {
        return MapBean.create(Maps.<K, V>newConcurrentMap()).add(key, value);
    }
}
