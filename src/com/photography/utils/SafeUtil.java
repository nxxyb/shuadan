package com.photography.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 提供保护措施
 * 
 * @author 汪晗
 * @since 2012-10-19
 * 
 * @Copyright (C) 2012 天津天大求实电力新技术股份有限公司 版权所有
 */
public class SafeUtil {
	
	private static final Map<String,String> charMap = new HashMap<String,String>();
	
	
	public static String toHtmlString(String str) { 
		if (str == null || ("").equals(str.trim())) { 
		return ""; 
		} 

		StringBuffer stringbuffer = new StringBuffer(); 
		int j = str.length(); 

		for (int i = 0; i < j; i++) { 
		char c = str.charAt(i); 

		switch (c) { 
		case 39: // ' 
		stringbuffer.append("&acute;"); 
		break; 
		case 60: // < 
		stringbuffer.append("&lt;"); 
		break; 
		case 62: // > 
		stringbuffer.append("&gt;"); 
		break; 
		case 38: // & 
		stringbuffer.append("&amp;"); 
		break; 
		case 34: // " 
		stringbuffer.append("&quot;"); 
		break; 
		case 13: 
		if (i < j - 1 && str.charAt(i + 1) == 10) { 
		stringbuffer.append("<br>"); 
		i++; 
		} 
		break; 
		case 32: 
		stringbuffer.append("&nbsp"); 
		break; 
		default: 
		stringbuffer.append(c); 
		break; 
		} 
		} 
		return new String(stringbuffer.toString()); 
		} 



	
	
	static {
		charMap.put("<", "&lt;");
		charMap.put(">", "&gt;");
		charMap.put("\"", "&quot;");
	}
	
	/**
	 * 将包含html字符转义成安全的字符串
	 * @param text
	 * @return
	 * @author 汪晗
	 */
	public static String encode(String text) {
		if(text==null) {
			return null;
		}
		text = text.replace("&", "&amp;");
		for(String key : charMap.keySet()) {
			text = text.replace(key, charMap.get(key));
		}
		return text;
	}
	
	/**
	 * 将转义后的字符串还原回html字符串
	 * @param text
	 * @return
	 * @author 汪晗
	 */
	public static String decode(String text) {
		if(text==null) {
			return null;
		}
		for(String key : charMap.keySet()) {
			text = text.replace(charMap.get(key),key);
		}
		return text;
	}
	
	public static void main(String[] args) {
		String value = "a href=\"http://ir.baidu.com\"";
		System.out.println(SafeUtil.encode(value));
	}
}
