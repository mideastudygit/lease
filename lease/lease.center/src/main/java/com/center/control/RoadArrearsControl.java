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
 * 路外补缴单控制器
 * 
 * @author 唐宗鸿
 * @date 20170531
 * @version 1.1.0
 *
 */
public class RoadArrearsControl {

	public ParaMap getArrearsOrderList(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		List<ParaMap> arrearsList = ((List<ParaMap>) resultMap.get(RespKey.DATA.getValue()));
		if (arrearsList.isEmpty()) {
			return resultMap;
		}
		List<String> idList = new ArrayList<String>();
		Iterator<ParaMap> idIterator = arrearsList.iterator();
		while (idIterator.hasNext()) {
			ParaMap tempMap = idIterator.next();
			idList.add(tempMap.getString("merchant_id"));
		}
		// 组装商户信息
		MerchantService merchantService = new MerchantService();
		List<ParaMap> merchantList = ((List<ParaMap>) merchantService.getMerchantList(idList).get(
				RespKey.DATA.getValue()));
		DataUtil.formatList(arrearsList, merchantList, new String[] { "merchant_id", "merchant_name" });
		return resultMap;
	}

	public ParaMap getArrearsOrder(ParaMap inMap) throws Exception {
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
}
