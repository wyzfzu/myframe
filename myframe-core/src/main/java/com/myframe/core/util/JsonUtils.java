package com.myframe.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * JSON工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class JsonUtils {
    private static final Logger logger = LogUtils.get();

    private static JSONObject toJson(Object bean, boolean include, String... fields) {
        if (bean == null) {
            return new JSONObject();
        }

        if (ArrayUtils.isEmpty(fields)) {
            return (JSONObject) JSONObject.toJSON(bean);
        }

        Method[] methods = ReflectUtils.getAccessibleMethods(bean.getClass());
        Set<String> fieldSet = Sets.newHashSet(fields);
        JSONObject json = new JSONObject();
        try {
            for (Method method : methods) {
                String name = method.getName();
                if (name.startsWith("get")) {
                    name = StringUtils.removeStart(name, "get");
                } else if (name.startsWith("is")) {
                    name = StringUtils.removeStart(name, "is");
                } else {
                    continue;
                }
                name = StringUtils.uncapitalize(name);
                if (include) {
                    if (fieldSet.contains(name)) {
                        json.put(name, method.invoke(bean));
                    }
                } else {
                    if (!fieldSet.contains(name)) {
                        json.put(name, method.invoke(bean));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("对象转换为json失败：" + e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将javabean对象转换成json对象
     * @param bean 对象
     * @param fields 需要转换的字段
     * @return JSON对象
     */
    public static JSONObject toJson(Object bean, String... fields) {
        return toJson(bean, true, fields);
    }

    /**
     * 将javabean对象转换成json对象
     * @param bean 对象
     * @param fields 需要排除的字段
     * @return JSON对象
     */
    public static JSONObject toJsonExclude(Object bean, String... fields) {
        return toJson(bean, false, fields);
    }

    private static <T> JSONArray toJsonArray(List<T> datas, boolean include, String... fields) {
        if (CollectUtils.isEmpty(datas)) {
            return new JSONArray();
        }
        if (ArrayUtils.isEmpty(fields)) {
            return (JSONArray) JSONArray.toJSON(datas);
        }

        JSONArray arr = new JSONArray();

        for (T data : datas) {
            arr.add(toJson(data, include, fields));
        }

        return arr;
    }

    public static <T> JSONArray toJsonArray(List<T> datas, String... fields) {
        return toJsonArray(datas, true, fields);
    }

    public static <T> JSONArray toJsonArrayExclude(List<T> datas, String... fields) {
        return toJsonArray(datas, false, fields);
    }

}
