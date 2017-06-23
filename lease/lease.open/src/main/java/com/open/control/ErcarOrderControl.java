package com.open.control;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.util.RespUtil;

public class ErcarOrderControl {

	public ParaMap addOrder(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("mer_order_id"))
				|| StrUtils.isNull(inMap.getString("car_plate"))
				|| StrUtils.isNull(inMap.getString("status"))
				|| StrUtils.isNull(inMap.getString("begin_time"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = RouteManager
				.route(inMap.getString("action"), inMap);
		return resultMap;
	}

	public ParaMap updateOrder(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("mer_order_id"))
				|| StrUtils.isNull(inMap.getString("status"))
				|| StrUtils.isNull(inMap.getString("amount"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = RouteManager
				.route(inMap.getString("action"), inMap);
		return resultMap;
	}
}
