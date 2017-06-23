package com.common.util;

import junit.framework.TestCase;

public class DateUtilTest extends TestCase {

	public void testGetStartTime() throws Exception {
		long startTime = DateUtil.getStartTimeOfDay("2017-06-15", DateUtil.TO_DAY_LINE);
		System.out.println(startTime);
	}

	public void testGetEndTime() throws Exception {
		long endTime = DateUtil.getEndTimeOfDay("2017-06-15", DateUtil.TO_DAY_LINE);
		System.out.println(endTime);
	}
}
