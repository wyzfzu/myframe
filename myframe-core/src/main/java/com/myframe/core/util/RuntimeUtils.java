package com.myframe.core.util;

import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 运行时工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class RuntimeUtils {
	
	private static final Logger logger = LogUtils.get();

	public static final String DEFAULT_LOCK_FILE = "~default.lock";

	public static void exec(String exec) {
        try {
            Runtime.getRuntime().exec(exec);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}

	public static boolean checkLock(String processTmpFile) {
        File file = new File(processTmpFile);
		return !file.exists() || file.delete();
	}

    public static void lock() {
        lock(DEFAULT_LOCK_FILE);
    }

    public static boolean checkLock() {
        return checkLock(DEFAULT_LOCK_FILE);
    }

	public static void lock(String processTmpFile) {
		try {
            RandomAccessFile raf = new RandomAccessFile(new File(processTmpFile), "rw");
			FileChannel fc = raf.getChannel();
		    fc.tryLock();
		} catch (IOException e) {
			logger.error("加文件锁异常", e);
		}
	}

}
