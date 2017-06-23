package com.open.control;

import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.common.util.HttpUtil;
import com.common.util.MD5SignUtil;

import junit.framework.TestCase;

public class RoadRefundControlTest extends TestCase {

	public void testGetRefundOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.refund.getRefundOrder");
		inMap.put("app_id", "bonicai");
		inMap.put("timestamp", DateUtils.nowTime());
		inMap.put("sign_type", "MD5");
		inMap.put("refund_id", "01200054");
		inMap.put("city", "340");
		String sign = MD5SignUtil.sign(inMap, "#451554$*qsdgrdfc");
		inMap.put("sign", sign);
		String response = HttpUtil.getData(OpenConsts.URL, inMap);
		System.out.println(response);
	}
}
