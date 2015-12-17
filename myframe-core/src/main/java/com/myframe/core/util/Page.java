package com.myframe.core.util;

import java.util.List;

/**
 * 用于分页的工具类
 * 
 * @author wyzfzu (wyzfzu@qq.com)
 * 
 */
public class Page<T> {
    public static final int DEFAULT_PAGE_SIZE = 10;

	/** 当前页号 */
	private Integer pageNo;
	/** 每页记录数量 */
	private Integer pageSize;
	/** 总共有几条记录 */
	private Long totalCount;
	/** 本页记录集 */
	private List<T> content;

	/**
	 * 根据当前页号来构造对象
	 * 
	 * @param pageNo
	 */
	public Page(int pageNo) {
		this.pageNo = pageNo;
		this.pageSize = DEFAULT_PAGE_SIZE;
	}

    public Page(int pageNo, int pageSize) {
        this(pageNo);
        this.pageSize = pageSize;
    }

	/**
	 *  默认构造函数，当前页号默认为1
	 */
	public Page() {
		this(1);
	}

	/**
	 * 获取当前页号
	 * 
	 * @return 当前页号
	 */
	public Integer getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页号
	 * 
	 * @param pageNo
	 * 			新的页号
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 返回上页的页号,序号从1开始.
	 * 
	 * @return 上页的页号
	 */
	public int getPrevPage() {
		if (isHasPrevPage()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 返回下页的页号,序号从1开始.
	 * 
	 * @return 下页的页号
	 */
	public int getNextPage() {
		if (isHasNextPage()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有下一页.
	 * 
	 * @return true或false
	 */
	public boolean isHasNextPage() {
		return (pageNo + 1 <= getTotalPage());
	}

	/**
	 * 是否还有上一页.
	 * 
	 * @return true或false
	 */
	public boolean isHasPrevPage() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 获得每页记录数量.
	 * 
	 * @return 每页记录数量
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页记录数量.
	 * @param pageSize
	 * 			新的每页记录数量
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取总的记录条数
	 * 
	 * @return 总的记录条数
	 */
	public Long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总的记录条数
	 * 
	 * @param totalCount
	 * 			新的总的记录条数
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 获取每页的记录条数
	 * 
	 * @return 每页的记录条数
	 */
	public Integer getTotalPage() {
		return (totalCount % pageSize) > 0 
				? (int) (totalCount / pageSize + 1)
				: (int) (totalCount / pageSize);
	}

	/**
	 * 获得每页的记录集
	 * 
	 * @return 每页的记录集
	 */
	public List<T> getContent() {
		return content;
	}

	/**
	 * 设置每页的记录集
	 * 
	 * @param content
	 * 			新的每页的记录集
	 */
	public void setContent(List<T> content) {
		this.content = content;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 * 
	 * @return 该页记录集的起始位置
	 */
	public Integer getStartIndex() {
		return (pageNo - 1) * pageSize + 1;
	}

	/**
	 * 获得该页记录集的最后的位置
	 * 
	 * @return 该页记录集的最后的位置
	 */
	public Integer getEndIndex() {
		if (null == content) {
			return pageNo * pageSize;
		} else if (content.size() < pageSize) {
			return (pageNo - 1) * pageSize + content.size();
		} else {
			return pageNo * pageSize;
		}
	}
}
