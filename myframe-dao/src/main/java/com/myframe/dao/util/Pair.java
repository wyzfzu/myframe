package com.myframe.dao.util;

import java.io.Serializable;

/**
 * 键值对。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class Pair<K, V> implements Serializable {
    private K key;
    private V value;

    public static <K, V> Pair<K, V> create(K key, V value) {
        return new Pair<K, V>(key, value);
    }

    public Pair() {

    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair that = (Pair) o;

        Object n1 = getKey();
        Object n2 = that.getKey();

        if (n1 == n2 || (n1 != null && n1.equals(n2))) {
            Object v1 = getValue();
            Object v2 = that.getValue();
            if (v1 == v2 || (v1 != null && v1.equals(v2))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (key == null ? 0 : key.hashCode()) ^
                (value == null ? 0 : value.hashCode());
    }
}
