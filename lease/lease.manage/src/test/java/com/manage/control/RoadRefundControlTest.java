package com.manage.control;

import com.base.utils.ParaMap;
import com.common.util.DateUtil;
import com.common.util.HttpUtil;

import junit.framework.TestCase;

public class RoadRefundControlTest extends TestCase {

	public void testGetRefundOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.refund.getRefundOrder");
		inMap.put("refund_id", "20170527111752941746741688037744");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetRefundOrderList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.refund.getRefundOrderList");
		inMap.put("start_time", DateUtil.nowTime());
		inMap.put("end_time", DateUtil.nowTime());
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testApplyRefund() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "road.refund.applyRefund");
		inMap.put("refund_id", "20170527111752941746741688037744");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
