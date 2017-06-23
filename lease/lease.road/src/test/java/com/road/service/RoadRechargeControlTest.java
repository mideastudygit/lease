package com.road.service;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

import junit.framework.TestCase;

public class RoadRechargeControlTest extends TestCase {

	public void testGetRoadRechargeList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.Recharge.getRoadRechargeList");
		// inMap.put("start_time", "1494259200000");
		// inMap.put("end_time", "1494345600000");
		// inMap.put("merchant_id", "1001");
		// inMap.put("status", "1");
		// inMap.put("keyword", "902091");
		inMap.put("page_index", "1");
		inMap.put("page_size", "5");
		HttpUtil.getData(RoadConsts.URL, inMap);
	}

}
