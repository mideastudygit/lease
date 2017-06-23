package com.lease.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.lease.utils.HttpUtil;
import com.lease.utils.MD5SignUtil;

public class ApiController extends Controller {
	String url = PropKit.get("url");
//	String appid = "bonicai";
//	String secret = "#451554$*qsdgrdfc";

	public void post() {
		String res = getData();
		renderText(res);
	}
	
	/**
	 * 获取参数值
	 */
	public String getData() {
		Map<String, String> inMap = new HashMap<>();
		Map<String, String[]> paraMap = getParaMap();
		for (String key : paraMap.keySet()) {
			String[] values = paraMap.get(key);
			if (values != null && values.length > 0) {
				inMap.put(key, values[0]);
			}

		}
		String secret = inMap.get("secret");
		inMap.remove("secret");
		inMap.put("timestamp", (new Date().getTime()) + "");
		inMap.put("format", "json");
		inMap.put("sign_type", "MD5");
		String sign = MD5SignUtil.sign(inMap, secret);
		inMap.put("sign", sign);

		String res = "";
		try {
			
			System.out.println(url + "?" + HttpUtil.getJoinUrl(inMap));
			res = HttpUtil.getData(url, inMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
