package com.myframe.dao;

import com.myframe.dao.orm.mybatis.MyBatisGenericDao;
import com.myframe.pojo.TestUser;

public interface TestUserDao extends MyBatisGenericDao<TestUser> {

    Integer getCount2();
}
