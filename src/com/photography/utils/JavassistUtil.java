package com.photography.utils;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * Javassist操作接口
 * 
 * @author 汪晗
 * @since 2012-6-18
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public final class JavassistUtil {
	
	private static ClassPool classPool = null;
	
	/**
	 * 对现有的class增加字段
	 * @param className 类名
	 * @param fieldName 字段名
	 * @param type 字段类型
	 * @throws NotFoundException
	 * @throws CannotCompileException
	 * @throws IOException
	 * @author 汪晗
	 */
	public static void addField(String className,String fieldName,String type) throws NotFoundException, CannotCompileException, IOException {
		ClassPool cp = JavassistUtil.getClassPool();
		CtClass cc = cp.get(className);

		CtField fld = new CtField(cp.get(type), fieldName, cc);
		fld.setModifiers(Modifier.PRIVATE);
		cc.addField(fld);

		String MethodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		
		CtMethod getMethod = new CtMethod(cp.get(type), "get" + MethodName, null, cc);
		getMethod.setBody("{return " + fieldName + ";}");
		cc.addMethod(getMethod);

		CtMethod setMethod = new CtMethod(CtClass.voidType, "set" + MethodName, new CtClass[] { cp.get(type) }, cc);
		setMethod.setBody("{this." + fieldName + "=$1;}");
		cc.addMethod(setMethod);

		//cc.writeFile(JavassistUtil.class.getResource("/").getPath());
	}
	
	/**
	 * 创建ctClass
	 * @param className
	 * @author 汪晗
	 * @throws CannotCompileException 
	 */
	public static CtClass addClass(String className) {
		ClassPool cp = JavassistUtil.getClassPool();
		return cp.makeClass(className);
	}
	
	/**
	 * 创建ctClass(带指定父类)
	 * @param className
	 * @author 汪晗
	 * @throws CannotCompileException 
	 */
	public static CtClass addClass(String className,String superClassName) {
		ClassPool cp = JavassistUtil.getClassPool();
		CtClass superClass = null;
		try {
			superClass = cp.get(superClassName);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		if(superClass!=null) {
			return cp.makeClass(className,superClass);
		} else {
			return cp.makeClass(className);
		}
	}
	
	/**
	 * 将ctClass转化为真正的class
	 * @param ctClass
	 * @return
	 * @throws CannotCompileException
	 * @author 汪晗
	 */
	public static Class getClass(CtClass ctClass) throws CannotCompileException {
		ClassPool cp = JavassistUtil.getClassPool();
		return cp.toClass(ctClass);
	}
	
	/**
	 * 获取classPool
	 * @return
	 * @author 汪晗
	 */
	public static synchronized ClassPool getClassPool() {
		if(classPool==null) {
			classPool = ClassPool.getDefault();
			try {
				classPool.appendClassPath(JavassistUtil.class.getResource("/").getPath());
			} catch(NotFoundException e){}
		}
		return classPool;
	}
	
}
