package com.manage.control;

import junit.framework.TestCase;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

public class RoleControlTest extends TestCase {

	public void testGetRoleList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "role.getRoleList");
		inMap.put("sys_id", "20170510182424942323819910269164");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testAddRole() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "role.addRole");
		inMap.put("sys_id", "20170510182424942323819910269164");
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("role_name", "运维");
		inMap.put("sort_num", "");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetRole() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "role.getRole");
		inMap.put("role_id", "20170517162947279615378358723336");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdateRole() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "role.updateRole");
		inMap.put("sys_id", "20170510182424942323819910269164");
		inMap.put("role_id", "20170517162947279615378358723336");
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("role_name", "运维");
		inMap.put("sort_num", "");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testEnableRole() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "role.enableRole");
		inMap.put("role_id", "20170517162947279615378358723336");
		inMap.put("status", 1);
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetRoleResourceList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "role.getRoleResourceList");
		inMap.put("role_id", "20170510190832791226319351102869");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testAddRoleResource() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "role.addRoleResource");
		inMap.put("role_id", "20170510190832791226319351102869");
		inMap.put(
				"res_id",
				"20170510191951060177640422948860,20170510191951060177640422948865,20170510191951060177640422948866");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testDelRoleResource() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "role.delRoleResource");
		inMap.put("role_id", "20170517162947279615378358723336");
		inMap.put("res_id", "20170516191707402712517126782324");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
