package com.center.router;

import com.base.utils.ParaMap;
import com.common.route.RouteManager;

public class RoadService {

	public ParaMap getRechargeList(ParaMap inMap) throws Exception {
		return RouteManager.route("road.recharge.getRechargeList", inMap);
	}
}
