package com.center.control;

import com.base.utils.ParaMap;
import com.center.service.SmsService;

/**
 * 短信服务控制器
 * 
 * @author 唐宗鸿
 * @date 20170530
 * @version 1.1.0
 */
public class SmsControl {

	private SmsService smsService = new SmsService();

	/**
	 * 商户预警余额消息
	 * 
	 * @author 唐宗鸿
	 * @date 20170530
	 * @param inMap
	 * @return
	 */
	public ParaMap notifyBalanceNotEnough(ParaMap inMap) throws Exception {
		ParaMap resultMap = smsService.notifyBalanceNotEnough(inMap);
		return resultMap;
	}
}
