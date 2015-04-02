package com.photography.dao.exp;

/**
 * 
 * 
 * @author 汪晗
 * @since 2013-1-31
 * 
 * @Copyright (C) 2013 天津天大求实电力新技术股份有限公司 版权所有
 */
public interface ExpressionVisitor {
	
	/**
	 * 返回表达式匹配的结果
	 * @param expression
	 * @return
	 * @author 汪晗
	 */
	public boolean find(PropertyExpression expression);
}
