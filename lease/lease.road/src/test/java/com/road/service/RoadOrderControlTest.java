package com.road.service;

import junit.framework.TestCase;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

public class RoadOrderControlTest extends TestCase {

	public void testGetOrderList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.order.getOrderList");
		inMap.put("keyword", "");
		String response = HttpUtil.getData(RoadConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetFinishedOrderList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.order.getFinishedOrderList");
		String response = HttpUtil.getData(RoadConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.order.getOrder");
		inMap.put("order_id", "20170526142706216147453643104369");
		String response = HttpUtil.getData(RoadConsts.URL, inMap);
		System.out.println(response);
	}

}
