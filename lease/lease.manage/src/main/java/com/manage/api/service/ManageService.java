package com.manage.api.service;

import java.lang.reflect.Method;

import com.base.log.Logging;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.service.CommonService;
import com.common.util.RespUtil;
import com.manage.service.HttpService;

public class ManageService extends CommonService {

	private static final Logging log = Logging.getLogging("manage");
	private static final String packagePrefix = "com.manage.control.";

	@Override
	public ParaMap execute(ParaMap inMap) throws Exception {
		log.debug("【" + inMap.getString("action") + "】--请求：" + inMap);
		ParaMap outMap = process(inMap, packagePrefix);
		log.debug("【" + inMap.getString("action") + "】--响应：" + outMap);
		return outMap;
	}

	@Override
	public void accept(ParaMap inMap) throws Exception {
		ParaMap paraMap = super.getParamater();
		log.debug("【" + paraMap.getString("action") + "】--请求：" + paraMap);
		ParaMap outMap = process(paraMap, packagePrefix);
		log.debug("【" + paraMap.getString("action") + "】--响应：" + outMap);
		super.response(outMap);
	}

	public byte[] downloadFile(ParaMap inMap) throws Exception {
		log.debug("【" + inMap.getString("action") + "】--请求：" + inMap);
		if (StrUtils.isNull(inMap.getString("action")) || inMap.getString("action").indexOf(".") <= 0) {
			return null;
		}
		String[] url = parseUrl(inMap.getString("action"), packagePrefix);
		HttpService service = (HttpService) Class.forName(url[0]).newInstance();
		service.setRequest(super.getRequest());
		service.setResponse(super.getResponse());
		service.setSession(super.getSession());
		Method method = service.getClass().getDeclaredMethod(url[1], ParaMap.class);
		byte[] response = (byte[]) method.invoke(service, inMap);
		return response;
	}

	@Override
	public ParaMap process(ParaMap inMap, String packagePrefix) {
		String action = inMap.getString("action");
		if (StrUtils.isNull(action) || action.indexOf(".") <= 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "invalid.action", "action格式不正确");
			return outMap;
		}
		String[] url = parseUrl(action, packagePrefix);
		Object object = null;
		Method method = null;
		boolean isRoute = false;
		try {
			object = Class.forName(url[0]).newInstance();
			method = object.getClass().getDeclaredMethod(url[1], ParaMap.class);
		} catch (Exception e) {
			isRoute = true;
		}
		if (isRoute) {
			// 管理接入层，没有做个性化定制，直接转发到公共服务层处理
			ParaMap outMap = router(inMap);
			return outMap;
		}
		try {
			ParaMap outMap = (ParaMap) method.invoke(object, inMap);
			return outMap;
		} catch (Exception e) {
			e.printStackTrace();
			return RespUtil.setResp(RespState.ERROR, "error", "sys.error");
		}
	}

	private ParaMap router(ParaMap inMap) {
		if (!RouteManager.hasRoute(inMap.getString("action"))) {
			return RespUtil.setResp(RespState.FAIL, "invalid.action", "action不存在");
		}
		ParaMap outMap = RouteManager.route(inMap.getString("action"), inMap);
		return outMap;
	}
}
