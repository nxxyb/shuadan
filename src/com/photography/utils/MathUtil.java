package com.photography.utils;

import java.math.BigDecimal;

/**
 * 
 * 
 * @author 李源 
 * @since 2012-7-16 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class MathUtil {

	//默认除法运算精度
	private   static   final   int   DEF_DIV_SCALE   =   20;

	/**
	 *   提供精确的加法运算。
	 *   @param   v1   被加数
	 *   @param   v2   加数
	 *   @return   两个参数的和
	 */

	public static double add(double v1, double v2){
		if(v1==1/0.0 || v2==1/0.0) {
			return 1/0.0;
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	
	/**
	 * 提供精确的加法运算
	 * @param b1
	 * @param b2
	 * @return
	 * @author 徐雁斌
	 */
	public static BigDecimal addBigDecimal(BigDecimal b1, BigDecimal b2){
		if(b1 == null){
			return b2;
		}
		if(b2 == null){
			return b1;
		}
		return b1.add(b2);
	}
	
	/**
	 * 提供精确的加法运算
	 * @param b1
	 * @param b2
	 * @return
	 * @author 徐雁斌
	 */
	public static Double addDouble(Double b1, Double b2){
		if(b1 == null){
			return b2;
		}
		if(b2 == null){
			return b1;
		}
		return add(b1,b2);
	}

	/**
	 *   提供精确的减法运算。
	 *   @param   v1   被减数
	 *   @param   v2   减数
	 *   @return   两个参数的差
	 */

	public static double sub(double v1, double v2){
		if(v1==1/0.0 || v2==1/0.0) {
			return 1/0.0;
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 *   提供精确的乘法运算。
	 *   @param   v1   被乘数
	 *   @param   v2   乘数
	 *   @return   两个参数的积
	 */

	public static double mul(double v1,double v2){
		if(v1==1/0.0 || v2==1/0.0) {
			return 1/0.0;
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 *   提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 *   小数点以后10位，以后的数字四舍五入。
	 *   @param   v1   被除数
	 *   @param   v2   除数
	 *   @return   两个参数的商
	 */

	public static double div(double v1, double v2){
		return div(v1,v2,DEF_DIV_SCALE);
	}

	/**
	 *   提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 *   定精度，以后的数字四舍五入。
	 *   @param   v1   被除数
	 *   @param   v2   除数
	 *   @param   scale   表示表示需要精确到小数点以后几位。
	 *   @return   两个参数的商
	 */

	public static double div(double v1, double v2, int scale){
		if(v1==1/0.0 || v2==1/0.0 || v2 == 0.0) {
			return 1/0.0;
		}
		if(scale<0){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}



	/**
	 *   提供精确的小数位四舍五入处理。
	 *   @param   v   需要四舍五入的数字
	 *   @param   scale   小数点后保留几位
	 *   @return   四舍五入后的结果
	 */

	public static double round(double v, int scale){
		if(scale<0){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double addMoreData(String[] data){
		double result = 0;
		for (String element : data) {
			if(element == null || "".equals(element)){
				return 0;
			}
			result = new BigDecimal(result).add(new BigDecimal(element)).doubleValue();
		}

		return result;
	}
	
	/**
	 * 取一定范围随机数
	 * @param min
	 * @param max
	 * @return
	 * @author 徐雁斌
	 */
	public static int getRanmon(int min,int max){
		return (int)Math.round(Math.random()*(max-min)+min);
	}
	
	public static void main(String[] args) {
		System.out.println(MathUtil.getRanmon(0, 10));
	}

}
