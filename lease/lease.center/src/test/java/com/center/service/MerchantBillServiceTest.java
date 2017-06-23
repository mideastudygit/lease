package com.center.service;

import com.base.ds.DataSourceManager;
import com.base.utils.DateUtils;
import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class MerchantBillServiceTest extends TestCase {

	private MerchantBillService merchantBillService = new MerchantBillService();

	public void testAddMerchantBill() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_id", "20170510182424942323819910269145");
		inMap.put("bank_name", "中国农业银行");
		inMap.put("account_no", "65452152655455");
		inMap.put("account_name", "张三");
		inMap.put("bill_type", "1");
		inMap.put("bill_cycle", "20");
		inMap.put("bill_start", DateUtils.nowTime());
		ParaMap outMap = merchantBillService.addMerchantBill(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testUpdatetMerchantBill() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_id", "20170510182424942323819910269145");
		inMap.put("bank_name", "中国农业银行");
		inMap.put("account_no", "65452152655455");
		inMap.put("account_name", "张三");
		inMap.put("bill_type", "0");
		inMap.put("bill_cycle", "28");
		inMap.put("bill_start", DateUtils.nowTime());
		ParaMap outMap = merchantBillService.updateMerchantBill(inMap);
		System.out.println(outMap);
	}
}
