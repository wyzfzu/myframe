package com.myframe.dao.impl;

import com.myframe.dao.TestUserDao;
import com.myframe.dao.orm.mybatis.impl.MyBatisGenericDaoImpl;
import com.myframe.dao.util.Cnd;
import com.myframe.pojo.TestUser;
import org.springframework.stereotype.Repository;

@Repository
public class TestUserDaoImpl extends MyBatisGenericDaoImpl<TestUser> implements TestUserDao {

    @Override
    public Long getCount2() {
        return getMyBatisDao().count(getStatement("selectCount2"), Cnd.where());
    }
}
