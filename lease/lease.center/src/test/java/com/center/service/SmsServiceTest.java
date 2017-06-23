package com.center.service;

import java.text.MessageFormat;

import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class SmsServiceTest extends TestCase {

	private SmsService smsService = new SmsService();

	public void testAddMerchantMessage() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("tel", "14525456526");
		inMap.put("password", "6526");
		inMap.put("admin_name", "张三");
		ParaMap outMap = smsService.notifyAddMerchant(inMap);
		System.out.println(outMap);
	}

	public void testFormat() {
		String paraValue = "尊敬的{admin_name}，您好！已为您开通管理平台账号，请及时登录并修改密码。账号：{user_name}，登录密码：{password}";
		String format = MessageFormat.format(paraValue, "张三", "14525456526", "6526");
		System.out.println(format);
	}

	public void testFormatString() {
		String str1 = "尊敬的商户，您的账户余额已不足{0}元，为避免影响正常停车，请您及时充值。谢谢！";
		System.out.println(MessageFormat.format(str1, "500"));
		String paraValue = "尊敬的{0}，您好！已为您开通管理平台账号，请及时登录并修改密码。账号：{1}，登录密码：{2}";
		System.out.println(MessageFormat.format(paraValue, "张三", "14525456526", "6526"));
	}
}
