package com.center.api.service;

import java.lang.reflect.Method;

import com.base.log.Logging;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.service.CommonService;
import com.common.util.RespUtil;

public class CenterService extends CommonService {

	private static final Logging log = Logging.getLogging("center");
	private static final String packagePrefix = "com.center.control.";

	@Override
	public void accept(ParaMap inMap) throws Exception {

		ParaMap paraMap = super.getParamater();

		log.debug("【" + paraMap.getString("action") + "】--请求：" + paraMap);

		ParaMap outMap = process(paraMap, packagePrefix);

		log.debug("【" + paraMap.getString("action") + "】--响应：" + outMap);

		super.response(outMap);
	}

	@Override
	public ParaMap process(ParaMap inMap, String packagePrefix) {
		String action = inMap.getString("action");
		if (StrUtils.isNull(action) || action.indexOf(".") <= 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "invalid.action",
					"action不存在或者有误");
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
			return RespUtil.setResp(RespState.FAIL, "invalid.action",
					"action不存在或者有误");
		}
		ParaMap outMap = RouteManager.route(inMap.getString("action"), inMap);
		return outMap;
	}
}
