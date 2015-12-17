package com.myframe.core.util;

import org.apache.commons.io.FilenameUtils;

import java.util.Collection;

/**
 * 文件名工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class FileNameUtils {

    public static String getFileExt(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }

    public static String getFileWithoutExt(String fileName) {
        return FilenameUtils.removeExtension(fileName);
    }

    public static String getFileName(String fileName) {
        return FilenameUtils.getName(fileName);
    }

    public static String getFilePath(String fileName) {
        return FilenameUtils.getFullPath(fileName);
    }

    public static String concat(String fileName, String... appends) {
        if (ArrayUtils.isEmpty(appends)) {
            return fileName;
        }
        String name = fileName;
        for (String ap : appends) {
            name = FilenameUtils.concat(name, ap);
        }
        return name;
    }

    public static boolean isExtension(String fileName, String... exts) {
        return FilenameUtils.isExtension(fileName, exts);
    }

    public static boolean isExtension(String fileName, Collection<String> exts) {
        return FilenameUtils.isExtension(fileName, exts);
    }

    public static String normalize(String fileName) {
        return FilenameUtils.normalize(fileName);
    }
}
