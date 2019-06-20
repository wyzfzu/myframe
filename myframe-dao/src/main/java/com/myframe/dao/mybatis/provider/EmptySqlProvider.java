// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.dao.mybatis.provider;


import com.myframe.dao.mybatis.MapperHelper;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/1/24 17:32
 */
public class EmptySqlProvider extends BaseSqlProvider {
    public EmptySqlProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }


}
