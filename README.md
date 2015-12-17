# 介绍
`myframe`是一个Java开发工具包，里面包含了：
- `myframe-core`  常用工具类集合
- `myframe-generator`  依赖`myframe-dao`的代码生成器
- `myframe-dao`  基于MyBatis和Spring的数据库访问层

# 使用

## 一、myframe-core
常用工具类包括了：`DateUtils`、`MailUtils`、`FileUtils`、`StreamUtils`、`StringUtils`、`JsonUtils`等。

## 二、myframe-generator
按照工程目录的`test/resources/generatorConfig.xml`配置好后，
运行`test/java`下的`GeneratorTest`，生成的文件会在配置文件中的`targetDir`指向的目录。

生成的文件包含：
- 数据库表映射的JavaBean
- MyBatis的Mapper配置文件（单表的增删改查语句）
- Dao接口和实现
- Service接口和实现
- MyBatis的Configuration文件

包结构如下（以com.test开头,T_USER表为例）：
- `com.test.pojo.User`
- `com.test.pojo.UserMapper.xml`
- `com.test.dao.UserDao`
- `com.test.dao.impl.UserDaoImpl`
- `com.test.service.UserService`
- `com.test.service.impl.UserServiceImpl`

## 三、myframe-dao
默认情况下无需生成Mapper的配置文件.
### 1. Service接口方法（以`UserService`为例）：

- 查询

```Java
// 根据ID查询
User u = userService.findById(1L);
// 根据条件查询一条记录
User u = userService.findOne(Cnd.whereEQ("userName", "xxx"));
// 查询全部
List<User> userList = userService.findAll();
// 根据条件查询
List<User> userList = userService.findList(Cnd.whereIsNotNull("gender"));
// 分页查询
Page<User> userPage = userService.findPage(Cnd.whereLike("userName", "%admin%"), 1, 10);
```
- 更新与删除

```Java
// 保存一条记录
userService.save(u);
// 批量保存
userService.saveList(userList);
// 根据ID更新
userService.modifyById(u);
// 批量更新
userService.modifyList(userList);
// 根据条件更新(将符合条件的记录的age设为26，updateTime设为当前时间）
userService.modifyByChain(UpdateChain.make("age", 26).add("updateTime", DateUtils.now()).whereIn("id", 1L, 2L, 3L);
// 根据ID删除一条记录
userService.removeById(1L);
// 根据条件删除记录
userService.remove(Cnd.whereEQ("id", 1L));
```
### 2. Cnd类
`Cnd`是查询条件构造类，目前支持`(x and y) and ((a and b) or (c and d))`的条件格式构造。支持的条件有：
`=`、`<>`、`>`、`>=`、`<`、`<=`、`LIKE`、`NOT LIKE`、`IS NULL`、`IS NOT NULL`、
`IN`、`NOT IN`、`BETWEEN...AND...`、`NOT BETWEEN...AND...`,另外还支持字段过滤。
- 例子
```Java
// 空条件
Cnd.where();
// 相等(userName为属性，下同)
Cnd.whereEQ("userName", "admin").andEQ("age", 26); 
// LIKE(忽略大小写，默认不忽略大小写）
Cnd.whereLike("userName", "%min%", true);
// 判断空
Cnd.whereIsNull("updateTime");
// IN
Cnd.whereIn("id", 1L, 2L, 3L);
// Between and
Cnd.whereBetween("id", 1L, 10L);
// 除了属性外的参数,可用于关联表时参数
Cnd.whereGT("id", 10L).addExtra("startTime", "2015-07-01");
// 排序
Cnd.whereEQ("age", 26).desc("createTime").asc("id");
// 字段过滤
Cnd.whereEQ("age", 26).includes("userName", "age");
Cnd.whereEQ("id", 12L).excludes("createTime", "updateTime");
```
### 3. 扩展
如果需要复杂的查询，可在对应的`UserMapper.xml`文件中添加语句。然后在`UserDao`和`UserDaoImpl`中添加方法，在方法中使用`getStatement("语句ID")`来获取语句，通过`getMyBatisDao`方法来操作。
- 例子
```Java
// UserMapper.xml	
<select id="selectComplex" resultMap="userResultMap" parameterType="com.myframe.dao.util.Cnd">
	select * from T_USER <include refid="WhereClause" />
</select>
// UserDao.java
public List<User> getComplex(Cnd cnd);
// UserDaoImpl.java
public List<User> getComplex(Cnd cnd) {
	return getMyBatisDao().selectList(getStatement("selectComplex"), cnd);
}
```

### 4. 下步计划
- 支持简单函数