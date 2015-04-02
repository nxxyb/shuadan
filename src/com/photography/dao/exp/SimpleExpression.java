package com.photography.dao.exp;


/**
 * 大于小于的表达式
 * 提供基本的表达式
 * @author 杜凯 
 * @since 2011-8-11 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class SimpleExpression extends PropertyExpression {

	
	private static final long serialVersionUID = -3139430839900462113L;

	protected Object object;

	protected String simpleExpression;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getSimpleExpression() {
		return simpleExpression;
	}

	public void setSimpleExpression(String simpleExpression) {
		this.simpleExpression = simpleExpression;
	}

	
	public SimpleExpression(String propertyName, Object object,
			String simpleExpression) {
		super();
		this.propertyName = propertyName;
		this.object = object;
		this.simpleExpression = simpleExpression;
	}

	@Override
	public Expression clone() {
		return new SimpleExpression(propertyName,object,simpleExpression);
	}

}
