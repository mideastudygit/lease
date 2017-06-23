package com.common.util;

import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class MD5SignUtilTest extends TestCase {

	public void testSign() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.order.endOrder");
		inMap.put("push_url", "http://fwetfwww.com/app/shlut/");
		inMap.put("app_id", "bonicai");
		inMap.put("state", 1);
		inMap.put("code", "success");
		inMap.put("msg", "请求成功");
		String sign = MD5SignUtil.sign(inMap, "#451554$*qsdgrdfc");
		System.out.println(sign);
	}

	public void testCheckSign() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.order.endOrder");
		inMap.put("push_url", "http://fwetfwww.com/app/shlut/");
		inMap.put("app_id", "bonicai");
		inMap.put("state", 1);
		inMap.put("code", "success");
		inMap.put("msg", "请求成功");
		String sign = MD5SignUtil.sign(inMap, "#451554$*qsdgrdfc");
		System.out.println(sign);
		inMap.put("sign", sign);
		System.out.println(MD5SignUtil.checkSign(inMap, "#451554$*qsdgrdfc"));
	}
}
