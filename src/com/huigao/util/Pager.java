package com.huigao.util;

import java.util.List;

/**
 * 分页处理通用类
 * @author cgx
 * @version 1.0
 * @param <T>数据集合中数据的类型
 */
public class Pager<T> {
	
	private Integer totalCount;
	
	private Integer totalPage;
	
	private Integer pageSize;
	
	private Integer nowPage;
	
	private List<T> data;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getNowPage() {
		return nowPage;
	}

	public void setNowPage(Integer nowPage) {
		this.nowPage = nowPage;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	public String toJsonString() {
		
		return null;
	}
	
}	
