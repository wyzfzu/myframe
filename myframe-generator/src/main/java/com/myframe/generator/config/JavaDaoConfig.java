package com.myframe.generator.config;

import java.io.Serializable;

/**
 * DAO生成模式和路径配置。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class JavaDaoConfig implements Serializable {
    private String targetPackage;
    private String targetDir;
    private String mode;

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
