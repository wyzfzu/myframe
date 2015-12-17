package com.myframe.dao.orm.mybatis.impl;

import com.myframe.core.util.Page;
import com.myframe.dao.orm.mybatis.MyBatisDao;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;
import java.util.Map;

/**
 * MyBatis的Spring封装实现。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class MyBatisSpringDaoImpl extends SqlSessionDaoSupport implements MyBatisDao {

	public <T> T selectOne(String statement) {
		return this.<T>selectOne(statement, null);
	}

	public <T> T selectOne(String statement, Object parameter) {
		return getSqlSession().<T>selectOne(statement, parameter);
	}

	public <T> List<T> selectList(String statement) {
	    return this.<T>selectList(statement, null);
	}

	public <T> List<T> selectList(String statement, Object parameter) {
	    return getSqlSession().selectList(statement, parameter);
	}

	public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
		return selectMap(statement, null, mapKey);
	}
	
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
		return this.<K, V>selectMap(statement, parameter, mapKey, RowBounds.DEFAULT);
	}
	
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
		return getSqlSession().selectMap(statement, parameter, mapKey, rowBounds);
	}

	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
	    return getSqlSession().selectList(statement, parameter, rowBounds);
	}

	public void select(String statement, ResultHandler handler) {
		select(statement, null, handler);
	}

	public void select(String statement, Object parameter, ResultHandler handler) {
	    select(statement, parameter, RowBounds.DEFAULT, handler);
	}

	public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
	    getSqlSession().select(statement, parameter, rowBounds, handler);
	}

	public int insert(String statement) {
	    return insert(statement, null);
	}

	public int insert(String statement, Object parameter) {
	    return getSqlSession().insert(statement, parameter);
	}

    public <T> void insertList(String statement, List<T> list) {
        if (list == null || list.isEmpty()) {
			return;
		}
        for (T obj : list) {
            insert(statement, obj);
        }
    }

	public int update(String statement) {
	    return update(statement, null);
	}

	public int update(String statement, Object parameter) {
	    return getSqlSession().update(statement, parameter);
	}

    public <T> void updateList(String statement, List<T> list) {
        insertList(statement, list);
    }

	public int delete(String statement) {
	    return getSqlSession().delete(statement);
	}

	public int delete(String statement, Object parameter) {
        return getSqlSession().delete(statement, parameter);
    }

	public Long count(String statement, Object parameter) {
        Long cnt = selectOne(statement, parameter);
		return cnt == null ? 0L : cnt;
	}

	public <T> Page<T> selectPage(String dataSql, String countSql, Page<T> page, Object param) {
		int maxResults = page.getPageSize();
		int skipResults = page.getStartIndex() - 1;
		//记录集合
		List<T> datas = selectList(dataSql, param, new RowBounds(skipResults, maxResults));
		page.setContent(datas);
		//总数
		Long totalItems = count(countSql, param);
		page.setTotalCount(totalItems);
		
		return page;
	}
}
