package com.photography.dao.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * 
 * 
 * @author 门宏建 
 * @since 2011-8-11 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class Sort implements Serializable {

	private static final long serialVersionUID = -7446672831789562504L;
	
	private List<SortItem> sortItemList = new ArrayList<SortItem>();

	public Sort() {
	}

	public Sort(String fieldName, String order) {
		addSort(fieldName, order);
	}

	public List<SortItem> getSortItemList() {
		return sortItemList;
	}

	public void setSortItemList(List<SortItem> sortItemList) {
		this.sortItemList = sortItemList;
	}

	public Sort addSort(String fieldName, String order) {
		sortItemList.add(new SortItem(fieldName, order));
		return this;
	}
	public Sort addSort(String fieldName, String order, String fieldId) {
		sortItemList.add(new SortItem(fieldName, order,fieldId));
		return this;
	}
	public Sort addSort(SortItem item) {
		sortItemList.add(item);
		return this;
	}
	public Sort addSort(String fieldName) {
		return addSort(fieldName,QueryConstants.ASC);
	}
	
	public boolean isEmpty() {
		return sortItemList.isEmpty();
	}

	public void clear() {
		this.sortItemList.clear();
	}
}
