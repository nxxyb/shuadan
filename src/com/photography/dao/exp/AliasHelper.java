package com.photography.dao.exp;

import java.util.Map;
import java.util.HashMap;


import com.photography.dao.query.Sort;
import com.photography.dao.query.SortItem;

/**
 * 处理QBC查询中的多级级联问题
 * 
 * @author 汪晗 
 * @since 2011-11-24 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public final class AliasHelper {

	private AliasHelper() {}

	/**
	 * 
	 * @function 在查询表达式中找到需要级联的别名，以map的形式返回
	 * @param
	 *
	 * @return
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	public final static Map<String,String> getAliasMap(final Expression expression) {
		Map<String,String> aliasMap = new HashMap<String,String>();
		if(expression instanceof BetweenExpression) {
			BetweenExpression exp = (BetweenExpression) expression;
			if(exp.getPropertyName()!=null && exp.getPropertyName().indexOf(".") > -1) {
				addAliasToMap(aliasMap,exp.getPropertyName());
				exp.setPropertyName(getRenameAlias(exp.getPropertyName()));
			}
		} else if(expression instanceof NoExpression) {
			NoExpression exp = (NoExpression) expression;
			if(exp.getExpression()!=null) {
				aliasMap.putAll(getAliasMap(exp.getExpression()));
			}
		} else if(expression instanceof SimpleExpression) {
			SimpleExpression exp = (SimpleExpression) expression;
			if(exp.getPropertyName()!=null && exp.getPropertyName().indexOf(".") > -1) {
				addAliasToMap(aliasMap,exp.getPropertyName());
				exp.setPropertyName(getRenameAlias(exp.getPropertyName()));
			}
		} else if(expression instanceof SimpleInExpression) {
			SimpleInExpression exp = (SimpleInExpression) expression;
			if(exp.getPropertyName()!=null && exp.getPropertyName().indexOf(".") > -1) {
				addAliasToMap(aliasMap,exp.getPropertyName());
				exp.setPropertyName(getRenameAlias(exp.getPropertyName()));
			}
		} else if(expression instanceof LogicExpression) {
			LogicExpression exp = (LogicExpression) expression;
			if(exp.getLeftExpression()!=null) {
				aliasMap.putAll(getAliasMap(exp.getLeftExpression()));
			}
			if(exp.getRightExpression()!=null) {
				aliasMap.putAll(getAliasMap(exp.getRightExpression()));
			}
		} else if(expression instanceof LogicExpressionExtr) {
			LogicExpressionExtr exp = (LogicExpressionExtr) expression;
			for(Expression subExp:exp.getExpressions()) {
				aliasMap.putAll(getAliasMap(subExp));
			}
		}
		return aliasMap;
	}
	
	public final static Map<String,String> getAliasMap(final Sort sort) {
		Map<String,String> aliasMap = new HashMap<String,String>();
		for(SortItem item : sort.getSortItemList()) {
			if(item.getFieldName()!=null && item.getFieldName().indexOf(".") > -1) {
				addAliasToMap(aliasMap,item.getFieldName());
				item.setFieldName(getRenameAlias(item.getFieldName()));
			}
		}
		return aliasMap;
	}

	/**
	 * 
	 * @function 解析属性名称，并将相应的别名存入map中
	 * @param aliasMap 别名map
	 * @param propertyName 属性名称
	 * 
	 * department.name : department -> department
	 * department.company.name : department -> department department.company -> department_company
	 * department.company.parent.name : department -> department department.company -> department_company department_company.parent -> department_company_parent
	 *
	 * @return
	 * @exception
	 *
	 * @author
	 * @history
	 *
	 */
	public static void addAliasToMap(Map<String,String> aliasMap,final String propertyName) {
		if(propertyName==null || propertyName.indexOf(".") == -1) {
			return;
		}
		String fullPropertyName = new String(propertyName);
		String rightPath = fullPropertyName.substring(fullPropertyName.indexOf(".")+1);
		while(true) {
			String alias = fullPropertyName.substring(0, fullPropertyName.lastIndexOf(rightPath)-1);
			aliasMap.put(getRenameAlias(alias), alias.replace(".", "_"));
			int index = rightPath.indexOf(".");
			if(index==-1) {
				break;
			}
			else {
				rightPath = rightPath.substring(index+1);
			}
		}
	}

	/**
	 * 
	 * @function 将原先的属性名进行重命名以符合转化后的别名
	 * @param propertyName 属性名
	 *
	 * department.name : department.name
	 * department.company.name : department_company.name
	 * department.company.parent.name : department_company_parent.name
	 *
	 * @return
	 * @exception
	 *
	 * @author
	 * @history
	 *
	 */
	public static String getRenameAlias(final String propertyName) {
		int lastIndex = propertyName.lastIndexOf(".");
		if(propertyName.indexOf(".")==lastIndex) {
			return propertyName;
		}
		else {
			return propertyName.substring(0, lastIndex).replace(".", "_")+propertyName.substring(lastIndex);
		}
	}

//	public static void main(String[] args) {
//		System.out.println(getRenameAlias("name"));
//		System.out.println(getRenameAlias("department.name"));
//		System.out.println(getRenameAlias("department.company.name"));
//		System.out.println(getRenameAlias("department.company.parent.name"));
//		Map<String,String> map = new HashMap<String,String>();
//		addAliasToMap(map,"department.name");
//		addAliasToMap(map,"department.company.name");
//		addAliasToMap(map,"department.company.parent.name");
//		System.out.println(map.size());
//	}
}
