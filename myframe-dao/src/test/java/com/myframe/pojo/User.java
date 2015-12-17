// Copyright (C) 2015 Meituan
// All rights reserved
package com.myframe.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 15/8/31 13:28
 **/
public class User implements Serializable {
    private Long id;
    private String userName;
    private String password;
    private Integer age;
    private Short gender;
    private Date birthday;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Short getGender() {
        return gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
