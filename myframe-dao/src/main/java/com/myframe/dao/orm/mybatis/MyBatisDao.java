package com.myframe.dao.orm.mybatis;

import com.myframe.core.util.Page;
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

	public <T> T selectOne(String statement);

	public <T> T selectOne(String statement, Object parameter);

	public <T> List<T> selectList(String statement);

	public <T> List<T> selectList(String statement, Object parameter);

	public <K, V> Map<K, V> selectMap(String statement, String mapKey);
	
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey);
	
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds);

	public <T> List<T> selectList(String statement, Object parameter, RowBounds rowBounds);

	public void select(String statement, ResultHandler handler);

	public void select(String statement, Object parameter, ResultHandler handler);

	public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler);

	public int insert(String statement);

	public int insert(String statement, Object parameter);

	public <T> void insertList(String statement, List<T> list);

	public int update(String statement);

	public int update(String statement, Object parameter);

	public <T> void updateList(String statement, List<T> list);

	public int delete(String statement);

	public int delete(String statement, Object parameter);

	public Long count(String statement, Object parameter);

    public <T> Page<T> selectPage(String dataSql, String countSql, Page<T> page, Object param);
}
