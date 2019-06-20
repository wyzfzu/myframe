package com.myframe.generator.config;

import java.io.Serializable;

/**
 * mapper配置文件生成配置。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class SqlMapConfig implements Serializable {
    private String targetDir;
    private String resourceDir;
    private boolean enable = false;
    private String templateFilePath;

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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getTemplateFilePath() {
        return templateFilePath;
    }

    public void setTemplateFilePath(String templateFilePath) {
        this.templateFilePath = templateFilePath;
    }
}
