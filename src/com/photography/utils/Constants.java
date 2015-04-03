package com.photography.utils;

/**
 * 
 * @author 徐雁斌
 * @since 2015-2-5
 * 
 * @copyright 2015 天大求实电力新技术股份有限公司 版权所有
 */
public class Constants {
	
	/**
	 * 是否
	 */
	public final static String NO = "0";
	public final static String YES = "1";
	
	public final static String SEPARATOR = "|";
	
	
	/**
	 * 乘客发单状态
	 */
	//0-乘客未发单 
	public final static String FD_TYPE_WEIFA = "0";
	
	//1-乘客已发单  
	public final static String FD_TYPE_YIFA = "1";
	
	//2-车主正在抢单 
	public final static String FD_TYPE_QD = "2";
	
	//3-车主抢单完成
	public final static String FD_TYPE_QDWC = "3";
	
	//5-乘客确认搭乘
	public final static String FD_TYPE_QRDC = "4";
	
	//6-乘客评价完成
	public final static String FD_TYPE_PJWC = "5";
	
}
