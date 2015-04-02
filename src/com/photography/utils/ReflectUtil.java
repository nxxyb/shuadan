package com.photography.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * 反射工具
 *
 * @since 2011-11-18
 * @author Zhao Zhihong
 *
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class ReflectUtil {

	/**
	 * 加载类
	 * @param className 完整的类名（包名+类名）
	 * @return
	 * @throws Exception 类找不到时抛出该异常
	 *
	 * @author Zhao Zhihong
	 */
	public static Class<?> loadClass(String className) throws Exception{
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new Exception(e);
		}
	}

	/**
	 * 通过Java反射机制创建类实例
	 * @param clz 类
	 * @return 类的实例对象
	 * @throws Exception 类找不到或者实例化失败时，抛出此异常
	 *
	 * @author Zhao Zhihong
	 */
	public static <T> T createInstance(Class<T> clz) throws Exception {
		try {
			return clz.newInstance();
		} catch (InstantiationException e) {
			throw new Exception(e);
		} catch (IllegalAccessException e) {
			throw new Exception(e);
		}
	}

	/**
	 * 通过Java反射机制创建类实例
	 * @param className 完整的类名（包名+类名）
	 * @return 类的实例对象
	 * @throws Exception 类找不到或者实例化失败时，抛出此异常
	 *
	 * @author Zhao Zhihong
	 */
	public static Object createInstance(String className) throws Exception {
		try {
			return createInstance(Class.forName(className));
		} catch (ClassNotFoundException e) {
			throw new Exception(e);
		}
	}

	/**
	 * 创建属性实例
	 * @param bean 类
	 * @param property 属性
	 * @throws Exception 当属性的类型对象没有提供空参构造方法时，抛出此异常
	 *
	 * @author Zhao Zhihong
	 */
	public static void createPropertyInstance(Object bean, String property) throws Exception {

		int index = property.indexOf('.');

		try {
			if (index == -1){
				if (PropertyUtils.getProperty(bean, property) != null){
					//如果属性已经被实例化，直接返回
					return;
				}

				PropertyDescriptor desc = getAllScopeDescriptor(bean.getClass(), property);

				String propertyType = desc.getPropertyType().getName();
				
				Object instance ;
				if (propertyType.indexOf('.') == -1 || propertyType.startsWith("java.lang")){
					//属性描述器不存在，或者属性是java基本数据类型，或者属性是java.lang包下的类时，不做实例化
					return;
				}else if (propertyType.equals("java.math.BigDecimal")){
					instance = new BigDecimal(0);
				}else{
				//实例化属性，属性类必须提供空参构造方法，否则抛出InstantiationException
					instance = desc.getPropertyType().newInstance();
				}

				BeanUtils.setProperty(bean, property, instance);
			} else {
				//取得宿主
				String parasitic = property.substring(0, index);
				//取得子属性
				String suf = property.substring(index + 1, property.length());

				if (PropertyUtils.getProperty(bean, parasitic) == null){
					//宿主对象为null时，先实例化宿主
					createPropertyInstance(bean, parasitic);
				}

				//实例化子属性
				createPropertyInstance(PropertyUtils.getProperty(bean, parasitic), suf);
			}
		} catch (IllegalAccessException e) {
			throw new Exception(e);
		} catch (InvocationTargetException e) {
			throw new Exception(e);
		} catch (InstantiationException e) {
			throw new Exception(e);
		} catch (NoSuchMethodException e) {
			throw new Exception(e);
		}
	}

	/**
	 * 设置属性的值
	 * @param bean 对象实例
	 * @param property 属性名，可以为属性表达式（如：user.deparment.company.id）
	 * @param value 值
	 * @throws Exception 设置属性值失败时抛出此异常
	 *
	 * @author Zhao Zhihong
	 */
	public static void setProperty(Object bean, String property, Object value) throws Exception {
		if (value == null || property == null || property.endsWith(".class")) {
			return;
		}
        setPropertyByInvoke(bean,property,value);
        /*
		if (property.indexOf('.') != -1 && getProperty(bean, property) == null){
			//当属性是子属性时，先实例化子属性宿主
			createPropertyInstance(bean, property);
		}


		try {
			BeanUtils.setProperty(bean, property, value);
		} catch (IllegalAccessException e) {
			throw new Exception(e);
		} catch (InvocationTargetException e) {
			throw new Exception(e);
		}
        */
	}

	/**
	 * 设置属性的字符串值（当传入的字符串值为空串或null时，不设置）
	 * @param bean 对象实例
	 * @param property 属性名，可以为属性表达式（如：user.deparment.company.id）
	 * @param value 值
	 * @throws Exception 设置属性值失败时抛出此异常
	 *
	 * @author Zhao Zhihong
	 */
	public static void setStringProperty(Object bean, String property, String value) throws Exception{
		if (value != null && !"".equals(value.trim())) {
			setProperty(bean, property, value);
		}
	}
	
	/**
	 * 设置属性的值为null
	 * @param bean 对象实例
	 * @param property 属性名，可以为属性表达式（如：user.deparment.company.id）
	 * @throws Exception 设置属性值失败时抛出此异常
	 *
	 * @author Zhao Zhihong
	 */
	public static void setNullProperty(Object bean, String property) throws Exception{
		if (property.indexOf('.') != -1 && getProperty(bean, property) == null){
			//当属性是子属性时，先实例化子属性宿主
			createPropertyInstance(bean, property);
		}

		try {
			PropertyUtils.setProperty(bean, property, null);
		} catch (IllegalAccessException e) {
			throw new Exception(e);
		} catch (InvocationTargetException e) {
			throw new Exception(e);
		} catch (NoSuchMethodException e) {
			throw new Exception(e);
		}
	}

	/**
	 * 取得属性表达式的宿主
	 * @param property 属性表达式（如：user.deparment.company.id）
	 * @return 宿主（如：user.deparment.company）
	 *
	 * @author Zhao Zhihong
	 */
	public static String getPropertyParasitic(String property){
		int index = property.lastIndexOf('.');
		if (index != -1){
			return property.substring(0,index);
		}

		return property;
	}

	/**
	 * 取得属性表达式的子孙
	 * @param property 属性表达式（如：user.deparment.company.id）
	 * @return 子孙（如：id）
	 *
	 * @author 陈立伟
	 */
	public static String getPropertyPosterity(String property){
		int index = property.lastIndexOf('.');
		if (index != -1){
			return property.substring(index+1);
		}
		return property;
	}

	/**
	 * 取对象的属性值，忽略异常
	 * @param bean 对象实例
	 * @param property 属性名
	 * @return
	 *
	 * @author Zhao Zhihong
	 */
	public static Object getProperty(Object bean, String property) {
        return getPropertyByInvoke(bean,property);
        /*
		try {
			return PropertyUtils.getProperty(bean, property);
		} catch (Exception e) {
			return null;
		}*/
	}

	/**
	 * 从对象Map中取属性值，并将其转换成字符串表示
	 * @param bean MAP表示的对象
	 * @param property 属性名称, 可以为属性表达式（如: property.sub.id）
	 * @return
	 * @throws Exception
	 * 
	 * @author Zhao Zhihong
	 */
	public static String getStringProperty(Map<String, ? extends Object> bean, String property) throws Exception {
		if (bean == null || property == null){
			return null;
		}

		if (bean.get(property) != null){
			Object value = bean.get(property);

			if (value == null){
				return "";
			} else if (value instanceof Double){
				DecimalFormat df = new DecimalFormat("0.######");
				return df.format(value);
			} else if (value instanceof BigDecimal){
				DecimalFormat df = new DecimalFormat("0.######");
				return df.format(value);
			} else {
				return value.toString();
			}
		} else {
			if (property.indexOf('.') == -1) {
				return null;
			} else {
				int index = property.indexOf('.');
				String pre = property.substring(0, index);
				String suf = property.substring(index+1, property.length());

				Object value = bean.get(pre);

				if (value == null){
					return null;
				} else {
					return getStringProperty(describe(value), suf);
				}
			}
		}
	}

	/**
	 * 取对象的属性值，并将其转换成字符串表示
	 * @param bean 对象
	 * @param property 属性名称, 可以为属性表达式（如: property.sub.id）
	 * @return
	 * @throws Exception
	 * 
	 * @author Zhao Zhihong
	 */
	public static <T> String getStringProperty(T bean, String property) throws Exception {
		return getStringProperty(describe(bean), property);
	}

	/**
	 * 取得属性描述器
	 * @param clz 类
	 * @param property 属性名
	 * @return 属性描述器
	 *
	 * @author Zhao Zhihong
	 */
	private static PropertyDescriptor getDescriptor(Class<?> clz, String property){
		PropertyDescriptor[] describers = PropertyUtils.getPropertyDescriptors(clz);

		for (PropertyDescriptor desc : describers){
			if (desc.getName().equals(property)){
				return desc;
			}
		}

		return null;
	}

	/**
	 * 以Map属性值对的方式描述一个对象实例
	 * @param bean 对象实例
	 * @return
	 * @throws Exception
	 *
	 * @author Zhao Zhihong
	 */
	public static Map<String, Object> describe(Object bean) throws Exception{
		Map<String, Object> description = new HashMap<String, Object>();

		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
		for (PropertyDescriptor descriptor : descriptors) {
			String name = descriptor.getName();
			if (descriptor.getReadMethod() != null) {
				Object value = getProperty(bean, name);
				description.put(name, value);
			}
		}

		return description;
	}

	/**
	 * 以Map属性值对的方式描述一个对象实例，Map的key为属性表达式（如property.sub.id），value为对应的值
	 * <B>注意：</B>集合对象被忽略
	 * @param bean
	 * @return
	 * @throws Exception
	 *
	 * @author Zhao Zhihong
	 */
	public static Map<String, String> beanToMap(Object bean) throws Exception {
		if (bean == null) {
			throw new IllegalArgumentException("No bean specified");
		}

		Map<String, String> description = new HashMap<String, String>();
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
		for (PropertyDescriptor descriptor : descriptors) {
			String property = descriptor.getName();

			//属性描述器，对有一个类名描述，忽略
			if ("class".equals(property)){
				continue;
			}

			if (descriptor.getReadMethod() != null) {
				Object value = getProperty(bean, property);

				//忽略集合属性
				if (value instanceof Collection){
					continue;
				}

				if (value == null) {
					description.put(property, null);
				} else {
					if (value.getClass().getName().startsWith("java")){
						//java内置对象，调用toString输出
						description.put(property, value.toString());
					} else {
						Map<String, String> valueMap = beanToMap(value);

						for (Entry<String, String> entry : valueMap.entrySet()){
							String key = entry.getKey();
							String val = entry.getValue();
							description.put(property + "." + key, val);
						}
					}
				}
			}
		}

		return description;
	}

	/**
	 * 以Map属性值对集合的方式描述对象集合，Map的key为属性表达式（如property.sub.id），value为对应的值
	 * <B>注意：</B>集合对象被忽略
	 * @param dataset 对象集合
	 * @return
	 * @throws Exception
	 *
	 * @author Zhao Zhihong
	 */
	public static List<Map<String, String>> beanToMap(List<?> dataset) throws Exception{
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();

		if (dataset != null && !dataset.isEmpty()){
			for (Object data : dataset){
				results.add(beanToMap(data));
			}
		}

		return results;
	}

	/**
	 * 用Map表示的属性值对，创建对象实例
	 * @param <T> 对象类型
	 * @param model 类
	 * @param data Map数据
	 * @return
	 * @throws Exception
	 *
	 * @author Zhao Zhihong
	 */
	public static <T> T mapToBean(Class<? extends T> model, Map<String, String> data) throws Exception{
		T bean = createInstance(model);

		for (Entry<String, String> entry : data.entrySet()){
			String property = entry.getKey();
			String value = entry.getValue();

			if (value != null){
				setStringProperty(bean, property, value);
			}
		}

		return bean;
	}
	
	/**
	 * 获取全局范围的属性,包括级联属性
	 * @param clz
	 * @param propertyName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @author 汪晗
	 */
	public static PropertyDescriptor getAllScopeDescriptor(Class clz,String propertyName) throws SecurityException {
		if(clz!=null && propertyName!=null) {
			if(propertyName.indexOf(".")==-1) {
				return getDescriptor(clz,propertyName);
			}
			else {
				String firstChild = propertyName.substring(0, propertyName.indexOf("."));
				String childPropertyName = propertyName.substring(propertyName.indexOf(".")+1);
				PropertyDescriptor childDesc = getDescriptor(clz, firstChild);
				if(childDesc!=null) {
					return getAllScopeDescriptor(childDesc.getPropertyType(),childPropertyName);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取指定属性名的直接对应类名
	 * @param rootClass
	 * @param propertyName
	 * @return
	 * @author 汪晗
	 */
	public static Class getDirectClass(Class rootClass,String propertyName) {
		if(rootClass!=null && propertyName!=null) {
			if(propertyName.indexOf(".")==-1) {
				return rootClass;
			}
			else {
				String firstChild = propertyName.substring(0, propertyName.indexOf("."));
				String childPropertyName = propertyName.substring(propertyName.indexOf(".")+1);
				PropertyDescriptor childDesc = getDescriptor(rootClass, firstChild);
				return getDirectClass(childDesc.getPropertyType(),childPropertyName);
			}
		}
		return null;
	}

    public static Object getPropertyByInvoke(Object bean, String property) {
        int index = property.indexOf(".");
        while(index > -1 && bean!=null) {
            String childProperty = property.substring(0, index);
            property = property.substring(index+1);
            bean = getSimplePropertyByInvoke(bean,childProperty);
            index = property.indexOf(".");
        }
        return getSimplePropertyByInvoke(bean,property);
    }

    private static Object getSimplePropertyByInvoke(Object bean, String property) {
        try {
            PropertyDescriptor descriptor = ReflectUtil.getDescriptor(bean.getClass(), property);
            Method method = descriptor.getReadMethod();
            return method.invoke(bean);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setPropertyByInvoke(Object bean, String property,Object value) {
        int index = property.indexOf(".");
        while(index > -1 && bean!=null) {
            String childProperty = property.substring(0, index);
            Object parent = bean;
            bean = getSimplePropertyByInvoke(bean,childProperty);
            if(bean == null) {
                try {
                    PropertyDescriptor descriptor = ReflectUtil.getDescriptor(parent.getClass(), childProperty);
                    Class fieldClass = descriptor.getPropertyType();
                    bean = fieldClass.newInstance();
                } catch(Exception e) {
                    return;
                }
                setSimplePropertyByInvoke(parent,childProperty,bean);
            }
            property = property.substring(index+1);
            index = property.indexOf(".");
        }
        setSimplePropertyByInvoke(bean,property,value);
    }

    private static void setSimplePropertyByInvoke(Object bean, String property,Object value) {
        try {
            PropertyDescriptor descriptor = ReflectUtil.getDescriptor(bean.getClass(), property);
            Class fieldClass = descriptor.getPropertyType();
            if(value instanceof String && !fieldClass.equals(String.class)) {
                if(fieldClass.equals(Long.class) || fieldClass.equals(long.class)) {
                    value = (Long.parseLong((String) value));
                }
                else if(fieldClass.equals(Integer.class) || fieldClass.equals(int.class)) {
                    value = (Integer.parseInt((String) value));
                }
                else if(fieldClass.equals(Double.class) || fieldClass.equals(double.class)) {
                    value = (Double.parseDouble((String) value));
                }
                else if(fieldClass.equals(BigDecimal.class) || fieldClass.equals(BigDecimal.class)) {
                    value = new BigDecimal(Double.parseDouble((String) value));
                }
            }
            Method method = descriptor.getWriteMethod();
            method.invoke(bean,value);
        } catch (Exception e) {}
    }
	
}
