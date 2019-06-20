package com.myframe.dao.util;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class AggResult extends HashMap<String, Object> {

    /**
     * 获取对应字段的值。
     *
     * 需要注意无符号类型的字段映射，例如unsigned bigint -> BigInteger
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getColumnValue(String key) {
        if (key == null) {
            return null;
        }

        T result = (T) get(key);
        if (result != null) {
            return result;
        }

        String camelKey = FieldHelper.fromCamelCase(key);
        result = (T) get(camelKey);
        if (result != null) {
            return result;
        }

        result = (T) get(key.toUpperCase());
        if (result != null) {
            return result;
        }
        result = (T) get(camelKey.toUpperCase());

        return result;
    }

    public <K> K toBean(Class<K> beanClass) {
        if (isEmpty()) {
            return null;
        }
        try {
            Constructor beanConstructor = beanClass.getConstructor();
            K bean = (K) beanConstructor.newInstance();
            MetaObject metaObject = SystemMetaObject.forObject(bean);
            this.forEach((k, v) -> {
                metaObject.setValue(FieldHelper.toCamelCase(k.toLowerCase()), v);
            });
            return bean;
        } catch (Exception e) {
            // eat it
        }

        return null;
    }
}
