package com.open.control;

import com.base.utils.MathUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.base.web.AppConfig;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.util.RespUtil;
import com.open.consts.OpenConsts;
import com.open.util.MerchantUtil;

public class RoadParkControl {

	public ParaMap getBerthStatus(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("berth_code")) || StrUtils.isNull(inMap.getString("city"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		if (StrUtils.isNull(inMap.getString("city"))) {
			inMap.put("city", AppConfig.getStringPro("default.city"));// 默认深圳市
		}
		// 校验该城市是否支持
		ParaMap sendMap = new ParaMap();
		sendMap.put("city", inMap.getString("city"));
		ParaMap resultMap = RouteManager.route("provider.getProvider", sendMap);
		ParaMap providerMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		if (providerMap == null || providerMap.isEmpty()) {
			return RespUtil.setResp(RespState.FAIL, "city.not.support");
		}
		inMap.put("provider_id", providerMap.getString("provider_id"));
		resultMap = RouteManager.route(inMap.getString("action"), inMap);
		return resultMap;
	}

	public ParaMap applyPark(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("berth_code")) || StrUtils.isNull(inMap.getString("city"))
				|| StrUtils.isNull(inMap.getString("car_plate")) || StrUtils.isNull(inMap.getString("ticket"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		if (StrUtils.isNull(inMap.getString("city"))) {
			inMap.put("city", AppConfig.getStringPro("default.city"));// 默认深圳市
		}
		// 校验该城市是否支持
		ParaMap sendMap = new ParaMap();
		sendMap.put("city", inMap.getString("city"));
		ParaMap resultMap = RouteManager.route("provider.getProvider", sendMap);
		ParaMap providerMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		if (providerMap == null || providerMap.isEmpty()) {
			return RespUtil.setResp(RespState.FAIL, "city.not.support");
		}
		inMap.put("provider_id", providerMap.getString("provider_id"));

		// 校验该泊位是否可用，是否为全日制泊位
		resultMap = RouteManager.route("road.park.getBerthStatus", inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap berthMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		if (berthMap == null || OpenConsts.BERTH_SATUS_NOTUSE == berthMap.getInt("berth_status")) {
			return RespUtil.setResp(RespState.FAIL, "road.berth.invalid");
		}

		// 校验商户余额，不足则发送短信提示
		ParaMap merchantMap = (ParaMap) RouteManager.route("merchant.getMerchant", inMap).get(RespKey.DATA.getValue());
		if (MathUtils.compareTo(merchantMap.getDouble("balance"), merchantMap.getDouble("alarm_balance")) <= 0) {
			sendMap.put("para_code", "consts");
			sendMap.put("para_name", "balance.notify.count");
			resultMap = RouteManager.route("param.getParameter", sendMap);// 查询发送的次数限制
			ParaMap dataMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
			int count = dataMap.getInt("para_value");
			int cacheCount = MerchantUtil.updateBalanceNotifyCount(inMap.getString("merchant_id"));
			if (cacheCount <= count) {
				sendMap.put("tel", merchantMap.getString("tel"));
				sendMap.put("alarm_balance", merchantMap.getString("alarm_balance"));
				RouteManager.route("sms.notifyBalanceNotEnough", sendMap);
			}
		} else {
			MerchantUtil.removeBalanceNotify(inMap.getString("merchant_id"));
		}
		inMap.put("pay_type", OpenConsts.PAY_TYPE_APPLY_PAY);// 后付费模式
		resultMap = RouteManager.route("road.park.applyPark", inMap);
		return resultMap;
	}
}
