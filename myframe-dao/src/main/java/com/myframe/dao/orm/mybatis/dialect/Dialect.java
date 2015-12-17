package com.myframe.dao.orm.mybatis.dialect;

/**     
 * 物理分页接口。
 *
 */   
public abstract class Dialect {

	public static enum Type {
		MYSQL, ORACLE, SQLSERVER2005
	}

	public abstract String getLimitString(String sql, int skipResults, int maxResults);

}
