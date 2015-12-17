package ${pkg}.impl;

<%
    var dao = 'MyBatisDaoImpl';
    if (daoConfig.mode == 'generic') {
        dao = 'MyBatisGenericDaoImpl<' + table.className + '>';
        print('import com.myframe.dao.orm.mybatis.impl.MyBatisGenericDaoImpl;\n');
    } else if (daoConfig.mode == 'spring') {
        dao = 'MyBatisSpringDaoImpl';
        print('import com.myframe.dao.orm.mybatis.impl.MyBatisSpringDaoImpl;\n');
    }
%>
import ${modelConfig.targetPackage}.${table.className};
import ${pkg}.${table.className}Dao;
import org.springframework.stereotype.Repository;

@Repository
public class ${table.className}DaoImpl extends ${dao} implements ${table.className}Dao {

}
