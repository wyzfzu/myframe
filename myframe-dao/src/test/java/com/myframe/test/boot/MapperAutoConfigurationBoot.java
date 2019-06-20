// Copyright (C) 2017 Meituan
// All rights reserved
package com.myframe.test.boot;

import com.google.common.collect.Lists;
import com.myframe.dao.util.Agg;
import com.myframe.dao.util.AggResult;
import com.myframe.dao.util.AggType;
import com.myframe.dao.util.Cnd;
import com.myframe.dao.util.Having;
import com.myframe.dao.util.UpdateChain;
import com.myframe.test.mapper.UserMapper;
import com.myframe.test.pojo.AggUserBean;
import com.myframe.test.pojo.User;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertTrue;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 17/2/2 23:07
 */
public class MapperAutoConfigurationBoot {

    private AnnotationConfigApplicationContext context;
    private Random random = new Random();

    @Before
    public void init() {
        this.context = new AnnotationConfigApplicationContext();
        this.context.register(DataSourceConfig.class, MybatisBootMapperScanAutoConfiguration.class,
                PropertyPlaceholderAutoConfiguration.class);
        this.context.refresh();
    }

    @After
    public void closeContext() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @SpringBootApplication(scanBasePackages = { "com.myframe.test.boot", "com.myframe.dao.mybatis.springboot" })
    static class MybatisBootMapperScanAutoConfiguration {
    }

    @Test
    public void testBean() {
        assertTrue(this.context.getBeanNamesForType(SqlSessionFactory.class).length >= 1);
        assertTrue(this.context.getBeanNamesForType(SqlSessionTemplate.class).length >= 1);
        assertTrue(this.context.getBeanNamesForType(UserMapper.class).length >= 1);
    }

    @Test
    public void testSelect() {
        UserMapper userMapper = this.context.getBean(UserMapper.class);
        User user = userMapper.getById(1L);
        Assert.assertNotNull(user);

        user = userMapper.get(Cnd.whereEQ("id", 1L).excludes("modifyTime", "createTime"));
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

        List<Long> list = Lists.newArrayList(1L, 2L, 3L);
        users = userMapper.getList(Cnd.whereIn("id", list));
        Assert.assertNotNull(users);
    }

    @Test
    public void testSelectMap() {
        UserMapper userMapper = this.context.getBean(UserMapper.class);
        Cnd cnd = Cnd.whereEQ("id", 2L).distinct(true).includes("id", "userName");
        Map<String, Object> result = userMapper.getMap(cnd);
        System.out.println(result.toString());
//        Map<Long, Map<String, Object>> keyResult = userMapper.getMapUseKey(cnd, "id");
//        System.out.println(keyResult.toString());

        Cnd listCnd = Cnd.whereIn("id", 2L, 3L, 4L).includes("id", "userName");
        List<Map<String, Object>> listResult = userMapper.getMapList(listCnd);
        System.out.println(listResult.toString());
//        List<Map<Long, Object>> listKeyResult = userMapper.getMapListUseKey(listCnd, "id");
//        System.out.println(listKeyResult.toString());

        Cnd cnd1 = Cnd.whereEQ("id", 1L);
        Cnd cnd2 = cnd1.or().andIsNotNull("updateTime").andGE("age", 23);
        Cnd cnd3 = Cnd.whereAnd(cnd2);
        cnd3.andLike("name", "%wyz%");
        System.out.println(cnd3.toCndString());
    }

