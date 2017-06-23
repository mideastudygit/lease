package com.center.service;

import com.base.ds.DataSourceManager;
import com.base.utils.DateUtils;
import com.base.utils.MathUtils;
import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class MerchantServiceTest extends TestCase {

	private MerchantService merchantService = new MerchantService();

	public void testGetMerchantList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_name", "bonikai");
		ParaMap outMap = merchantService.getMerchantList(inMap);
		System.out.println(outMap);
	}

	public void testAddMerchant() throws Exception {
		ParaMap inMap = new ParaMap();
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
		ParaMap outMap = merchantService.addMerchant(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testGetMerchant() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_id", "20170511181305918332233476614521");
		inMap.put("merchant_name", "bonikai");
		inMap.put("app_id", "bonicai");
		ParaMap outMap = merchantService.getMerchant(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testUpdatetMerchant() throws Exception {
		ParaMap inMap = new ParaMap();
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
		ParaMap outMap = merchantService.updateMerchant(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testEnableMerchant() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_id", "20170511181305918332233476614521");
		inMap.put("status", 0);
		ParaMap outMap = merchantService.enableMerchant(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	public void testUpdatetMerchantBalance() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_id", "20170511181305918332233476614521");
		inMap.put("amount", "14.15");
		inMap.put("type", "1");
		ParaMap outMap = merchantService.updateMerchantBalance(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	public void testUpdate() throws Exception {
		System.out.println(MathUtils.add(53.14, -52.65));
		System.out.println(MathUtils.subtract(0, 52.65));
		MathUtils.subtract(0, 52.65);
	}
}
