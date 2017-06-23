package com.manage.control;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.util.RespUtil;

public class ParamControl {

	public ParaMap getParameter(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("parameter_id"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		ParaMap sendMap = new ParaMap();
		sendMap.put("parameter_id", inMap.getString("parameter_id"));
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), sendMap);
		return resultMap;
	}
}
