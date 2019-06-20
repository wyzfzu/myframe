// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.test.mapper;


import com.myframe.dao.mybatis.mapper.BaseMapper;
import com.myframe.test.pojo.User;
import com.myframe.test.pojo.UserEntity;

import java.util.Map;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/2 22:30
 */
public interface UserEntityMapper extends BaseMapper<UserEntity> {

    // @Select("select count(1) from t_test_user where user_name like #{userName}")
    Integer selectCount2(Map<String, Object> param);

    User selectOne2(Map<String, Object> param);
}
