package com.photography.dao.exp;

import org.hibernate.criterion.Criterion;

/**
 * 
 * 
 * @author 李源 
 * @since 2012-7-16 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class CriterionExpression extends Expression {

	private static final long serialVersionUID = 3857909889940204332L;
	
	private Criterion criterion;
	
	CriterionExpression(Criterion criterion) {
		this.criterion = criterion;
	}

	public Criterion getCriterion() {
		return criterion;
	}

	public void setCriterion(Criterion criterion) {
		this.criterion = criterion;
	}

	@Override
	public Expression clone() {
		return new CriterionExpression(criterion);
	}
	
}	
