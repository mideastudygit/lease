package com.center.service;

import junit.framework.TestCase;

import com.base.utils.ParaMap;
import com.common.route.RouteManager;

public class RouteManageTest extends TestCase {

	public void testRouter() throws Exception {
		ParaMap inMap = new ParaMap();
		RouteManager.route("road.recharge.getRechargeList", inMap);
	}
}
