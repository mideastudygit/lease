
package com.road.service;  

import junit.framework.TestCase;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

public class RoadBillControlTest extends TestCase{

	public void testGetListOfBill() throws Exception {
		ParaMap inMap = new ParaMap();
		// 两种action方式都可以
		inMap.put("action", "road.bill.getListOfBill");
		
		inMap.put("start_time", "1494086400000");
		inMap.put("end_time", "1494259200000");
		inMap.put("merchant_id", "1001");
		inMap.put("status", "1");
		inMap.put("page_index", "1");
		inMap.put("page_size", "10");

		HttpUtil.getData(RoadConsts.URL, inMap);
	}
}
  