package com.myframe.dao.orm;

import com.myframe.core.util.Pageable;
import com.myframe.dao.util.UpdateChain;
import com.myframe.dao.util.Cnd;

import java.io.Serializable;
import java.util.List;

/**
 * 泛型DAO通用方法。
 *
 * @author wyzfzu (wyzfzu@qq.com).
 */
public interface GenericDao<T> {

    T get(Serializable id);

    T get(Cnd cnd);
    
    List<T> getAll();

    List<T> getList(Cnd cnd);

    List<T> getPageList(Cnd cnd, int pageNo, int pageSize);

    List<T> getPageList(Cnd cnd, Pageable<T> page);

    Pageable<T> getPage(Cnd cnd, int pageNo, int pageSize);

    Pageable<T> getPage(Cnd cnd, Pageable<T> page);

    int update(T obj);

    int updateByChain(UpdateChain chain);

    void updateList(List<T> objs);

    int delete(Serializable id);

    int delete(Cnd cnd);

    int insert(T obj);

    void insertList(List<T> objs);

    Integer count();

    Integer count(Cnd cnd);
}
