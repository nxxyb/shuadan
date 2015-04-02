package com.photography.dao.exp;

import java.util.List;


/**
 * SimpleInExpression
 * 建议in表达式
 * @author 门宏建 
 * @since 2011-8-11 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class SimpleInExpression extends PropertyExpression {

	/**
	 * @function
	 * @param
	 *
	 * @return
	 * @exception
	 *
	 * @author
	 * @history
	 *
	 */
	private static final long serialVersionUID = 6270792740437008439L;
	
	protected List<? extends Object> list;

	public List<? extends Object> getList() {
		return list;
	}

	public void setList(List<? extends Object> list) {
		this.list = list;
	}

	public SimpleInExpression(String propertyName, List<? extends Object> list) {
		super();
		this.propertyName = propertyName;
		this.list = list;
	}

	@Override
	public Expression clone() {
		return new SimpleInExpression(propertyName,list);
	}

}
