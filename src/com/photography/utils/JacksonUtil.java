package com.photography.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson JSON 解析
 * 
 * @author 汪晗
 * @since 2012-10-17
 * 
 * @Copyright (C) 2012 天津天大求实电力新技术股份有限公司 版权所有
 */
public class JacksonUtil {
	public static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper;
	}

}
