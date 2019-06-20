package com.myframe.dao.util;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 18/5/11
 */
public class Agg {
    // 聚合类型
    private AggType type;
    // 聚合字段
    private String fieldName;
    // 聚合结果别名
    private String aliasName;
    // 其余查询字段
    private List<String> extraFields = Lists.newArrayList();

    public static Agg min(String field, String alias) {
        return make(AggType.MIN, field, alias);
    }

    public static Agg max(String field, String alias) {
        return make(AggType.MAX, field, alias);
    }

    public static Agg avg(String field, String alias) {
        return make(AggType.AVG, field, alias);
    }

    public static Agg sum(String field, String alias) {
        return make(AggType.SUM, field, alias);
    }

    public static Agg count(String field, String alias) {
        return make(AggType.COUNT, field, alias);
    }

    public static Agg make() {
        return new Agg();
    }

    public static Agg make(AggType type, String field) {
        return make().type(type).field(field);
    }

    public static Agg make(AggType type, String field, String alias) {
        return make().type(type).field(field).alias(alias);
    }

    public static Agg make(AggType type, String field, String alias, String... extraFields) {
        return make(type, field, alias).extraFields(extraFields);
    }

    public static Agg make(AggType type, String field, String alias, Collection<String> extraFields) {
        return make(type, field, alias).extraFields(extraFields);
    }

    public Agg type(AggType type) {
        this.type = type;
        return this;
    }

    public Agg field(String fieldName) {
        this.fieldName = FieldHelper.fromCamelCase(fieldName);
        return this;
    }

    public Agg alias(String aliasName) {
        this.aliasName = aliasName;
        return this;
    }

    public Agg extraFields(String... fields) {
        if (fields == null || fields.length < 1) {
            return this;
        }
        return extraFields(Arrays.asList(fields));
    }

    public Agg extraFields(Collection<String> fields) {
        if (fields == null || fields.isEmpty()) {
            return this;
        }
        if (this.extraFields == null) {
            this.extraFields = Lists.newArrayList();
        }
        this.extraFields.addAll(
                fields.stream()
                        .map(FieldHelper::wrapFromCamelCase)
                        .collect(Collectors.toList())
        );
        return this;
    }

    public AggType getType() {
        return type;
    }

    public void setType(AggType type) {
        this.type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        field(fieldName);
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public List<String> getExtraFields() {
        return extraFields;
    }

    public void setExtraFields(List<String> extraFields) {
        extraFields(extraFields);
    }
}
