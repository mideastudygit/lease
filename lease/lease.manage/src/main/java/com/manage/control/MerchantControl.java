package com.manage.control;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.util.DateUtil;
import com.common.util.RespUtil;

public class MerchantControl {

	public ParaMap addMerchant(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("merchant_name")) || StrUtils.isNull(inMap.getString("card_name"))
				|| StrUtils.isNull(inMap.getString("card_no")) || StrUtils.isNull(inMap.getString("first_amount"))
				|| StrUtils.isNull(inMap.getString("alarm_balance"))
				|| StrUtils.isNull(inMap.getString("buss_license")) || StrUtils.isNull(inMap.getString("app_id"))
				|| StrUtils.isNull(inMap.getString("app_secret")) || StrUtils.isNull(inMap.getString("push_url"))
				|| StrUtils.isNull(inMap.getString("admin_name")) || StrUtils.isNull(inMap.getString("tel"))
				|| StrUtils.isNull(inMap.getString("address")) || StrUtils.isNull(inMap.getString("bank_name"))
				|| StrUtils.isNull(inMap.getString("account_no")) || StrUtils.isNull(inMap.getString("account_name"))
				|| StrUtils.isNull(inMap.getString("bill_type")) || StrUtils.isNull(inMap.getString("bill_cycle"))
				|| StrUtils.isNull(inMap.getString("bill_start"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		String billStart = DateUtil.format(inMap.getLong("bill_start"), DateUtil.TO_DAY_LINE);
		inMap.put("bill_start", DateUtil.getTime(billStart, DateUtil.TO_DAY_LINE));
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
		return resultMap;
	}

	public ParaMap updateMerchant(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("merchant_id")) || StrUtils.isNull(inMap.getString("merchant_name"))
				|| StrUtils.isNull(inMap.getString("card_name")) || StrUtils.isNull(inMap.getString("card_no"))
				|| StrUtils.isNull(inMap.getString("alarm_balance"))
				|| StrUtils.isNull(inMap.getString("buss_license")) || StrUtils.isNull(inMap.getString("app_id"))
				|| StrUtils.isNull(inMap.getString("app_secret")) || StrUtils.isNull(inMap.getString("push_url"))
				|| StrUtils.isNull(inMap.getString("admin_name")) || StrUtils.isNull(inMap.getString("tel"))
				|| StrUtils.isNull(inMap.getString("address")) || StrUtils.isNull(inMap.getString("bank_name"))
				|| StrUtils.isNull(inMap.getString("account_no")) || StrUtils.isNull(inMap.getString("account_name"))
				|| StrUtils.isNull(inMap.getString("bill_type")) || StrUtils.isNull(inMap.getString("bill_cycle"))
				|| StrUtils.isNull(inMap.getString("bill_start"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		String billStart = DateUtil.format(inMap.getLong("bill_start"), DateUtil.TO_DAY_LINE);
		inMap.put("bill_start", DateUtil.getTime(billStart, DateUtil.TO_DAY_LINE));
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
		return resultMap;
	}
}
