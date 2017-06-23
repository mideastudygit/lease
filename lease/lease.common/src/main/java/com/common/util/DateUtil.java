package com.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 日期时间工具类
 * 
 * @author 唐宗鸿
 *
 */
public class DateUtil {

	private DateUtil() {
	}

	public final static String TO_YEAR = "yyyy";
	public final static String TO_MONTH = "yyyyMM";
	public final static String TO_DAY = "yyyyMMdd";
	public final static String TO_MINUTE = "yyyyMMddHHmm";
	public final static String TO_SECOND = "yyyyMMddHHmmss";
	public final static String TO_MILLISECOND = "yyyyMMddHHmmssSSS";

	public final static String TO_DAY_LINE = "yyyy-MM-dd";
	public final static String TO_MINUTE_LINE = "yyyy-MM-dd HH:mm";
	public final static String TO_SECOND_LINE = "yyyy-MM-dd HH:mm:ss";
	public final static String TO_MILLISECOND_LINE = "yyyy-MM-dd HH:mm:ss.SSS";

	public final static String TO_MONTH_SLASH = "yyyy/MM";
	public final static String TO_DAY_SLASH = "yyyy/MM/dd";
	public final static String TO_MINUTE_SLASH = "yyyy/MM/dd HH:mm";
	public final static String TO_SECOND_SLASH = "yyyy/MM/dd HH:mm:ss";
	public final static String TO_MILLISECOND_SLASH = "yyyy/MM/dd HH:mm:ss.SSS";

	/** field */
	public final static int DAY_OF_WEEK = Calendar.DAY_OF_WEEK;
	public final static int DAY_OF_MONTH = Calendar.DAY_OF_MONTH;
	public final static int DAY_OF_YEAR = Calendar.DAY_OF_YEAR;
	public final static int MONTH_OF_YEAR = Calendar.MONTH;

	public static final String ZROZ_STR = " 00:00:00";

	public final static long DAY_TIME_MILLIS = 86400000;
	public final static long HOUR_TIME_MILLIS = 3600000;

	/**
	 * 将Date转换成指定格式的字符串
	 * 
	 * @param timestamp
	 * @param format
	 * @return
	 * @author 唐宗鸿
	 */
	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 将时间戳转换成指定格式的字符串
	 * 
	 * @param timestamp
	 * @param format
	 * @return
	 * @author 唐宗鸿
	 */
	public static String format(long timestamp, String format) {
		return format(new Date(timestamp), format);
	}

	/**
	 * 将时间戳转换成指定格式的字符串
	 * 
	 * @param timestamp
	 * @param format
	 * @return
	 * @author 唐宗鸿
	 */
	public static String format(String timestamp, String format) {
		if (StringUtils.isEmpty(timestamp)) {
			return "";
		}
		return format(Long.valueOf(timestamp), format);
	}

