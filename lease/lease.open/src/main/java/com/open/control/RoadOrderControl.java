package com.open.control;

import java.util.ArrayList;
import java.util.List;

import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.base.web.AppConfig;
import com.common.conf.MsgConfig;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.consts.SqlConsts;
import com.common.route.RouteManager;
import com.common.util.DateUtil;
import com.common.util.RespUtil;
import com.open.consts.OpenConsts;

public class RoadOrderControl {

	public ParaMap getOrder(ParaMap inMap) throws Exception {
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
		if (StrUtils.isNotNull(inMap.getString("order_id"))) {
			sendMap.put("order_id", inMap.getString("order_id"));
			resultMap = RouteManager.route("road.order.getOrder", sendMap);
			resultMap = this.dealResponMap(resultMap);
			return resultMap;
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

		sendMap.put("merchant_id", inMap.getString("merchant_id"));
		sendMap.put("start_time", inMap.getString("start_time"));
		sendMap.put("end_time", inMap.getString("end_time"));
		sendMap.put("status", inMap.getString("order_status"));
		sendMap.put("keyword", inMap.getString("keyword"));
		sendMap.put("page_index", pageIndex);
		sendMap.put("page_size", pageSize);
		resultMap = RouteManager.route("road.order.getOrderList", sendMap);
		resultMap = this.dealResponListMap(resultMap);
		return resultMap;
	}

	private ParaMap dealResponListMap(ParaMap inMap) {
		if (RespState.SUCCESS.getValue() != inMap.getInt(RespKey.STATE.getValue())) {
			return inMap;
		}
		List<ParaMap> orderConvertList = new ArrayList<ParaMap>();
		List<ParaMap> orderList = (List<ParaMap>) inMap.get(RespKey.DATA.getValue());
		if (orderList == null || orderList.isEmpty()) {
			inMap.put(RespKey.DATA.getValue(), orderConvertList);
			return inMap;
		}
		for (ParaMap orderMap : orderList) {
			ParaMap convertOrderMap = this.orderConvert(orderMap);
			orderConvertList.add(convertOrderMap);
		}
		inMap.put(RespKey.DATA.getValue(), orderConvertList);
		return inMap;
	}

	/**
	 * 将单条订单信息，转化成数组的统一返回格式
	 */
	private ParaMap dealResponMap(ParaMap inMap) {
		if (RespState.SUCCESS.getValue() != inMap.getInt(RespKey.STATE.getValue())) {
			return inMap;
		}
		List<ParaMap> orderList = new ArrayList<ParaMap>();
		ParaMap orderMap = (ParaMap) inMap.get(RespKey.DATA.getValue());
		if (orderMap == null || orderMap.isEmpty()) {
			inMap.put(RespKey.DATA.getValue(), orderList);
			return inMap;
		}
		ParaMap convertOrderMap = this.orderConvert(orderMap);
		orderList.add(convertOrderMap);
		inMap.put(RespKey.DATA.getValue(), orderList);
		return inMap;
	}

	/**
	 * 将返回的订单信息过滤
	 * 
	 * @param 原订单信息
	 * @return
	 */
	private ParaMap orderConvert(ParaMap orderMap) {
		ParaMap convertOrderMap = new ParaMap();
		convertOrderMap.put("order_id", orderMap.getString("order_id"));
		convertOrderMap.put("ticket", orderMap.getString("ticket"));
		convertOrderMap.put("order_status", orderMap.getString("status"));
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
		convertOrderMap.put("create_time", orderMap.getString("create_time"));
		return convertOrderMap;
	}

}
