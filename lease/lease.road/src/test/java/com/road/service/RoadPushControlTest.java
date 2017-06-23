package com.road.service;

import junit.framework.TestCase;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;
import com.common.util.MD5SignUtil;

public class RoadPushControlTest extends TestCase {

	public void testPushOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.order.endOrder");
		inMap.put("app_id", "102591626");
		inMap.put("BargainOrderCode", "20810520170605170633");
		inMap.put("StartParkingTime", "2017-06-05 17:16:27");
		inMap.put("EndParkingTime", "2017-06-05 18:16:27");
		inMap.put("ActualDuration", 60);
		inMap.put("ActualPrice", 10);
		inMap.put("DetailsPrice", 10);
		inMap.put("ApplyDuration", 0);
		inMap.put("BerthCode", "208105");
		inMap.put("SectionName", "文心一路");
		inMap.put("AreaName", "南山中心区");
		inMap.put("BerthStartParkingTime", "2017-06-05 17:16:20");
		inMap.put("BerthEndParkingTime", "2017-06-05 18:16:20");
		String sign = MD5SignUtil.sign(inMap, "e10adc3949ba59abbe56e057f20f883e");
		inMap.put("sign", sign);
		String response = HttpUtil.getData(RoadConsts.PUSH_URL, inMap);
		System.out.println(response);
	}
}
