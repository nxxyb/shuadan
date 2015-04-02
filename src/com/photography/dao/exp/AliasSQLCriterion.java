package com.photography.dao.exp;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.SQLCriterion;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.type.Type;

/**
 * 
 * 改造SQLCriterion，允许级联
 * @author 汪晗
 * @since 2012-6-27
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class AliasSQLCriterion extends SQLCriterion {
	
	private static final long serialVersionUID = -1180163065880885630L;
	
	private Map<String,String> aliasMap = new HashMap<String,String>();
	
	public void addAlias(String propertyName) {
		aliasMap.put(propertyName, propertyName);
	}

	public AliasSQLCriterion(String sql, Object[] values, Type[] types) {
		super(sql, values, types);
	}
	
	@Override
	public String toSqlString(
			Criteria criteria,
			CriteriaQuery criteriaQuery)
		throws HibernateException {
			String sql = StringHelper.replace( this.toString(), "{alias}", criteriaQuery.getSQLAlias(criteria) );
			for(String propertyName : aliasMap.values()) {
				sql = StringHelper.replace( sql, "{"+propertyName+"}", criteriaQuery.getSQLAlias(criteria,propertyName) );
			}
			return sql;
		}

}
