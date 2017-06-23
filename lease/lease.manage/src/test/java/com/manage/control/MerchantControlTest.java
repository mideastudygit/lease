package com.manage.control;

import junit.framework.TestCase;

import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.common.util.DateUtil;
import com.common.util.HttpUtil;

public class MerchantControlTest extends TestCase {

	public void testGetMerchantList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "merchant.getMerchantList");
		inMap.put("merchant_name", "bonikai");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testAddMerchant() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "merchant.addMerchant");
		inMap.put("merchant_name", "bonikaiasf");
		inMap.put("card_name", "xiughuwe");
		inMap.put("card_no", "451265452150");
		inMap.put("first_amount", "20000");
		inMap.put("alarm_balance", "500");
		inMap.put("buss_license", "01545256554");
		inMap.put("app_id", "bonicaiasf");
		inMap.put("app_secret", "#451554$*qsdgrdfc");
		inMap.put("push_url", "http://fwetfwww.com/app/shlut/");
		inMap.put("admin_name", "张三");
		inMap.put("tel", "14525456526");
		inMap.put("phone", "0550-0215-2101");
		inMap.put("address", "gugandongsheng");
		inMap.put("bank_name", "中国农业银行");
		inMap.put("account_no", "65452152655455");
		inMap.put("account_name", "张三");
		inMap.put("bill_type", "1");
		inMap.put("bill_cycle", "20");
		inMap.put("bill_start", DateUtils.nowTime());
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetMerchant() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "merchant.getMerchant");
		inMap.put("merchant_id", "20170511181305918332233476614521");
		inMap.put("merchant_name", "bonikai");
		inMap.put("app_id", "bonicai");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdatetMerchant() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "merchant.updateMerchant");
		inMap.put("merchant_id", "20170511181305918332233476614521");
		inMap.put("merchant_name", "bonikai");
		inMap.put("card_name", "xiughu");
		inMap.put("card_no", "451265452150");
		inMap.put("first_amount", "20000");
		inMap.put("alarm_balance", "500");
		inMap.put("buss_license", "01545256554");
		inMap.put("app_id", "bonicai");
		inMap.put("app_secret", "#451554$*qsdgrdfc");
		inMap.put("push_url", "http://fwetfwww.com/app/shlut/");
		inMap.put("admin_name", "张三e3");
		inMap.put("tel", "14525456526");
		inMap.put("phone", "0550-0215-2101");
		inMap.put("address", "gugandongsheng");
		inMap.put("bank_name", "中国农业银行");
		inMap.put("account_no", "65452152655455");
		inMap.put("account_name", "张三");
		inMap.put("bill_type", "1");
		inMap.put("bill_cycle", "20");
		inMap.put("bill_start", DateUtil.nowTime());
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testEnableMerchant() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "merchant.enableMerchant");
		inMap.put("merchant_id", "20170511181305918332233476614521");
		inMap.put("status", 0);
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdatetMerchantBalance() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "merchant.updateMerchantBalance");
		inMap.put("merchant_id", "20170511181305918332233476614521");
		inMap.put("amount", "14.15");
		inMap.put("type", "1");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
