package com.ercar.api.service;

import com.base.log.Logging;
import com.base.utils.ParaMap;
import com.common.service.CommonService;

public class ErCarService extends CommonService {

	private static final Logging log = Logging.getLogging("ercar");
	private static final String packagePrefix = "com.ercar.control.";

	@Override
	public void accept(ParaMap inMap) throws Exception {

		ParaMap paraMap = super.getParamater();

		log.debug("【" + paraMap.getString("action") + "】--请求：" + paraMap);

		ParaMap outMap = super.process(paraMap, packagePrefix);

		log.debug("【" + paraMap.getString("action") + "】--响应：" + outMap);

		super.response(outMap);
	}
}
