package com.open.control;

import java.util.ArrayList;
import java.util.List;

import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.base.web.AppConfig;
import com.common.conf.MsgConfig;
import com.common.consts.SqlConsts;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.util.DateUtil;
import com.common.util.RespUtil;
import com.open.consts.OpenConsts;

public class RoadRefundControl {

	public ParaMap getRefundOrder(ParaMap inMap) throws Exception {
		int pageIndex = inMap.getInt("page_index", SqlConsts.PAGE_INDEX);
		if (pageIndex < 1) {
			return RespUtil.setResp(RespState.FAIL, "param.error");
		}
		int pageSize = inMap.getInt("page_size", SqlConsts.PAGE_SIZE);
		if (pageSize > OpenConsts.MAX_QUERY_COUNT) {
			return RespUtil.setResp(RespState.FAIL, "param.error");
		}
		if (StrUtils.isNull(inMap.getString("start_time"))) {
			inMap.put("start_time", DateUtil.prevMonth(AppConfig.getIntPro("default.delay.month")).getTime()); // 默认当天前推一个月时间
		}
		if (StrUtils.isNull(inMap.getString("end_time"))) {
			inMap.put("end_time", DateUtils.nowTime());
		}
		if (inMap.getLong("end_time") - inMap.getLong("start_time") < 0) {
			return RespUtil.setResp(RespState.FAIL, "invalid.end.time");
		}

		// 校验时间查询的时间跨度，不能超出一年，时间间隔不能超过三个月
		int periodYear = AppConfig.getIntPro("year.period.max");
		if (DateUtil.periodYearDate(inMap.getLong("start_time"), inMap.getLong("end_time")) > periodYear) {
			return RespUtil.setResp(RespState.FAIL, MsgConfig.get("invalid.year.period", periodYear));
		}
		int periodMonth = AppConfig.getIntPro("month.period.max");
		if (DateUtil.periodMonthDate(inMap.getLong("start_time"), inMap.getLong("end_time")) > periodMonth) {
			return RespUtil.setResp(RespState.FAIL, MsgConfig.get("invalid.month.period", periodMonth));
		}

		if (StrUtils.isNull(inMap.getString("city"))) {
			inMap.put("city", AppConfig.getStringPro("default.city"));// 默认深圳市
		}
		// 校验该城市是否支持
		ParaMap cityMap = new ParaMap();
		cityMap.put("city", inMap.getString("city"));
		ParaMap resultMap = RouteManager.route("provider.getProvider", cityMap);
		ParaMap providerMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		if (providerMap == null || providerMap.isEmpty()) {
			return RespUtil.setResp(RespState.FAIL, "city.not.support");
		}

		ParaMap sendMap = new ParaMap();
		sendMap.put("provider_id", providerMap.getString("provider_id"));
		if (StrUtils.isNotNull(inMap.getString("refund_id"))) {
			sendMap.put("refund_id", inMap.getString("refund_id"));
			resultMap = RouteManager.route("road.refund.getRefundOrder", sendMap);
			resultMap = this.dealResponMap(resultMap);
			return resultMap;
		}

		sendMap.put("merchant_id", inMap.getString("merchant_id"));
		sendMap.put("order_id", inMap.getString("order_id"));
		sendMap.put("refund_id", inMap.getString("refund_id"));
		sendMap.put("refund_status", inMap.getString("refund_status"));
		sendMap.put("start_time", inMap.getString("start_time"));
		sendMap.put("end_time", inMap.getString("end_time"));
		sendMap.put("page_index", pageIndex);
		sendMap.put("page_size", pageSize);
		resultMap = RouteManager.route("road.refund.getRefundOrderList", sendMap);
		resultMap = this.dealResponListMap(resultMap);
		return resultMap;
	}

	private ParaMap dealResponListMap(ParaMap inMap) {
		if (RespState.SUCCESS.getValue() != inMap.getInt(RespKey.STATE.getValue())) {
			return inMap;
		}
		List<ParaMap> refundConvertList = new ArrayList<ParaMap>();
		List<ParaMap> refundList = (List<ParaMap>) inMap.get(RespKey.DATA.getValue());
		if (refundList == null || refundList.isEmpty()) {
			inMap.put(RespKey.DATA.getValue(), refundConvertList);
			return inMap;
		}
		for (ParaMap orderMap : refundList) {
			ParaMap convertOrderMap = this.refundConvert(orderMap);
			refundConvertList.add(convertOrderMap);
		}
		inMap.put(RespKey.DATA.getValue(), refundConvertList);
		return inMap;
	}

	/**
	 * 将单条订单信息，转化成数组的统一返回格式
	 */
	private ParaMap dealResponMap(ParaMap inMap) {
		if (RespState.SUCCESS.getValue() != inMap.getInt(RespKey.STATE.getValue())) {
			return inMap;
		}
		List<ParaMap> refundList = new ArrayList<ParaMap>();
		ParaMap orderMap = (ParaMap) inMap.get(RespKey.DATA.getValue());
		if (orderMap == null || orderMap.isEmpty()) {
			inMap.put(RespKey.DATA.getValue(), refundList);
			return inMap;
		}
		ParaMap convertOrderMap = this.refundConvert(orderMap);
		refundList.add(convertOrderMap);
		inMap.put(RespKey.DATA.getValue(), refundList);
		return inMap;
	}

	/**
	 * 将返回的退费单信息过滤
	 * 
	 * @param 原退费单信息
	 * @return
	 */
	private ParaMap refundConvert(ParaMap orderMap) {
		ParaMap convertOrderMap = new ParaMap();
		convertOrderMap.put("order_id", orderMap.getString("order_id"));
		convertOrderMap.put("refund_id", orderMap.getString("refund_id"));
		convertOrderMap.put("refund_status", orderMap.getString("refund_status"));
		convertOrderMap.put("berth_code", orderMap.getString("berth_code"));
		convertOrderMap.put("order_amount", String.format("%.2f", orderMap.getDouble("should_pay")));
		convertOrderMap.put("refund_amount", String.format("%.2f", orderMap.getDouble("refund_amount")));
		convertOrderMap.put("car_plate", orderMap.getString("car_plate"));
		convertOrderMap.put("canton", orderMap.getString("canton"));
		convertOrderMap.put("area", orderMap.getString("area"));
		convertOrderMap.put("section", orderMap.getString("section"));
		convertOrderMap.put("refund_time", orderMap.getString("refund_time"));
		return convertOrderMap;
	}
}
