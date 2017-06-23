package com.common.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.base.http.HttpManager;
import com.base.log.Logging;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;

/**
 * HTTP请求工具类
 * 
 * @author 唐宗鸿
 *
 */
public class HttpUtil {

	private static Logging log = Logging.getLogging("httplog");

	/**
	 * HTTP请求数据
	 * 
	 * @param requestParam
	 *            请求参数对象
	 * @param url
	 *            请求地址
	 * @return
	 * @author 唐宗鸿
	 */
	public static String getData(String url, ParaMap requestParam)
			throws Exception {
		String params = getJoinUrl(requestParam);
		log.info("请求地址：" + url + "?" + params);
		String result = HttpManager.getData(url + "?", params);
		log.info("响应结果：" + result);
		return result;
	}

	/**
	 * 拼接地址参数
	 * 
	 * @param inMap
	 *            请求参数对象
	 * @return String 拼接后的url参数形式
	 * @author 唐宗鸿
	 */
	public static String getJoinUrl(ParaMap inMap) {
		ArrayList<String> keys = new ArrayList<String>(inMap.keySet());
		Collections.sort(keys);
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = inMap.getString(key);
			if (buff.toString().length() == 0) {
				buff.append(key + "=" + value);
			} else {
				buff.append("&" + key + "=" + value);
			}
		}
		return buff.toString();
	}

	/**
	 * 下载文件响应
	 * 
	 * @param fileName
	 *            下载文件名
	 * @param contentType
	 *            响应格式
	 * @param request
	 * @param response
	 * @author 唐宗鸿
	 */
	public static void setFileResponse(String fileName, String contentType,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String userAgent = request.getHeader("USER-AGENT");
		if (StrUtils.isNotNull(userAgent) && userAgent.contains("Firefox")) {
			fileName = new String(fileName.getBytes(), "ISO-8859-1");
		} else {
			fileName = URLEncoder.encode(fileName, "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");// 去掉空格字符
		}
		if (StrUtils.isNull(contentType))
			response.setContentType("application/vnd.ms-excel");
		else
			response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment;filename=\""
				+ fileName + "\"");
	}
}
