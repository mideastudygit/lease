package com.center.api.servcie;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

import junit.framework.TestCase;

public class BankControlTest extends TestCase {

	public void testGetBankList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "bank.getBankList");
		HttpUtil.getData(CenterConsts.URL, inMap);
	}
}
