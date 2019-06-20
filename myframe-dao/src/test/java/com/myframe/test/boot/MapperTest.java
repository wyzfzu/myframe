// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.test.boot;

import com.google.common.collect.Maps;
import com.myframe.dao.util.Cnd;
import com.myframe.dao.util.UpdateChain;
import com.myframe.test.mapper.PriceMapper;
import com.myframe.test.mapper.UserMapper;
import com.myframe.test.pojo.TestPrice;
import com.myframe.test.pojo.User;
import org.apache.ibatis.session.RowBounds;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/2 22:23
 */
@SpringBootApplication(scanBasePackages = {
        "com.sankuai.ia.mapper.test.boot",
        "com.sankuai.ia.mapper.springboot"
})
public class MapperTest implements CommandLineRunner {

    //@Autowired
    // private BaseMapper<User> userMapper; // 无自定义接口可直接使用该方式
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PriceMapper priceMapper;
    private static final String dynamicSuffix = "20170207";

    @Override
    public void run(String... strings) throws Exception {
        testGet();
        testUpdate();
        testInsert();
        testDelete();
        testCustom();
        Cnd.registerDynamicTableNameSuffix(MapperTest::getSuffix);
        testPriceGet();
        Cnd.unregisterDynamicTableNameSuffix();
        testPriceUpdate();
        testPriceInsert();
        testPriceDelete();

        testInsertList();
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
                .set("userName", "user_mapperUpdate")
                .where(Cnd.whereEQ("id", 1L)));
        user = userMapper.getById(1L);
        Assert.assertEquals(3, user.getAge().intValue());
        Assert.assertEquals("user_mapperUpdate", user.getUserName());

        userMapper.updateByChain(UpdateChain.make("userName", "user_mapperUpdate")
                .plus("age", 2).where(Cnd.whereEQ("id", 1L)));

        user = userMapper.getById(1L);
        Assert.assertEquals(5, user.getAge().intValue());
        Assert.assertEquals("user_mapperUpdate", user.getUserName());

        userMapper.updateByChain(UpdateChain.make().plus("age", 2).where(Cnd.whereEQ("id", 1L)));
        user = userMapper.getById(1L);
        Assert.assertEquals(7, user.getAge().intValue());
        userMapper.updateByChain(UpdateChain.make().minus("age", 2).where(Cnd.whereEQ("id", 1L)));
        user = userMapper.getById(1L);
        Assert.assertEquals(5, user.getAge().intValue());
        userMapper.updateByChain(UpdateChain.make().multiply("age", 2).where(Cnd.whereEQ("id", 1L)));
        user = userMapper.getById(1L);
        Assert.assertEquals(10, user.getAge().intValue());
        userMapper.updateByChain(UpdateChain.make().divide("age", 2).where(Cnd.whereEQ("id", 1L)));
        user = userMapper.getById(1L);
        Assert.assertEquals(5, user.getAge().intValue());

        user.setGender(null);
        user.setBirthday(null);
        user.setPassword(null);
        user.setAge(null);
        user.setUserName("user_updateNotNull");

        userMapper.updateNotNullById(user);

        user = userMapper.getById(1L);
        Assert.assertEquals("user_updateNotNull", user.getUserName());
        Assert.assertNotSame(user.getModifyTime(), user.getCreateTime());
    }

    public void testInsert() {
        User user = new User();
        user.setAge(1);
        user.setPassword("123456");
        user.setUserName("test_for_insertOne");
        user.setGender((short) 1);
        user.setBirthday(new Date());

        userMapper.insert(user);

        Assert.assertNotNull(user.getId());
        user = userMapper.getById(user.getId());
        Assert.assertNotNull(user.getCreateTime());
        Assert.assertNotNull(user.getModifyTime());
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
        Assert.assertEquals(0, count.intValue());
        params.put("id", 2L);
        User user = userMapper.selectOne2(params);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getUserName(), "user2_modifyList");
    }

    public static String getSuffix() {
        return dynamicSuffix;
    }

    public void testPriceGet() {
        TestPrice price = priceMapper.get(Cnd.whereEQ("id", 1L).excludes("createTime"));
        Assert.assertNotNull(price);

        List<TestPrice> prices = priceMapper.getList(Cnd.whereGT("price", 305)
                .includes("id", "price")
                .desc("price"));
        Assert.assertNotNull(prices);
        Assert.assertEquals(7, prices.size());

        prices = priceMapper.getPageList(Cnd.where().excludes("createTime"), new RowBounds(1, 10));
        Assert.assertNotNull(prices);
        Assert.assertEquals(10, prices.size());

        Integer count = priceMapper.getCount(Cnd.whereEQ("id", 2L));
        Assert.assertEquals(1, count.intValue());
    }

    public void testPriceUpdate() {
        TestPrice price = priceMapper.get(Cnd.whereEQ("id", 1L)
                                             .dynamicSuffix(dynamicSuffix));
        Assert.assertNotNull(price);

        price.setPrice(250);

        priceMapper.updateById(price);
        price = priceMapper.get(Cnd.whereEQ("id", 1L).dynamicSuffix(dynamicSuffix));
        Assert.assertEquals(250, price.getPrice().intValue());

        priceMapper.updateByChain(UpdateChain.make("price", 298)
                .where(Cnd.whereEQ("id", 1L).dynamicSuffix(dynamicSuffix).dynamicSuffixSeparator("")));
        price = priceMapper.get(Cnd.whereEQ("id", 1L).dynamicSuffix(dynamicSuffix).dynamicSuffixSeparator(""));
        Assert.assertEquals(298, price.getPrice().intValue());
    }

    public void testPriceInsert() {
        TestPrice price = new TestPrice();
        price.setPrice(288);
        price.setPriceDate(Integer.parseInt(dynamicSuffix));
        price.setCreateTime(new Date());

        priceMapper.insert(price);

        Assert.assertNotNull(price.getId());
        price = priceMapper.get(Cnd.whereEQ("id", price.getId()).dynamicSuffix(dynamicSuffix));
        Assert.assertEquals(price.getPrice().intValue(), 288);
    }

    public void testPriceDelete() {
        priceMapper.delete(Cnd.whereEQ("id", 1L).dynamicSuffix(dynamicSuffix).dynamicSuffixSeparator(""));

        Integer count = priceMapper.getCount(Cnd.whereEQ("id", 1L).dynamicSuffix(dynamicSuffix).dynamicSuffixSeparator(""));

        Assert.assertEquals(count.intValue(), 0);
    }

    public void testInsertList() {
        List<User> users = mockData();
        userMapper.insertList(users);
        User user1 = userMapper.get(Cnd.whereEQ("userName", "a"));
        User user2 = userMapper.get(Cnd.whereEQ("userName", "b"));
        Assert.assertNotNull(user1);
        Assert.assertNotNull(user1.getCreateTime());
        Assert.assertNotNull(user1.getModifyTime());
        Assert.assertNotNull(user2);
        Assert.assertNotNull(user2.getCreateTime());
        Assert.assertNotNull(user2.getModifyTime());
    }

    private List<User> mockData() {
        User user1 = new User();
        user1.setAge(1);
        user1.setPassword("123456");
        user1.setUserName("a");
        user1.setGender((short) 1);
        user1.setBirthday(new Date());

        User user2 = new User();
        user2.setAge(2);
        user2.setPassword("123456");
        user2.setUserName("b");
        user2.setGender((short) 1);
        user2.setBirthday(new Date());
        return Arrays.asList(user1, user2);
    }
}
