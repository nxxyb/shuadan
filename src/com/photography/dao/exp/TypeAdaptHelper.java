package com.photography.dao.exp;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

/**
 * 参数值类型转换器
 * 强制转换参数值类型
 * @author 汪晗 
 * @since 2011-11-25 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
@SuppressWarnings("rawtypes")
public final class TypeAdaptHelper {
	
	private static Logger log = Logger.getLogger(TypeAdaptHelper.class);

	private TypeAdaptHelper() {}

	/**
	 * @function 对于参数值类型为String的查询条件，需要将参数值转换为对应属性的类型
	 * @param
	 *
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public final static void typeAdapt(Expression expression,final Class entityClass) throws SecurityException, NoSuchFieldException {
		if(expression instanceof BetweenExpression) {
			// Nothing to do
		} else if(expression instanceof NoExpression) {
			NoExpression exp = (NoExpression) expression;
			if(exp.getExpression()!=null) {
				typeAdapt(exp.getExpression(),entityClass);
			}
		} else if(expression instanceof SimpleExpression) {
			SimpleExpression exp = (SimpleExpression) expression;
			if(exp.getObject()!=null && exp.getObject() instanceof String) {
				Class fieldClass = getFieldClass(exp.getPropertyName(),entityClass);
				if(fieldClass!=null) {
					if(!fieldClass.equals(String.class) && "".equals((String) exp.getObject())){
						exp.setObject(null);
					}
					if(exp.getObject()!=null) {
						if(fieldClass.equals(Long.class) || fieldClass.equals(long.class)) {
                            dealWithLike(exp);
                            try {
							    exp.setObject(Long.parseLong((String) exp.getObject()));
                            } catch(Exception e) {
                                exp.setObject(null);
                            }
						}
						else if(fieldClass.equals(Integer.class) || fieldClass.equals(int.class)) {
                            dealWithLike(exp);
                            try {
							    exp.setObject(Integer.parseInt((String) exp.getObject()));
                            } catch(Exception e) {
                                exp.setObject(null);
                            }
						}
						else if(fieldClass.equals(Double.class) || fieldClass.equals(double.class)) {
                            dealWithLike(exp);
                            try {
                                exp.setObject(Double.parseDouble((String) exp.getObject()));
                            } catch(Exception e) {
                                exp.setObject(null);
                            }
						}
                        else if(fieldClass.equals(BigDecimal.class) || fieldClass.equals(BigDecimal.class)) {
                            dealWithLike(exp);
                            try {
                                exp.setObject(new BigDecimal(Double.parseDouble((String) exp.getObject())));
                            } catch(Exception e) {
                                exp.setObject(null);
                            }
                        }
					}
				}
				// other types etc.
			}
			else if(exp.getObject()!=null && exp.getObject() instanceof Integer) {
				Class fieldClass = getFieldClass(exp.getPropertyName(),entityClass);
				if(fieldClass!=null) {
					if(fieldClass.equals(Long.class) || fieldClass.equals(long.class)) {
						exp.setObject(Long.parseLong(Integer.toString((Integer) exp.getObject())));
					}
					if(fieldClass.equals(Double.class) || fieldClass.equals(double.class)) {
						exp.setObject(Double.parseDouble(Integer.toString((Integer) exp.getObject())));
					}
				}
			}
		} else if(expression instanceof SimpleInExpression) {
			// Nothing to do
		} else if(expression instanceof LogicExpression) {
			LogicExpression exp = (LogicExpression) expression;
			if(exp.getLeftExpression()!=null) {
				typeAdapt(exp.getLeftExpression(),entityClass);
			}
			if(exp.getRightExpression()!=null) {
				typeAdapt(exp.getRightExpression(),entityClass);
			}
		} else if(expression instanceof LogicExpressionExtr) {
			LogicExpressionExtr exp = (LogicExpressionExtr) expression;
			for(Expression subExp:exp.getExpressions()) {
				typeAdapt(subExp,entityClass);
			}
		}
	}

    /**
     * @function 处理对整型、Long型以及浮点型字段的like查询处理
     *
     * @param
     * @return

     */
    private static void dealWithLike(SimpleExpression exp) {
        String value = (String) exp.getObject();
        if(value.indexOf("%") > -1) {
            value = value.replace("%","");
            if(exp.getSimpleExpression()==null || exp.getSimpleExpression().toLowerCase().equals("like") || exp.getSimpleExpression().toLowerCase().equals("ilike"))  {
                exp.setSimpleExpression("eq");
            }
            exp.setObject(value);
        }
    }

    /**
     * @function 获取字段的class类型
     *
     * @param fieldName
     * @param entityClass
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    public static Class getFieldClass(final String fieldName,final Class entityClass) throws SecurityException,NoSuchFieldException {
		if(entityClass==null || fieldName==null) {
			return null;
		}
		int index = fieldName.indexOf(".");
		if(index == -1) {
			try {
				Field field = entityClass.getDeclaredField(fieldName);
				return field.getType();
			} catch (NoSuchFieldException e) {
				if(entityClass.equals(Object.class)) {
					log.error(e);
					return null;
				}
				else {
					return getFieldClass(fieldName,entityClass.getSuperclass());
				}
			}
			
		}
		else {
			String firstFieldName = fieldName.substring(0,index);
			Field firstField = null;
			try {
				firstField = entityClass.getDeclaredField(firstFieldName);
			} catch (NoSuchFieldException e) {
				if(entityClass.equals(Object.class)) {
					log.error(e);
					return null;
				}
				else {
					return getFieldClass(fieldName,entityClass.getSuperclass());
				}
			}
			String subFieldName = fieldName.substring(index+1);
			return getFieldClass(subFieldName,firstField.getType());
		}
	}

}
