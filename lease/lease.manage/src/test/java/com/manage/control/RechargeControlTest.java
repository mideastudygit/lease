package com.manage.control;

import junit.framework.TestCase;

import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

public class RechargeControlTest extends TestCase {

	public void testGetRechargeList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "recharge.getRechargeList");
		inMap.put("merchant_name", "bonikai");
		inMap.put("start_time", DateUtils.nowTime());
		inMap.put("end_time", DateUtils.nowTime());
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testAddRechargeRecord() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "recharge.addRechargeRecord");
		inMap.put("merchant_id", "20170511181305918332233476614521");
		inMap.put("amount", 10.25);
		inMap.put("user_id", "20170511102023226898714041279263");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetProviderRechargeList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "recharge.getProviderRechargeList");
		inMap.put("provider_id", "20170509114724329438503352764125");
		inMap.put("page_index", 1);
		inMap.put("page_size", 10);
		inMap.put("recharge_type", 7);
		inMap.put("start_time", DateUtils.nowTime());
		inMap.put("end_time", DateUtils.nowTime());
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
