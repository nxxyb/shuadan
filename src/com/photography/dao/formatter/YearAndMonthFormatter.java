package com.photography.dao.formatter;

import com.photography.utils.DateUtil;


/**
 * 日期格式转换
 * 日期格式转换 yyyy年M月
 * @author 汪晗 
 * @since 2011-6-16 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class YearAndMonthFormatter implements Formatter {

	public Object format(Object obj) {
		return DateUtil.string2Date((String) obj,"yyyy年M月");
	}

}
