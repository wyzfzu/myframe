package com.myframe.service.impl;

import com.myframe.dao.TestUserDao;
import com.myframe.dao.service.AbstractBaseService;
import com.myframe.pojo.TestUser;
import com.myframe.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestUserServiceImpl extends AbstractBaseService<TestUser> implements TestUserService {

    @Autowired
    private TestUserDao testUserDao;

    @Override
    public Long findCount2() {
        return testUserDao.getCount2();
    }
}
