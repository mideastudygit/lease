package com.center.service;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class ProviderServiceTest extends TestCase {

	private ProviderService providerService = new ProviderService();

	public void testGetProviderList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("ispage", 1);
		inMap.put("page_index", 1);
		inMap.put("page_size", 10);
		ParaMap outMap = providerService.getProviderList(inMap);
		System.out.println(outMap);
	}

	public void testAddProvider() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("provider_name", "宜停车");
		inMap.put("provider_code", "1000012");
		inMap.put("provider_type", "1");
		inMap.put("tel", "15245626532");
		inMap.put("app_id", "ecaray");
		inMap.put("app_secret", "tye15452ewset");
		inMap.put("password", "67a263a82b87eb328d1da800a78f5d0c");
		inMap.put("clazz", "com.order.service.YiRoadService");
		inMap.put("city", "15452");
		ParaMap outMap = providerService.addProvider(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testGetProvider() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("provider_id", "20170509113903286519424756571551");
		inMap.put("provider_code", "1000012");
		inMap.put("provider_type", "1");
		inMap.put("tel", "15245626532");
		inMap.put("app_id", "ecaray");
		ParaMap outMap = providerService.getProvider(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testUpdateProvider() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("provider_id", "20170509113903286519424756571555");
		inMap.put("provider_name", "合肥停车");
		inMap.put("provider_code", "1000012");
		inMap.put("provider_type", "1");
		inMap.put("tel", "15245626532");
		inMap.put("app_id", "ecaray");
		inMap.put("app_secret", "tye15452ewset");
		inMap.put("password", "67a263a82b87eb328d1da800a78f5d0c");
		inMap.put("clazz", "com.order.service.HeRoadService");
		inMap.put("city", "15452");
		providerService.updateProvider(inMap);
		DataSourceManager.commit();
		System.out.println();
	}
}
