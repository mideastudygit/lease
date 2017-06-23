package com.center.service;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class ResourceServiceTest extends TestCase {

	private ResourceService resourceService = new ResourceService();

	public void testGetResourceList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("is_page", 1);
		inMap.put("page_index", 1);
		inMap.put("page_size", 10);
		ParaMap outMap = resourceService.getResourceList(inMap);
		System.out.println(outMap);
	}

	public void testAddResource() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("sys_id", "20170510182424942323819910269164");
		inMap.put("res_name", "模拟开resource");
		inMap.put("show_name", "模拟开resource");
		ParaMap outMap = resourceService.addResource(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testGetResource() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("res_id", "20170510191951060177640422948862");
		inMap.put("res_name", "模拟开resource");
		inMap.put("show_name", "模拟开resource");
		ParaMap outMap = resourceService.getResource(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testUpdateResource() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("res_id", "20170510191951060177640422948862");
		inMap.put("sys_id", "20170510182424942323819910269164");
		inMap.put("res_name", "模拟开resource");
		inMap.put("show_name", "模拟开resource");
		ParaMap outMap = resourceService.updateResource(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testEnableResource() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("res_id", "20170510191951060177640422948862");
		inMap.put("status", "0");
		ParaMap outMap = resourceService.enableResource(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
}
