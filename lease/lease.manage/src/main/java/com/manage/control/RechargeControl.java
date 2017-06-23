package com.manage.control;

import com.base.utils.ParaMap;
import com.common.consts.SqlConsts;
import com.common.route.RouteManager;

public class RechargeControl {

	public ParaMap getProviderRechargeList(ParaMap inMap) throws Exception {
		inMap.put("page_index", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		inMap.put("page_size", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
		return resultMap;
	}
}
