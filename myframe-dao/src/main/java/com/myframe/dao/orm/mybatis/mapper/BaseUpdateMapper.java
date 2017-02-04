// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.dao.orm.mybatis.mapper;

import com.myframe.dao.orm.mybatis.provider.UpdateSqlProvider;
import com.myframe.dao.util.UpdateChain;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/2 23:47
 */
public interface BaseUpdateMapper<T> {
    @UpdateProvider(type = UpdateSqlProvider.class, method = "dynamicSQL")
    int updateById(T bean);

    @UpdateProvider(type = UpdateSqlProvider.class, method = "dynamicSQL")
    int updateByChain(UpdateChain updateChain);

}
