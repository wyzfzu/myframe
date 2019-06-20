// Copyright (C) 2015 Meituan
// All rights reserved
package com.myframe.test.pojo;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 15/8/31 13:28
 **/
@Table(name = "t_test_user")
public class UserEntity extends BaseEntity {
    @Column(name = "user_name")
    private String name;
    private String password;
    @Column(name = "age")
    private Integer userAge;
    private Short gender;
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(getId());
        sb.append(", name='").append(name).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", age=").append(userAge);
        sb.append(", gender=").append(gender);
        sb.append(", birthday=").append(birthday);
        sb.append(", createTime=").append(getCreateTime());
        sb.append(", modifyTime=").append(getModifyTime());
        sb.append('}');
        return sb.toString();
    }
}
