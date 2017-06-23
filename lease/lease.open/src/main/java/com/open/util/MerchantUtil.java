package com.open.util;

import com.base.utils.CacheUtils;
import com.base.utils.StrUtils;
import com.open.consts.OpenConsts;

public final class MerchantUtil {

	public static synchronized int updateBalanceNotifyCount(String merchantId) throws Exception {
		String key = OpenConsts.BALANCE_NOTIFY_COUNT + merchantId;
		String count = CacheUtils.get(key);
		if (StrUtils.isNull(count)) {
			CacheUtils.set(key, 1 + "");
			return 1;
		}
		int value = Integer.valueOf(count) + 1;
		CacheUtils.set(key, value + "");
		return value;
	}

	public static void removeBalanceNotify(String merchantId) {
		String key = OpenConsts.BALANCE_NOTIFY_COUNT + merchantId;
		CacheUtils.remove(key);
	}
}
