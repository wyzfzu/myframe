package com.myframe.dao.mybatis.mapper;

import com.myframe.dao.mybatis.provider.AggSqlProvider;
import com.myframe.dao.util.AggResult;
import com.myframe.dao.util.Cnd;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 聚合方法接口
 *
 * @author wuyuzhen
 * @version 1.0
 * @created 18/5/11
 */
public interface BaseAggMapper<T> {

    /**
     * 获取单个聚合查询结果
     *
     * @param cnd
     * @return
     */
    @SelectProvider(type = AggSqlProvider.class, method = "dynamicSQL")
    AggResult getAgg(Cnd cnd);

    /**
     * 获取聚合查询结果数量
     *
     * @param cnd
     * @return
     */
    @SelectProvider(type = AggSqlProvider.class, method = "dynamicSQL")
    Integer getAggCount(Cnd cnd);

    /**
     * 获取聚合结果列表
     *
     * @param cnd
     * @return
     */
    @SelectProvider(type = AggSqlProvider.class, method = "dynamicSQL")
    List<AggResult> getAggList(Cnd cnd);

    /**
     * 根据条件获取分页列表
     *
     * @param cnd
     * @param rowBounds
     * @return
     */
    @SelectProvider(type = AggSqlProvider.class, method = "dynamicSQL")
    List<AggResult> getAggPageList(Cnd cnd, RowBounds rowBounds);
}
