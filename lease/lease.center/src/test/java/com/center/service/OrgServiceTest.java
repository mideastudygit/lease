package com.center.service;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class OrgServiceTest extends TestCase {

	private OrgService orgService = new OrgService();

	public void testGetOrgList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("is_page", 1);
		inMap.put("page_index", 1);
		inMap.put("page_size", 10);
		ParaMap outMap = orgService.getOrgList(inMap);
		System.out.println(outMap);
	}

	public void testAddOrg() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("sys_id", "20170510182424942323819910269164");
		inMap.put("org_code", "100001201");
		inMap.put("org_name", "模拟开");
		ParaMap outMap = orgService.addOrg(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testGetOrg() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("org_code", "100001201");
		inMap.put("org_name", "模拟开");
		ParaMap outMap = orgService.getOrg(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testUpdateOrg() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("org_code", "100001201");
		inMap.put("org_name", "模拟开");
		inMap.put("sys_id", "20170510182424942323819910269164");
		ParaMap outMap = orgService.updateOrg(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testEnableOrg() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("status", "0");
		ParaMap outMap = orgService.enableOrg(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
}
