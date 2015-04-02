package com.photography.dao.query;

import com.photography.dao.exp.Condition;
import com.photography.dao.exp.Expression;
import com.photography.dao.exp.SimpleExpression;


/**
 * QueryCriterions
 * 组合查询表达式的工具类
 * @author 门宏建 
 * @since 2011-8-11 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
@Deprecated
public class QueryCriterions {

	private Expression expression;
	private Sort sort;

	public QueryCriterions and(Expression expression) {
		if (expression == null) {
			return this;
		}
		if (this.expression == null) {
			this.expression = expression;
		} else {
			this.expression = Condition.and(this.expression, expression);
		}
		return this;
	}

	public QueryCriterions or(Expression expression) {
		if (expression == null) {
			return this;
		}
		if (this.expression == null) {
			this.expression = expression;
		} else {
			this.expression = Condition.or(this.expression, expression);
		}
		return this;
	}

	public QueryCriterions and(String fieldName, Object value, String condition) {
		Expression newExpression = new SimpleExpression(fieldName, value, condition);
		return this.and(newExpression);
	}

	public QueryCriterions or(String fieldName, Object value, String condition) {
		Expression newExpression = new SimpleExpression(fieldName, value, condition);
		return this.or(newExpression);
	}

	public QueryCriterions sort(String fieldName, String sort) {
		this.sort = new Sort(fieldName,sort);
		return this;
	}

	public Expression toExpression() {
		return expression;
	}

	public Sort getSort() {
		return sort;
	}

	public void clear() {
		this.expression = null;
		this.sort = null;
	}

}
