package com.manage.control;

import junit.framework.TestCase;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

public class OrgControlTest extends TestCase {

	public void testGetOrgList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "org.getOrgList");
		inMap.put("org_code", "100001201");
		inMap.put("org_name", "模拟开");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testAddOrg() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "org.addOrg");
		inMap.put("sys_id", "20170510182424942323819910269164");
		inMap.put("org_name", "亿车科技有限公司");
		inMap.put("org_code", "012545010");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetOrg() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "org.getOrg");
		inMap.put("org_id", "20170517174227378704349351101487");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdateOrg() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "org.updateOrg");
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("org_name", "ecaray");
		inMap.put("org_code", "100001201");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testEnableOrg() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "org.enableOrg");
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("status", 1);
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
