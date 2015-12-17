<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<properties resource="database.properties" />
    
	<settings>
		<setting name="defaultStatementTimeout" value="30"/>
	</settings>

    <typeAliases>
        <%
            for (table in tables) {
        %>
        <typeAlias type="${modelConfig.targetPackage}.${table.className}" alias="${table.className}" />
        <%
            }
        %>
    </typeAliases>

    <environments default="development">
		<environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="\${jdbc.driver}" />
                <property name="url" value="\${jdbc.url}" />
                <property name="username" value="\${jdbc.username}" />
                <property name="password" value="\${jdbc.password}" />
            </dataSource>
		</environment>
	</environments>

	<mappers>
	    <%
            for (table in tables) {
        %>
        <mapper resource="${sqlMapConfig.resourceDir}/${table.className}Mapper.xml" />
        <%
            }
        %>
	</mappers>
</configuration>