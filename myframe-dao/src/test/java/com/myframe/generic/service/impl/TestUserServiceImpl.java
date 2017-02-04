package com.myframe.generic.service.impl;

import com.myframe.generic.dao.TestUserDao;
import com.myframe.dao.service.AbstractBaseService;
import com.myframe.pojo.TestUser;
import com.myframe.generic.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestUserServiceImpl extends AbstractBaseService<TestUser> implements TestUserService {

    @Autowired
    private TestUserDao testUserDao;

    @Override
    public Integer findCount2() {
        return testUserDao.getCount2();
    }
}
