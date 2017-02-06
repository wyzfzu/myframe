
package com.myframe.dao.orm.mybatis;

import com.google.common.collect.Lists;
import com.myframe.dao.orm.mybatis.mapper.BaseMapper;
import com.myframe.dao.orm.mybatis.provider.BaseSqlProvider;
import com.myframe.dao.orm.mybatis.provider.EmptySqlProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MapperHelper {
    /**
     * 缓存skip结果
     */
    private final Map<String, Boolean> msIdSkip = new HashMap<>();

    /**
     * 注册的接口
     */
    private List<Class<?>> registerClass = new ArrayList<>();

    /**
     * 注册的通用Mapper接口
     */
    private Map<Class<?>, BaseSqlProvider> registerMapper = new ConcurrentHashMap<>();

    /**
     * 缓存msid和MapperTemplate
     */
    private Map<String, BaseSqlProvider> msIdCache = new HashMap<>();

    /**
     * 默认构造方法
     */
    public MapperHelper() {
    }

    /**
     * 通过通用Mapper接口获取对应的SqlProvider
     *
     * @param mapperClass
     * @return
     * @throws Exception
     */
    private BaseSqlProvider fromMapperClass(Class<?> mapperClass) {
        Method[] methods = mapperClass.getDeclaredMethods();
        Class<?> templateClass = null;
        Class<?> tempClass = null;
        Set<String> methodSet = new HashSet<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(SelectProvider.class)) {
                SelectProvider provider = method.getAnnotation(SelectProvider.class);
                tempClass = provider.type();
                methodSet.add(method.getName());
            } else if (method.isAnnotationPresent(InsertProvider.class)) {
                InsertProvider provider = method.getAnnotation(InsertProvider.class);
                tempClass = provider.type();
                methodSet.add(method.getName());
            } else if (method.isAnnotationPresent(DeleteProvider.class)) {
                DeleteProvider provider = method.getAnnotation(DeleteProvider.class);
                tempClass = provider.type();
                methodSet.add(method.getName());
            } else if (method.isAnnotationPresent(UpdateProvider.class)) {
                UpdateProvider provider = method.getAnnotation(UpdateProvider.class);
                tempClass = provider.type();
                methodSet.add(method.getName());
            }
            if (templateClass == null) {
                templateClass = tempClass;
            } else if (templateClass != tempClass) {
                throw new RuntimeException("一个通用Mapper中只允许存在一个SqlProvider子类!");
            }
        }
        if (templateClass == null || !BaseSqlProvider.class.isAssignableFrom(templateClass)) {
            templateClass = EmptySqlProvider.class;
        }
        BaseSqlProvider baseSqlProvider = null;
        try {
            baseSqlProvider = (BaseSqlProvider) templateClass.getConstructor(Class.class, MapperHelper.class).newInstance(mapperClass, this);
        } catch (Exception e) {
            throw new RuntimeException("实例化MapperTemplate对象失败:" + e.getMessage());
        }
        //注册方法
        for (String methodName : methodSet) {
            try {
                baseSqlProvider.addMethodMap(methodName, templateClass.getMethod(methodName, MappedStatement.class));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(templateClass.getCanonicalName() + "中缺少" + methodName + "方法!");
            }
        }
        return baseSqlProvider;
    }

    /**
     * 注册通用Mapper接口
     *
     * @param mapperClass
     */
    public void registerMapper(Class<?> mapperClass) {
        if (!registerMapper.containsKey(mapperClass)) {
            registerClass.add(mapperClass);
            registerMapper.put(mapperClass, fromMapperClass(mapperClass));
        }
        //自动注册继承的接口
        Class<?>[] interfaces = mapperClass.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (Class<?> anInterface : interfaces) {
                registerMapper(anInterface);
            }
        }
    }

    /**
     * 判断接口是否包含通用接口
     *
     * @param mapperInterface
     * @return
     */
    public boolean isExtendCommonMapper(Class<?> mapperInterface) {
        for (Class<?> mapperClass : registerClass) {
            if (mapperClass.isAssignableFrom(mapperInterface)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果当前注册的接口为空，自动注册默认接口
     */
    public void ifEmptyRegisterDefaultInterface() {
        if (registerClass.isEmpty()) {
            registerMapper(BaseMapper.class);
        }
    }

    /**
     * 判断当前的接口方法是否需要进行拦截
     *
     * @param msId
     * @return
     */
    public boolean isMapperMethod(String msId) {
        if (msIdSkip.get(msId) != null) {
            return msIdSkip.get(msId);
        }
        for (Map.Entry<Class<?>, BaseSqlProvider> entry : registerMapper.entrySet()) {
            if (entry.getValue().supportMethod(msId)) {
                msIdSkip.put(msId, true);
                msIdCache.put(msId, entry.getValue());
                return true;
            }
        }
        msIdSkip.put(msId, false);
        return false;
    }

    /**
     * 重新设置SqlSource
     * <p/>
     * 执行该方法前必须使用isMapperMethod判断，否则msIdCache会空
     *
     * @param ms
     */
    public void setSqlSource(MappedStatement ms) {
        BaseSqlProvider sqlProvider = msIdCache.get(ms.getId());
        try {
            if (sqlProvider != null) {
                sqlProvider.setSqlSource(ms);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 配置指定的接口
     *
     * @param configuration
     * @param mapperInterface
     */
    public void processConfiguration(Configuration configuration, Class<?> mapperInterface) {
        String prefix;
        if (mapperInterface != null) {
            prefix = mapperInterface.getCanonicalName();
        } else {
            prefix = "";
        }
        List<Object> mappedStatements = Lists.newArrayList(configuration.getMappedStatements());
        mappedStatements.forEach((obj) -> {
            if (obj instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) obj;
                if (ms.getId().startsWith(prefix)
                        && isMapperMethod(ms.getId())
                        && (ms.getSqlSource() instanceof ProviderSqlSource)) {
                    setSqlSource(ms);
                }
            }
        });
    }
}