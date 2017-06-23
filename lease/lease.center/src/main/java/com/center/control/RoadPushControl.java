package com.center.control;

import com.base.mq.MQSendUtils;
import com.base.utils.ParaMap;
import com.center.consts.CenterConsts;
import com.center.consts.MqConsts;
import com.center.service.MerchantService;
import com.common.util.RespUtil;

public class RoadPushControl {

	public ParaMap endOrder(ParaMap inMap) throws Exception {
		// 推送结算订单，先更改商户余额
		ParaMap sendMap = new ParaMap();
		sendMap.put("amount", inMap.getString("should_pay"));
		sendMap.put("type", CenterConsts.BALANCE_TYPE_CUSTOMER);
		sendMap.put("merchant_id", inMap.getString("merchant_id"));
		MerchantService merchantService = new MerchantService();
		merchantService.updateMerchantBalance(sendMap);

		MQSendUtils.send(MqConsts.ROAD_ORDER_PUSH, inMap);
		return RespUtil.setResp();
	}

}
