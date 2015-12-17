package com.myframe.dao.util;

import com.google.common.collect.Maps;
import com.myframe.core.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 更新链，用于构造Update操作时的更新字段和条件。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class UpdateChain {
    private Map<String, Object> params;
    private Cnd cnd;

    public UpdateChain() {
        params = Maps.newHashMap();
        cnd = Cnd.where();
    }

    public static UpdateChain make(String name, Object value) {
        return new UpdateChain().add(name, value);
    }

    public UpdateChain add(String name, Object value) {
        String field = StringUtils.fromCamelCase(name, '_').toLowerCase();
        params.put(field, value);
        return this;
    }

    public UpdateChain where(Cnd cnd) {
        this.cnd = cnd;
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public Cnd getCnd() {
        return cnd;
    }

    public List<Criteria> getOrderCriteria() {
        return cnd.getOrderCriteria();
    }

    public Cnd getInnerCnd() {
        return cnd != null ? cnd.getInnerCnd() : null;
    }
}
