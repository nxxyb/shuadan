package com.photography.dao.query;

import java.io.Serializable;


/**
 * 排序项
 * 
 * @author 汪晗
 * @since 2012-4-19
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class SortItem implements Serializable {
	private String fieldName;
	private String order;
	private String fieldId;
	
	public SortItem(String fieldName,String order) {
		this.fieldName = fieldName;
		this.order = order;
	}
	public SortItem(String fieldName,String order,String fieldId) {
		this.fieldName = fieldName;
		this.order = order;
		this.fieldId = fieldId;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	
	
}
