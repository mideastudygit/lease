package com.center.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.base.mq.MQSendUtils;
import com.base.utils.CacheUtils;
import com.base.utils.ParaMap;
import com.center.consts.MqConsts;
import com.center.service.MerchantService;
import com.center.util.DataUtil;
import com.common.consts.CacheConsts;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.util.DateUtil;
import com.common.util.RespUtil;

/**
 * 对账控制器
 * 
 * @author 唐宗鸿
 * @date 20170524
 * @version 1.1.0
 */
public class BillControl {

	public ParaMap getBillList(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route("road.bill.getBillList", inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		List<ParaMap> billList = ((List<ParaMap>) resultMap.get(RespKey.DATA.getValue()));
		if (billList.isEmpty()) {
			return resultMap;
		}
		List<String> idList = new ArrayList<String>();
		Iterator<ParaMap> idIterator = billList.iterator();
		while (idIterator.hasNext()) {
			ParaMap tempMap = idIterator.next();
			idList.add(tempMap.getString("merchant_id"));
		}
		// 组装商户信息
		MerchantService merchantService = new MerchantService();
		List<ParaMap> merchantList = ((List<ParaMap>) merchantService.getMerchantList(idList).get(
				RespKey.DATA.getValue()));
		DataUtil.formatList(billList, merchantList, new String[] { "merchant_id", "merchant_name" });
		return resultMap;
	}

	public ParaMap getBill(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route("road.bill.getBill", inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap dataMap = ((ParaMap) resultMap.get(RespKey.DATA.getValue()));
		if (dataMap.isEmpty()) {
			return resultMap;
		}
		// 组装商户信息
		MerchantService merchantService = new MerchantService();
		ParaMap merchantMap = merchantService.getMerchant(dataMap);
		DataUtil.format(dataMap, merchantMap, new String[] { "merchant_name" });
		return resultMap;
	}

	public ParaMap updateBill(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route("road.bill.updateBill", inMap);
		return resultMap;
	}

	public ParaMap getBillDataList(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route("road.bill.getBillDataList", inMap);
		return resultMap;
	}

	public ParaMap repeatBill(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route("road.bill.getBill", inMap);
		ParaMap merchantBillMap = ((ParaMap) resultMap.get(RespKey.DATA.getValue()));
		if (merchantBillMap.isEmpty()) {
			return resultMap;
		}
		long startTime = merchantBillMap.getLong("start_time");
		long endTime = merchantBillMap.getLong("end_time");
		String billDate = DateUtil.format(merchantBillMap.getLong("bill_date"), DateUtil.TO_DAY);

		ParaMap sendMap = new ParaMap();
		sendMap.put("action", "road.bill.repeatSystemBill");
		sendMap.put("merchant_id", merchantBillMap.getString("merchant_id"));
		sendMap.put("bill_id", inMap.getString("bill_id"));// 商户对账单id
		sendMap.put("start_date", DateUtil.format(startTime, DateUtil.TO_DAY));
		sendMap.put("end_date", DateUtil.format(endTime, DateUtil.TO_DAY));

		String key = CacheConsts.REPEAT_BILL_FLAG + merchantBillMap.getString("merchant_id") + "_" + billDate;

		// 设置缓存对账标识
		long day = (endTime - startTime) / DateUtil.DAY_TIME_MILLIS + 1;
		CacheUtils.set(key, day + "");
		String firstDay = DateUtil.format(startTime, DateUtil.TO_DAY);

		// 从账单第一天开始重新进行系统对账
		for (int i = 1; i <= day; i++) {
			sendMap.put("bill_date", firstDay);
			MQSendUtils.send(MqConsts.SYSTEM_BILL, sendMap);
			firstDay = DateUtil.nextDay(firstDay, DateUtil.TO_DAY);
		}
		return RespUtil.setResp();
	}
}
