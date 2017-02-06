// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.dao.orm.mybatis.mapper;

import com.myframe.dao.orm.mybatis.provider.InsertSqlProvider;
import org.apache.ibatis.annotations.InsertProvider;

import java.util.List;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/2 23:47
 */
public interface BaseInsertMapper<T> {
    @InsertProvider(type = InsertSqlProvider.class, method = "dynamicSQL")
    int insert(T bean);

    @InsertProvider(type = InsertSqlProvider.class, method = "dynamicSQL")
    int insertList(List<T> beans);
}
