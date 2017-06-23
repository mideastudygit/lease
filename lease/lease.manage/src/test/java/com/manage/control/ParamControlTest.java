package com.manage.control;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

import junit.framework.TestCase;

public class ParamControlTest extends TestCase {

	public void testAddParam() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "param.addParam");
		inMap.put("para_name", "短信模板");
		inMap.put("para_code", "msgMode");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetParamList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "param.getParamList");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetParam() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "param.getParam");
		inMap.put("param_id", "20170508175201186628952748901461");
		inMap.put("para_code", "msgMode");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetParameterList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "param.getParameterList");
		// inMap.put("para_name", "registerMsg");
		inMap.put("para_code", "msgMode");
		// inMap.put("para_value", "msgMode");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testAddParameter() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "param.addParameter");
		inMap.put("para_code", "msgMode");
		inMap.put("para_name", "registerMsg");
		inMap.put("para_value", "验证码是{0}，有效期5分钟，欢迎加入骆驼快充。如非本人操作，请忽略本短信。");
		inMap.put("remark", "手机注册短信");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetParameter() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "param.getParameter");
		inMap.put("parameter_id", "20170516180142486223159257904205");
		inMap.put("para_code", "msgMode");
		inMap.put("para_name", "registerMsg");
		inMap.put("para_value", "验证码是{0}，有效期5分钟，欢迎加入骆驼快充。如非本人操作，请忽略本短信。");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdateParameter() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "param.updateParameter");
		inMap.put("parameter_id", "20170516180142486223159257904205");
		inMap.put("para_code", "msgMode");
		inMap.put("para_name", "registerMsg");
		inMap.put("para_value", "验证码是{0}，有效期5分钟，欢迎加入骆驼快充。如非本人操作，请忽略本短信。");
		inMap.put("remark", "手机注册短信beizhu");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testEnableParameter() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "param.enableParameter");
		inMap.put("parameter_id", "20170516180142486223159257904205");
		inMap.put("status", "1");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
