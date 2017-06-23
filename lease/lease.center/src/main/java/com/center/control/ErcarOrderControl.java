package com.center.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.base.utils.ParaMap;
import com.center.service.MerchantService;
import com.center.util.DataUtil;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;

/**
 * 租车订单服务控制器
 * 
 * @author 唐宗鸿
 * @date 20170519
 * @version 1.1.0
 */
public class ErcarOrderControl {

	public ParaMap getOrderList(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		List<ParaMap> orderList = ((List<ParaMap>) resultMap.get(RespKey.DATA.getValue()));
		if (orderList.isEmpty()) {
			return resultMap;
		}
		List<String> idList = new ArrayList<String>();
		Iterator<ParaMap> idIterator = orderList.iterator();
		while (idIterator.hasNext()) {
			ParaMap tempMap = idIterator.next();
			idList.add(tempMap.getString("merchant_id"));
		}
		// 组装商户信息
		MerchantService merchantService = new MerchantService();
		List<ParaMap> merchantList = ((List<ParaMap>) merchantService.getMerchantList(idList).get(
				RespKey.DATA.getValue()));
		DataUtil.formatList(orderList, merchantList, new String[] { "merchant_id", "merchant_name" });
		return resultMap;
	}

	public ParaMap getOrder(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
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
}
