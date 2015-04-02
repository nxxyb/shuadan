package com.photography.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.CharSetUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 字符串工具
 * 
 * @author 李源 
 * @since 2011-8-11 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class StringUtil {

	/**
	 * 从指定的字符串中删除某字符
	 * 
	 * @param str
	 *            原始字符串
	 * @param seq
	 *            要删除的字符串
	 */
	public static String deleteSeqFromString(String str, String seq) {
		return CharSetUtils.delete(str, seq);
	}

	/**
	 * 忽略大小写判定在指定字符串中是否含有指定字符序列
	 * 
	 * @param str
	 *            原始字符串
	 * @param seq
	 *            是否含有的字符序列
	 * @return
	 */
	public static boolean containsIgnoreCase(String str, String seq) {
		return StringUtils.containsIgnoreCase(str, seq);
	}

	/**
	 * 删除字符串中的空格
	 * 
	 * @param str
	 * @return
	 */
	public static String deleteWhitespace(String str) {
		return StringUtils.deleteWhitespace(str);
	}

	/**
	 * 字符串是否只有大写或者小写字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAlpha(String str) {
		return StringUtils.isAlpha(str);
	}

	/**
	 * 字符串是否只有正整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		return StringUtils.isNumeric(str);
	}

	/**
	 * 判定字符串是否是有效数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return NumberUtils.isNumber(str);
	}

	/**
	 * 判定是否全是整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDigits(String str) {
		return NumberUtils.isDigits(str);
	}

	/**
	 * 将该字符串的是字母变大写
	 * 
	 * @param str
	 * @return
	 */
	public static String capitalize(String str) {
		return WordUtils.capitalize(str);
	}

	/**
	 * 指定从字符或者数字中生成指定长度的随机字符串
	 * 
	 * @param n
	 */
	public static String randomString(Integer n, boolean letter, boolean number) {
		return RandomStringUtils.random(n, letter, number);
	}

	/**
	 * 判定一个字符串是否在字符串数组中存在
	 * 
	 * @param str
	 * @param list
	 */
	public static boolean isInStringList(String str, List<String> list) {
		if (list == null) {
			return false;
		}
		for (String string : list) {
			if (str.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 通过指定的字符将Object数组转换成一个字符串
	 * 
	 * @param objects
	 *            需要转换的数组对象
	 * @param joinSeq
	 *            指定的字符
	 * @return
	 */
	public static String join(Object[] objects, String joinSeq) {
		return StringUtils.join(objects, joinSeq);
	}

	/**
	 * 通过指定的字符将传入的字符拆分成数组
	 * 
	 * @param str
	 *            传入的字符
	 * @param separatorChar
	 *            指定的字符
	 * @return
	 */
	public static String[] split(String str, String separatorChar) {
		return StringUtils.split(str, separatorChar);
	}

	/**
	 * 截取传入字符串从指定字符到结尾的字符串 eg.StringUtil.substringAfterLast("abcba", "b") = "a"
	 * 
	 * @param str
	 *            传入字符串
	 * @param separator
	 *            指定字符
	 * @return
	 */
	public static String substringAfterLast(String str, String separator) {
		return StringUtils.substringAfterLast(str, separator);
	}

	/**
	 * 截取传入字符串从指定字符到字符串开始的字符串 eg：StringUtil.substringBeforeLast("abcba", "b") = "abc"
	 * 
	 * @param str
	 *            传入字符串
	 * @param separator
	 *            指定字符
	 * @return
	 */
	public static String substringBeforeLast(String str, String separator) {
		return StringUtils.substringBeforeLast(str, separator);
	}

	/**
	 * 截取传入字符串从指定字符到结尾的字符串 eg.StringUtil.substringAfter("abcba", "b") = "cba"
	 * 
	 * @param str
	 *            传入字符串
	 * @param separator
	 *            指定字符
	 * @return
	 */
	public static String substringAfter(String str, String separator) {
		return StringUtils.substringAfter(str, separator);
	}

	/**
	 * 截取传入字符串从指定字符到字符串开始的字符串 eg、StringUtil.substringBefore("abcba", "b") = "a"
	 * 
	 * @param str
	 *            传入字符串
	 * @param separator
	 *            指定字符
	 * @return
	 */
	public static String substringBefore(String str, String separator) {
		return StringUtils.substringBefore(str, separator);
	}

	/**
	 * 截取传入字符串在指定字符之间的部分
	 * 
	 * @param str
	 *            传入的字符串
	 * @param open
	 *            开始字符
	 * @param close
	 *            结束字符
	 * @return
	 */
	public static String substringBetween(String str, String open, String close) {
		return StringUtils.substringBetween(str, open, close);
	}

	/**
	 * 截取传入字符串在指定字符之间的所有部分
	 * 
	 * @param str
	 *            传入的字符串
	 * @param open
	 *            开始字符
	 * @param close
	 *            结束字符
	 * @return
	 */
	public static String[] substringBetweenAll(String str, String open, String close) {
		return StringUtils.substringsBetween(str, open, close);
	}

	/**
	 * 判定是否为空
	 * 
	 * @return
	 */
	public static boolean isEmpty(String string) {
		return StringUtils.isEmpty(string);
	}
	
	/**
	 * 全角字符串转半角字符串
	 * @param fullString
	 * @return
	 * @author 汪晗
	 */
	public static final String full2Half(String fullString) {
		return fullString.replace("　", " ");
	}

    /**
     * 数字格式字符串转汉字
     * @param number
     * @return
     * @author 汪晗
     */
    private static final String numberToChinese(String number) {
        Map<String,String> numberMap = new HashMap<String,String>();
        numberMap.put("0", "零");
        numberMap.put("1", "一");
        numberMap.put("2", "二");
        numberMap.put("3", "三");
        numberMap.put("4", "四");
        numberMap.put("5", "五");
        numberMap.put("6", "六");
        numberMap.put("7", "七");
        numberMap.put("8", "八");
        numberMap.put("9", "九");
        Map<String,String> unitMap = new HashMap<String,String>();
        unitMap.put("0", "");
        unitMap.put("1", "十");
        unitMap.put("2", "百");
        unitMap.put("3", "千");
        unitMap.put("4", "万");
        unitMap.put("6", "十万");
        unitMap.put("7", "百万");
        unitMap.put("8", "千万");
        unitMap.put("9", "亿");

        String str = number;
        StringBuilder value = new StringBuilder();
        int length = str.length();
        if(str.length()>8) {
            value.append(numberToChinese(str.substring(0, length-8))).append(unitMap.get("9")).append(numberToChinese(str.substring(length-8, length)));
        } else if(str.length()>4){
            value.append(numberToChinese(str.substring(0, length-4))).append(unitMap.get("4")).append(numberToChinese(str.substring(length-4, length)));
        } else {
            for(int i=0;i<length;i++) {
                String bit = Integer.toString(length-i-1);
                String n = str.substring(i,i+1);
                if(!"0".equals(n)) {
                    if("1".equals(bit) && "1".equals(n) && length==2) {
                        value.append(unitMap.get(bit));
                    } else {
                        value.append(numberMap.get(n)).append(unitMap.get(bit));
                    }
                } else {
                    if(i<length-1) {
                        String next = str.substring(i+1,i+2);
                        if(!"0".equals(next)) {
                            value.append(numberMap.get(n));
                        }
                    }
                }
            }
        }
        return value.toString();
    }
	
	/**
	 * 数字转汉字
	 * @param number
	 * @return
	 * @author 汪晗
	 */
	public static final String numberToChinese(int number) {
         return numberToChinese(Integer.toString(number));
	}


}
