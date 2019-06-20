// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.dao.mybatis.mapper;

import com.myframe.dao.mybatis.provider.InsertSqlProvider;
import org.apache.ibatis.annotations.InsertProvider;

import java.util.List;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/2 23:47
 */
public interface BaseInsertMapper<T> {
    /**
     * 插入数据,若主键值存在则用该值,若不存在,则根据注解策略生成
     * @param bean
     * @return
     */
    @InsertProvider(type = InsertSqlProvider.class, method = "dynamicSQL")
    int insert(T bean);

    /**
     * 批量插入数据,不支持返回主键值
     * @param beans
     * @return
     */
    @InsertProvider(type = InsertSqlProvider.class, method = "dynamicSQL")
    int insertList(List<T> beans);
}
