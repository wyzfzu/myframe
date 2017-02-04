// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public DataSource getDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:import.sql")
                .build();
    }

}
