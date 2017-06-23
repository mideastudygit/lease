package com.open.control;

import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.common.util.HttpUtil;
import com.common.util.MD5SignUtil;

import junit.framework.TestCase;

public class ErcarOrderControlTest extends TestCase {

	public void testAddErcarOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "ercar.order.addOrder");
		inMap.put("app_id", "bonicai");
		inMap.put("timestamp", DateUtils.nowTime());
		inMap.put("sign_type", "MD5");
		inMap.put("mer_order_id", "01254562510");
		inMap.put("car_plate", "V1粤15452");
		inMap.put("status", 1);
		inMap.put("begin_time", DateUtils.nowTime());
		String sign = MD5SignUtil.sign(inMap, "#451554$*qsdgrdfc");
		inMap.put("sign", sign);
		String response = HttpUtil.getData(OpenConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdateErcarOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "ercar.order.updateOrder");
		inMap.put("app_id", "bonicai");
		inMap.put("timestamp", DateUtils.nowTime());
		inMap.put("sign_type", "MD5");
		inMap.put("mer_order_id", "01254562510");
		inMap.put("status", 1);
		inMap.put("amount", 10.56);
		inMap.put("end_time", DateUtils.nowTime());
		String sign = MD5SignUtil.sign(inMap, "#451554$*qsdgrdfc");
		inMap.put("sign", sign);
		String response = HttpUtil.getData(OpenConsts.URL, inMap);
		System.out.println(response);
	}
}
