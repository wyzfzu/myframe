package com.myframe.generator.config;

import java.io.Serializable;

/**
 * mapper配置文件生成配置。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class SqlMapConfig implements Serializable {
    private String targetPackage;
    private String targetDir;
    private String resourceDir;

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

    public String getResourceDir() {
        return resourceDir;
    }

    public void setResourceDir(String resourceDir) {
        this.resourceDir = resourceDir;
    }
}
