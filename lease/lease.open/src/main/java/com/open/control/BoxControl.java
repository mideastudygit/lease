package com.open.control;

import com.base.utils.ParaMap;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.util.RespUtil;

public class BoxControl {

	public ParaMap updateBox(ParaMap inMap) throws Exception {
		// 校验车载盒子信息
		ParaMap resultMap = RouteManager.route("box.getBox", inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE
				.getValue())) {
			return resultMap;
		}
		ParaMap dataMap = ((ParaMap) resultMap.get(RespKey.DATA.getValue()));
		if (dataMap.isEmpty()) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL,
					"car.box.notexist");
			return outMap;
		}
		// 更新车载盒子信息
		resultMap = RouteManager.route(inMap.getString("action"), inMap);
		return resultMap;
	}
}
