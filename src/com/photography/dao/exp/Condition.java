package com.photography.dao.exp;

import java.util.List;

import org.hibernate.criterion.Criterion;

import com.photography.dao.query.QueryConstants;


/**
 * QBC查询条件
 * 
 * @author 汪晗 
 * @since 2011-11-22 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public final class Condition {


	private Condition() {}

	public static final String AND = "and";

	public static final String OR = "or";

	/**
	 * 等于
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression eq(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value,
				QueryConstants.EQ);
	}

	/**
	 * 小于
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression lt(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value,
				QueryConstants.LT);
	}

	/**
	 * 大于
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression gt(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value,
				QueryConstants.GT);
	}

	/**
	 * 小于等于
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression le(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value,
				QueryConstants.LE);
	}

	/**
	 * 大于等于
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression ge(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value,
				QueryConstants.GE);
	}

	/**
	 * 不等于
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression ne(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value,
				QueryConstants.NE);
	}

	/**
	 * like
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression like(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value,
				QueryConstants.LIKE);
	}

	/**
	 * like
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static Expression ilike(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value,
				QueryConstants.LIKE);
	}

	/**
	 * 逻辑与
	 * 
	 * @param leftException
	 * @param rightexception
	 * @return
	 */
	public static Expression and(Expression leftException, Expression rightexception) {
		return new LogicExpression(leftException, rightexception,
				AND);
	}

	/**
	 * 逻辑或
	 * 
	 * @param leftException
	 * @param rightexception
	 * @return
	 */
	public static Expression or(Expression leftException, Expression rightexception) {
		return new LogicExpression(leftException, rightexception,
				OR);
	}

	/**
	 * 逻辑非
	 * 
	 * @param expression
	 * @return
	 */
	public static Expression not(Expression expression) {
		return new NoExpression(expression);
	}

	/**
	 * between
	 */
	public static Expression between(String propertyName, Object leftObject,
			Object rightObject) {
		return new BetweenExpression(leftObject, rightObject,
				propertyName);
	}

	/**
	 * in
	 */
	public static Expression in(String propertyName, List<? extends Object> list) {
		return new SimpleInExpression(propertyName, list);
	}

	/**
	 * 逻辑与
	 * 
	 * @param expressions
	 * @return
	 */
	public static Expression and(List<Expression> expressions) {
		return new LogicExpressionExtr(expressions, AND);
	}

	/**
	 * 逻辑或
	 *
	 * @param expressions
	 * @return
	 */
	public static Expression or(List<Expression> expressions) {
		return new LogicExpressionExtr(expressions, OR);
	}
	
	/**
	 * 装饰原生的查询条件
	 * 
	 * @param criterion
	 * @return
	 */
	public static Expression decorate(Criterion criterion) {
		return new CriterionExpression(criterion);
	}

}
