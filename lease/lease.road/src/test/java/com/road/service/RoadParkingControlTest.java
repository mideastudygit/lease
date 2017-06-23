package com.road.service;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

import junit.framework.TestCase;

public class RoadParkingControlTest extends TestCase {

	public void testGetBerthStatus() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.park.getBerthStatus");
		inMap.put("provider_id", "20170509114724329438503352764125");
		inMap.put("berth_code", "208102");
		HttpUtil.getData(RoadConsts.URL, inMap);
	}

	public void testApplyPark() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.park.applyPark");
		inMap.put("provider_id", "20170509114724329438503352764125");
		inMap.put("berth_code", "208102");
		inMap.put("merchant_id", "20170511181305918332233476614521");
		inMap.put("car_plate", "粤A000056");
		HttpUtil.getData(RoadConsts.URL, inMap);
	}

}
