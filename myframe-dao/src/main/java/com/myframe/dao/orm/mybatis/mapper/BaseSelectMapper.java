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

    /**
     * 根据主键获取数据,不支持分表
     *
     * @param id
     * @return
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "dynamicSQL")
    T getById(Serializable id);

    /**
     * 根据条件获取单条数据
     *
     * @param cnd
     * @return
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "dynamicSQL")
    T get(Cnd cnd);

    /**
     * 获取所有数据,不支持分表(可使用<code>getList(Cnd cnd)</code>方法)
     *
     * @return
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "dynamicSQL")
    List<T> getAll();

    /**
     * 根据条件获取列表
     *
     * @param cnd
     * @return
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "dynamicSQL")
    List<T> getList(Cnd cnd);

    /**
     * 根据条件获取分页列表
     *
     * @param cnd
     * @param rowBounds
     * @return
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "dynamicSQL")
    List<T> getPageList(Cnd cnd, RowBounds rowBounds);

    /**
     * 根据条件获取数量
     *
     * @param cnd
     * @return
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "dynamicSQL")
    Integer getCount(Cnd cnd);

    /**
     * 获取所有数量, 不支持分表(可使用<code>getCount(Cnd cnd)</code>方法)
     * @return
     */
    @SelectProvider(type = SelectSqlProvider.class, method = "dynamicSQL")
    Integer getAllCount();
}
