package com.photography.dao.exp;


public abstract class PropertyExpression extends Expression {
	
	protected String propertyName;
	
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
}
