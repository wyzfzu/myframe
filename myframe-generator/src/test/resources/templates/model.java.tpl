package ${pkg};

<%
    for (imp in imports) {
        print('import ' + imp + ';\n');
    }
%>
import java.io.Serializable;
import com.myframe.dao.annotation.Column;
import com.myframe.dao.annotation.Id;
import com.myframe.dao.annotation.Table;

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
    @Id(name="${column.name}")
    <% } else { %>
    @Column(name="${column.name}")
    <% } %>
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
