package com.center.mq;

import com.alibaba.fastjson.JSONObject;
import com.base.log.Logging;
import com.base.mq.MessageRece;
import com.base.utils.JsonUtils;
import com.base.utils.ParaMap;
import com.center.consts.MqConsts;
import com.center.service.MerchantService;
import com.common.route.RouteManager;

/**
 * 路边订单推送服务
 * 
 * @author 唐宗鸿
 * @date 20170523
 * @version 1.1.0
 */
public class RoadOrderPushRece extends MessageRece {

	private static final Logging log = Logging.getLogging("task");

	@Override
	public String appName() {
		return MqConsts.APP_TRADE;
	}

	@Override
	public void doAction(JSONObject inMap) throws Exception {
		log.info("RoadOrderPushRece，请求：" + inMap);
		ParaMap orderMap = JsonUtils.jsonToMap(inMap);

		ParaMap sendMap = new ParaMap();
		sendMap.put("merchant_id", orderMap.getString("merchant_id"));

		// 新增商户信息
		MerchantService merchantService = new MerchantService();
		ParaMap merchantMap = merchantService.getMerchant(sendMap);

		orderMap.put("app_id", merchantMap.getString("app_id"));
		orderMap.put("push_url", merchantMap.getString("push_url"));
		orderMap.put("app_secret", merchantMap.getString("app_secret"));

		// 推送到开放层
		ParaMap response = RouteManager.route("road.push.endOrder", orderMap);
		log.info("RoadOrderPushRece，响应：" + response);
	}

	@Override
	public String queue() {
		return MqConsts.ROAD_ORDER_PUSH;
	}

}
