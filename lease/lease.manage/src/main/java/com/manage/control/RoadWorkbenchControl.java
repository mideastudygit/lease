package com.manage.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.base.utils.MathUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.consts.SqlConsts;
import com.common.route.RouteManager;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;
import com.manage.consts.ManageConsts;
import com.manage.util.DateConvertUtil;

public class RoadWorkbenchControl {

	public ParaMap getSectionList(ParaMap inMap) throws Exception {
		// 获取服务商列表，查询第一条记录
		ParaMap sendMap = new ParaMap();
		sendMap.put("provider_type", ManageConsts.PROVIDER_TYPE_ROAD);
		sendMap.put("is_page", SqlConsts.NOT_PAGE);
		ParaMap resultMap = RouteManager.route("provider.getProviderList", sendMap);
		List<ParaMap> providerList = (List<ParaMap>) resultMap.get(RespKey.DATA.getValue());
		ParaMap providerMap = providerList.get(0);
		inMap.put("provider_id", providerMap.getString("provider_id"));
		resultMap = RouteManager.route(inMap.getString("action"), inMap);
		return resultMap;
	}

	public ParaMap getNearbyBerthList(ParaMap inMap) throws Exception {
		// 获取服务商列表，查询第一条记录
		ParaMap sendMap = new ParaMap();
		sendMap.put("provider_type", ManageConsts.PROVIDER_TYPE_ROAD);
		sendMap.put("is_page", SqlConsts.NOT_PAGE);
		ParaMap resultMap = RouteManager.route("provider.getProviderList", sendMap);
		List<ParaMap> providerList = (List<ParaMap>) resultMap.get(RespKey.DATA.getValue());
		ParaMap providerMap = providerList.get(0);
		inMap.put("provider_id", providerMap.getString("provider_id"));
		resultMap = RouteManager.route(inMap.getString("action"), inMap);
		return resultMap;
	}

	public ParaMap getOrderFeeTop(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("date"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		inMap.put("start_time", DateConvertUtil.getStartTime(inMap.getString("date")));
		inMap.put("end_time", DateConvertUtil.getEndTime(inMap.getString("date")));
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
		return resultMap;
	}

	public ParaMap getOrderAnalysis(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("date"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		inMap.put("start_time", DateConvertUtil.getStartTime(inMap.getString("date")));
		inMap.put("end_time", DateConvertUtil.getEndTime(inMap.getString("date")));

		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
		List<ParaMap> outList = (List<ParaMap>) resultMap.get(RespKey.DATA.getValue());

		List<Object> dateList = DateConvertUtil.getMonthDateList(inMap.getString("date"));

		ParaMap outMap = new ParaMap();
		List<Integer> dayList = new ArrayList<Integer>();
		List<Integer> parkCountList = new ArrayList<Integer>();
		List<Double> parkAmountList = new ArrayList<Double>();
		if (outList == null || outList.isEmpty()) {
			Iterator<Object> it = dateList.iterator();
			while (it.hasNext()) {
				it.next();
				dayList.add((Integer) it.next());
				parkCountList.add(0);
				parkAmountList.add(0.0);
			}
			outMap.put("day", dayList);
			outMap.put("park_count", parkCountList);
			outMap.put("park_amount", parkAmountList);
			outMap.put("park_month_count", 0);
			outMap.put("park_month_amount", 0);
			return RespUtil.setResp(outMap);
		}

		ParaMap orderTotalMap = ApiUtil.formatMap(outList, "order_date");// 将统计列表，转化成以日期为key的Map对象
		int orderCount = 0;
		double orderAmount = 0.0;
		Iterator<Object> it = dateList.iterator();
		while (it.hasNext()) {
			ParaMap tempMap = (ParaMap) orderTotalMap.get(it.next());
			dayList.add((Integer) it.next());
			if (tempMap == null || tempMap.isEmpty()) {
				parkCountList.add(0);
				parkAmountList.add(0.0);
				continue;
			}
			orderCount = orderCount + tempMap.getInt("order_count");
			orderAmount = MathUtils.add(orderAmount, tempMap.getDouble("order_amount"));
			parkCountList.add(tempMap.getInt("order_count"));
			parkAmountList.add(tempMap.getDouble("order_amount"));
		}
		outMap.put("day", dayList);
		outMap.put("park_count", parkCountList);
		outMap.put("park_amount", parkAmountList);
		outMap.put("park_month_count", orderCount);
		outMap.put("park_month_amount", orderAmount);
		return RespUtil.setResp(outMap);
	}
}
