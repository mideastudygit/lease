package com.manage.control;

import junit.framework.TestCase;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

public class RoadWorkbenchControlTest extends TestCase {

	public void testGetSectionList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.workbench.getSectionList");
		inMap.put("keywords", "百花三路");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetNearbyBerthList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.workbench.getNearbyBerthList");
		inMap.put("longitude", "113.920240");
		inMap.put("latitude", "22.521419");
		inMap.put("distance", "2000");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetOrderTotal() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.workbench.getOrderTotal");
		inMap.put("merchant_id", "20170511181305918332233476614521");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testBerthUseTotal() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.workbench.getBerthUseTotal");
		inMap.put("merchant_id", "20170511181305918332233476614521");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetOrderFeeTop() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.workbench.getOrderFeeTop");
		inMap.put("date", "201706");
		inMap.put("merchant_id", "20170511181305918332233476614521");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetOrderAnalysis() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.workbench.getOrderAnalysis");
		inMap.put("date", "201706");
		// inMap.put("merchant_id", "20170511181305918332233476614521");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetOrderMonthTotal() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.workbench.getOrderMonthTotal");
		inMap.put("merchant_id", "20170511181305918332233476614521");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

}
