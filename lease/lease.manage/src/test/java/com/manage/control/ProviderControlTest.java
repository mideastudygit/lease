package com.manage.control;

import junit.framework.TestCase;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

public class ProviderControlTest extends TestCase {

	public void testGetProviderList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "provider.getProviderList");
		inMap.put("provider_name", "宜停车");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testAddProvider() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "provider.addProvider");
		inMap.put("provider_name", "camel");
		inMap.put("provider_type", 1);
		inMap.put("provider_code", "00000121");
		inMap.put("clazz", "com.order.service.Order");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetProvider() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "provider.getProvider");
		inMap.put("provider_id", "20170518153714088327469591745561");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdatetProvider() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "provider.updateProvider");
		inMap.put("provider_id", "20170518153714088327469591745561");
		inMap.put("provider_name", "camel");
		inMap.put("provider_type", 1);
		inMap.put("provider_code", "00000121");
		inMap.put("clazz", "com.order.service.Order");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
