package com.photography.dao.formatter;


/**
 * 处理Long类型
 * 
 * @author 汪晗 
 * @since 2011-11-22 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class LongFormatter implements Formatter {

	public Object format(Object obj) {
		return Long.parseLong((String) obj);
	}

}
