package com.myframe.dao.mybatis.dialect;

 
/**     
 * Oracle物理分页实现。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */   
public class OracleDialect extends Dialect {

	public String getLimitString(String sql, int offset, int limit) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);
		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ")
					.append(sql)
					.append(" ) row_ ) where rownum_ > ")
                    .append(offset)
                    .append(" and rownum_ <= ")
                    .append(offset + limit);
        if (isForUpdate) {
			pagingSelect.append(" for update");
		}
		return pagingSelect.toString();
	}
}
