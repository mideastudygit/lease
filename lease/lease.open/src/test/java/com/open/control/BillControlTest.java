package com.open.control;

import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.common.util.HttpUtil;
import com.common.util.MD5SignUtil;

import junit.framework.TestCase;

public class BillControlTest extends TestCase {

	public void testGetBillList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "bill.getBillList");
		inMap.put("app_id", "bonicai");
		inMap.put("timestamp", DateUtils.nowTime());
		inMap.put("sign_type", "MD5");
		String sign = MD5SignUtil.sign(inMap, "#451554$*qsdgrdfc");
		inMap.put("sign", sign);
		String response = HttpUtil.getData(OpenConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetBill() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "bill.getBill");
		inMap.put("app_id", "bonicai");
		inMap.put("timestamp", DateUtils.nowTime());
		inMap.put("sign_type", "MD5");
		inMap.put("bill_id", "20170526175824908367339728869533");
		String sign = MD5SignUtil.sign(inMap, "#451554$*qsdgrdfc");
		inMap.put("sign", sign);
		String response = HttpUtil.getData(OpenConsts.URL, inMap);
		System.out.println(response);
	}
}
