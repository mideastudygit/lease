package com.manage.control;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.base.web.AccessCheck;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.util.RespUtil;
import com.manage.consts.ManageConsts;

public class UserControl {

	public ParaMap userLogin(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap userMap = ((ParaMap) resultMap.get(RespKey.DATA.getValue()));

		ParaMap sendMap = new ParaMap();
		sendMap.put("merchant_id", userMap.getString("org_code"));
		ParaMap merchantMap = (ParaMap) RouteManager.route("merchant.getMerchant", sendMap)
				.get(RespKey.DATA.getValue());
		// 登录时，获取用户类型信息
		if (merchantMap == null || merchantMap.isEmpty()) {
			userMap.put("user_type", ManageConsts.USER_TYPE_SYSTEM);
		} else {
			userMap.put("merchant_id", merchantMap.getString("merchant_id"));
			userMap.put("user_type", ManageConsts.USER_TYPE_MERCHANT);
		}

		AccessCheck.login(resultMap);
		return resultMap;
	}

	public ParaMap userLogout(ParaMap inMap) throws Exception {
		String u = inMap.getString("u");
		if (StrUtils.isNull(u)) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		AccessCheck.logout(u);
		return RespUtil.setResp();
	}
}
