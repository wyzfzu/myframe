package com.myframe.dao.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Collections;
import java.util.List;

/**
 * 更新链，用于构造Update操作时的更新字段和条件。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public class UpdateChain {
    private OpChain opChain;
    private Cnd cnd;

    public UpdateChain() {
        opChain = new OpChain();
        cnd = Cnd.where();
    }

    public static UpdateChain make() {
        return new UpdateChain();
    }

    public static UpdateChain make(String name, Object value) {
        return make().set(name, value);
    }

    public UpdateChain set(String name, Object value) {
        return add(name, UpdateOp.SET, value);
    }

    public UpdateChain plus(String name, Number value) {
        return add(name, UpdateOp.PLUS, value);
    }

    public UpdateChain minus(String name, Number value) {
        return add(name, UpdateOp.MINUS, value);
    }

    public UpdateChain multiply(String name, Number value) {
        return add(name, UpdateOp.MULTIPLY, value);
    }

    public UpdateChain divide(String name, Number value) {
        return add(name, UpdateOp.DIVIDE, value);
    }

    private UpdateChain add(String name, UpdateOp op, Object value) {
        String field = FieldHelper.wrapFromCamelCase(name, '_');
        opChain.add(field, op, value);
        return this;
    }

    public UpdateChain where(Cnd cnd) {
        this.cnd = cnd;
        return this;
    }

    public List<Triple<String, String, Object>> getOpList() {
        return opChain.getOpList();
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

    private static class OpChain {
        private List<Triple<String, String, Object>> opList;

        public OpChain() {
            opList = Lists.newArrayList();
        }

        public OpChain add(String name, UpdateOp op, Object value) {
            opList.add(Triple.of(name, op.getOp(), value));
            return this;
        }

        public List<Triple<String, String, Object>> getOpList() {
            return Collections.unmodifiableList(opList);
        }
    }

    private enum UpdateOp {
        SET("="),
        PLUS("+"),
        MINUS("-"),
        MULTIPLY("*"),
        DIVIDE("/");

        private String op;

        UpdateOp(String op) {
            this.op = op;
        }

        public String getOp() {
            return op;
        }
    }
}
