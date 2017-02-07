package ${pkg};

<%
    for (imp in imports) {
        print('import ' + imp + ';\n');
    }
%>
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

<%
   if (table.autoIncrement) {
%>
import com.myframe.dao.util.IdGenerator;
<%
  }
%>

/**
 * ${table.remark}
 */
@Table(name="${table.tableName}")
public class ${table.className} implements Serializable {
    <%
        for (column in table.columns) {
    %>
    public static final String ${strutil.toUpperCase(column.name)} = "${column.property}";
    <%
        }
    %>
    <%
        for (column in table.columns) {
    %>
    /**
     * ${column.remark}
     */
    <% if (column.pk) { %>
    @Id
    <%
       if (table.autoIncrement) {
    %>
    @GeneratedValue(generator = IdGenerator.JDBC)
    <%
       }
     }
    %>
    private ${column.javaType} ${column.property};
    <%
        }
    %>

    <%
        for (column in table.columns) {
    %>
    public ${column.javaType} get${column.firstUpperProperty}() {
        return ${column.property};
    }

    public void set${column.firstUpperProperty}(${column.javaType} ${column.property}) {
        this.${column.property} = ${column.property};
    }

    <%
        }
    %>
}
