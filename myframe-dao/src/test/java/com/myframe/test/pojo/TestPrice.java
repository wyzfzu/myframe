// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.test.pojo;


import com.myframe.dao.dynamic.DynamicTableName;
import com.myframe.dao.util.IdGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/7 11:57
 */
@Table(name = "t_test_price")
public class TestPrice implements Serializable, DynamicTableName {
    @Id
    @GeneratedValue(generator = IdGenerator.JDBC)
    private Long id;
    private Integer priceDate;
    private Integer price;
    @Column(updatable = false)
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Integer priceDate) {
        this.priceDate = priceDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getDynamicSuffix() {
        return getPriceDate().toString();
    }

    @Override
    public String getDynamicSuffixSeparator() {
        return "_";
    }
}
