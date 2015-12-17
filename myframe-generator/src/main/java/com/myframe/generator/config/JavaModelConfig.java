package com.myframe.generator.config;

import java.io.Serializable;

/**
 * model对象路径配置。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class JavaModelConfig implements Serializable {
    private String targetPackage;
    private String targetDir;

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
}
