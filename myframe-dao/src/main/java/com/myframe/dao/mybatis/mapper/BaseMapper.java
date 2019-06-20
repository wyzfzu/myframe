// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.dao.mybatis.mapper;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/1/24 15:14
 */
public interface BaseMapper<T> extends BaseSelectMapper<T>,
        BaseUpdateMapper<T>, BaseDeleteMapper<T>,
        BaseInsertMapper<T>, BaseAggMapper<T> {

}
