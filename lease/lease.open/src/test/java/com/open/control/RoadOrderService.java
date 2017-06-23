package com.open.control;

import java.util.List;

import com.base.http.HttpManager;
import com.base.utils.JsonUtils;
import com.base.utils.ParaMap;
import com.common.util.DateUtil;
import com.common.util.HttpUtil;
import com.common.util.MD5SignUtil;

public class RoadOrderService {

	private Provider provider = new Provider();

	public ParaMap getOrder(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("appkey", provider.getAppId());
		sendMap.put("method", "parkingrecord");
		sendMap.put("bargainorder", inMap.getString("provider_order_id"));
		ParaMap resultMap = this.getShenZhenData(sendMap);
		ParaMap orderMap = this.findOrder(resultMap);
		return orderMap;
	}

	public ParaMap endOrder(ParaMap inMap) throws Exception {
		inMap.put("app_id", provider.getAppId());
		inMap.put("action", "road.order.endOrder");
		ParaMap resultMap = this.getData(inMap);
		return resultMap;
	}

	/**
	 * 发起宜停车订单结算推送
	 */
	private ParaMap getData(ParaMap inMap) {
		String url = OpenConsts.PUSH_URL + "?";
		String requestParam = HttpUtil.getJoinUrl(inMap);
		String sign = MD5SignUtil.sign(inMap, provider.getAppSecret());
		requestParam = requestParam + "&sign=" + sign;
		System.out.println("【" + inMap.getString("action") + "】--请求：" + url + requestParam);
		try {
			String response = HttpManager.getData(url, requestParam);
			System.out.println("【" + inMap.getString("action") + "】--响应：" + response);
			ParaMap dataMap = JsonUtils.strToMap(response);
			return dataMap;
		} catch (Exception e) {
			System.out.println("错误信息为：" + e.getMessage());
		}
		return null;
	}

	/**
	 * 发起宜停车请求
	 */
	private ParaMap getShenZhenData(ParaMap inMap) {
		String url = OpenConsts.SHENZHEN_URL + "?";
		String requestParam = HttpUtil.getJoinUrl(inMap);
		String sign = MD5SignUtil.sign(inMap, provider.getAppSecret());
		requestParam = requestParam + "&sign=" + sign;
		System.out.println("【" + inMap.getString("method") + "】--请求：" + url + requestParam);
		try {
			String response = HttpManager.getData(url, requestParam);
			System.out.println("【" + inMap.getString("method") + "】--响应：" + response);
			ParaMap dataMap = JsonUtils.strToMap(response);
			return dataMap;
		} catch (Exception e) {
			System.out.println("错误信息为：" + e.getMessage());
		}
		return null;
	}

	public ParaMap findOrder(ParaMap inMap) {
		ParaMap orderMap = new ParaMap();
		ParaMap dataMap = (ParaMap) inMap.get("data");
		if (dataMap == null || dataMap.isEmpty()) {
			return orderMap;
		}
		List<ParaMap> itemList = (List<ParaMap>) dataMap.get("items");
		if (!(itemList == null || itemList.isEmpty())) {
			if (itemList.size() > 1) {
				for (ParaMap tempMap : itemList) {
					if (3 == tempMap.getInt("BillDetailsType")) {
						dataMap = tempMap;
						break;
					}
				}
			} else {
				dataMap = itemList.get(0);
			}
		}
		orderMap = new ParaMap(dataMap);
		return orderMap;
	}

	public ParaMap getProviderOrder(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("appkey", provider.getAppId());
		sendMap.put("method", "parkingrecord");
		sendMap.put("bargainorder", inMap.getString("provider_order_id"));
		ParaMap resultMap = this.getShenZhenData(sendMap);
		ParaMap orderMap = this.orderConvert(resultMap);
		return orderMap;
	}

	public static ParaMap orderConvert(ParaMap inMap) {
		ParaMap orderMap = new ParaMap();
		ParaMap dataMap = (ParaMap) inMap.get("data");
		if (dataMap == null || dataMap.isEmpty()) {
			return orderMap;
		}
		List<ParaMap> itemList = (List<ParaMap>) dataMap.get("items");
		if (!(itemList == null || itemList.isEmpty())) {
			if (itemList.size() > 1) {
				for (ParaMap tempMap : itemList) {
					if (3 == tempMap.getInt("BillDetailsType")) {
						dataMap = tempMap;
						break;
					}
				}
			} else {
				dataMap = itemList.get(0);
			}
		}
		orderMap.put("provider_order_id", dataMap.getString("BargainOrderCode"));
		orderMap.put("start_park_time",
				DateUtil.getTime(dataMap.getString("StartParkingTime"), DateUtil.TO_SECOND_LINE));
		orderMap.put("end_park_time", DateUtil.getTime(dataMap.getString("EndParkingTime"), DateUtil.TO_SECOND_LINE));
		orderMap.put("actual_duration", dataMap.getString("ActualDuration"));
		orderMap.put("actual_pay", dataMap.getString("ActualPrice"));
		// 当路边返回字段没有items时，DetailsPrice字段并不存在
		orderMap.put("should_pay", dataMap.getString("DetailsPrice") == null ? 0.00 : dataMap.getString("DetailsPrice"));
		orderMap.put("apply_duration", dataMap.getString("ApplyDuration"));
		orderMap.put("berth_code", dataMap.getString("BerthCode"));
		orderMap.put("canton", "深圳市");
		orderMap.put("section", dataMap.getString("SectionName"));
		orderMap.put("area", dataMap.getString("AreaName"));
		orderMap.put("start_time",
				DateUtil.getTime(dataMap.getString("BerthStartParkingTime"), DateUtil.TO_SECOND_LINE));
		orderMap.put("end_time", DateUtil.getTime(dataMap.getString("BerthEndParkingTime"), DateUtil.TO_SECOND_LINE));
		return orderMap;
	}
}
