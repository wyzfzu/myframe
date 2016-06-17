// Copyright (C) 2016 Meituan
// All rights reserved
package com.myframe.core.util;

import java.util.List;

/**
 * @author wuyuzhen
 * @version 1.0
 * @created 16/6/16 10:33
 */
public interface Pageable<T> {
    public int getPageSize();

    public int getPageNo();

    public int getTotalCount();

    public void setTotalCount(int totalCount);

    public void setList(List<T> list);

    public List<T> getList();
}
