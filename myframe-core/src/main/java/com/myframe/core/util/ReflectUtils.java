package com.myframe.core.util;

import com.google.common.base.Optional;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class ReflectUtils {

    private static final Logger logger = LogUtils.get();

    public static final Method[] EMPTY_METHOD_ARRAY = new Method[0];

    public static Method[] getAccessibleMethods(Class<?> clazz) {
        return clazz == null ? EMPTY_METHOD_ARRAY : clazz.getDeclaredMethods();
    }

    public static Optional<Method> getAccessibleMethod(final Object obj,
                                             final String methodName,
                                             final Class<?>... parameterTypes) {
        for (Class<?> c = obj.getClass(); c != Object.class; c = c.getSuperclass()) {
            try {
                Method method = c.getDeclaredMethod(methodName, parameterTypes);
                method.setAccessible(true);
                return Optional.of(method);
            } catch (NoSuchMethodException e) {
                // Method不在当前类定义,继续向上转型
            }
        }
        return Optional.absent();
    }

    public static Optional<Field> getAccessibleField(final Object obj,
                                           final String fieldName) {
        for (Class<?> c = obj.getClass(); c != Object.class; c = c.getSuperclass()) {
            try {
                Field field = c.getDeclaredField(fieldName);
                field.setAccessible(true);
                return Optional.of(field);
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
            }
        }
        return Optional.absent();
    }

    public static Optional<Object> getFieldValue(final Object obj, final String fieldName) {
        Optional<Field> field = getAccessibleField(obj, fieldName);

        if (!field.isPresent()) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + obj + "]");
        }

        try {
            return Optional.fromNullable(field.get().get(obj));
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常", e);
        }
        return Optional.absent();
    }

    public static void setFieldValue(final Object obj, final String fieldName,
                                     final Object value) {
        Optional<Field> field = getAccessibleField(obj, fieldName);

        if (!field.isPresent()) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + obj + "]");
        }

        try {
            field.get().set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常", e);
        }
    }

    public static Object invokeMethod(final Object obj,
                                      final String methodName,
                                      final Class<?>[] parameterTypes,
                                      final Object[] args) {
        Optional<Method> method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (!method.isPresent()) {
            throw new IllegalArgumentException("Could not find method ["
                    + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.get().invoke(obj, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invokeGetter(Object obj, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, new Class[] {},
                new Object[] {});
    }

    public static void invokeSetter(Object obj, String propertyName, Object value) {
        invokeSetter(obj, propertyName, value, null);
    }

    public static void invokeSetter(Object obj, String propertyName,
                                    Object value, Class<?> propertyType) {
        Class<?> type = propertyType != null ? propertyType : value.getClass();
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        invokeMethod(obj, setterMethodName, new Class[] { type },
                new Object[] { value });
    }
}
