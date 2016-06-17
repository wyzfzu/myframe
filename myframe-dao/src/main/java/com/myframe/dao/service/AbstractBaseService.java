package com.myframe.dao.service;

import com.myframe.core.util.Pageable;
import com.myframe.dao.orm.GenericDao;
import com.myframe.dao.util.Cnd;
import com.myframe.dao.util.UpdateChain;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * 通用业务层默认抽象实现。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public abstract class AbstractBaseService<T> implements BaseService<T> {

    @Autowired
    private GenericDao<T> genericDao;

    public GenericDao<T> getDao() {
        return genericDao;
    }

    @Override
    public T findById(Serializable id) {
        return getDao().get(id);
    }

    @Override
    public T findOne(Cnd cnd) {
        return getDao().get(cnd);
    }

    @Override
    public List<T> findAll() {
        return getDao().getAll();
    }

    @Override
    public List<T> findList(Cnd cnd) {
        return getDao().getList(cnd);
    }

    @Override
    public List<T> findPageList(Cnd cnd, int pageNo, int pageSize) {
        return getDao().getPageList(cnd, pageNo, pageSize);
    }

    @Override
    public List<T> findPageList(Cnd cnd, Pageable<T> page) {
        return getDao().getPageList(cnd, page);
    }

    @Override
    public Pageable<T> findPage(Cnd cnd, Pageable<T> page) {
        return findPage(cnd, page.getPageNo(), page.getPageSize());
    }

    @Override
    public Pageable<T> findPage(Cnd cnd, int pageNo, int pageSize) {
        return getDao().getPage(cnd, pageNo, pageSize);
    }

    @Override
    public boolean save(T bean) {
        return 0 < getDao().insert(bean);
    }

    @Override
    public void saveList(List<T> beans) {
        getDao().insertList(beans);
    }

    @Override
    public boolean modifyById(T bean) {
        return 0 < getDao().update(bean);
    }

    @Override
    public void modifyList(List<T> beans) {
        getDao().updateList(beans);
    }

    @Override
    public boolean modifyByChain(UpdateChain chain) {
        return 0 < getDao().updateByChain(chain);
    }

    @Override
    public boolean removeById(Serializable id) {
        return 0 < getDao().delete(id);
    }

    @Override
    public boolean remove(Cnd cnd) {
        return 0 < getDao().delete(cnd);
    }

    @Override
    public Integer count() {
        return getDao().count();
    }

    @Override
    public Integer count(Cnd cnd) {
        return getDao().count(cnd);
    }
}
