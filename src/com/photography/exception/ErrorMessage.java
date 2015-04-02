package com.photography.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常错误消息
 *
 * @since 2011-11-9
 * @author Zhao Zhihong
 *
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class ErrorMessage {
	private static Map<Integer, String> msg = new HashMap<Integer, String>();

	static {
		/*
		 * 公共异常错误码
		 */
		msg.put(ErrorCode.UNKNOWN_ERROR, "数据库异常");
		msg.put(ErrorCode.DATABASE_ERROR, "数据库异常");
		msg.put(ErrorCode.SESSION_TIMEOUT, "未登录或会话已超时，请重新登录");

		
		/*
		 * 用户
		 */
		msg.put(ErrorCode.USER_NOT_EXIST, "用户不存在");
		msg.put(ErrorCode.USER_PWD_NOT_MATCH, "密码错误");
		msg.put(ErrorCode.USER_NOT_VERIFY, "用户未审核通过");
		msg.put(ErrorCode.USER_NOT_ENABLE, "用户未激活");
		
		/*
		 * 活动
		 */
		msg.put(ErrorCode.PROJECT_NOT_EXIST, "活动不存在");
	}

	public static String get(int errorCode){
		return msg.get(errorCode);
	}
	
	public static void put(Map<Integer, String> message){
		msg.putAll(message);
	}
}
