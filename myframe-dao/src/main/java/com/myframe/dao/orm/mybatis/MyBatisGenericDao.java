package com.myframe.dao.orm.mybatis;

import com.myframe.dao.orm.GenericDao;

/**
 * MyBatis泛型DAO
 * @author wyzfzu (wyzfzu@qq.com).
 * @param <T>
 */
public interface MyBatisGenericDao<T> extends GenericDao<T> {

    public MyBatisDao getMyBatisDao();

}
