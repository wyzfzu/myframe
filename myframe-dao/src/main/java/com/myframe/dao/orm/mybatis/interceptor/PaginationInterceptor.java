package com.myframe.dao.orm.mybatis.interceptor;

import com.myframe.core.util.StringUtils;
import com.myframe.dao.orm.mybatis.dialect.Dialect;
import com.myframe.dao.orm.mybatis.dialect.MySql5Dialect;
import com.myframe.dao.orm.mybatis.dialect.OracleDialect;
import com.myframe.dao.orm.mybatis.dialect.SQLServer2005Dialect;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.Properties;

/**     
 * MyBatis物理分页的拦截器。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
@Intercepts({
	@Signature(
		type = StatementHandler.class,
		method = "prepare",
		args = { Connection.class }
	)
})
public class PaginationInterceptor implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler handler = (StatementHandler) invocation.getTarget();
		MetaObject metaObject = SystemMetaObject.forObject(handler);
		RowBounds rowBounds = (RowBounds) metaObject.getValue("delegate.rowBounds");

		if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
			return invocation.proceed();
		}

		Configuration config = (Configuration) metaObject.getValue("delegate.configuration");

		String dialectStr = config.getVariables().getProperty("dialect");
		if (StringUtils.isEmpty(dialectStr)) {
			throw new RuntimeException("数据库方言没有配置!");
		}

		Dialect.Type databaseType = Dialect.Type.valueOf(dialectStr.toUpperCase());

		Dialect dialect = null;
		switch (databaseType) {
		case MYSQL:
			dialect = new MySql5Dialect();
			break;
		case ORACLE:
			dialect = new OracleDialect();
			break;
		case SQLSERVER2005:
			dialect = new SQLServer2005Dialect();
			break;
		default:
			dialect = new MySql5Dialect();
			break;
		}

		String originalSql = (String) metaObject.getValue("delegate.boundSql.sql");
		String limitString = dialect.getLimitString(originalSql,
				rowBounds.getOffset(), rowBounds.getLimit());
		metaObject.setValue("delegate.boundSql.sql", limitString);
		metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
		metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);

		return invocation.proceed();
	}

	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	public void setProperties(Properties properties) {
	}

}
