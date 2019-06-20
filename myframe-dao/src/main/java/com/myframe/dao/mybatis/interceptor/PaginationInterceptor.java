package com.myframe.dao.mybatis.interceptor;

import com.myframe.dao.mybatis.dialect.Dialect;
import com.myframe.dao.mybatis.dialect.MySql5Dialect;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
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
		args = { Connection.class, Integer.class}
	)
})
public class PaginationInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler handler = (StatementHandler) invocation.getTarget();
		MetaObject metaObject = SystemMetaObject.forObject(handler);
		RowBounds rowBounds = (RowBounds) metaObject.getValue("delegate.rowBounds");

		if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
			return invocation.proceed();
		}

		Dialect dialect = new MySql5Dialect();

		String originalSql = (String) metaObject.getValue("delegate.boundSql.sql");
		String limitString = dialect.getLimitString(originalSql,
				rowBounds.getOffset(), rowBounds.getLimit());
		metaObject.setValue("delegate.boundSql.sql", limitString);
		metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
		metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
