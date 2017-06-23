package com.manage.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.base.utils.ParaMap;
import com.common.util.DateUtil;

public class DateConvertUtil {

	/**
	 * 获取该月的开始时间
	 * 
	 * @param date
	 *            yyyyMM格式
	 * @return 时间戳
	 */
	public static long getStartTime(String date) {
		int year = Integer.valueOf(date.substring(0, 4));
		int month = Integer.valueOf(date.substring(date.length() - 2, date.length()));
		return DateUtil.getMonthStartTime(year, month);
	}

	/**
	 * 获取该月的结束时间
	 * 
	 * @param date
	 *            yyyyMM格式
	 * @return 时间戳
	 */
	public static long getEndTime(String date) {
		int year = Integer.valueOf(date.substring(0, 4));
		int month = Integer.valueOf(date.substring(date.length() - 2, date.length()));
		return DateUtil.getMonthEndTime(year, month);
	}

	/**
	 * 获取该月的结束时间
	 * 
	 * @param date
	 *            yyyyMM格式
	 * @return 该月份的时间集合，奇数表示yyyyMMdd日期，偶数表示该月份天数，例如：20170601,1
	 */
	public static List<Object> getMonthDateList(String date) {
		int year = Integer.valueOf(date.substring(0, 4));
		int month = Integer.valueOf(date.substring(date.length() - 2, date.length()));
		Calendar cale = Calendar.getInstance();
		cale.set(year, month - 1, Calendar.DATE);
		int maxDate = cale.getActualMaximum(Calendar.DAY_OF_MONTH);
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.TO_DAY);
		List<Object> dateList = new ArrayList<Object>();
		for (int i = 1; i <= maxDate; i++) {
			cale.set(Calendar.DAY_OF_MONTH, i);
			dateList.add(format.format(cale.getTime()));
			dateList.add(i);
		}
		return dateList;
	}

}
