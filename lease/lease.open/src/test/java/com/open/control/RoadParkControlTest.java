package com.open.control;

import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.common.util.HttpUtil;
import com.common.util.MD5SignUtil;

import junit.framework.TestCase;

public class RoadParkControlTest extends TestCase {

	public void testGetBerthStatus() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.park.getBerthStatus");
		inMap.put("app_id", "bonicai");
		inMap.put("timestamp", DateUtils.nowTime());
		inMap.put("sign_type", "MD5");
		inMap.put("berth_code", "208105");
		inMap.put("city", "340");
		String sign = MD5SignUtil.sign(inMap, "#451554$*qsdgrdfc");
		inMap.put("sign", sign);
		String response = HttpUtil.getData(OpenConsts.URL, inMap);
		System.out.println(response);
	}

	public void testApplyPark() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.park.applyPark");
		inMap.put("app_id", "bonicai");
		inMap.put("timestamp", DateUtils.nowTime());
		inMap.put("sign_type", "MD5");
		inMap.put("berth_code", "208105");
		inMap.put("city", "340");
		inMap.put("car_plate", "V1粤15452");
		inMap.put("ticket", "18175488545");
		String sign = MD5SignUtil.sign(inMap, "#451554$*qsdgrdfc");
		inMap.put("sign", sign);
		String response = HttpUtil.getData(OpenConsts.URL, inMap);
		System.out.println(response);
	}
}
