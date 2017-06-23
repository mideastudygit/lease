package com.open.control;

import com.base.utils.ParaMap;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.util.RespUtil;

public class CarControl {

	public ParaMap updateCar(ParaMap inMap) throws Exception {
		// 校验车辆信息
		ParaMap resultMap = RouteManager.route("car.getCar", inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE
				.getValue())) {
			return resultMap;
		}
		Object obj = resultMap.get(RespKey.DATA.getValue());
		if (obj == null || obj.equals("")) {
			return RespUtil.setResp(RespState.FAIL, "merchant.car.notexist");
		}
		ParaMap dataMap = (ParaMap) obj;
		if (dataMap == null || dataMap.isEmpty()) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL,
					"merchant.car.notexist");
			return outMap;
		}
		// 更新车辆信息
		resultMap = RouteManager.route(inMap.getString("action"), inMap);
		return resultMap;
	}
}
