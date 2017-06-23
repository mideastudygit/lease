package com.road.control;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.util.RespUtil;
import com.road.consts.RoadConsts;
import com.road.entity.RoadAdaptor;
import com.road.service.OrderService;
import com.road.util.AdaptorUtils;

/**
 * 申请停车控制器
 * 
 * @author 唐宗鸿
 * @date 20170603
 * @version 1.1.0
 */
public class RoadParkControl {

	/**
	 * 用户停车前检查泊位停车状态
	 */
	public ParaMap getBerthStatus(ParaMap inMap) throws Exception {
		RoadAdaptor roadAdaptor = AdaptorUtils.getRoadAdaptor(inMap);
		ParaMap resultMap = roadAdaptor.getBerthStatus(inMap);
		return resultMap;
	}

	/**
	 * 停车申请
	 */
	public ParaMap applyPark(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("berth_code")) || StrUtils.isNull(inMap.getString("provider_id"))
				|| StrUtils.isNull(inMap.getString("merchant_id")) || StrUtils.isNull(inMap.getString("car_plate"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		// 泊位号有正在进行中的订单，不能申请停车
		ParaMap qryMap = new ParaMap();
		qryMap.put("berth_code", inMap.getString("berth_code"));
		qryMap.put("status", RoadConsts.ORDER_STATUS_ONGOING);
		OrderService orderService = new OrderService();
		ParaMap resultMap = orderService.getOrder(qryMap);
		if (!resultMap.isEmpty()) {
			return RespUtil.setResp(RespState.FAIL, "road.berth.parking");
		}

		RoadAdaptor roadAdaptor = AdaptorUtils.getRoadAdaptor(inMap);
		resultMap = roadAdaptor.applyPark(inMap);
		if (RespState.SUCCESS.getValue() == resultMap.getInt(RespKey.STATE.getValue())) {
			ParaMap dataMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
			if (dataMap == null || dataMap.isEmpty()) {
				return RespUtil.setResp(RespState.FAIL, "road.apply.park.fail");
			}
			inMap.put("provider_order_id", dataMap.getString("provider_order_id"));
			ParaMap orderMap = orderService.addOrder(inMap);// 添加申请停车订单记录
			
			ParaMap outMap = new ParaMap();
			outMap.put("order_id", orderMap.getString("order_id"));// 返回商户平台生成的订单id
			outMap = RespUtil.setResp(outMap);
			return outMap;

		}
		ParaMap outMap = RespUtil.setResp(RespState.FAIL, "road.apply.park.fail");
		return outMap;
	}
}
