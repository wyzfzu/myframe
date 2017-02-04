package com.myframe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.myframe.core.util.Page;
import com.myframe.core.util.Pageable;
import com.myframe.dao.util.Cnd;
import com.myframe.dao.util.UpdateChain;
import com.myframe.pojo.TestUser;
import com.myframe.generic.service.TestUserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 15/8/31 13:27
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AbstractBaseServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private TestUserService testUserService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static boolean inited = false;
    private static final int DATA_SIZE = 57;

    private static List<TestUser> prepareData() {
        List<TestUser> users = Lists.newArrayList();

        for (int i = 1; i <= DATA_SIZE; ++i) {
            TestUser user = new TestUser();
            user.setId((long)i);
            user.setUserName("user" + i);
            user.setPassword("password" + i);
            user.setAge(i + 20);
            user.setGender((short) (i % 2));
            user.setBirthday(new Date());
            user.setCreateTime(new Date());
            users.add(user);
        }

        return users;
    }

    @Before
    public void beforeClass() {
        if (inited) {
            return;
        }
        jdbcTemplate.execute("truncate table t_test_user;");
        final List<TestUser> users = prepareData();
        jdbcTemplate.batchUpdate(
                "insert into t_test_user(id, user_name, password, age, gender, birthday, create_time)"
                + " values (?,?,?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TestUser user = users.get(i);
                ps.setLong(1, user.getId());
                ps.setString(2, user.getUserName());
                ps.setString(3, user.getPassword());
                ps.setInt(4, user.getAge());
                ps.setShort(5, user.getGender());
                ps.setObject(6, user.getBirthday());
                ps.setObject(7, user.getCreateTime());
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
        inited = true;
    }

    @Test
    public void testFindById() throws Exception {
        HashMap<String, Object> params = Maps.newHashMap();
        params.put("id", 1L);
        params.put("userName", "user1");
        TestUser user = testUserService.findById(params);
        Assert.assertNotNull("找不到ID为1L的用户", user);
        Assert.assertEquals("user1", user.getUserName());
        Assert.assertEquals(21, user.getAge().longValue());
    }

    @Test
    public void testFindOne() throws Exception {
        TestUser user = testUserService.findOne(Cnd.whereEQ("id", 1L));
        Assert.assertNotNull("找不到ID为1L的用户", user);
        Assert.assertEquals("user1", user.getUserName());
        Assert.assertEquals(21, user.getAge().longValue());
    }

    @Test
    public void testFindAll() throws Exception {
        List<TestUser> users = testUserService.findAll();
        Assert.assertNotNull(users);
        Assert.assertEquals(DATA_SIZE, users.size());
    }

    @Test
    public void testFindList() throws Exception {
        List<TestUser> users = testUserService.findList(Cnd.whereEQ("id", 1L));
        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.size());
    }

    @Test
    public void testFindPageList() throws Exception {
        Page<TestUser> page = new Page<TestUser>(1, 10);
        List<TestUser> users = testUserService.findPageList(Cnd.whereIn("id", 1L, 2L, 3L), page);
        Assert.assertNotNull(users);
        Assert.assertEquals(3, users.size());
    }

    @Test
    public void testFindPageList1() throws Exception {
        List<TestUser> users = testUserService.findPageList(Cnd.whereEQ("id", 1L), 1, 10);
        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.size());
    }

    @Test
    public void testFindPage() throws Exception {
        Page<TestUser> page = new Page<TestUser>(1, 10);
        Pageable<TestUser> users = testUserService.findPage(Cnd.whereLE("id", 20L), page);
        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.getPageNo());
        Assert.assertEquals(10, users.getPageSize());
        Assert.assertNotNull(users.getList());
        Assert.assertEquals(20L, users.getTotalCount());
    }

    @Test
    public void testFindPage1() throws Exception {
        Pageable<TestUser> users = testUserService.findPage(Cnd.whereEQ("id", 1L), 1, 10);
        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.getPageNo());
        Assert.assertEquals(10, users.getPageSize());
        Assert.assertNotNull(users.getList());
        Assert.assertEquals(1L, users.getTotalCount());
    }

    @Test
    @Rollback
    public void testSave() throws Exception {
        TestUser user = new TestUser();
        user.setUserName("testNameSave1");
        user.setPassword("testPassword1");
        user.setBirthday(new Date());
        user.setCreateTime(new Date());
        user.setAge(23);
        user.setGender((short) 1);

        boolean result = testUserService.save(user);
        Assert.assertNotNull(user.getId());
        Assert.assertTrue(result);
    }

    @Test
    @Rollback
    public void testSaveList() throws Exception {
        List<TestUser> users = Lists.newArrayList();
        for (int i = 0; i < 5; ++i) {
            TestUser user = new TestUser();
            user.setUserName("testNameSaveList" + i);
            user.setPassword("testPasswordList" + i);
            user.setBirthday(new Date());
            user.setCreateTime(new Date());
            user.setAge(23 + i);
            user.setGender((short) 1);
            users.add(user);
        }
        testUserService.saveList(users);
    }

    @Test
    @Rollback
    public void testModifyById() throws Exception {
        TestUser user = testUserService.findById(1L);
        user.setUserName("user1_modified");
        boolean result = testUserService.modifyById(user);
        Assert.assertTrue(result);
    }

    @Test
    @Rollback
    public void testModifyList() throws Exception {
        List<TestUser> users = testUserService.findList(Cnd.whereIn("id", 2L, 3L, 4L));
        Assert.assertNotNull(users);
        for (TestUser user : users) {
            user.setUserName(user.getUserName() + "_modifyList");
        }
        testUserService.modifyList(users);
    }

    @Test
    @Rollback
    public void testModifyByChain() throws Exception {
        boolean result = testUserService.modifyByChain(
                UpdateChain.make("userName", "user_modifyByChain").where(Cnd.whereIn("id", 5L, 6L, 7L)));
        Assert.assertTrue(result);
    }

    @Test
    @Rollback
    public void testRemoveById() throws Exception {
        boolean result = testUserService.removeById(53L);
        Assert.assertTrue(result);
    }

    @Test
    @Rollback
    public void testRemove() throws Exception {
        boolean result = testUserService.remove(Cnd.whereIn("id", 57L, 56L, 55L, 54L));
        Assert.assertTrue(result);
    }

    @Test
    public void testCount() throws Exception {
        Integer allCount = testUserService.count();
        Assert.assertEquals((long) DATA_SIZE, allCount.longValue());
    }

    @Test
    public void testCount1() throws Exception {
        Integer count = testUserService.count(Cnd.whereLE("age", 31));
        Assert.assertEquals(11L, count.longValue());
    }

    @Test
    public void testIncludes() {
        TestUser user = testUserService.findOne(Cnd.whereEQ("id", 1).includes("userName", "password", "createTime"));
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getUserName());
        Assert.assertNotNull(user.getCreateTime());
        Assert.assertNull(user.getBirthday());
    }

    @Test
    public void testExclude() {
        TestUser user = testUserService.findOne(
                Cnd.whereEQ("id", 1).excludes("createTime", "birthday")
        );
        Assert.assertNull(user.getBirthday());
        Assert.assertNull(user.getCreateTime());
    }

    @Test
    public void testNone() {
        Integer count = testUserService.findCount2();
    }
}