package com.center.util;

import java.net.URLEncoder;
import java.util.Iterator;

import com.alibaba.fastjson.JSON;
import com.base.http.HttpManager;
import com.base.log.Logging;
import com.base.log.ReqUtils;
import com.base.security.ApiUtils;
import com.base.utils.ParaMap;
import com.base.web.AppConfig;

/**
 * 发送短信工具
 */
public class SMSUtil {

	public static final Logging log = Logging.getLogging("smslog");
	public static final String SMS_KEY = AppConfig.getStringPro("sms.key");
	public static final String SMS_URL = AppConfig.getStringPro("sms.url");
	public static final boolean SMS_SEND_FLAG = AppConfig.getBooleanPro("sms.send.flag");

	/**
	 * 发送短信
	 */
	public static ParaMap sendSms(String content, String tel) {
		ParaMap sendMap = new ParaMap();
		sendMap.put("clientType", "net");
		sendMap.put("content", content);
		sendMap.put("method", "sendSms");
		sendMap.put("module", "meb");
		sendMap.put("phonenum", tel);
		sendMap.put("sender", SMS_KEY);
		sendMap.put("service", "SendSms");
		sendMap.put("ver", "2");

		String response = "";
		try {

			ApiUtils.initSecretKey(new ParaMap());
			ReqUtils.initRId(new ParaMap());

			String uri = SMS_URL + "?" + encode(sendMap) + "&sign=" + sendMap.md5();
			if (SMS_SEND_FLAG) {
				response = HttpManager.getData(uri, "");
			}
			log.info("发送短信，请求：" + uri + "，响应：" + response);
		} catch (Exception e) {
			log.info("发送短信失败：" + e.getMessage());
		}

		ParaMap resultMap = new ParaMap(JSON.parseObject(response));
		return resultMap;
	}

	public static String encode(ParaMap inMap) throws Exception {
		StringBuffer sb = new StringBuffer();
		Iterator<String> it = inMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = URLEncoder.encode(inMap.getString(key), "UTF-8");
			if (sb.toString().length() == 0) {
				sb.append(key + "=" + value);
			} else
				sb.append("&" + key + "=" + value);
		}
		sb.append("&requestKey=" + ApiUtils.getSecretKey());
		return sb.toString();
	}
}
