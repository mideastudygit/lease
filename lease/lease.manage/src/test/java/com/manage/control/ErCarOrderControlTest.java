package com.manage.control;

import junit.framework.TestCase;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

public class ErCarOrderControlTest extends TestCase {

	public void testGetOrderList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "ercar.order.getOrderList");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testAddOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "ercar.order.addOrder");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "ercar.order.getOrder");
		inMap.put("order_id", "20170516160815626654086083513529");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdateOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "ercar.order.updateOrder");
		inMap.put("order_id", "20170518145739921441504516669327");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	// 今日数据统计
	public void testGetOrderToday() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "ercar.order.getTodayCount");
		inMap.put("merchant_id", "20170518145739921441504516669327");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	// 租车收费数据分析
	public void testGetOrderAnalysis() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "ercar.order.getOrderAnalysis");
		inMap.put("merchant_id", "20170513181530248919062988563556");
//		inMap.put("month", "1491358800000");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	// 本月收入前十路段统计
	public void testGetRoad() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "ercar.order.getRoad");
//		inMap.put("merchant_id", "20170518145739921441504516669327");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	// 当月数据统计
	public void testGetMonthCount() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "ercar.order.getMonthCount");
		inMap.put("merchant_id", "20170518145739921441504516669327");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
