package com.photography.dao.formatter;

import com.photography.utils.StringUtil;




/**
 * 模糊查询格式化类
 * 为模糊查询的参数值加上匹配符
 * @author 汪晗 
 * @since 2011-3-22 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class LikeFormatter implements Formatter {

	public Object format(Object obj) {
		if(obj!=null && !StringUtil.isEmpty(obj.toString()))
		{
			return "%"+obj+"%";
		}
		return null;
	}

}
