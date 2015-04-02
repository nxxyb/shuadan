package com.photography.utils;

import java.math.BigDecimal;

/**
 * 数据工具
 * 
 * @since 2011-8-11
 * @author 巩向兵
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class DoubleUtil {

	/**
	 * 加法
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 减法
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 乘法
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 除法，精确到小数点以后2位，之后的数字四舍五入
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, 2);
	}

	/**
	 * 除法，精确到小数点后指定位数，之后的数字四舍五入
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            小数点后保留位数
	 * @return 商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 小数四舍五入
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留位数
	 * @return 结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static boolean equals(double v1, double v2) {
		return Double.compare(v1, v2) == 0;
	}
	
	/**
	 * 将Double数值转换成字符串，保留两位小数
	 * @param val
	 * @return 如果以.0或.00结尾，则只取整数位
	 */
	public static String doubleToString(Double val){
		BigDecimal bg = new BigDecimal(val);
		
		return decimalToString(bg);
	}
	
	/**
	 * 将BigDecimal数值转换成字符串，保留两位小数
	 * @param val
	 * @return 如果以.0或.00结尾，则只取整数位
	 */
	public static String decimalToString(BigDecimal bg){
		String tmpv = String.valueOf(bg.setScale(2, BigDecimal.ROUND_HALF_UP));
		if (tmpv.endsWith(".0")) {
			tmpv = tmpv.replace(".0", "");
		}else if (tmpv.endsWith(".00")) {
			tmpv = tmpv.replace(".00", "");
		}
		
		return tmpv;
	}
}