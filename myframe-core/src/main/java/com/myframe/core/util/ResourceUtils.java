package com.myframe.core.util;

import java.io.InputStream;
import java.net.URL;

/**
 * 资源工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class ResourceUtils {

    private static ClassLoader getClassLoader() {
        return ResourceUtils.class.getClassLoader();
    }

	public static String getPath(String fileName) {
		return ResourceUtils.class.getResource("/").getPath() + fileName;
	}

    public static InputStream getResourceStream(String fileName) {
        return getClassLoader().getResourceAsStream(fileName);
    }

    public static URL getResource(String name) {
        return getClassLoader().getResource(name);
    }
}
