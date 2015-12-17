package com.myframe.generator.config;

import java.io.Serializable;

/**
 * 生成语句配置项。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class GenerateConfig implements Serializable {
    private boolean all = true;
    private boolean insert = true;
    private boolean delete = true;
    private boolean updateById = true;
    private boolean updateByChain = true;
    private boolean deleteById = true;
    private boolean selectCount = true;
    private boolean selectById = true;
    private boolean selectOne = true;
    private boolean selectList = true;

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isUpdateById() {
        return updateById;
    }

    public void setUpdateById(boolean updateById) {
        this.updateById = updateById;
    }

    public boolean isDeleteById() {
        return deleteById;
    }

    public boolean isUpdateByChain() {
        return updateByChain;
    }

    public void setUpdateByChain(boolean updateByChain) {
        this.updateByChain = updateByChain;
    }

    public void setDeleteById(boolean deleteById) {
        this.deleteById = deleteById;
    }

    public boolean isSelectCount() {
        return selectCount;
    }

    public void setSelectCount(boolean selectCount) {
        this.selectCount = selectCount;
    }

    public boolean isSelectById() {
        return selectById;
    }

    public void setSelectById(boolean selectById) {
        this.selectById = selectById;
    }

    public boolean isSelectOne() {
        return selectOne;
    }

    public void setSelectOne(boolean selectOne) {
        this.selectOne = selectOne;
    }

    public boolean isSelectList() {
        return selectList;
    }

    public void setSelectList(boolean selectList) {
        this.selectList = selectList;
    }
}
