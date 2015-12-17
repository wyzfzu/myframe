package ${pkg}.impl;

import com.myframe.dao.orm.mybatis.MyBatisGenericDao;
import com.myframe.dao.service.AbstractBaseService;
import ${modelConfig.targetPackage}.${table.className};
import ${daoConfig.targetPackage}.${table.className}Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ${table.className}ServiceImpl extends AbstractBaseService<${table.className}> implements ${table.className}Service {
    @Autowired
    private ${table.className}Dao ${tableNameFirstLower}Dao;
}
