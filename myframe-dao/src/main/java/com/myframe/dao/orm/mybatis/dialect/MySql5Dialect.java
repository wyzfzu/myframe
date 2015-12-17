package com.myframe.dao.orm.mybatis.dialect;


import com.myframe.core.util.StringUtils;

/**
 * Mysql5的mybaits物理分页实现。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */   
public class MySql5Dialect extends Dialect {

	public String getLimitString(String sql, int offset, int limit) {
		if (StringUtils.isEmpty(sql)) {
			return "";
		}
		String lineSql = sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");
		return new StringBuilder(lineSql)
					.append(" limit ")
					.append(offset)
					.append(" ,")
					.append(limit).toString();
	}
}
