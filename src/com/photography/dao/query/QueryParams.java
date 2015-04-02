package com.photography.dao.query;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;



/**
 * 分页参数
 * 保存分页参数和查询条件
 * @author 汪晗 
 * @since 2011-3-22 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class QueryParams implements Serializable{

	private static final long serialVersionUID = 1L;

	private Sort sorter;
	private Pager pager;
	private FilterMap filter=new FilterMap();
	private Map<String,Object> paramsMap = new HashMap<String, Object>();

	public Sort getSorter() {
		return sorter;
	}

	public void setSorter(Sort sorter) {
		this.sorter = sorter;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public FilterMap getFilter() {
		return filter;
	}

	public void setFilter(FilterMap filter) {
		this.filter = filter;
	}

	public Map<String, Object> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}

}
