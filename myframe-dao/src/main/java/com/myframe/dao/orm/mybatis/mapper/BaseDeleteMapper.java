// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.dao.orm.mybatis.mapper;

import com.myframe.dao.orm.mybatis.provider.DeleteSqlProvider;
import com.myframe.dao.util.Cnd;
import org.apache.ibatis.annotations.DeleteProvider;

import java.io.Serializable;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/2 23:47
 */
public interface BaseDeleteMapper<T> {
    @DeleteProvider(type = DeleteSqlProvider.class, method = "dynamicSQL")
    int deleteById(Serializable id);

    @DeleteProvider(type = DeleteSqlProvider.class, method = "dynamicSQL")
    int delete(Cnd cnd);

}
