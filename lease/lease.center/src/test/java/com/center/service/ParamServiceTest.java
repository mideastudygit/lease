package com.center.service;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class ParamServiceTest extends TestCase {

	private ParamService paramService = new ParamService();

	public void testGetParamList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("ispage", 1);
		inMap.put("page_index", 1);
		inMap.put("page_size", 10);
		ParaMap outMap = paramService.getParamList(inMap);
		System.out.println(outMap);
	}

	public void testAddParam() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("para_code", "msgMode");
		inMap.put("para_name", "短信模板");
		ParaMap outMap = paramService.addParam(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testGetParam() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("para_code", "msgMode");
		inMap.put("para_name", "短信模板");
		ParaMap outMap = paramService.getParam(inMap);
		System.out.println(outMap);
	}

	public void testGetParameterList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("ispage", 1);
		inMap.put("page_index", 1);
		inMap.put("page_size", 10);
		ParaMap outMap = paramService.getParameterList(inMap);
		System.out.println(outMap);
	}

	public void testAddParameter() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("para_code", "msgMode");
		inMap.put("para_name", "registerMsg");
		inMap.put("para_value", "验证码是{0}，有效期5分钟");
		ParaMap outMap = paramService.addParameter(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testGetParameter() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("para_code", "msgMode");
		inMap.put("para_name", "registerMsg");
		ParaMap outMap = paramService.getParameter(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testUpdateParameter() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("parameter_id", "20170508190950141197968210301772");
		inMap.put("para_code", "msgMode");
		inMap.put("para_name", "registerMsg");
		inMap.put("para_value", "{0}");
		ParaMap outMap = paramService.updateParameter(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testEnableParameter() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("parameter_id", "20170508190950141197968210301772");
		inMap.put("status", "0");
		ParaMap outMap = paramService.enableParameter(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
}
