package com.center.service;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class RoleServiceTest extends TestCase {

	private RoleService roleService = new RoleService();

	public void testGetRoleList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("is_page", 1);
		inMap.put("page_index", 1);
		inMap.put("page_size", 10);
		ParaMap outMap = roleService.getRoleList(inMap);
		System.out.println(outMap);
	}

	public void testAddRole() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("sys_id", "20170510182424942323819910269164");
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("role_name", "模拟开role");
		ParaMap outMap = roleService.addRole(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testGetRole() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("role_id", "20170510190832791226319351102869");
		inMap.put("role_name", "模拟开role");
		ParaMap outMap = roleService.getRole(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testUpdateRole() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("role_id", "20170510190832791226319351102869");
		inMap.put("sys_id", "20170510182424942323819910269164");
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("role_name", "模拟开");
		ParaMap outMap = roleService.updateRole(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testEnableRole() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("role_id", "20170510190832791226319351102869");
		inMap.put("status", "0");
		ParaMap outMap = roleService.enableRole(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
}
