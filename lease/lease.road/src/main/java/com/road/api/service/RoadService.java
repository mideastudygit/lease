package com.road.api.service;

import com.base.log.Logging;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.service.CommonService;
import com.common.util.MD5SignUtil;
import com.common.util.RespUtil;

public class RoadService extends CommonService {

	private static final Logging log = Logging.getLogging("road");
	private static final String packagePrefix = "com.road.control.";

	@Override
	public void accept(ParaMap inMap) throws Exception {

		ParaMap paraMap = super.getParamater();

		log.debug("【" + paraMap.getString("action") + "】--请求：" + paraMap);

		ParaMap outMap = super.process(paraMap, packagePrefix);

		log.debug("【" + paraMap.getString("action") + "】--响应：" + outMap);

		super.response(outMap);
	}

	/**
	 * 
	 * 外部系统统一接收地址
	 */
	@Override
	public void dispatch(ParaMap inMap) throws Exception {
		ParaMap requestMap = super.getParamater();
		log.debug("【" + requestMap.getString("action") + "】--请求：" + requestMap);
		if (StrUtils.isNull(requestMap.getString("app_id")) || StrUtils.isNull(requestMap.getString("sign"))
				|| StrUtils.isNull(requestMap.getString("action"))) {
			super.response(RespUtil.setResp(RespState.FAIL, "param.missing"));
			return;
		}
		// 服务商校验
		ParaMap sendMap = new ParaMap();
		sendMap.put("app_id", requestMap.getString("app_id"));
		ParaMap resultMap = RouteManager.route("provider.getProvider", sendMap);
		ParaMap dataMap = ((ParaMap) resultMap.get(RespKey.DATA.getValue()));
		if (dataMap == null || dataMap.isEmpty()) {
			this.response(RespUtil.setResp(RespState.FAIL, "invalid.appid"));
			return;
		}
		// 签名验证
		String appSecret = dataMap.getString("app_secret");
		if (!MD5SignUtil.checkSign(requestMap, appSecret)) {
			this.response(RespUtil.setResp(RespState.FAIL, "invalid.sign"));
			return;
		}
		// 添加服务商信息，并移除签名信息
		ParaMap paraMap = new ParaMap(requestMap);
		paraMap.put("provider_id", dataMap.getString("provider_id"));
		paraMap.remove("sign");
		// 业务处理
		ParaMap outMap = super.process(paraMap, packagePrefix);
		log.debug("【" + paraMap.getString("action") + "】--响应：" + outMap);
		super.response(outMap);
	}
}
