// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.dao.orm.mybatis.mapper;

import com.myframe.dao.orm.mybatis.provider.SelectSqlProvider;
import com.myframe.dao.util.Cnd;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.List;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/2 23:47
 */
public interface BaseSelectMapper<T> {

    @SelectProvider(type = SelectSqlProvider.class, method = "dynamicSQL")
    T getById(Serializable id);

    @SelectProvider(type = SelectSqlProvider.class, method = "dynamicSQL")
    T get(Cnd cnd);

    @SelectProvider(type = SelectSqlProvider.class, method = "dynamicSQL")
    List<T> getAll();

    @SelectProvider(type = SelectSqlProvider.class, method = "dynamicSQL")
    List<T> getList(Cnd cnd);

    @SelectProvider(type = SelectSqlProvider.class, method = "dynamicSQL")
    List<T> getPageList(Cnd cnd, RowBounds rowBounds);

    @SelectProvider(type = SelectSqlProvider.class, method = "dynamicSQL")
    Integer getCount(Cnd cnd);

    @SelectProvider(type = SelectSqlProvider.class, method = "dynamicSQL")
    Integer getAllCount();
}