    @Test
    public void testAgg() {
        UserMapper userMapper = this.context.getBean(UserMapper.class);

        Agg maxAgg = Agg.max("id", "max_id");
        Agg minAgg = Agg.min("id", "min_id");
        Agg avgAgg = Agg.avg("age", "avg_age");
        AggResult maxId = userMapper.getAgg(Cnd.whereAgg(maxAgg, minAgg, avgAgg));
        System.out.println(maxId.toString() + ", " + maxId.getColumnValue("maxId")
                                   + ", " + maxId.getColumnValue("minId")
                                   + ", " + maxId.getColumnValue("AVG_AGE"));

        AggUserBean bean = maxId.toBean(AggUserBean.class);
        System.out.println(bean);

        Agg cntAgeAgg = Agg.count("1", "cnt");
        Cnd cntCnd = Cnd.whereAgg(cntAgeAgg).groupBy("gender").desc("cnt");
        List<AggResult> ageList = userMapper.getAggList(cntCnd);
        System.out.println(ageList.toString());
        // System.out.println(cntCnd.toCndString());

        Agg sumAgeAgg = Agg.make(AggType.SUM, "age", "sum_age", "gender");
        Having having = Having.make(sumAgeAgg).gte(20);
        Cnd cnd = Cnd.whereAgg(sumAgeAgg).groupBy("gender").having(having).desc("sum_age", true);
        List<AggResult> sumAge = userMapper.getAggList(cnd);
        System.out.println(sumAge.toString());

        Agg maxNameAgg = Agg.max("userName", "max_user_name").extraFields("userName");
        Having nameHaving = Having.make(maxNameAgg).like("%2%");
        Cnd nameCnd = Cnd.whereAgg(maxNameAgg).groupBy("userName").having(nameHaving);
        List<AggResult> maxName = userMapper.getAggList(nameCnd);
        System.out.println(maxName.toString());

        Integer count = userMapper.getAggCount(nameCnd);
        System.out.println(count);

        List<AggResult> resultList = userMapper.getAggPageList(nameCnd, new RowBounds(10, 10));
        System.out.println(resultList.toString());

        System.out.println(nameCnd.toCndString());
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

        userMapper.updateByChain(
                UpdateChain.make("age", 3).set("userName", "user_mapperUpdate").where(Cnd.whereEQ("id", 1L)));
        user = userMapper.getById(1L);
        Assert.assertEquals(3, user.getAge().intValue());
        Assert.assertEquals("user_mapperUpdate", user.getUserName());

        userMapper.updateByChain(UpdateChain.make().plus("age", 2).where(Cnd.whereEQ("id", 1L)));
        user = userMapper.getById(1L);
        Assert.assertEquals(5, user.getAge().intValue());
        userMapper.updateByChain(UpdateChain.make().minus("age", 2).where(Cnd.whereEQ("id", 1L)));
        user = userMapper.getById(1L);
        Assert.assertEquals(3, user.getAge().intValue());
        userMapper.updateByChain(UpdateChain.make().multiply("age", 2).where(Cnd.whereEQ("id", 1L)));
        user = userMapper.getById(1L);
        Assert.assertEquals(6, user.getAge().intValue());
        userMapper.updateByChain(UpdateChain.make().divide("age", 2).where(Cnd.whereEQ("id", 1L)));
        user = userMapper.getById(1L);
        Assert.assertEquals(3, user.getAge().intValue());

        /*
        // 使用MySQL数据库测试，并在链接URL中加上allowMultiQueries=true
        List<User> userList = userMapper.getList(Cnd.whereLE("id", 5));
        for (User u : userList) {
            u.setBirthday(new Date());
        }
        int ret = userMapper.updateNotNullBatchById(userList);
        Assert.assertEquals(userList.size(), ret);
         */
    }

    @BeforeClass
    public static void setUp() throws Exception {
        System.setProperty("mybatis.mapperLocations", "classpath:mapper/anno/*.xml");
    }

    @Test
    public void testUpdateEntity() {
        UserMapper userMapper = this.context.getBean(UserMapper.class);
        User user = userMapper.getById(1L);
        Assert.assertNotNull(user);

        User newUser = new User();
        newUser.setAge(user.getAge() + 1);
        newUser.setId(user.getId());
        user = userMapper.getById(1L);
        assert user.getAge().equals(newUser.getAge())
                && user.getModifyTime().getTime() > user.getModifyTime().getTime();
        userMapper.insertOne("test", "password");

        User user1 = new User();
        user1.setUserName("hhh");
        user1.setPassword("pwd");
        List<User> list = Collections.singletonList(user1);
        userMapper.insertOneList(list);
    }

    @Test
    public void testInsert() {
        UserMapper userMapper = this.context.getBean(UserMapper.class);
        User user = new User();
        user.setAge(1);
        user.setPassword("123456");
        user.setUserName("test_for_insert" + random.nextInt());
        user.setGender((short) 1);
        user.setBirthday(new Date());

        userMapper.insert(user);

        Assert.assertNotNull(user.getId());
        List<User> beans = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            User bean = new User();
            // bean.setId(200L + i);
            bean.setAge(i + 10);
            bean.setPassword("123456");
            bean.setUserName("test_for_insertList" + random.nextInt());
            bean.setGender((short) 1);
            bean.setBirthday(new Date());
            beans.add(bean);
        }

        userMapper.insertList(beans);

        List<User> users = userMapper.getList(Cnd.whereLike("userName", "test_for_insertList%"));
        Assert.assertEquals(users.size(), 10);
        for (User bean : users) {
            Assert.assertNotNull(bean.getId());
        }

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
