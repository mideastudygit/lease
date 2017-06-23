package com.center.service;

import java.text.MessageFormat;

import com.base.utils.ParaMap;
import com.center.util.SMSUtil;

/**
 * 短信服务
 * 
 * @author 唐宗鸿
 * @date 20170530
 * @version 1.1.0
 */
public class SmsService {

	/**
	 * 新增系统登录用户提示消息
	 * 
	 * @author 唐宗鸿
	 * @date 20170530
	 * @param inMap
	 * @return
	 */
	public ParaMap notifyAddMerchant(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("para_code", "msg");
		sendMap.put("para_name", "add.merchant");
		ParamService paramService = new ParamService();
		ParaMap messageMap = paramService.getParameter(sendMap);
		String content = MessageFormat.format(messageMap.getString("para_value"), inMap.getString("admin_name"),
				inMap.getString("tel"), inMap.getString("password"));
		ParaMap outMap = SMSUtil.sendSms(content, inMap.getString("tel"));
		return outMap;
	}

	/**
	 * 修改系统登录用户的手机号提示消息
	 * 
	 * @author 唐宗鸿
	 * @date 20170530
	 * @param inMap
	 * @return
	 */
	public ParaMap notifyUpdateMerchant(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("para_code", "msg");
		sendMap.put("para_name", "update.merchant");
		ParamService paramService = new ParamService();
		ParaMap messageMap = paramService.getParameter(sendMap);
		String content = MessageFormat.format(messageMap.getString("para_value"), inMap.getString("admin_name"),
				inMap.getString("tel"), inMap.getString("password"));
		ParaMap outMap = SMSUtil.sendSms(content, inMap.getString("tel"));
		return outMap;
	}

	/**
	 * 商户预警余额消息
	 * 
	 * @author 唐宗鸿
	 * @date 20170530
	 * @param inMap
	 * @return
	 */
	public ParaMap notifyBalanceNotEnough(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("para_code", "msg");
		sendMap.put("para_name", "merchant.balance.notenough");
		ParamService paramService = new ParamService();
		ParaMap messageMap = paramService.getParameter(sendMap);
		String content = MessageFormat.format(messageMap.getString("para_value"), inMap.getString("alarm_balance"));
		ParaMap outMap = SMSUtil.sendSms(content, inMap.getString("tel"));
		return outMap;
	}
}
