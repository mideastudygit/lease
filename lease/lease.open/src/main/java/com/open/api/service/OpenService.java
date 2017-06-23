package com.open.api.service;

import java.lang.reflect.Method;

import com.base.log.Logging;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.service.CommonService;
import com.common.util.MD5SignUtil;
import com.common.util.RespUtil;
import com.open.consts.OpenConsts;

public class OpenService extends CommonService {

	private static final Logging log = Logging.getLogging("open");
	private static final String packagePrefix = "com.open.control.";

	@Override
	public void accept(ParaMap inMap) throws Exception {
		ParaMap requestMap = super.getParamater();
		log.debug("【" + requestMap.getString("action") + "】--请求：" + requestMap);
		if (StrUtils.isNull(requestMap.getString("app_id")) || StrUtils.isNull(requestMap.getString("sign"))
				|| StrUtils.isNull(requestMap.getString("action"))) {
			super.response(RespUtil.setResp(RespState.FAIL, "param.missing"));
			return;
		}
		// 商户校验
		ParaMap resultMap = RouteManager.route("merchant.getMerchant", requestMap);
		ParaMap dataMap = ((ParaMap) resultMap.get(RespKey.DATA.getValue()));
		if (dataMap == null || dataMap.isEmpty()) {
			this.response(RespUtil.setResp(RespState.FAIL, "invalid.appid"));
			return;
		}
		// 被禁用的商户不能发起申请停车操作
		if (dataMap.getInt("status") == OpenConsts.MERCHANT_STATUS_DISABLED) {
			this.response(RespUtil.setResp(RespState.FAIL, "invalid.merchant.status"));
			return;
		}
		// 签名验证
		String appSecret = dataMap.getString("app_secret");
		if (!MD5SignUtil.checkSign(requestMap, appSecret)) {
			this.response(RespUtil.setResp(RespState.FAIL, "invalid.sign"));
			return;
		}
		// 添加商户信息，并移除请求头信息
		ParaMap paraMap = new ParaMap(requestMap);
		paraMap.put("merchant_id", dataMap.getString("merchant_id"));
		paraMap.put("merchant_name", dataMap.getString("merchant_name"));
		paraMap.remove("sign");
		paraMap.remove("timestamp");
		paraMap.remove("format");
		paraMap.remove("sign_type");
		// 业务处理
		ParaMap outMap = process(paraMap, packagePrefix);
		log.debug("【" + paraMap.getString("action") + "】--响应：" + outMap);
		super.response(outMap);
	}

	@Override
	public void dispatch(ParaMap inMap) throws Exception {
		ParaMap paraMap = super.getParamater();
		log.debug("【" + paraMap.getString("action") + "】--请求：" + paraMap);
		// 业务处理
		ParaMap outMap = super.process(paraMap, packagePrefix);
		log.debug("【" + paraMap.getString("action") + "】--响应：" + outMap);
		super.response(outMap);
	}

	@Override
	public ParaMap process(ParaMap inMap, String packagePrefix) {
		String[] url = parseUrl(inMap.getString("action"), packagePrefix);
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
			// 接入层，没有做个性化定制，直接转发到公共服务层处理
			return router(inMap);
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
			return RespUtil.setResp(RespState.FAIL, "invalid.action", "action不存在或者有误");
		}
		ParaMap outMap = RouteManager.route(inMap.getString("action"), inMap);
		return outMap;
	}
}
