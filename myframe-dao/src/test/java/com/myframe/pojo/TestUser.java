package com.myframe.pojo;

import com.myframe.dao.orm.annotation.Column;
import com.myframe.dao.orm.annotation.Id;
import com.myframe.dao.orm.annotation.QueryExclude;
import com.myframe.dao.orm.annotation.Table;
import com.myframe.dao.orm.annotation.UpdateExclude;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 */
@Table(name = "t_test_user")
public class TestUser implements Serializable {
    /**
     * 主键
     */
    @Id(name = "id")
    private Long id;
    /**
     * 用户名
     */
    @Column(name = "name")
    private String userName;
    /**
     * 密码
     */
    @Column
    @QueryExclude
    private String password;
    /**
     * 年龄
     */
    @Column
    private Integer age;
    /**
     * 性别
     */
    @Column
    private Short gender;
    /**
     * 生日
     */
    @Column
    private Date birthday;
    /**
     * 创建日期
     */
    @Column
    @UpdateExclude
    @QueryExclude
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
