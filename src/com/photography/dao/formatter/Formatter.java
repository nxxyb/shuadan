package com.photography.dao.formatter;

/**
 * 格式化类
 * 格式化查询条件的值
 * @author 汪晗 
 * @since 2011-3-22 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public interface Formatter {

	/**
	 * 
	 * @function 格式化对象
	 * @param obj 需要处理的对象
	 *
	 * @return 返回格式化后的对象
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	public Object format(Object obj);
}
