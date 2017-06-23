package com.center.mq;

import com.alibaba.fastjson.JSONObject;
import com.base.log.Logging;
import com.base.mq.MessageRece;
import com.base.utils.JsonUtils;
import com.base.utils.ParaMap;
import com.center.consts.MqConsts;
import com.common.route.RouteManager;

/**
 * 系统对账服务
 * 
 * @author 唐宗鸿
 * @date 20170523
 * @version 1.1.0
 */
public class SystemBillRece extends MessageRece {

	private static final Logging log = Logging.getLogging("task");

	@Override
	public String appName() {
		return MqConsts.APP_TRADE;
	}

	@Override
	public void doAction(JSONObject inMap) throws Exception {
		log.info("SystemBillRece，请求：" + inMap);
		ParaMap paraMap = JsonUtils.jsonToMap(inMap);
		// 发送到路边模块
		ParaMap response = RouteManager.route(paraMap.getString("action"), paraMap);
		log.info("SysteBillRece，响应：" + response);
	}

	@Override
	public String queue() {
		return MqConsts.SYSTEM_BILL;
	}
}
