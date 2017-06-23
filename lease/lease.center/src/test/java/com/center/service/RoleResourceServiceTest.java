package com.center.service;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class RoleResourceServiceTest extends TestCase {

	private RoleResourceService roleResourceService = new RoleResourceService();

	public void testGetRoleResourceList() throws Exception {
		ParaMap inMap = new ParaMap();
//		inMap.put("is_page", 1);
		inMap.put("role_id", "20170510190832791226319351102869");
		inMap.put("page_index", 1);
		inMap.put("page_size", 10);
		ParaMap outMap = roleResourceService.getRoleResourceList(inMap);
		System.out.println(outMap);
	}

	public void testAddRoleResource() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("role_id", "20170510190832791226319351102869");
		inMap.put(
				"res_id",
				"20170601143534468417641062889516,20170601143602111763508406185647");
		ParaMap outMap = roleResourceService.addRoleResource(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testDelRoleResource() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("role_id", "20170510190832791226319351102869");
		inMap.put("res_id", "20170510191951060177640422948865,20170510191951060177640422948866");
		ParaMap outMap = roleResourceService.delRoleResource(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
}
