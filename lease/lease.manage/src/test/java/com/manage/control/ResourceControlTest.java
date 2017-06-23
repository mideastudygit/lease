package com.manage.control;

import junit.framework.TestCase;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

public class ResourceControlTest extends TestCase {

	public void testGetResourceList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "resource.getResourceList");
		inMap.put("sys_id", "20170510182424942323819910269164");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testAddResource() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "resource.addResource");
		inMap.put("sys_id", "20170510182424942323819910269164");
		inMap.put("res_name", "工作台");
		inMap.put("show_name", "工作台");
		inMap.put("res_type", 1);
		inMap.put("res_icon", "#plate");
		inMap.put("url", "/plat/weclome");
		inMap.put("parent_id", "");
		inMap.put("sort_num", "");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetResource() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "resource.getResource");
		inMap.put("res_id", "20170516191707402712517126782324");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdateResource() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "resource.updateResource");
		inMap.put("res_id", "20170516191707402712517126782324");
		inMap.put("sys_id", "20170510182424942323819910269164");
		inMap.put("res_name", "工作台");
		inMap.put("show_name", "工作台");
		inMap.put("res_type", 1);
		inMap.put("res_icon", "#plate");
		inMap.put("url", "/plat/weclome");
		inMap.put("parent_id", "");
		inMap.put("sort_num", "");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testEnableResource() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "resource.enableResource");
		inMap.put("res_id", "20170516191707402712517126782324");
		inMap.put("status", 1);
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
