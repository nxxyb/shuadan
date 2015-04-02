package com.photography.dao.query;

import java.io.Serializable;

/**
 * 
 * 
 * @author 门宏建 
 * @since 2011-8-11 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class Pager implements Serializable {

	private static final long serialVersionUID = 8916582610329737188L;

	// 当前页
	private int currentPage;

	// 页面大小
	private int pageSize;

	// 总记录数
	private int totalCount;

	private int totalPage;
	
	// 偏移量
	private int offset = 0;

	public Pager(){
	}

	public Pager(int currentPage , int pageSize){
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;

		if (pageSize != 0){
			totalPage = ( totalCount + pageSize - 1 ) / pageSize;
		}
	}

	public int getFromRowIndex() {
		if(currentPage ==0){
			return 0;
		}
		return (currentPage - 1) * pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		if(offset > 0 && offset < pageSize) {
			this.offset = offset;
		}
	}
	
	
}
