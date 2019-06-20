package com.myframe.dao.mybatis.dialect;

import com.myframe.core.util.StringUtils;

/**
 * SQLServer2005物理分页实现。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class SQLServer2005Dialect extends Dialect {

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		StringBuilder pagingBuilder = new StringBuilder();

		String distinctStr = "";

		String loweredString = sql.toLowerCase();
		String sqlPartString = sql;
		if (loweredString.trim().startsWith("select")) {
			int index = 6;
			if (loweredString.startsWith("select distinct")) {
				distinctStr = "DISTINCT ";
				index = 15;
			}
			sqlPartString = sqlPartString.substring(index);
		}
		pagingBuilder.append(sqlPartString);

		//如果没有排序则根据当前时间属性进行排序
		String orderby = getOrderByPart(sql);
		if (StringUtils.isEmpty(orderby)) {
			orderby = "ORDER BY CURRENT_TIMESTAMP";
		}

		StringBuilder result = new StringBuilder(sql.length() + 200);
		result.append("WITH query AS (SELECT ")
			.append(distinctStr)
			.append("TOP 100 PERCENT ")
			.append(" ROW_NUMBER() OVER (")
			.append(orderby)
			.append(") as __row_number__, ")
			.append(pagingBuilder)
			.append(") SELECT * FROM query WHERE __row_number__ > ")
			.append(offset)
			.append(" and __row_number__ <= ")
			.append(offset + limit)
			.append(" ORDER BY __row_number__");

		return result.toString();
	}

	private String getOrderByPart(String sql) {
		String loweredString = sql.toLowerCase();
		int orderByIndex = loweredString.indexOf("order by");
		return orderByIndex == -1 ? "" : sql.substring(orderByIndex);
	}
}
