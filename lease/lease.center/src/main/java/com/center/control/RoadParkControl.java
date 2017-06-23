package com.center.control;

import com.base.utils.ParaMap;
import com.common.route.RouteManager;

/**
 * 路外停车控制器
 * 
 * @author 唐宗鸿
 * @date 20170527
 * @version 1.1.0
 */
public class RoadParkControl {

	public ParaMap getBerthStatus(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
		return resultMap;
	}

	public ParaMap applyPark(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
		return resultMap;
	}
}
