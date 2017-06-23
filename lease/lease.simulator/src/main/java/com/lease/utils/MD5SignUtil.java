package com.lease.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.base.utils.CharsetUtils;
import com.base.utils.MD5;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;

public class MD5SignUtil {

	/**
	 * MD5请求签名校验
	 * 
	 * @param inMap
	 *            请求参数，该参数包含签名信息
	 * @param secret
	 *            密钥
	 */
	public static boolean checkSign(ParaMap inMap, String secret) {
		if (StrUtils.isNull(inMap.getString("sign"))) {
			return false;
		}
		String actualSign = sign(inMap, secret);
		return actualSign.equals(inMap.get("sign"));
	}

	/**
	 * MD5签名
	 * 
	 * @param inMap
	 *            签名数据
	 * @param secret
	 *            密钥
	 */
	public static String sign(Map<String, String> inMap, String secret) {
		List<String> keys = new ArrayList<String>(inMap.keySet());
		Collections.sort(keys);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			if (StrUtils.isNotNull(key) && !key.equals("sign")) {
				Object value = inMap.get(key);
				if (value == null || value.equals("")) {
					continue;
				}
				sb.append(key);
				sb.append("=");
				sb.append(value);
				sb.append("&");
			}
		}
		int length = sb.length();
		if (length > 0) {
			sb.deleteCharAt(length - 1);
		}
		sb.append("&key=" + secret);
		String sign = MD5.MD5Encode(sb.toString(), CharsetUtils.utf)
				.toUpperCase();
		return sign;
	}
	
	
	/**
	 * 加密
	 * md5:(). <br/> 
	 * TODO().<br/> 
	 * 
	 * @author yxd 
	 * @param content
	 * @return
	 */
	public static String md5(String content) {
		String sign = MD5.MD5Encode(content, CharsetUtils.utf);
		return sign;
	}
}
