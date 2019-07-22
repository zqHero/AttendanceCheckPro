package com.huigao.util;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理通用类
 * @author cgx
 * @version 1.0
 */
public class DateUtil {
	
	/**
	 * 返回date1 - date2 的分钟数
	 * @param date1  当前时间
	 * @param date2  
	 * @return
	 */
	public static int getMinites(Date date1,Date date2) { 
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		
		int hour = cal1.get(Calendar.HOUR_OF_DAY) - cal2.get(Calendar.HOUR_OF_DAY);
		int minite = cal1.get(Calendar.MINUTE) - cal2.get(Calendar.MINUTE);
		
		return hour * 60 + minite;
	}
	
	/**
	 * 格式化日期 ： yyyy-MM-dd HH:mm:ss
	 * @param date 日期
	 * @return 日期字符串，如果date为空返回空串("")
	 */
	public static String getFormatDateString(Date date) {
		if(date == null) return "";
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	/**
	 * 格式化日期 ： yyyy-MM-dd HH:mm
	 * @param date 日期
	 * @return 日期字符串，如果date为空返回空串("")
	 */
	public static String getFormatDateTimeString(Date date) {
		if(date == null) return "";
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
	}
	
	/**
	 * 格式化日期 ： yyyy-MM-dd
	 * @param date 日期
	 * @return 日期字符串，如果为空返回空串("")
	 */
	public static String getDateString(Date date) {
		if(date == null) return "";
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	/**
	 * 格式化时间 ： HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String getTimeString(Date date) {
		if(date == null) return "";
		return new SimpleDateFormat("HH:mm:ss").format(date);
	}
	
	/**
	 * 由字符串转换为日期类型
	 * @param date
	 * @return
	 */
	public static Date getDate(String str, String format) {
		try {
			return new SimpleDateFormat(format).parse(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取日期对应的时间，其中年月日为当前的年月日，时间为参数中的时间
	 * @param time 时间
	 * @return 日期
	 */
	public static Date getDateFromTime(Time time){
		Calendar c = Calendar.getInstance(); 
		try {
			c.setTime(new SimpleDateFormat("HH:mm:ss").parse(time.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		Calendar cal = Calendar.getInstance();
		c.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
		return c.getTime();
	}
	
}
