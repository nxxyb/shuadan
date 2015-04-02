package com.photography.dao.exp;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

/**
 * 对投影的包装
 * 
 * @author 汪晗
 * @since 2013-1-15
 * 
 * @Copyright (C) 2013 天津天大求实电力新技术股份有限公司 版权所有
 */
public final class AliasProjection {
	private String propertyName;
	private String functionName;
	private Projection projection;
	
	public AliasProjection(String functionName,String propertyName) {
		this.functionName = functionName;
		this.propertyName = propertyName;
	}
	
	public AliasProjection(AliasSQLProjection projection) {
		this.projection = projection;
	}
	
	public AliasProjection(String functionName,String propertyName,Projection projection) {
		this.functionName = functionName;
		this.propertyName = propertyName;
		this.projection = projection;
	}


	public String getPropertyName() {
		return propertyName;
	}

	public String getFunctionName() {
		return functionName;
	}
	
	public Projection getProjection() {
		if(projection instanceof AliasSQLProjection) {
			return projection;
		}
		if("sum".equals(functionName)) {
			return Projections.sum(AliasHelper.getRenameAlias(propertyName));
		} else if("max".equals(functionName)) {
			return Projections.max(AliasHelper.getRenameAlias(propertyName));
		} else if("min".equals(functionName)) {
			return Projections.min(AliasHelper.getRenameAlias(propertyName));
		} else if("avg".equals(functionName)) {
			return Projections.avg(AliasHelper.getRenameAlias(propertyName));
		} else if("count".equals(functionName)) {
			return Projections.count(AliasHelper.getRenameAlias(propertyName));
		}
		return projection;
	}
	
	public static AliasProjection sum(String propertyName) {
		return new AliasProjection("sum",propertyName,Projections.sum(propertyName));
	}
	
	public static AliasProjection max(String propertyName) {
		return new AliasProjection("max",propertyName,Projections.max(propertyName));
	}
	
	public static AliasProjection min(String propertyName) {
		return new AliasProjection("min",propertyName,Projections.min(propertyName));
	}
	
	public static AliasProjection avg(String propertyName) {
		return new AliasProjection("avg",propertyName,Projections.avg(propertyName));
	}
	
	public static AliasProjection count(String propertyName) {
		return new AliasProjection("count",propertyName,Projections.count(propertyName).setDistinct());
	}
}
