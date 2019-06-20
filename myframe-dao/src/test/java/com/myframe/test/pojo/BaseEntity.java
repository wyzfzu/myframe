// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.test.pojo;

import com.myframe.dao.util.IdGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author cyy
 * @version 1.0
 * @created 2017/7/12 上午11:00
 **/
public class BaseEntity {
    @GeneratedValue(generator = IdGenerator.JDBC)
    @Id
    private Long id;
    @Column(updatable = false)
    private Date createTime;
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
