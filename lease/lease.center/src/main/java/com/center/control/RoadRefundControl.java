package com.center.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.base.utils.ParaMap;
import com.center.consts.CenterConsts;
import com.center.service.MerchantService;
import com.center.util.DataUtil;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.util.RespUtil;

/**
 * 路外退款控制器
 * 
 * @author 唐宗鸿
 * @date 20170531
 * @version 1.1.0
 *
 */
public class RoadRefundControl {

	public ParaMap getRefundOrderList(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		List<ParaMap> carList = ((List<ParaMap>) resultMap.get(RespKey.DATA.getValue()));
		if (carList.isEmpty()) {
			return resultMap;
		}
		List<String> idList = new ArrayList<String>();
		Iterator<ParaMap> idIterator = carList.iterator();
		while (idIterator.hasNext()) {
			ParaMap tempMap = idIterator.next();
			idList.add(tempMap.getString("merchant_id"));
		}
		// 组装商户信息
		MerchantService merchantService = new MerchantService();
		List<ParaMap> merchantList = ((List<ParaMap>) merchantService.getMerchantList(idList).get(
				RespKey.DATA.getValue()));
		DataUtil.formatList(carList, merchantList, new String[] { "merchant_id", "merchant_name" });
		return resultMap;
	}

	public ParaMap getRefundOrder(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap dataMap = ((ParaMap) resultMap.get(RespKey.DATA.getValue()));
		if (dataMap == null || dataMap.isEmpty()) {
			return resultMap;
		}
		// 组装商户信息
		MerchantService merchantService = new MerchantService();
		ParaMap merchantMap = merchantService.getMerchant(dataMap);
		DataUtil.format(dataMap, merchantMap, new String[] { "merchant_name" });
		return resultMap;
	}

	/**
	 * 申请退费，更改商户余额
	 */
	public ParaMap applyRefund(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route("road.refund.getRefundOrder", inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap refundMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		resultMap = RouteManager.route(inMap.getString("action"), inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap sendMap = new ParaMap();
		sendMap.put("merchant_id", refundMap.getString("merchant_id"));
		sendMap.put("amount", refundMap.getString("refund_amount"));
		sendMap.put("type", CenterConsts.BALANCE_TYPE_RECHARGE);
		MerchantService merchantService = new MerchantService();
		merchantService.updateMerchantBalance(sendMap);
		return RespUtil.setResp();
	}
}
