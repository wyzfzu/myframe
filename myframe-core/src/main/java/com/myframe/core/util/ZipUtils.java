package com.myframe.core.util;

import jodd.io.FileNameUtil;
import jodd.io.StreamUtil;
import jodd.io.ZipBuilder;
import jodd.io.ZipUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;

/**
 * 压缩解压缩工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class ZipUtils {

    public static void gzip(String srcPath) {
        gzip(new File(srcPath));
    }

    public static void gzip(File srcFile) {
        try {
            ZipUtil.gzip(srcFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void gzip(String srcPath, String destPath) {
        gzip(new File(srcPath), new File(destPath));
    }

    public static void gzip(File srcFile, File destFile) {
        try {
            if (srcFile.isDirectory()) {
                throw new IOException("Can't gzip folder");
            }
            FileInputStream fis = new FileInputStream(srcFile);
            GZIPOutputStream gzos = new GZIPOutputStream(new FileOutputStream(destFile));
            try {
                StreamUtil.copy(fis, gzos);
            } finally {
                StreamUtil.close(gzos);
                StreamUtil.close(fis);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void zip(String srcPath) {
        zip(new File(srcPath));
    }

    public static void zip(File srcFile) {
        try {
            ZipUtil.zip(srcFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void zip(String srcPath, String destPath) {
        zip(new File(srcPath), new File(destPath));
    }

    public static void zip(File srcFile, File destFile) {
        try {
            ZipBuilder.createZipFile(destFile).add(srcFile).recursive().save();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void ungzip(String gzipFile) {
        ungzip(new File(gzipFile));
    }

    public static void ungzip(File gzipFile) {
        try {
            ZipUtil.ungzip(gzipFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void ungzip(String gzipFile, String destFile, String... patterns) {
        ungzip(new File(gzipFile), new File(destFile), patterns);
    }

    public static void ungzip(File gzipFile, File destFile, String... patterns) {
        try {
            String outFileName = FileNameUtil.removeExtension(destFile.getAbsolutePath());
            File out = new File(outFileName);
            if (!out.createNewFile()) {
                throw new RuntimeException("创建文件[" + outFileName + "]失败！");
            }

            FileOutputStream fos = new FileOutputStream(out);
            GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(gzipFile));
            try {
                StreamUtil.copy(gzis, fos);
            } finally {
                StreamUtil.close(fos);
                StreamUtil.close(gzis);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void unzip(String zipFile) {
        ungzip(new File(zipFile));
    }

    public static void unzip(File zipFile) {
        try {
            ZipUtil.ungzip(zipFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void unzip(String zipFile, String destDir, String... patterns) {
        unzip(new File(zipFile), new File(destDir), patterns);
    }

    public static void unzip(File zipFile, File destDir, String... patterns) {
        try {
            ZipUtil.unzip(zipFile, destDir, patterns);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
