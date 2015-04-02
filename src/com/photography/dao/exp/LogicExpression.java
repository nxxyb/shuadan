package com.photography.dao.exp;

/**
 * LogicExpression
 * 逻辑表达式
 * @author 门宏建 
 * @since 2011-8-11 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class LogicExpression extends Expression {

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
	private static final long serialVersionUID = -7764899329859617180L;

	protected Expression leftExpression;

	protected Expression rightExpression;

	protected String logic;

	public LogicExpression(Expression leftExpression,
			Expression rightExpression, String logic) {
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
		this.logic = logic;
	}

	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}

	public Expression getLeftExpression() {
		return leftExpression;
	}

	public void setLeftExpression(Expression leftExpression) {
		this.leftExpression = leftExpression;
	}

	public Expression getRightExpression() {
		return rightExpression;
	}

	public void setRightExpression(Expression rightExpression) {
		this.rightExpression = rightExpression;
	}

	@Override
	public Expression clone() {
		return new LogicExpression(leftExpression!=null?leftExpression.clone():null,rightExpression!=null?rightExpression.clone():null,logic);
	}

}
