package com.photography.dao.exp;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.SQLProjection;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

/**
 * sql投影扩展，允许多级别名
 * 
 * @author 汪晗
 * @since 2013-1-15
 * 
 * @Copyright (C) 2013 天津天大求实电力新技术股份有限公司 版权所有
 */
public class AliasSQLProjection extends SQLProjection {
	
	private Map<String,String> aliasMap = new HashMap<String,String>();
	
	public void addAlias(String propertyName) {
		aliasMap.put(propertyName, propertyName);
	}
	
	public Map<String,String> getAliasMap() {
		return aliasMap;
	}

	protected AliasSQLProjection(String sql, String groupBy, String[] columnAliases, Type[] types) {
		super(sql, groupBy, columnAliases, types);
	}
	
	public AliasSQLProjection(String sql, String[] columnAliases, Type[] types) {
		super(sql, null, columnAliases, types);
	}
	
	public AliasSQLProjection(String sql) {
		super(sql, null, new String[]{"alias"},new Type[]{StringType.INSTANCE});
	}
	
	public String toSqlString(
			Criteria criteria, 
			int loc, 
			CriteriaQuery criteriaQuery)
	throws HibernateException {
		String sql = super.toSqlString(criteria, loc, criteriaQuery);
		for(String propertyName : aliasMap.values()) {
			sql = StringHelper.replace( sql, "{"+getPropertyNameAlias(propertyName)+"}", criteriaQuery.getSQLAlias(criteria,AliasHelper.getRenameAlias(propertyName)));
		}
		sql = sql + " as y"+loc+"_";
		this.getColumnAliases(loc)[0]="y"+loc+"_";
		return sql;
	}

	public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
	throws HibernateException {
		String groupBy = super.toGroupSqlString(criteria, criteriaQuery);
		for(String propertyName : aliasMap.values()) {
			groupBy = StringHelper.replace( groupBy, "{"+getPropertyNameAlias(propertyName)+"}", criteriaQuery.getSQLAlias(criteria,AliasHelper.getRenameAlias(propertyName)));
		}
		return groupBy;
	}
	
	public static String getSqlPropertyName(String propertyName) {
		String sqlPropertyName = "{" + getPropertyNameAlias(propertyName) + "}" + "." +getLastPropertyName(propertyName);
		return sqlPropertyName;
	}
	
	private static String getPropertyNameAlias(String propertyName) {
		if(propertyName==null) {
			return null;
		}
		if(propertyName.indexOf(".")==-1) {
			return "alias";
		} else {
			int lastIndex = propertyName.lastIndexOf(".");
			return propertyName.substring(0, lastIndex);
		}
	}
	
	private static String getLastPropertyName(String propertyName) {
		if(propertyName==null) {
			return null;
		}
		if(propertyName.indexOf(".")==-1) {
			return propertyName;
		} else {
			int lastIndex = propertyName.lastIndexOf(".");
			return propertyName.substring(lastIndex+1);
		}
	}
	
}
