
package com.myframe.dao.orm.mybatis;

import org.mybatis.spring.mapper.MapperFactoryBean;

/**
 * 增加mapperHelper
 *
 * @param <T>
 * @author wuyuzhen
 */
public class MtMapperFactoryBean<T> extends MapperFactoryBean<T> {

    private MapperHelper mapperHelper;

    public MtMapperFactoryBean() {
    }

    public MtMapperFactoryBean(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
        //通用Mapper
        if (mapperHelper.isExtendCommonMapper(getObjectType())) {
            // 开启自动驼峰映射
            getSqlSession().getConfiguration().setMapUnderscoreToCamelCase(true);
            mapperHelper.processConfiguration(getSqlSession().getConfiguration(), getObjectType());
        }
    }

    public void setMapperHelper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }
}
