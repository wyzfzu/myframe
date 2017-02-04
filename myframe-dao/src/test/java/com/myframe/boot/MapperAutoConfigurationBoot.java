// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.boot;

import com.myframe.core.util.RandUtils;
import com.myframe.dao.orm.mybatis.springboot.MybatisAutoConfiguration;
import com.myframe.dao.util.Cnd;
import com.myframe.dao.util.UpdateChain;
import com.myframe.mapper.UserMapper;
import com.myframe.pojo.User;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/2 23:07
 */
public class MapperAutoConfigurationBoot {

    private AnnotationConfigApplicationContext context;

    @Before
    public void init() {
        this.context = new AnnotationConfigApplicationContext();
        this.context.register(DataSourceConfig.class,
                MybatisBootMapperScanAutoConfiguration.class,
                MybatisAutoConfiguration.class,
                PropertyPlaceholderAutoConfiguration.class);
        this.context.refresh();
    }

    @After
    public void closeContext() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @SpringBootApplication(scanBasePackages = {"com.myframe.boot"})
    @MapperScan(basePackages = {"com.myframe.mapper"})
    static class MybatisBootMapperScanAutoConfiguration {
    }

    @Test
    public void testBean() {
        assertEquals(1, this.context.getBeanNamesForType(SqlSessionFactory.class).length);
        assertEquals(1, this.context.getBeanNamesForType(SqlSessionTemplate.class).length);
        assertEquals(1, this.context.getBeanNamesForType(UserMapper.class).length);
    }

    @Test
    public void testSelect() {
        UserMapper userMapper = this.context.getBean(UserMapper.class);
        User user = userMapper.getById(1L);
        Assert.assertNotNull(user);

        user = userMapper.get(Cnd.whereEQ("id", 1L));
        Assert.assertNotNull(user);

        List<User> users = userMapper.getList(Cnd.whereLike("userName", "%user%").andLT("age", 31).desc("age"));
        Assert.assertNotNull(users);
        Assert.assertEquals(10, users.size());

        users = userMapper.getAll();
        Assert.assertNotNull(users);
        Assert.assertEquals(58, users.size());

        users = userMapper.getPageList(Cnd.where(), new RowBounds(0, 10));
        Assert.assertNotNull(users);
        Assert.assertEquals(10, users.size());

        Integer count = userMapper.getAllCount();
        Assert.assertEquals(58, count.intValue());

        count = userMapper.getCount(Cnd.whereEQ("id", 2L));
        Assert.assertEquals(1, count.intValue());
    }

    @Test
    public void testUpdate() {
        UserMapper userMapper = this.context.getBean(UserMapper.class);
        User user = userMapper.getById(1L);
        Assert.assertNotNull(user);
        user.setAge(1);
        userMapper.updateById(user);
        user = userMapper.getById(1L);
        Assert.assertEquals(1, user.getAge().intValue());

        userMapper.updateByChain(UpdateChain.make("age", 3)
                .add("userName", "user_mapperUpdate")
                .where(Cnd.whereEQ("id", 1L)));
        user = userMapper.getById(1L);
        Assert.assertEquals(3, user.getAge().intValue());
        Assert.assertEquals("user_mapperUpdate", user.getUserName());
    }

    @Test
    public void testInsert() {
        UserMapper userMapper = this.context.getBean(UserMapper.class);
        User user = new User();
        user.setAge(1);
        user.setPassword("123456");
        user.setUserName("test_for_insert" + RandUtils.randInt());
        user.setGender((short) 1);
        user.setCreateTime(new Date());
        user.setBirthday(new Date());

        userMapper.insert(user);

        Assert.assertNotNull(user.getId());
    }

    @Test
    public void testDelete() {
        UserMapper userMapper = this.context.getBean(UserMapper.class);
        userMapper.deleteById(64L);

        userMapper.delete(Cnd.whereLike("userName", "test_for_insert%"));

        Integer count = userMapper.getCount(Cnd.whereLike("userName", "test_for_insert%"));

        Assert.assertEquals(count.intValue(), 0);
    }
}
