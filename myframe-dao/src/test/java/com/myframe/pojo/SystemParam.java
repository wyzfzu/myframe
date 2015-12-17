// Copyright (C) 2015 Meituan
// All rights reserved
package com.myframe.pojo;

import com.myframe.dao.orm.annotation.Column;
import com.myframe.dao.orm.annotation.Id;
import com.myframe.dao.orm.annotation.Table;

import java.io.Serializable;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 15/9/29 19:10
 */
@Table(name = "t_system_param")
public class SystemParam implements Serializable {
    @Id(name="id")
    private Integer id;
    @Column(name = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
