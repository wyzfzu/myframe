// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.test.boot;

import com.myframe.dao.mybatis.springboot.MybatisAutoConfiguration;
import com.myframe.dao.mybatis.springboot.MybatisProperties;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/11/20 21:39
 */
@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
public class MultiMybatisAutoConfiguration extends MybatisAutoConfiguration {
    public MultiMybatisAutoConfiguration(MybatisProperties properties,
                                         ObjectProvider<Interceptor[]> interceptorsProvider,
                                         ResourceLoader resourceLoader,
                                         ObjectProvider<DatabaseIdProvider> databaseIdProvider) {
        super(properties, interceptorsProvider, resourceLoader, databaseIdProvider);
    }

    @Bean("sqlSessionFactory")
    @Override
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        return createSqlSessionFactory(dataSource);
    }
}
