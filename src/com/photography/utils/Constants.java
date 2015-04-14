package com.photography.utils;

import java.util.ArrayList;
import java.util.List;

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
	
	public final static String SEPARATOR = ",";
	
	
	/**
	 * 准备状态
	 */
	//申请，重启虚拟机
	public final static String TYPE_SQ = "0";
	
	//就绪
	public final static String TYPE_JX = "1";
	
	/**
	 * 发单标识
	 */
	//上午
	public final static String FD_BS_SW = "0";
	
	//下午
	public final static String FD_BS_XW = "1";
	
	//附近
	public final static String FD_BS_FJ = "2";
	
	
	/**
	 * 乘客发单状态
	 */
	//0-乘客未发单 
	public final static String FD_TYPE_WEIFA = "0";
	
	//1-乘客已发单  
	public final static String FD_TYPE_YIFA = "1";
	
	//3-车主抢单完成
	public final static String FD_TYPE_QDWC = "2";
	
	//5-乘客确认搭乘
	public final static String FD_TYPE_QRDC = "3";
	
	//6-乘客评价完成
	public final static String FD_TYPE_PJWC = "4";
	
	//乘客评价常量
	public final static List<String> chengkePJ = new ArrayList<String>();
	
	static{
		chengkePJ.add("非常好的车主，车很干净，人很和善！谢谢");
		chengkePJ.add("车很干净，人到的很早，很好的一次搭乘");
		chengkePJ.add("特有fan的大哥，赞一个");
		chengkePJ.add("车主准时，车开的很稳");
		chengkePJ.add("车主路况熟悉，一路很开心");
		chengkePJ.add("人酷，车也不错");
		chengkePJ.add("希望有机会再合乘，缘分啊");
		chengkePJ.add("车主健谈，一路很开心");
		chengkePJ.add("车干净，开车稳，拼车周到");
		
	}
	
}
