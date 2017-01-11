package com.myframe.dao.orm.mybatis;

import com.myframe.core.util.Pageable;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * Mybatis接口。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public interface MyBatisDao {

	<T> T selectOne(String statement);

	<T> T selectOne(String statement, Object parameter);

	<T> List<T> selectList(String statement);

	<T> List<T> selectList(String statement, Object parameter);

	<K, V> Map<K, V> selectMap(String statement, String mapKey);
	
	<K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey);
	
	<K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds);

	<T> List<T> selectList(String statement, Object parameter, RowBounds rowBounds);

	void select(String statement, ResultHandler handler);

	void select(String statement, Object parameter, ResultHandler handler);

	void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler);

	int insert(String statement);

	int insert(String statement, Object parameter);

	<T> void insertList(String statement, List<T> list);

	int update(String statement);

	int update(String statement, Object parameter);

	<T> void updateList(String statement, List<T> list);

	int delete(String statement);

	int delete(String statement, Object parameter);

	Integer count(String statement, Object parameter);

    <T> Pageable<T> selectPage(String dataSql, String countSql, Pageable<T> page, Object param);
}
