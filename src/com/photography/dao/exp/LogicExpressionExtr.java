package com.photography.dao.exp;

import java.util.ArrayList;
import java.util.List;

/**
 * LogicExpressionExtr
 * 逻辑表达式
 * @author 门宏建 
 * @since 2011-8-11 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class LogicExpressionExtr extends Expression {

	private static final long serialVersionUID = -5477270650928332876L;

	protected List<Expression> expressions;

	protected String logic;

	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}

	public List<Expression> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<Expression> expressions) {
		this.expressions = expressions;
	}

	public LogicExpressionExtr(List<Expression> expressions, String logic) {
		super();
		this.expressions = expressions;
		this.logic = logic;
	}

	@Override
	public Expression clone() {
		List<Expression> newExprs = new ArrayList<Expression>();
		if(expressions!=null) {
			for(Expression expression : expressions) {
				newExprs.add(expression.clone());
			}
		}
		return new LogicExpressionExtr(newExprs,logic);
	}
	
	

}
