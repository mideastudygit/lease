package com.open.control;

import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.common.util.HttpUtil;
import com.common.util.MD5SignUtil;

import junit.framework.TestCase;

public class RoadOrderControlTest extends TestCase {

	public void testGetOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.order.getOrder");
		inMap.put("app_id", "bonicai");
		inMap.put("timestamp", DateUtils.nowTime());
		inMap.put("sign_type", "MD5");// 20170606115509847110790531931532
		inMap.put("order_id", "20170606115509847110790531931532");// 20170606103750186724621992940675
		inMap.put("city", "340");
		String sign = MD5SignUtil.sign(inMap, "#451554$*qsdgrdfc");
		inMap.put("sign", sign);
		String response = HttpUtil.getData(OpenConsts.URL, inMap);
		System.out.println(response);
	}

	/**
	 * 查询宜停车订单信息，查看是否已经结束
	 */
	public void testProviderOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("provider_order_id", "20810520170606115508");
		RoadOrderService service = new RoadOrderService();
		ParaMap orderMap = service.getProviderOrder(inMap);
		System.out.println(orderMap);
	}
	
	/**
	 * 查询宜停车订单信息，查看是否已经结束
	 */
	public void testProviderGetOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("provider_order_id", "20810520170606115508");
		RoadOrderService service = new RoadOrderService();
		ParaMap orderMap = service.getOrder(inMap);
		System.out.println(orderMap);
	}

	/**
	 * 模拟宜停车订单结算推送，从宜停车查询已经结算订单，然后推送给分时租赁系统
	 */
	public void testProviderPushOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("provider_order_id", "20810520170606103734");
		RoadOrderService service = new RoadOrderService();
		ParaMap orderMap = service.getOrder(inMap);
		service.endOrder(orderMap);
	}
}
