package com.center.service;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class UserServiceTest extends TestCase {

	private UserService userService = new UserService();

	public void testGetUserList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("is_page", 1);
		inMap.put("page_index", 1);
		inMap.put("page_size", 10);
		ParaMap outMap = userService.getUserList(inMap);
		System.out.println(outMap);
	}

	public void testAddUser() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("dept_id", "20170510185936775743402734567469");
		inMap.put("role_id", "20170510190832791226319351102869");
		inMap.put("user_name", "18175456253");
		inMap.put("user_pwd", "e10adc3949ba59abbe56e057f20f883e");
		inMap.put("real_name", "ter14521");
		ParaMap outMap = userService.addUser(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testGetUser() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("user_id", "2017051110202322689871404127926300");
//		inMap.put("user_name", "18175456253");
		ParaMap outMap = userService.getUser(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testUpdateUser() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("user_id", "20170511102023226898714041279263");
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("dept_id", "20170510185936775743402734567469");
		inMap.put("role_id", "20170510190832791226319351102869");
		inMap.put("user_name", "18175456253");
		inMap.put("user_pwd", "e10adc3949ba59abbe56e057f20f883e");
		inMap.put("real_name", "ter145");
		ParaMap outMap = userService.updateUser(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testEnableUser() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("user_id", "20170511102023226898714041279263");
		inMap.put("status", "0");
		ParaMap outMap = userService.enableUser(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testUpdateUserPwd() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("user_id", "20170511102023226898714041279263");
		inMap.put("user_pwd", "e10adc3949ba59abbe56e057f20f883e");
		inMap.put("new_pwd", "e10adc3949ba59abbe56e057f20f8835");
		ParaMap outMap = userService.updateUserPassword(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testResetUserPwd() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("user_id", "20170511102023226898714041279263");
		inMap.put("new_pwd", "e10adc3949ba59abbe56e057f20f883e");
		ParaMap outMap = userService.resetUserPassword(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testCheckUserName() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("user_id", "20170511102023226898714041279263");
		inMap.put("new_pwd", "e10adc3949ba59abbe56e057f20f883e");
		ParaMap outMap = userService.checkUserName("18175456253");
		System.out.println(outMap);
	}

	public void testUserLogin() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("user_id", "20170511102023226898714041279263");
		inMap.put("new_pwd", "e10adc3949ba59abbe56e057f20f883e");
		ParaMap outMap = userService.userLogin("18175456253",
				"e10adc3949ba59abbe56e057f20f883e", "1000013");
		System.out.println(outMap);
	}

	public void testGetUserResource() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("user_id", "20170511102023226898714041279263");
		ParaMap outMap = userService
				.getUserResource("20170511102023226898714041279263");
		System.out.println(outMap);
	}
	
}
