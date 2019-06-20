package ${pkg};

<%
    var dao = 'MyBatisDao';
    if (daoConfig.mode == 'generic') {
        dao = 'MyBatisGenericDao<' + table.className + '>';
        print('import com.myframe.dao.mybatis.MyBatisGenericDao;\n');
    } else {
        print('import com.myframe.dao.mybatis.MyBatisDao;\n');
    }
%>
import ${modelConfig.targetPackage}.${table.className};

public interface ${table.className}Dao extends ${dao} {

}
