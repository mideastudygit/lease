package com.open.control;

import junit.framework.TestCase;

import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.common.util.HttpUtil;
import com.common.util.MD5SignUtil;

public class CarControlTest extends TestCase {

	public void testUpdateCarRealtime() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "car.updateCarRealtime");
		inMap.put("app_id", "bonicai");
		inMap.put("timestamp", DateUtils.nowTime());
		inMap.put("sign_type", "MD5");
		inMap.put("sign", MD5SignUtil.sign(inMap, "#451554$*qsdgrdfc"));
		
		inMap.put("car_plate", "V1粤15452");
		inMap.put("status", 1);
		inMap.put("soc", "95.56%");
		inMap.put("capacity", "100AM");
		String response = HttpUtil.getData(OpenConsts.URL, inMap);
		System.out.println(response);
	}
	
}
