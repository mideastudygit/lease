package com.center.service;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class DeptServiceTest extends TestCase {

	private DeptService deptService = new DeptService();

	public void testGetDeptList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("is_page", 1);
		inMap.put("page_index", 1);
		inMap.put("page_size", 10);
		ParaMap outMap = deptService.getDeptList(inMap);
		System.out.println(outMap);
	}

	public void testAddDept() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("dept_code", "10000120101");
		inMap.put("dept_name", "模拟开werew");
		ParaMap outMap = deptService.addDept(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testGetDept() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("dept_id", "20170510185936775743402734567469");
		inMap.put("dept_code", "10000120101");
		inMap.put("dept_name", "模拟开werew");
		ParaMap outMap = deptService.getDept(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testUpdateDept() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("dept_id", "20170510185936775743402734567469");
		inMap.put("org_id", "20170510184347596100473562310474");
		inMap.put("dept_code", "10000120101");
		inMap.put("dept_name", "模拟开");
		ParaMap outMap = deptService.updateDept(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testEnableDept() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("dept_id", "20170510185936775743402734567469");
		inMap.put("status", "0");
		ParaMap outMap = deptService.enableDept(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
}
