// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.test.boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/2 23:23
 */
@Configuration
@EnableAutoConfiguration
public class DataSourceConfig {

    @Bean(name = "dataSource")
    @Primary
    public DataSource getDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2)
                      .addScript("classpath:import.sql")
                      .build();
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUsername("root");
//        dataSource.setPassword("root");
//        dataSource.setUrl("jdbc:mysql://localhost:3307/wuyuzhen?allowMultiQueries=true");

//        return dataSource;
    }
}
