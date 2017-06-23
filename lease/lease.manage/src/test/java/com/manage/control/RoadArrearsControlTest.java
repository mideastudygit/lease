package com.manage.control;

import com.base.utils.ParaMap;
import com.common.util.DateUtil;
import com.common.util.HttpUtil;

import junit.framework.TestCase;

public class RoadArrearsControlTest extends TestCase {

	public void testGetArrearsOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.arrears.getArrearsOrder");
		inMap.put("arrears_id", "20170526161851365640189121563366");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetArrearsOrderList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.arrears.getArrearsOrderList");
		inMap.put("start_time", DateUtil.nowTime());
		inMap.put("end_time", DateUtil.nowTime());
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testApplyPayArrears() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.arrears.applyPayArrears");
		inMap.put("arrears_id", "20170527111752941746741688037744");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
