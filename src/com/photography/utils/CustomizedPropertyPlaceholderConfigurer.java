package com.photography.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 
 * @author 徐雁斌
 * @since 2015-3-2
 * 
 * @copyright 2015 天大求实电力新技术股份有限公司 版权所有
 */
public class CustomizedPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private static Map<String, Object> ctxPropertiesMap;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			try {
				String keyStr = key.toString();
				String value = new String(props.getProperty(keyStr).getBytes("ISO-8859-1"),"utf-8");
				ctxPropertiesMap.put(keyStr, value);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		}
	}

	public static Object getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}
}
