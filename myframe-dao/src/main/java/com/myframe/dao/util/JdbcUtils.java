/**
 * 
 */
package com.myframe.dao.util;

import com.google.common.collect.Maps;
import com.myframe.core.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author wuyzh Nov 14, 2012 11:48:31 AM
 *
 */
public class JdbcUtils {

	private JdbcUtils() {}

	private static final Map<String, String> typeMap = Maps.newHashMap();

	static {
		typeMap.put("BIGINT", "Long");
		typeMap.put("INTEGER", "Integer");
		typeMap.put("TINYINT", "Integer");
		typeMap.put("BIT", "Short");
		typeMap.put("SMALLINT", "Integer");
		typeMap.put("FLOAT", "Float");
		typeMap.put("DOUBLE", "Double");
		typeMap.put("DECIMAL", "Double");
		typeMap.put("NUMERIC", "BigDecimal");
		typeMap.put("REAL", "Float");
		typeMap.put("VARCHAR", "String");
		typeMap.put("NVARCHAR", "String");
		typeMap.put("LONGVARCHAR", "String");
		typeMap.put("TEXT", "String");
		typeMap.put("LONGTEXT", "String");
		typeMap.put("TINYTEXT", "String");
		typeMap.put("SMALLTEXT", "String");
		typeMap.put("CHAR", "String");
		typeMap.put("NCHAR", "String");
		typeMap.put("CLOB", "String");
		typeMap.put("NCLOB", "String");
		typeMap.put("BLOB", "byte[]");
		typeMap.put("DATE", "Date");
		typeMap.put("TIMESTAMP", "Date");
		typeMap.put("DATETIME", "Date");
		typeMap.put("TIME", "Date");
	}

	/**
	 * 关闭所有连接
	 * 
	 * @param conn 数据库连接
	 * @param ps 预编译SQL语句
	 * @param rs SQL结果集
	 */
	public static void closeAll(Connection conn,
						PreparedStatement ps, 
						ResultSet rs) {
		try {
			close(rs);
		} finally {
			try {
				close(ps);
			} finally {
				close(conn);
			}
		}
	}

	/**
	 * 关闭结果集
	 * 
	 * @param rs SQL结果集
	 */
	public static void close(ResultSet rs){
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("结果集关闭失败！", e);
		}
	}
	
	/**
	 * 关闭预编译的SQL语句
	 * 
	 * @param ps 预编译SQL语句
	 */
	public static void close(PreparedStatement ps) {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("预编译的SQL语句关闭失败！", e);
		}
	}
	
	/**
	 * 关闭数据库连接
	 * 
	 * @param conn 数据库连接
	 */
	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("数据库连接关闭失败！", e);
		}
	}
	
	/**
	 * 填充参数, 参数的顺序必须和sql语句中的参数顺序相同
	 * 
	 * @param ps 预编译SQL语句
	 * @param params 参数数组
	 */
	public static void fillParams(PreparedStatement ps, Object[] params)
			throws SQLException {
		if (params != null) {
			for (int i = 0; i < params.length; ++i) {
				ps.setObject(i + 1, params[i]);
			}
		}
	}

	private static String getProperty(String column) {
		return StringUtils.toCamelCase(column.toLowerCase(), false, '_');
	}

	public static String getClassName(List<String> tablePrefixes, String tableName) {
		tableName = tableName.toLowerCase();
        for (String tablePrefix : tablePrefixes) {
            if (tableName.startsWith(tablePrefix)) {
                tableName = StringUtils.substringAfter(tableName, tablePrefix);
            }
        }
		String[] tns = tableName.split("_");
		if (tns.length == 1) {
			return StringUtils.capitalize(tableName);
		}
		StringBuilder sb = new StringBuilder();

		for (String tn : tns) {
			sb.append(StringUtils.capitalize(tn));
		}

		return sb.toString();
	}
}
