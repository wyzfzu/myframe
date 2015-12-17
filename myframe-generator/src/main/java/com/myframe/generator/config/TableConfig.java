package com.myframe.generator.config;

import java.io.Serializable;
import java.util.Set;

/**
 * 要生成的表配置。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class TableConfig implements Serializable {
    private String tablePattern;
    private String trimPrefix;
    private boolean autoIncrement;
    private String priority;
    private String caseFormat;
    private Set<String> includes;
    private Set<String> excludes;

    public String getTablePattern() {
        return tablePattern;
    }

    public void setTablePattern(String tablePattern) {
        this.tablePattern = tablePattern;
    }

    public String getTrimPrefix() {
        return trimPrefix;
    }

    public String getCaseFormat() {
        return caseFormat;
    }

    public void setCaseFormat(String caseFormat) {
        this.caseFormat = caseFormat;
    }

    public void setTrimPrefix(String trimPrefix) {
        this.trimPrefix = trimPrefix;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Set<String> getIncludes() {
        return includes;
    }

    public void setIncludes(Set<String> includes) {
        this.includes = includes;
    }

    public Set<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(Set<String> excludes) {
        this.excludes = excludes;
    }
}
