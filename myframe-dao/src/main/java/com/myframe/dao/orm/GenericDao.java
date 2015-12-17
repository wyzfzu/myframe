package com.myframe.dao.orm;

import com.myframe.core.util.Page;
import com.myframe.dao.util.Cnd;
import com.myframe.dao.util.UpdateChain;

import java.io.Serializable;
import java.util.List;

/**
 * 泛型DAO通用方法。
 *
 * @author wyzfzu (wyzfzu@qq.com).
 */
public interface GenericDao<T> {

    public T get(Serializable id);

    public T get(Cnd cnd);

    public List<T> getAll();

    public List<T> getList(Cnd cnd);

    public List<T> getPageList(Cnd cnd, int pageNo, int pageSize);

    public List<T> getPageList(Cnd cnd, Page<T> page);

    public Page<T> getPage(Cnd cnd, int pageNo, int pageSize);

    public Page<T> getPage(Cnd cnd, Page<T> page);

    public int update(T obj);

    public int updateByChain(UpdateChain chain);

    public void updateList(List<T> objs);

    public int delete(Serializable id);

    public int delete(Cnd cnd);

    public int insert(T obj);

    public void insertList(List<T> objs);

    public Long count();

    public Long count(Cnd cnd);
}
