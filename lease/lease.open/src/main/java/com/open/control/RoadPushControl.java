package com.open.control;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;
import com.common.util.MD5SignUtil;
import com.common.util.RespUtil;

public class RoadPushControl {

	public ParaMap endOrder(ParaMap inMap) throws Exception {
		String url = inMap.getString("push_url");
		String appSecret = inMap.getString("app_secret");
		ParaMap orderMap = this.orderConvert(inMap);
		orderMap.put("action", "road.order.endOrder");
		// 签名信息
		String sign = MD5SignUtil.sign(orderMap, appSecret);
		orderMap.put("sign", sign);
		HttpUtil.getData(url, orderMap);
		return RespUtil.setResp();
	}

	/**
	 * 将返回的订单信息过滤
	 * 
	 * @param 原订单信息
	 * @return
	 */
	private ParaMap orderConvert(ParaMap orderMap) {
		ParaMap convertOrderMap = new ParaMap();
		convertOrderMap.put("app_id", orderMap.getString("app_id"));
		convertOrderMap.put("order_id", orderMap.getString("order_id"));
		convertOrderMap.put("ticket", orderMap.getString("ticket"));
		convertOrderMap.put("berth_code", orderMap.getString("berth_code"));
		convertOrderMap.put("amount", String.format("%.2f", orderMap.getDouble("should_pay")));
		convertOrderMap.put("car_plate", orderMap.getString("car_plate"));
		convertOrderMap.put("canton", orderMap.getString("canton"));
		convertOrderMap.put("area", orderMap.getString("area"));
		convertOrderMap.put("section", orderMap.getString("section"));
		convertOrderMap.put("start_park_time", orderMap.getString("start_park_time"));
		convertOrderMap.put("end_park_time", orderMap.getString("end_park_time"));
		convertOrderMap.put("start_time", orderMap.getString("start_time"));
		convertOrderMap.put("end_time", orderMap.getString("end_time"));
		convertOrderMap.put("duration", orderMap.getString("actual_duration"));
		return convertOrderMap;
	}
}
