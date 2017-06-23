package com.center.api.servcie;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

import junit.framework.TestCase;

public class RechargeControlTest extends TestCase {

	public void testGetRechargeList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "recharge.getRechargeList");
		inMap.put("type", 1);
		HttpUtil.getData(CenterConsts.URL, inMap);
	}
}
