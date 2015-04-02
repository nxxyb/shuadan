package com.photography.dao.exp;


/**
 * BetweenExpression
 * between表达式
 * @author 门宏建 
 * @since 2011-8-11 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class BetweenExpression extends PropertyExpression {
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
	private static final long serialVersionUID = 2044198391127612414L;
	
	protected Object leftObject;
	protected Object rightObject;

	public Object getLeftObject() {
		return leftObject;
	}

	public void setLeftObject(Object leftObject) {
		this.leftObject = leftObject;
	}

	public Object getRightObject() {
		return rightObject;
	}

	public void setRightObject(Object rightObject) {
		this.rightObject = rightObject;
	}

	public BetweenExpression(Object leftObject, Object rightObject,
			String propertyName) {
		super();
		this.leftObject = leftObject;
		this.rightObject = rightObject;
		this.propertyName = propertyName;
	}

	@Override
	public Expression clone() {
		return new BetweenExpression(leftObject,rightObject,propertyName);
	}

}
