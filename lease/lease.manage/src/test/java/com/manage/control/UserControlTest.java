package com.manage.control;

import junit.framework.TestCase;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

public class UserControlTest extends TestCase {

	public void testGetUserList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "user.getUserList");
		// inMap.put("user_name", "新能源事业部");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testAddUser() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "user.addUser");
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("dept_id", "20170517181852799366356512427422");
		inMap.put("role_id", "20170517162947279615378358723336");
		inMap.put("user_name", "zhangsan");
		inMap.put("user_pwd", "e10adc3949ba59abbe56e057f20f883e");
		inMap.put("real_name", "张三");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetUser() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "user.getUser");
		inMap.put("user_id", "20170517192013674604434179431401");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdateUser() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "user.updateUser");
		inMap.put("user_id", "20170517192013674604434179431401");
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("dept_id", "20170517181852799366356512427422");
		inMap.put("role_id", "20170517162947279615378358723336");
		inMap.put("user_name", "zhangsan");
		inMap.put("user_pwd", "e10adc3949ba59abbe56e057f20f883e");
		inMap.put("real_name", "张三");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testEnableUser() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "user.enableUser");
		inMap.put("user_id", "20170517192013674604434179431401");
		inMap.put("status", 1);
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetUserResource() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "user.getUserResource");
		inMap.put("user_id", "20170524172217328669515416291819");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdateUserPassword() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "user.updateUserPassword");
		inMap.put("user_id", "20170517192013674604434179431401");
		inMap.put("user_pwd", "e10adc3949ba59abbe56e057f20f883e");
		inMap.put("new_pwd", "e10adc3949ba59abbe56e057f20f883e");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testResetUserPassword() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "user.resetUserPassword");
		inMap.put("user_id", "20170517192013674604434179431401");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
	
	public void testUserLogin() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "user.userLogin");
		inMap.put("u_type_action", "user.getUserType");
		inMap.put("sys_code", "1000013");
		inMap.put("user_name", "14525456526");
		inMap.put("user_pwd", "a2bf8043be45e409704a5b5c206208d7");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
	
	public void testUserLogout() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "user.userLogout");
		inMap.put("u", "20170530152248942105250555136233");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
