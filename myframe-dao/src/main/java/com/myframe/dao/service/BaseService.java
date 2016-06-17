package com.myframe.dao.service;

import com.myframe.core.util.Pageable;
import com.myframe.dao.util.Cnd;
import com.myframe.dao.util.UpdateChain;

import java.io.Serializable;
import java.util.List;

/**
 * 通用业务层接口。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public interface BaseService<T> {

    /**
     * 根据ID来查找。
     *
     * @param id 主键ID
     * @return ID对象的bean
     */
    public T findById(Serializable id);

    /**
     * 根据条件来查找当个bean,若有多个bean，则返回第一个。
     *
     * @param cnd 条件对象
     * @return 根据条件第一个找到的bean
     */
    public T findOne(Cnd cnd);

    /**
     * 查询所有bean。
     *
     * @return 所有的bean
     */
    public List<T> findAll();

    /**
     * 根据条件来查找列表。
     *
     * @param cnd 条件对象
     * @return 符合条件的bean列表
     */
    public List<T> findList(Cnd cnd);

    /**
     * 根据条件查找分页列表，只保留分页的列表数据。
     * @param cnd 条件对象
     * @param pageNo 页码
     * @param pageSize 每页数量
     * @return 符合条件的bean列表
     */
    public List<T> findPageList(Cnd cnd, int pageNo, int pageSize);

    /**
     * 根据条件查找分页列表，只保留分页的列表数据。
     * @param cnd 条件对象
     * @param page 分页对象
     * @return 符合条件的bean列表
     */
    public List<T> findPageList(Cnd cnd, Pageable<T> page);

    /**
     * 根据条件分页查询。
     *
     * @param cnd 条件对象
     * @param page 分页信息
     * @return 分页对象
     */
    public Pageable<T> findPage(Cnd cnd, Pageable<T> page);

    /**
     * 根据条件分页查询。
     *
     * @param cnd 条件对象
     * @param pageNo 页码
     * @param pageSize 每页条数
     * @return 分页对象
     */
    public Pageable<T> findPage(Cnd cnd, int pageNo, int pageSize);

    /**
     * 保存bean.
     *
     * @param bean 要保存的对象
     */
    public boolean save(T bean);

    /**
     * 批量保存bean。
     *
     * @param beans 要保存的bean集合
     */
    public void saveList(List<T> beans);

    /**
     * 根据bean的ID来修改bean的内容。
     * 修改的内容就是bean中已填充的字段内容。
     *
     * @param bean 要修改的bean
     */
    public boolean modifyById(T bean);

    /**
     * 根据bean的ID批量修改bean。
     *
     * @param beans 要修改的bean列表
     */
    public void modifyList(List<T> beans);

    /**
     * 根据构造的更新链来修改。
     *
     * @param chain
     */
    public boolean modifyByChain(UpdateChain chain);

    /**
     * 根据ID来删除bean。
     *
     * @param id bean的ID
     */
    public boolean removeById(Serializable id);

    /**
     * 根据条件来删除bean。
     *
     * @param cnd 条件对象
     */
    public boolean remove(Cnd cnd);

    /**
     * 统计所有bean数量。
     *
     * @return 所有bean数量
     */
    public Integer count();

    /**
     * 根据条件来统计bean数量。
     *
     * @param cnd 条件对象
     * @return 符合条件的bean数量
     */
    public Integer count(Cnd cnd);
}
