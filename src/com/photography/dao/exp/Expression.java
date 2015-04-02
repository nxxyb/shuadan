package com.photography.dao.exp;

/**
 * 
 * 
 * @author 门宏建 
 * @since 2011-8-11 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public abstract class Expression implements java.io.Serializable {
	
	/**
	 * 复制出一个同样含义的新的表达式
	 * @return 新的表达式
	 * @author 汪晗
	 */
	public abstract Expression clone();
	
	/**
	 * 与传入的表达式做“and”运算
	 * @param expression
	 * @return
	 * @author 汪晗
	 */
	public Expression and(Expression expression) {
		return Condition.and(this, expression);
	}
	
	/**
	 * 与传入的表达式做“or”运算
	 * @param expression
	 * @return
	 * @author 汪晗
	 */
	public Expression or(Expression expression) {
		return Condition.or(this, expression);
	}

    /**
     * and的简便用法
     * @param propertyName
     * @param object
     * @param simpleExpression
     * @return
     * @author 汪晗
     */
    public Expression and(String propertyName, Object object,
                          String simpleExpression) {
        return Condition.and(this, new SimpleExpression(propertyName,object,simpleExpression));
    }

    /**
     * or的简便用法
     * @param propertyName
     * @param object
     * @param simpleExpression
     * @return
     * @author 汪晗
     */
    public Expression or(String propertyName, Object object,
                          String simpleExpression) {
        return Condition.or(this, new SimpleExpression(propertyName,object,simpleExpression));
    }
	
}