	/**
	 * 将时间字符串转换成Date
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @author 唐宗鸿
	 */
	public static Date parse(String date, String format) {
		if (StringUtils.isEmpty(date)) {
			return new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 * @author 唐宗鸿
	 */
	public static Date now() {
		return new Date();
	}

	/**
	 * 获取当前时间,返回指定格式的字符串
	 * 
	 * @param format
	 * @return
	 * @author 唐宗鸿
	 */
	public static String now(String format) {
		return format(now(), format);
	}

	/**
	 * 获取当前毫秒数
	 * 
	 * @return
	 * @author 唐宗鸿
	 */
	public static long nowTime() {
		return now().getTime();
	}

	/**
	 * 获取
	 * 
	 * @param date
	 * @return
	 * @author 唐宗鸿
	 */
	public static int get(int field) {
		return get(now(), field);
	}

	/**
	 * 获取
	 * 
	 * @param date
	 * @return
	 * @author 唐宗鸿
	 */
	public static int get(Date date, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(field);
	}

	/**
	 * 获取目标时间的毫秒数
	 * 
	 * @param date
	 * @return
	 * @author 唐宗鸿
	 */
	public static long getTime(Date date) {
		return date.getTime();
	}

	/**
	 * 获取目标时间的毫秒数
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @author 唐宗鸿
	 */
	public static long getTime(String date, String format) {
		return parse(date, format).getTime();
	}

	/**
	 * 获取某月的最后一天
	 * 
	 * @param date
	 * @return
	 * @author 唐宗鸿
	 */
	public static int lastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取某月的最后一天
	 * 
	 * @param date
	 * @return
	 * @author 唐宗鸿
	 */
	public static int lastDayOfMonth(String date, String format) {
		return lastDayOfMonth(parse(date, format));
	}

	/**
	 * 获取下一天
	 * 
	 * @return
	 * @author 唐宗鸿
	 */
	public static Date nextDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期的下一天
	 * 
	 * @param date
	 * @return
	 * @author 唐宗鸿
	 */
	public static Date nextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期的下一天<br>
	 * 输入格式与输出格式一致
	 * 
	 * @param day
	 * @param format
	 *            默认格式：yyyy-MM-dd
	 * @return
	 * @author 唐宗鸿
	 */
	public static String nextDay(String day, String format) {
		format = StringUtils.isEmpty(format) ? TO_DAY_LINE : format;
		Date date = nextDay(StringUtils.isEmpty(day) ? now() : parse(day, format));
		return format(date, format);
	}

	/**
	 * 当前时间往上推 i天
	 * 
	 * @param i
	 * @return
	 * @author 唐宗鸿
	 */
	public static long prevDay(int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parse(now(TO_DAY), TO_DAY));
		calendar.add(Calendar.DAY_OF_MONTH, -i);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取当前月份
	 * 
	 * @return
	 * @author 唐宗鸿
	 */
	public static Integer getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}

	/**
	 * 获取下一月份
	 * 
	 * @return
	 * @author 唐宗鸿
	 */
	public static Date nextMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 获取下一月份
	 * 
	 * @return
	 * @author 唐宗鸿
	 */
	public static String nextMonth(String format) {
		return format(nextMonth(), format);
	}

	/**
	 * 获取指定月分的下一月份
	 * 
	 * @param date
	 * @return
	 * @author 唐宗鸿
	 */
	public static Date nextMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 获取上一月份
	 * 
	 * @return
	 * @author 唐宗鸿
	 */
	public static Date prevMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 获取前month个月份
	 * 
	 * @return
	 * @author 唐宗鸿
	 */
	public static Date prevMonth(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -month);
		return calendar.getTime();
	}

	/**
	 * 获取上一月份
	 * 
	 * @return
	 * @author 唐宗鸿
	 */
	public static String prevMonth(String format) {
		return format(prevMonth(), format);
	}

