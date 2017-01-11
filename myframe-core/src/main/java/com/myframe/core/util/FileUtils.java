package com.myframe.core.util;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 文件操作工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class FileUtils {

    public static BufferedReader newReader(File file, Charset charset) {
        try {
            return Files.newReader(file, charset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedInputStream newInputStream(File file) {
        try {
            return new BufferedInputStream(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedWriter newWriter(File file, Charset charset) {
        try {
            return Files.newWriter(file, charset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedOutputStream newOutputStream(File file) {
        try {
            return new BufferedOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readAll(File file) {
        return readAll(file, Encoding.UTF8);
    }

    public static String readAll(File file, Charset charset) {
        try {
            return Files.toString(file, charset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readLines(File file) {
        return readLines(file, Encoding.UTF8);
    }

    public static List<String> readLines(File file, Charset charset) {
        try {
            return Files.readLines(file, charset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(String content, File to) {
        try {
            Files.write(content, to, Encoding.UTF8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(String content, File to, Charset charset) {
        try {
            Files.write(content, to, charset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(byte[] content, File to) {
        try {
            Files.write(content, to);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyFile(File from, File to) {
        try {
            Files.copy(from, to);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyFile(File from, OutputStream to) {
        try {
            Files.copy(from, to);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyFileToDir(String from, String to) {
        copyFileToDir(new File(from), new File(to));
    }

    public static void copyFileToDir(File from, File to) {
        try {
            org.apache.commons.io.FileUtils.copyFileToDirectory(from, to);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void moveFile(File from, File to) {
        try {
            Files.move(from, to);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void moveDir(File from, File to) {
        try {
            org.apache.commons.io.FileUtils.moveDirectoryToDirectory(from, to, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isExists(File file) {
        return file != null && file.exists();
    }

    public static boolean isExists(String fileName) {
        return StringUtils.isNotEmpty(fileName) && new File(fileName).exists();
    }

    public static File createFile(File file) {
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new RuntimeException("创建文件" + file.getName() + "失败！");
                }
            }
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File createFile(String fileName) {
        return createFile(new File(fileName));
    }

    public static File createDir(File dir) {
        try {
            org.apache.commons.io.FileUtils.forceMkdir(dir);
            return dir;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File createDir(String dirName) {
        return createDir(new File(dirName));
    }

    public static boolean deleteFile(String fileName) {
        return deleteFile(new File(fileName));
    }

    public static boolean deleteFile(File file) {
        return isExists(file) && file.isFile() && file.delete();
    }

    public static void deleteDir(String dir) {
        deleteDir(new File(dir));
    }

    public static void deleteDir(File dir) {
        try {
            org.apache.commons.io.FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanDir(String dir) {
        cleanDir(new File(dir));
    }

    public static void cleanDir(File dir) {
        try {
            org.apache.commons.io.FileUtils.cleanDirectory(dir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean equals(String file1, String file2) {
        return equals(new File(file1), new File(file2));
    }

    public static boolean equals(File file1, File file2) {
        try {
            return org.apache.commons.io.FileUtils.contentEqualsIgnoreEOL(file1, file2, Encoding.S_UTF8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<File> listFiles(String dir) {
        return listFiles(new File(dir));
    }

    public static List<File> listFiles(File dir) {
        return Lists.newArrayList(iterateChildren(dir));
    }

    public static Iterable<File> iterateChildren(String dir) {
        return iterateChildren(new File(dir));
    }

    public static Iterable<File> iterateChildren(File dir) {
        return Files.fileTreeTraverser().children(dir);
    }
}
