package com.center.util;

import com.center.consts.CenterConsts;

public class ProviderUtil {

	public static String getAction(int providerType) {
		String action = "road.recharge.getRechargeList";
		if (providerType == CenterConsts.PROVIDER_TYPE_CHARGE) {
			action = "charge.recharge.getRechargeList";
		} else if (providerType == CenterConsts.PROVIDER_TYPE_PARK) {
			action = "park.recharge.getRechargeList";
		}
		return action;
	}
}
