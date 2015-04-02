package com.photography.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * MD5加密工具类
 * 
 * @author 张璐宇
 * @since 2012-7-5
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class MD5Util {
	private static Logger log = Logger.getLogger(MD5Util.class);

	public static String md5(String str) {
		if (str == null) {
			return null;
		}
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("UTF-8"));
			byte[] result = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : result) {
				sb.append(String.format("%02X", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			log.error("MD5Util.md5(): NoSuchAlgorithmException", e);
			return str;
		} catch (UnsupportedEncodingException e) {
			log.error("MD5Util.md5(): UnsupportedEncodingException", e);
			return str;
		}
	}
}
