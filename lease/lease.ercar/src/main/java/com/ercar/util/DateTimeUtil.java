package com.ercar.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 * 
 */
public class DateTimeUtil {
	
	public DateTimeUtil() {
	}

	/**
	 * 获取0点时间 
	 * 
	 * @return
	 */
	public static Calendar getTimesZero(Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance(); 
		}
		cal.set(Calendar.HOUR_OF_DAY, 0); 
		cal.set(Calendar.SECOND, 0); 
		cal.set(Calendar.MINUTE, 0); 
		cal.set(Calendar.MILLISECOND, 0); 
		return cal; 
	}
	
	/**
	 * 获取23:59:59点时间 
	 * 
	 * @return
	 */
	public static Calendar getTimesTwentyThree(Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance(); 
		}
		cal.set(Calendar.HOUR_OF_DAY, 23); 
		cal.set(Calendar.SECOND, 59); 
		cal.set(Calendar.MINUTE, 59); 
		cal.set(Calendar.MILLISECOND, 999); 
		return cal; 
	}
	
	/**
	 * 获得当月第一天 
	 * 
	 * @return
	 */
	public static Calendar getFirstday(Calendar cal){ 
		if (cal == null) {
			cal = Calendar.getInstance();  
		}
		cal.add(Calendar.MONTH, 0);  
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal;
	}
	
	/**
	 * 获取当月最后一天 
	 * 
	 * @return
	 */
	public static Calendar getLastday(Calendar cal){ 
		if (cal == null) {
			cal = Calendar.getInstance();  
		}
		cal.add(Calendar.MONTH, 1);  
		cal.set(Calendar.DAY_OF_MONTH, 0);
		cal = getTimesTwentyThree(cal);
		return cal;
	}
	
	
	/**
	 * 获得当天0点时间 
	 * 
	 * @return
	 */
	public static long getTimesmorning() {
		return getTimesZero(null).getTimeInMillis(); 
	}
	
	/**
	 * 获得当天23:59:59时间 
	 * 
	 * @return
	 */
	public static long getTimesnight(){ 
		return getTimesTwentyThree(null).getTimeInMillis(); 
		}
	
	/**
	 * 获得当月第一天 0:0:0 
	 * 
	 * @return
	 */
	public static long getFirstday(){ 
		Calendar cal = getFirstday(null);
		cal = getTimesZero(cal);
		return cal.getTimeInMillis();
	}
	/**
	 * 获得某月第一天 0:0:0 
	 * 
	 * @return
	 */
	public static long getFirstday(long month){ 
		Calendar cal = Calendar.getInstance();
		Date date = new Date(month);
		cal.setTime(date);
		cal = getFirstday(cal);
		cal = getTimesZero(cal);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 获取当月最后一天 23:59:59
	 * 
	 * @return
	 */
	public static long getLastday(){ 
		Calendar cal = getLastday(null);  
		cal = getTimesTwentyThree(cal);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 获取某月最后一天 23:59:59
	 * 
	 * @return
	 */
	public static long getLastday(long month){ 
		Date date = new Date(month);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal = getLastday(cal);  
		cal = getTimesTwentyThree(cal);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 获取当月的 天数  
	 * 
	 * @return
	 */
	public static int getCurrentMonthDay(){ 
		Calendar a = Calendar.getInstance();  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);   
        return a.get(Calendar.DATE); 
	}
	
	/**
	 * 获取某一月的天数  
	 * 
	 * @return
	 */
	public static int getDaysOfMonth(long date){ 
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
	}
	
	public static void main(String[] args) throws Exception {
		long firstday = getFirstday();
		System.out.println(firstday);
		
		long lastday = getLastday(1491358800000L);
		System.out.println(lastday);
	}
	
}
