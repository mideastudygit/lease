package com.road.util;

import com.base.utils.CacheUtils;
import com.base.utils.StrUtils;
import com.common.consts.CacheConsts;

public final class BillUtil {

	public static synchronized int updateBillTaskCount(String merchantId, String billDate) throws Exception {
		String key = CacheConsts.REPEAT_BILL_FLAG + merchantId + "_" + billDate;
		String billCount = CacheUtils.get(key);
		if (StrUtils.isNull(billCount)) {
			return -1;
		}
		int value = Integer.valueOf(billCount) - 1;
		if (value > 0) {
			CacheUtils.set(key, value + "");
		} else {
			CacheUtils.remove(key);
		}
		return value;
	}
}
