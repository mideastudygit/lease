package com.common.route;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.base.log.Logging;
import com.base.utils.JsonUtils;
import com.base.utils.ParaMap;
import com.base.web.AppConfig;
import com.common.consts.RespConsts.RespState;
import com.common.util.HttpUtil;
import com.common.util.RespUtil;

public class RouteManager {

	private static Logging log = Logging.getLogging("RouteManager");

	private static String xmlResource = "/route.xml";
	private static Map<String, Map<String, Map<String, Object>>> modules = null;
	private static Map<String, Map<String, Map<String, Object>>> methods = null;

	static {
		load();
	}

	/**
	 * 路由转发
	 * 
	 * @param action
	 *            请求的acton
	 * @param inMap
	 *            请求参数
	 * @return
	 */
	public static ParaMap route(String action, ParaMap inMap) {
		Map<String, Map<String, Object>> moethodInfo = methods.get(action);
		String moduleKey = moethodInfo.get("attributes").get("module")
				.toString();
		Map<String, Map<String, Object>> moduleInfo = modules.get(moduleKey);
		String url = getUrl(moduleInfo);
		Map<String, Object> params = getParams(moethodInfo);
		ParaMap sendMap = new ParaMap(inMap);
		sendMap.putAll(params);
		try {
			log.info("【Route-Url】 " + url + "?" + HttpUtil.getJoinUrl(sendMap));
			String response = HttpUtil.getData(url, sendMap);
			log.info("【Route-Response】" + response);
			return JsonUtils.strToMap(response);
		} catch (Exception e) {
			log.error(e.getMessage());
			return RespUtil.setResp(RespState.FAIL, "fail", e.getMessage());
		}
	}

	/**
	 * 判断输入的action是否配置映射关系
	 * 
	 * @param action
	 *            请求的acton
	 * @return boolean 已经配置返回true，否则返回false
	 */
	public static boolean hasRoute(String action) {
		Map<String, Map<String, Object>> methodInfo = methods.get(action);
		if (methodInfo == null) {
			return false;
		}
		return true;
	}

	private static String getUrl(Map<String, Map<String, Object>> moduleInfo) {
		Map<String, Object> moduleAttributes = moduleInfo.get("attributes");
		String url = moduleAttributes.get("url").toString();
		return url;
	}

	private static Map<String, Object> getParams(
			Map<String, Map<String, Object>> moethodInfo) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> moethodParams = moethodInfo.get("params");
		for (Entry<String, Object> entry : moethodParams.entrySet()) {
			params.put(entry.getKey(), entry.getValue());
		}
		return params;
	}

	private static void load() {
		modules = new HashMap<String, Map<String, Map<String, Object>>>();
		methods = new HashMap<String, Map<String, Map<String, Object>>>();
		SAXReader reader = new SAXReader();
		InputStream in = RouteManager.class.getResourceAsStream(xmlResource);
		Element root = null;
		try {
			Document document = reader.read(in);
			root = document.getRootElement();
		} catch (DocumentException e) {
			log.error(e.getMessage() + e.getStackTrace());
		}
		Element modulesEle = root.element("modules");
		Iterator<?> modulesIt = modulesEle.elementIterator();
		while (modulesIt.hasNext()) {
			Element ele = (Element) modulesIt.next();
			Map<String, Map<String, Object>> module = new HashMap<String, Map<String, Object>>();
			// 存属性的map
			Map<String, Object> attributes = new HashMap<String, Object>();
			Iterator<?> it = ele.attributeIterator();
			while (it.hasNext()) {
				Attribute attr = (Attribute) it.next();
				String attrValue = attr.getText();
				if (attr.getName().equals("url")) {
					attrValue = AppConfig.getStringPro(attrValue);
				}
				attributes.put(attr.getName(), attrValue);
			}
			module.put("attributes", attributes);
			modules.put(ele.attributeValue("name"), module);
		}

		Element methodsEle = root.element("methods");
		Iterator<?> methodsIt = methodsEle.elementIterator();
		while (methodsIt.hasNext()) {
			Element ele = (Element) methodsIt.next();
			String id = ele.attributeValue("id");
			if (methods.containsKey(id)) {
				throw new RuntimeException("route.xml文件id[" + id + "]重复");
			}
			Map<String, Map<String, Object>> method = new HashMap<String, Map<String, Object>>();
			// 存属性的map
			Map<String, Object> attributes = new HashMap<String, Object>();
			Iterator<?> it = ele.attributeIterator();
			while (it.hasNext()) {
				Attribute attr = (Attribute) it.next();
				attributes.put(attr.getName(), attr.getText());
			}
			method.put("attributes", attributes);
			// 存参数(私有)的map
			Map<String, Object> params = new HashMap<String, Object>();
			Element paramEle = ele.element("param");
			Iterator<?> paramIt = paramEle.attributeIterator();
			while (paramIt.hasNext()) {
				Attribute attr = (Attribute) paramIt.next();
				params.put(attr.getName(), attr.getText());
			}
			method.put("params", params);
			methods.put(id, method);
		}
	}
}
