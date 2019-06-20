// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.dao.mybatis.mapper;

import com.myframe.dao.mybatis.provider.UpdateSqlProvider;
import com.myframe.dao.util.UpdateChain;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/2 23:47
 */
public interface BaseUpdateMapper<T> {
    /**
     * 根据ID更新数据,不支持分表
     *
     * @param bean
     * @return
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = "dynamicSQL")
    int updateById(T bean);

    /**
     * 批量更新(jdbcUrl上需要增加allowMultiQueries=true)
     * @param beanList
     * @return
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = "dynamicSQL")
    int updateBatchById(List<T> beanList);

    /**
     * 更新值不为null的字段
     *
     * @param bean
     * @return
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = "dynamicSQL")
    int updateNotNullById(T bean);

    /**
     * 批量更新不为null的字段(jdbcUrl上需要增加allowMultiQueries=true)
     * @param beanList
     * @return
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = "dynamicSQL")
    int updateNotNullBatchById(List<T> beanList);

    /**
     * 根据条件更新指定的字段
     *
     * @param updateChain
     * @return
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = "dynamicSQL")
    int updateByChain(UpdateChain updateChain);

}