	/**
	 * 获取指定月分的上一月份
	 * 
	 * @param date
	 * @return
	 * @author 唐宗鸿
	 */
	public static Date prevMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 获取当前年份
	 * 
	 * @return
	 * @author 唐宗鸿
	 */
	public static Integer getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 将一种格式的时间字符串转换成另一种格式
	 * 
	 * @param date
	 * @param oldFormat
	 * @param newFormat
	 * @return
	 * @author 唐宗鸿
	 */
	public static String convert(String date, String oldFormat, String newFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
		SimpleDateFormat newSdf = new SimpleDateFormat(newFormat);
		try {
			return newSdf.format(sdf.parse(date));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取指定日期的开始时间
	 * 
	 * @param dayTime
	 * @return
	 * @author 唐宗鸿
	 */
	public static long getStartTimeOfDay(long dayTime) {
		Calendar day = Calendar.getInstance();
		day.setTime(new Date(dayTime));
		day.set(Calendar.HOUR_OF_DAY, 0);
		day.set(Calendar.MINUTE, 0);
		day.set(Calendar.SECOND, 0);
		day.set(Calendar.MILLISECOND, 0);
		return day.getTimeInMillis();
	}

	/**
	 * 获取指定日期的开始时间
	 * 
	 * @param dayTime
	 * @return
	 * @author 唐宗鸿
	 */
	public static long getStartTimeOfDay(String date, String pattern) {
		long startTime = getStartTimeOfDay(getTime(date, pattern));
		return startTime;
	}

	/**
	 * 获取指定日期的结束时间
	 * 
	 * @param dayTime
	 * @return
	 * @author 唐宗鸿
	 */
	public static long getEndTimeOfDay(long dayTime) {
		Calendar day = Calendar.getInstance();
		day.setTime(new Date(dayTime));
		day.set(Calendar.HOUR_OF_DAY, 23);
		day.set(Calendar.MINUTE, 59);
		day.set(Calendar.SECOND, 59);
		day.set(Calendar.MILLISECOND, 999);
		return day.getTimeInMillis();
	}

	/**
	 * 获取指定日期的结束时间
	 * 
	 * @param dayTime
	 * @return
	 * @author 唐宗鸿
	 */
	public static long getEndTimeOfDay(String date, String pattern) {
		long endTime = getEndTimeOfDay(getTime(date, pattern));
		return endTime;
	}

	/**
	 * 获取当天开始的时间戳
	 * 
	 * @return
	 * @author 唐宗鸿
	 */
	public static long getTodayStartTime() {
		Calendar day = Calendar.getInstance();
		day.set(Calendar.HOUR_OF_DAY, 0);
		day.set(Calendar.MINUTE, 0);
		day.set(Calendar.SECOND, 0);
		day.set(Calendar.MILLISECOND, 0);
		return day.getTimeInMillis();
	}

	/**
	 * 获获取当天结束时间戳
	 * 
	 * @return
	 * @author 唐宗鸿
	 */
	public static long getTodayEndTime() {
		Calendar day = Calendar.getInstance();
		day.set(Calendar.HOUR_OF_DAY, 23);
		day.set(Calendar.MINUTE, 59);
		day.set(Calendar.SECOND, 59);
		day.set(Calendar.MILLISECOND, 999);
		return day.getTimeInMillis();
	}

	/**
	 * 获取指定年月的开始时间
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static long getMonthStartTime(int year, int month) {
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.TO_DAY);
		Calendar cale = Calendar.getInstance();
		cale.set(year, month - 1, Calendar.DATE);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		String firstday = format.format(cale.getTime());
		return getTime(firstday, DateUtil.TO_DAY);
	}

	/**
	 * 获取指定年月的结束时间
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static long getMonthEndTime(int year, int month) {
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.TO_DAY);
		Calendar cale = Calendar.getInstance();
		cale.set(year, month, Calendar.DATE);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		String lastday = format.format(cale.getTime());
		return getTime(lastday, DateUtil.TO_DAY);
	}

	/**
	 * 获取指定起始结束时间间隔年数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int periodYearDate(long startDate, long endDate) {
		Calendar calBegin = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.TO_YEAR);
		String start = format.format(new Date(startDate));
		String end = format.format(new Date(endDate));
		try {
			calBegin.setTime(format.parse(start));
			calEnd.setTime(format.parse(end));
		} catch (ParseException e) {
		}
		return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
	}

	/**
	 * 获取指定起始结束时间间隔月数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int periodMonthDate(long startDate, long endDate) {
		Calendar calBegin = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.TO_MONTH);
		String start = format.format(new Date(startDate));
		String end = format.format(new Date(endDate));
		try {
			calBegin.setTime(format.parse(start));
			calEnd.setTime(format.parse(end));
		} catch (ParseException e) {
		}
		int month = calEnd.get(Calendar.MONTH) - calBegin.get(Calendar.MONTH);
		return month;
	}

	/**
	 * 获取指定起始结束时间间隔月数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int periodMonthYearDate(long startDate, long endDate) {
		Calendar calBegin = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.TO_MONTH);
		String start = format.format(new Date(startDate));
		String end = format.format(new Date(endDate));
		try {
			calBegin.setTime(format.parse(start));
			calEnd.setTime(format.parse(end));
		} catch (ParseException e) {
		}
		int month = calEnd.get(Calendar.MONTH) - calBegin.get(Calendar.MONTH);
		int year = calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
		int count = month + year * 12;
		return count;
	}
}
