package com.photography.dao.formatter;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理Long类型的in查询
 * 
 * @author 汪晗 
 * @since 2011-11-22 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class LongInFormatter implements Formatter {

	public Object format(Object obj) {
		return parseStringToList((String) obj);
	}

	private List<Object> parseStringToList(String str) {
		List<Object> strList = new ArrayList<Object>();
		String[] strs = str.split(",");
		for(String s:strs) {
			strList.add(Long.parseLong(s));
		}
		return strList;
	}

}
