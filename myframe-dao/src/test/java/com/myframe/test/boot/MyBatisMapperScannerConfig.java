// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.test.boot;

import com.myframe.dao.mybatis.MtMapperScannerConfigurer;
import com.myframe.dao.mybatis.springboot.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/6 20:32
 */
@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MyBatisMapperScannerConfig {
    @Bean
    public MtMapperScannerConfigurer mapperScannerConfigurer() {
        MtMapperScannerConfigurer mapperScannerConfigurer = new MtMapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.myframe.test.mapper");
        return mapperScannerConfigurer;
    }

}
