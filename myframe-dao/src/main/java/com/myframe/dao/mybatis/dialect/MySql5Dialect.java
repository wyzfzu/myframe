package com.myframe.dao.mybatis.dialect;


import com.myframe.core.util.StringUtils;

import java.util.regex.Pattern;

/**
 * Mysql5的mybaits物理分页实现。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */   
public class MySql5Dialect extends Dialect {
	private static final Pattern BLANK = Pattern.compile("[\r\n]");
	private static final Pattern EMPTY = Pattern.compile("\\s{2,}");

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		if (StringUtils.isEmpty(sql)) {
			return "";
		}
		String lineSql = BLANK.matcher(sql).replaceAll(" ");
		lineSql = EMPTY.matcher(lineSql).replaceAll(" ");
		return new StringBuilder(lineSql)
				.append(" limit ")
				.append(offset)
				.append(",")
				.append(limit).toString();
	}

}
