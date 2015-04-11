package com.photography.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/**
 * 日期转换工具
 * 
 * @since 2011-8-11
 * @author 李源
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class DateUtil {
	private static Logger logger = Logger.getLogger(DateUtil.class);

	/**
	 * 将传入的Date转换成yyyy-MM-dd的形式
	 * 
	 * @param src
	 * @return
	 */
	public static String date2String(Date src) {
		return date2String(src, "yyyy-MM-dd");
	}

	/**
	 * 将传入的Date转换成指定的格式
	 * 
	 * @param src
	 *            需要转换的Date
	 * @param pattern
	 *            需要转成的格式
	 * @return
	 */
	public static String date2String(Date src, String pattern) {
		if (pattern == null) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}

		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.format(src);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 得到参数日期所在月份的天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDaysOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.roll(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 将指定的字符串转换为Date的方法
	 * 
	 * @param dateString
	 *            需要转换的字符串
	 * @param pattern
	 *            要转换的格式
	 * @return
	 */
	public static Date string2Date(String dateString, String pattern) {
		if (pattern == null) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}

		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date result = format.parse(dateString);
			return result;
		} catch (ParseException e) {
			//logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 将指定的字符根据yyyy-MM-dd的格式串转换为Date的方法
	 * 
	 * @param dateString
	 *            需要转换的字符串
	 * @param pattern
	 *            要转换的格式
	 * @return
	 */
	public static Date string2Date(String dateString) {
		return string2Date(dateString, "yyyy-MM-dd");
	}

	/**
	 * 是否是同一天
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		return DateUtils.isSameDay(date1, date2);
	}

	/**
	 * 时间是否完全相同包括时分秒
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameInstant(Date date1, Date date2) {
		return DateUtils.isSameInstant(date1, date2);
	}

	/**
	 * 一个日期对象＋ n天
	 * 
	 * @param date
	 * @param count
	 * @return
	 */
	public static Date addDays(Date date, int count) {
		return DateUtils.addDays(date, count);
	}

	/**
	 * 一个日期对象＋ n小时
	 * 
	 * @param date
	 * @param count
	 * @return
	 */
	public static Date addHours(Date date, int count) {
		return DateUtils.addHours(date, count);
	}

	/**
	 * 一个日期对象＋ n秒
	 * 
	 * @param date
	 * @param count
	 * @return
	 */
	public static Date addSeconds(Date date, int count) {
		return DateUtils.addSeconds(date, count);
	}

	/**
	 * 一个日期对象＋ n月
	 * 
	 * @param date
	 * @param count
	 * @return
	 */
	public static Date addMonths(Date date, int count) {
		return DateUtils.addMonths(date, count);
	}

	/**
	 * 一个日期对象＋ n年
	 * 
	 * @param date
	 * @param count
	 * @return
	 */
	public static Date addYears(Date date, int count) {
		return DateUtils.addYears(date, count);
	}
	
	/**
	 * 处理各种格式的字符串，并转换为日期(2012/3/4,2012/03/4,2012/3,2012/02,2012年3月,2012年03月,2012-3,2012-03,2012,2012年)
	 * 其中月份默认为当月最后一天，年份默认为当年12月31日
	 * @param dateValue
	 * @return
	 * @author 汪晗
	 */
	public static Date parseDate(String dateValue) {
		Date date = null;
		if (dateValue!=null && dateValue.trim().length() > 0){
			if(dateValue.indexOf("/")!=-1) {
				date = DateUtil.string2Date(dateValue,"yyyy/MM/dd");
				if(date==null) {
					date = DateUtil.string2Date(dateValue,"yyyy/MM");
					if(date!=null) {
						date = dealWithYearAndMonth(date);
					}
				}
			} else if(dateValue.indexOf("年")!=-1 && dateValue.indexOf("月")!=-1) {
				date = DateUtil.string2Date(dateValue,"yyyy年M月");
				if(date!=null) {
					date = dealWithYearAndMonth(date);
				}
			} else if(dateValue.indexOf("-")!=-1) {
                if(dateValue.indexOf(":")>-1) {
                    date = DateUtil.string2Date(dateValue,null);
                } else {
				    date = DateUtil.string2Date(dateValue);
                }
				if(date==null) {
					date = DateUtil.string2Date(dateValue,"yyyy-MM");
					if(date!=null) {
						date = dealWithYearAndMonth(date);
					}
				}
			} else {
				String regex = "^[1-9]{1}\\d{3}$";
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(dateValue);
				if(m.find()) {
					//只填写年份则默认为当年的12月31日
					Calendar calendar = Calendar.getInstance();
					calendar.set(Integer.parseInt(dateValue),11,31);
					date = calendar.getTime();
				} else if(dateValue.endsWith("年")) {
					dateValue = dateValue.substring(0, dateValue.length()-1);
					m = p.matcher(dateValue);
					if(m.find()) {
						//只填写年份则默认为当年的12月31日
						Calendar calendar = Calendar.getInstance();
						calendar.set(Integer.parseInt(dateValue),11,31);
						date = calendar.getTime();
					}
				}
			}
		}
		if(date!=null && dateValue.length() > 4) {
			String dateStr = date2String(date);
			if(dateStr.length() > 4 && !dateStr.substring(0, 4).equals(dateValue.substring(0, 4))) {
				return null;
			}
		}
		return date;
	}
	
	/**
	 * 处理年月时间
	 * @param date
	 * @return
	 * @author 汪晗
	 */
	private static Date dealWithYearAndMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
		calendar.setTimeInMillis(calendar.getTimeInMillis()-86400000);
		return calendar.getTime();
	}
	
	/**
	 * @return 系统日期年份
	 */
	public static Integer getCurrYear(){
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	/**
	 * @return 系统日期月份
	 */
	public static Integer getCurrMonth(){
		return Calendar.getInstance().get(Calendar.MONTH)+1;
	}
	
	/**
	 * @return 系统日期下一月份
	 */
	public static Integer getNextMonth(){
		if(getCurrMonth()==12) {
			// 转入下年1月
			return 1;
		} else {
			// 转入下月
			return getCurrMonth()+1;
		}
	}
	
	/**
	 * 在当前日期的基础上增加或者减少月份
	 * @param date
	 * @param month
	 * @return
	 * @author 汪晗
	 */
	public static Date getDateByIncrement(Date date,int month) {
		month = month +1;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int m = Math.abs(month);
		int oldMonth = calendar.get(Calendar.MONTH);
		int oldYear = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.DATE, 1);
		if(month > 0) {
			int year = month/12;
			month = month%12;
			int newMonth = oldMonth+month;
			if(newMonth >= 12) {
				year++;
				calendar.set(Calendar.MONTH, newMonth%12);
			} else {
				calendar.set(Calendar.MONTH, newMonth);
			}
			calendar.set(Calendar.YEAR, oldYear+year);
			
		} else if(month < 0) {
			int year = m/12;
			month = m%12;
			if(month > oldMonth) {
				year++;
				calendar.set(Calendar.MONTH, (oldMonth+12)-month);
			} else {
				calendar.set(Calendar.MONTH, oldMonth-month);
			}
			calendar.set(Calendar.YEAR, oldYear-year);
		}
		calendar.setTimeInMillis(calendar.getTimeInMillis()-1000L*60*60*24);
		return calendar.getTime();
	}
	
	/**
	 * 得到当前时间是上午还是下午
	 */
	public static String getSX(){
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if(hour < 12){
			return Constants.FD_BS_SW;
		}else{
			return Constants.FD_BS_XW;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.getSX());
	}
}
