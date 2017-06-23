package com.manage.control;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

import junit.framework.TestCase;

public class BillControlTest extends TestCase {

	public void testGetBillList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "bill.getBillList");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetBill() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "bill.getBill");
		inMap.put("bill_id", "100000000000");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdateBill() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "bill.updateBill");
		inMap.put("bill_id", "100000000000");
		inMap.put("status", 4);
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testRepeatBill() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "bill.repeatBill");
		inMap.put("bill_id", "20170526175824908367339728869533");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetBillDataList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "bill.getBillDataList");
		inMap.put("bill_id", "20170616030000413172276050695185");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
