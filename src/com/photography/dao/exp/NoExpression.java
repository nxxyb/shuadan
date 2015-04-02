package com.photography.dao.exp;


/**
 * NoExpression
 * 否定表达式
 * @author 门宏建 
 * @since 2011-8-11 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class NoExpression extends Expression {

	private static final long serialVersionUID = -8956232056126633211L;
	
	protected Expression expression;

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public NoExpression(Expression expression) {
		super();
		this.expression = expression;
	}

	@Override
	public Expression clone() {
		return new NoExpression(expression.clone());
	}

}
