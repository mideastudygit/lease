package com.manage.control;

import junit.framework.TestCase;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

public class DeptControlTest extends TestCase {

	public void testGetDeptList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "dept.getDeptList");
		inMap.put("dept_code", "012545010");
		inMap.put("dept_name", "新能源事业部");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testAddDept() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "dept.addDept");
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("dept_name", "新能源事业部");
		inMap.put("dept_code", "012545010");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetDept() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "dept.getDept");
		inMap.put("dept_id", "20170517181852799366356512427422");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdateDept() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "dept.updateDept");
		inMap.put("dept_id", "20170517181852799366356512427422");
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("dept_name", "新能源事业部2");
		inMap.put("dept_code", "10000120110");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testEnableDept() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "dept.enableDept");
		inMap.put("dept_id", "20170517181852799366356512427422");
		inMap.put("status", 1);
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
