package com.myframe.dao.orm.mybatis.impl;

import com.myframe.core.util.DefaultPage;
import com.myframe.core.util.GenericUtils;
import com.myframe.core.util.Pageable;
import com.myframe.dao.orm.mybatis.MyBatisDao;
import com.myframe.dao.orm.mybatis.MyBatisGenericDao;
import com.myframe.dao.util.Cnd;
import com.myframe.dao.util.UpdateChain;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * MyBatis泛型实现类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class MyBatisGenericDaoImpl<T> implements MyBatisGenericDao<T> {

    public static final String ST_SELECT_BY_ID = "__selectByIdInner";
    public static final String ST_SELECT_ONE = "__selectOneInner";
    public static final String ST_SELECT_LIST = "__selectListInner";
    public static final String ST_SELECT_COUNT = "__selectCountInner";
    public static final String ST_UPDATE_BY_ID = "__updateByIdInner";
    public static final String ST_UPDATE_BY_CHAIN = "__updateByChainInner";
    public static final String ST_DELETE_BY_ID = "__deleteByIdInner";
    public static final String ST_DELETE = "__deleteInner";
    public static final String ST_INSERT = "__insertInner";

    @Autowired
    private MyBatisDao myBatisDao;

    private Class<T> clazz;

    public MyBatisGenericDaoImpl() {
        clazz = GenericUtils.getGenericType(this.getClass());
    }

    @Override
    public void setMyBatisDao(MyBatisDao myBatisDao) {
        this.myBatisDao = myBatisDao;
    }

    @Override
    public MyBatisDao getMyBatisDao() {
        return this.myBatisDao;
    }

    protected String getStatement(String op) {
        return clazz.getSimpleName() + "." + op;
    }

    @Override
    public T get(Serializable id) {
        return getMyBatisDao().<T>selectOne(getStatement(ST_SELECT_BY_ID), id);
    }

    @Override
    public T get(Cnd cnd) {
        if (cnd == null) {
            cnd = Cnd.where();
        }
        return getMyBatisDao().<T>selectOne(getStatement(ST_SELECT_ONE), cnd);
    }

    @Override
    public List<T> getAll() {
        return getList(null);
    }

    @Override
    public List<T> getList(Cnd cnd) {
        if (cnd == null) {
            cnd = Cnd.where();
        }
        return getMyBatisDao().selectList(getStatement(ST_SELECT_LIST), cnd);
    }

    @Override
    public List<T> getPageList(Cnd cnd, int pageNo, int pageSize) {
        return getMyBatisDao().selectList(
                getStatement(ST_SELECT_LIST),
                cnd, new RowBounds((pageNo - 1) * pageSize, pageSize)
        );
    }

    @Override
    public List<T> getPageList(Cnd cnd, Pageable<T> page) {
        return getPageList(cnd, page.getPageNo(), page.getPageSize());
    }

    @Override
    public Pageable<T> getPage(Cnd cnd, int pageNo, int pageSize) {
        return getPage(cnd, new DefaultPage<T>(pageNo, pageSize));
    }

    @Override
    public Pageable<T> getPage(Cnd cnd, Pageable<T> page) {
        if (cnd == null) {
            cnd = Cnd.where();
        }
        return getMyBatisDao().selectPage(
                getStatement(ST_SELECT_LIST),
                getStatement(ST_SELECT_COUNT),
                page, cnd);
    }

    @Override
    public int update(T obj) {
        return getMyBatisDao().update(getStatement(ST_UPDATE_BY_ID), obj);
    }

    @Override
    public int updateByChain(UpdateChain chain) {
        return getMyBatisDao().update(getStatement(ST_UPDATE_BY_CHAIN), chain);
    }

    @Override
    public void updateList(List<T> objs) {
        getMyBatisDao().updateList(getStatement(ST_UPDATE_BY_ID), objs);
    }

    @Override
    public int delete(Serializable id) {
        return getMyBatisDao().delete(getStatement(ST_DELETE_BY_ID), id);
    }

    @Override
    public int delete(Cnd cnd) {
        if (cnd == null) {
            cnd = Cnd.where();
        }
        return getMyBatisDao().delete(getStatement(ST_DELETE), cnd);
    }

    @Override
    public int insert(T obj) {
        return getMyBatisDao().insert(getStatement(ST_INSERT), obj);
    }

    @Override
    public void insertList(List<T> objs) {
        getMyBatisDao().insertList(getStatement(ST_INSERT), objs);
    }

    @Override
    public Integer count() {
        return count(null);
    }

    @Override
    public Integer count(Cnd cnd) {
        if (cnd == null) {
            cnd = Cnd.where();
        }
        return getMyBatisDao().count(getStatement(ST_SELECT_COUNT), cnd);
    }
}
