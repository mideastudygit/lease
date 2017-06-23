package com.center.service;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class RechargeServiceTest extends TestCase {

	private RechargeService rechargeService = new RechargeService();

	public void testGetRechargeList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_name", "");
		inMap.put("start_time", "");
		inMap.put("end_time", "");
		inMap.put("page_index", 1);
		inMap.put("page_size", 10);
		ParaMap outMap = rechargeService.getRechargeList(inMap);
		System.out.println(outMap);
	}

	public void testAddRecharge() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_id", "20170510182424942323819910269145");
		inMap.put("amount", "52.10");
		ParaMap outMap = rechargeService.addRecharge(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
}
