package com.myframe.service;

import com.myframe.dao.service.BaseService;
import com.myframe.pojo.TestUser;

public interface TestUserService extends BaseService<TestUser> {

    Integer findCount2();
}
