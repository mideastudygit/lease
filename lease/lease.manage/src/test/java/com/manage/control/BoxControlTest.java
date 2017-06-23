package com.manage.control;

import junit.framework.TestCase;

import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

public class BoxControlTest extends TestCase {

	public void testGetBoxList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "box.getBoxList");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testAddBox() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "box.addBox");
		inMap.put("box_no", "01254210");
		inMap.put("car_plate", "V1粤15452");
		inMap.put("use_date", DateUtils.nowTime());
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetBox() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "box.getBox");
		inMap.put("box_id", "20170519142243811686415190657165");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdateBox() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "box.updateBox");
		inMap.put("box_id", "20170519142243811686415190657165");
		inMap.put("box_no", "102145221");
		inMap.put("car_plate", "V1粤15452");
		inMap.put("use_date", DateUtils.nowTime());
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetBoxUseStatistics() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "box.getBoxUseStatistics");
		inMap.put("merchant_id", "20170518145739921441504516669327");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
