package com.manage.control;

import java.util.List;

import com.base.utils.ParaMap;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.SqlConsts;
import com.common.route.RouteManager;
import com.manage.consts.ManageConsts;

public class RoadArrearsControl {

	public ParaMap getArrearsOrderList(ParaMap inMap) throws Exception {
		// 获取服务商列表，查询第一条记录
		ParaMap sendMap = new ParaMap();
		sendMap.put("provider_type", ManageConsts.PROVIDER_TYPE_ROAD);
		sendMap.put("is_page", SqlConsts.NOT_PAGE);
		ParaMap resultMap = RouteManager.route("provider.getProviderList", sendMap);
		List<ParaMap> providerList = (List<ParaMap>) resultMap.get(RespKey.DATA.getValue());
		ParaMap providerMap = providerList.get(0);
		inMap.put("provider_id", providerMap.getString("provider_id"));
		inMap.put("arrears_status", inMap.getInt("arrears_status", ManageConsts.ARREARS_STATUS_NO_PAY)); // 默认查询未补缴状态
		resultMap = RouteManager.route(inMap.getString("action"), inMap);
		return resultMap;
	}

	public ParaMap applyPayArrears(ParaMap inMap) throws Exception {
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
}
