package com.myframe.core.util;

import org.slf4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class GenericUtils {
	private static final Logger logger = LogUtils.get();
	
	private GenericUtils() {
		
	}
	
	/**
	 * 获取泛型类型的实际类型。
	 * @param clazz 泛型参数
	 * @param index 类型位置
	 * @return 实际类型
	 */
	public static Class getGenericType(final Class<?> clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn("{}'s superclass not ParameterizedType", clazz.getSimpleName());
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: {}, Size of {}'s Parameterized Type: {}",
					index, clazz.getSimpleName(), params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn("{} not set the actual class on superclass generic parameter",
                    clazz.getSimpleName());
			return Object.class;
		}

		return (Class) params[index];
	}
	
	/**
	 * 获取泛型类型的实际类型。
	 * @param <T> 实际类型
	 * @param clazz 泛型参数
	 * @return 实际类型
	 */
	public static <T> Class<T> getGenericType(final Class<?> clazz) {
		return getGenericType(clazz, 0);
	}
}
