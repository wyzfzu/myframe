// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.boot;

import com.google.common.collect.Maps;
import com.myframe.core.util.RandUtils;
import com.myframe.dao.util.Cnd;
import com.myframe.dao.util.UpdateChain;
import com.myframe.mapper.UserMapper;
import com.myframe.pojo.User;
import org.apache.ibatis.session.RowBounds;
import org.junit.Assert;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/2 22:23
 */
@SpringBootApplication(scanBasePackages = {
        "com.myframe.boot",
        "com.myframe.dao.orm.mybatis.springboot"
})
@MapperScan(basePackages = "com.myframe.mapper")
public class MapperTest implements CommandLineRunner {

    //@Autowired
    // private BaseMapper<User> userMapper; // 无自定义接口可直接使用该方式
    @Autowired
    private UserMapper userMapper;

    @Override
    public void run(String... strings) throws Exception {
        testGet();
        testUpdate();
        testInsert();
        testDelete();
        testCustom();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MapperTest.class, args);
    }


    public void testGet() {
        User user = userMapper.getById(1L);
        Assert.assertNotNull(user);

        user = userMapper.get(Cnd.whereEQ("id", 1L).excludes("createTime", "birthday"));
        Assert.assertNotNull(user);

        List<User> users = userMapper.getList(Cnd.whereLike("userName", "%user%")
                .andLT("age", 31).includes("id", "userName").desc("age"));
        Assert.assertNotNull(users);
        Assert.assertEquals(10, users.size());

        users = userMapper.getAll();
        Assert.assertNotNull(users);
        Assert.assertEquals(58, users.size());

        users = userMapper.getPageList(Cnd.where().excludes("createTime"), new RowBounds(1, 10));
        Assert.assertNotNull(users);
        Assert.assertEquals(10, users.size());

        Integer count = userMapper.getAllCount();
        Assert.assertEquals(58, count.intValue());

        count = userMapper.getCount(Cnd.whereEQ("id", 2L));
        Assert.assertEquals(1, count.intValue());
    }

    public void testUpdate() {
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

    public void testInsert() {
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

    public void testDelete() {
        userMapper.deleteById(64L);

        userMapper.delete(Cnd.whereLike("userName", "test_for_insert%"));

        Integer count = userMapper.getCount(Cnd.whereLike("userName", "test_for_insert%"));

        Assert.assertEquals(count.intValue(), 0);
    }

    public void testCustom() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("userName", "%test_for%");
        Integer count = userMapper.selectCount2(params);
        Assert.assertNotNull(count);
        Assert.assertEquals(count.intValue(), 0);
        params.put("id", 2L);
        User user = userMapper.selectOne2(params);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getUserName(), "user2_modifyList");
    }
}
