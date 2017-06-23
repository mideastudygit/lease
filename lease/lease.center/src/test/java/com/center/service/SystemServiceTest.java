package com.center.service;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class SystemServiceTest extends TestCase {

	private SystemService systemService = new SystemService();

	public void testGetSystemList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("is_page", 1);
		inMap.put("page_index", 1);
		inMap.put("page_size", 10);
		ParaMap outMap = systemService.getSystemList(inMap);
		System.out.println(outMap);
	}

	public void testAddSystem() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("sys_name", "运营平台");
		inMap.put("sys_code", "1000012");
		ParaMap outMap = systemService.addSystem(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testGetSystem() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("sys_id", "20170510182424942323819910269164");
		inMap.put("sys_code", "1000012");
		inMap.put("sys_name", "运营平台");
		ParaMap outMap = systemService.getSystem(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testUpdateSystem() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("sys_id", "20170510182424942323819910269164");
		inMap.put("sys_name", "运营平台");
		inMap.put("sys_code", "1000013");
		ParaMap outMap = systemService.updateSystem(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	public void testEnableSystem() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("sys_id", "20170510182424942323819910269164");
		inMap.put("status", "0");
		ParaMap outMap = systemService.enableSystem(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
}
