package com.photography.dao.query;

/**
 * 
 * 
 * @author 李源 
 * @since 2012-7-16 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public interface ParseListener {
	public void onGetFilterItem(String var);

	public void onGetOperator(char var, String lastToken);

};
