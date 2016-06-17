<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<%
    var resultMap = table.className + "ResultMap";
    var cndType = "com.myframe.dao.util.Cnd";
    var pkSize = table.pkColumns.~size;
    var pkJavaType = pkSize > 1 ? 'map' : 'java.io.Serializable';
%>
<mapper namespace="${table.className}">
    <resultMap id="${resultMap}" type="${table.className}">
        <%  for (col in table.pkColumns) { %>
        <id column="${col.name}" property="${col.property}" jdbcType="${col.jdbcType}" />
        <%
            }
            if (!isEmpty(table.columns)) {
                for (col in table.columns) {
                    if (col.pk) {
                        continue;
                    }
        %>
        <result column="${col.name}" property="${col.property}" jdbcType="${col.jdbcType}" />
        <%
                }
            }
        %>
    </resultMap>

    <sql id="FilterColumnList">
        <choose>
            <when test="includeFields != null and includeFields.size() > 0">
                <foreach item="field" collection="includeFields" open="" close="" separator=",">
                    \${field}
                </foreach>
            </when>
            <when test="excludeFields != null and excludeFields.size() > 0">
                <foreach item="field" collection="{<% for (col in table.columns) { %>'${col.name}'${colLP.last ? '' : ','}<% } %>}" open="" close="" separator=",">
                    <if test="field not in excludeFields">
                        \${field}
                    </if>
                </foreach>
            </when>
            <otherwise>
            <% for (col in table.columns) { %>${col.name}${colLP.last ? '' : ','}<% } %>
            </otherwise>
        </choose>
    </sql>

    <sql id="ColumnList">
        <% for (col in table.columns) { %>${col.name}${colLP.last ? '' : ','}<% } %>
    </sql>

    <sql id="InnerCondition">
        <if test="criteria.valid">
            <trim prefix="(" suffix=")" prefixOverrides="and">
                <foreach collection="criteria.allCriteria" item="cri">
                    <choose>
                        <when test="cri.nullExp">
                            and \${cri.field} \${cri.op}
                        </when>
                        <when test="cri.betweenExp">
                            and \${cri.field} \${cri.op} #{cri.value} and #{cri.secondValue}
                        </when>
                        <when test="cri.inExp">
                            and \${cri.field} \${cri.op}
                            <foreach collection="cri.value" item="listItem" open="(" close=")" separator=",">
                                #{listItem}
                            </foreach>
                        </when>
                        <when test="cri.likeExp">
                            <if test="cri.ignoreCase">
                                and LOWER(\${cri.field}) \${cri.op} LOWER(#{cri.value})
                            </if>
                            <if test="!cri.ignoreCase">
                                and \${cri.field} \${cri.op} #{cri.value}
                            </if>
                        </when>
                        <otherwise>
                            and \${cri.field} \${cri.op} #{cri.value}
                        </otherwise>
                    </choose>
                </foreach>
            </trim>
        </if>
    </sql>

    <sql id="WhereClause">
        <where>
            <if test="innerCnd != null">(</if>
            <foreach collection="orderCriteria" item="criteria" separator="or">
                <include refid="InnerCondition" />
            </foreach>
            <if test="innerCnd != null">
                ) AND
                <trim prefix="(" suffix=")" prefixOverrides="and">
                    <foreach collection="innerCnd.orderCriteria" item="criteria" separator="or">
                        <include refid="InnerCondition" />
                    </foreach>
                </trim>
            </if>
        </where>
    </sql>

    <select id="__selectListInner" resultMap="${resultMap}" parameterType="${cndType}">
        select
        <if test="distinct">
            distinct
        </if>
        <include refid="FilterColumnList" />
        from ${table.tableName}
        <include refid="WhereClause" />
        <if test="orderBy">
        order by
        <foreach collection="orders" item="od" separator=",">
            \${od.key} \${od.value}
        </foreach>
        </if>
    </select>

    <select id="__selectCountInner" resultType="java.lang.Integer" parameterType="${cndType}">
        select count(1) from ${table.tableName}
        <include refid="WhereClause" />
    </select>

    <select id="__selectByIdInner" resultMap="${resultMap}" parameterType="${pkJavaType}">
        select <include refid="ColumnList" /> from ${table.tableName}
        where 1 = 1
        <% for (col in table.pkColumns) { %>
            and ${col.name} = #{${col.property}}
        <% } %>
    </select>

    <select id="__selectOneInner" resultMap="${resultMap}" parameterType="${cndType}">
        select <include refid="FilterColumnList" /> from ${table.tableName}
        <include refid="WhereClause" />
    </select>

    <insert id="__insertInner" parameterType="${table.className}" <% if (table.autoIncrement) { %>useGeneratedKeys="true" keyProperty="${table.pkColumns[0].property}"<%}%>>
        insert into ${table.tableName} (
        <%
            for (col in table.columns) {
                if (col.pk && table.autoIncrement) {
                    continue;
                }
        %>
            ${col.name}${colLP.last ? '' : ','}
        <%  } %>
        ) values (
        <%
            for (col in table.columns) {
                if (col.pk && table.autoIncrement) {
                    continue;
                }
        %>
            #{${col.property},jdbcType=${col.jdbcType}}${colLP.last ? '' : ','}
        <%  } %>
        )
    </insert>
    <update id="__updateByIdInner" parameterType="${table.className}">
        update ${table.tableName} set
        <%
            for (col in table.columns) {
                if (col.pk) {
                    continue;
                }
        %>
            ${col.name} = #{${col.property}, jdbcType=${col.jdbcType}}${colLP.last ? '':','}
        <%  } %>
        where 1 = 1
        <% for (col in table.pkColumns) { %>
            and ${col.name} = #{${col.property}}
        <% } %>
    </update>

    <update id="__updateByChainInner" parameterType="com.myframe.dao.util.UpdateChain">
        update ${table.tableName} set
        <foreach collection="params" item="item" index="key" separator=",">
            \${key} = #{item}
        </foreach>
        <include refid="WhereClause" />
    </update>

    <delete id="__deleteByIdInner" parameterType="${pkJavaType}">
        delete from ${table.tableName}
        where 1 = 1
        <% for (col in table.pkColumns) { %>
            and ${col.name} = #{${col.property}}
        <% } %>
    </delete>

    <delete id="__deleteInner" parameterType="${cndType}">
        delete from ${table.tableName}
        <include refid="WhereClause" />
    </delete>
</mapper>