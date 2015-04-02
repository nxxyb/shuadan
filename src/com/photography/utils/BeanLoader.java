package com.photography.utils;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 在spring上下文环境之外获得实体对象
 * @author 汪晗 
 * @since 2011-6-8 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public final class BeanLoader implements ApplicationContextAware{
	
	//Spring应用上下文环境
	private static ApplicationContext applicationContext;     
	
	/**
	 * 用于获取spring对象，适用于有struts2上下文环境
	 * @author 汪晗
	 * @param beanName 实体名
	 * @return 实体对象
	 */
	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	/**
	 * 用于获取spring对象，适用于Listener,Filter
	 * @author 汪晗
	 * @param beanName 实体名
	 * @param sc servlet上下文
	 * @return 实体对象
	 */
	public static Object getBean(ServletContext sc,String beanName) {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
		return context.getBean(beanName);
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BeanLoader.applicationContext = applicationContext;
	}
	
	public static String[] getBeansByParentClass(Class parentClass) {
		return applicationContext.getBeanNamesForType(parentClass);
	}
}
