package com.road.control;

import java.util.ArrayList;
import java.util.List;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.util.RespUtil;
import com.road.consts.RoadConsts;
import com.road.entity.RoadAdaptor;
import com.road.service.OrderService;
import com.road.util.AdaptorUtils;

/**
 * 停车订单对外提供服务类
 * 
 * @author 唐宗鸿
 * @date 20170603
 * @version 1.1.0
 */
public class RoadOrderControl {

	private OrderService orderService = new OrderService();

	/**
	 * 获取正在进行中的订单
	 */
	public ParaMap getOrderList(ParaMap inMap) throws Exception {
		inMap.put("status", RoadConsts.ORDER_STATUS_ONGOING);
		ParaMap resultMap = orderService.getOrderList(inMap);
		List<ParaMap> orderList = (List<ParaMap>) resultMap.get(RespKey.DATA.getValue());
		if (orderList.size() == 0) {
			return resultMap;
		}
		RoadAdaptor roadAdaptor = AdaptorUtils.getRoadAdaptor(inMap);
		List<ParaMap> updateOrderList = new ArrayList<ParaMap>();
		ParaMap sendMap = new ParaMap();
		for (ParaMap order : orderList) {
			// 根据订单编号查询路边正在进行中的订单详情
			sendMap.put("provider_order_id", order.getString("provider_order_id"));
			ParaMap roadMap = roadAdaptor.getOrder(sendMap);
			if (RespState.SUCCESS.getValue() != roadMap.getInt(RespKey.STATE.getValue())) {
				return roadMap;
			}
			ParaMap orderMap = (ParaMap) roadMap.get(RespKey.DATA.getValue());
			if (!(orderMap == null || orderMap.isEmpty())) {
				updateOrderList.add(orderMap);
			}
		}
		if (updateOrderList.size() > 0) {
			// 批量修改订单信息
			orderService.batchUpdateRoadOrder(updateOrderList);
			DataSourceManager.commit();
		}

		ParaMap outMap = orderService.getOrderList(inMap);
		return outMap;
	}

	/**
	 * 获取订单详细信息
	 */
	public ParaMap getOrder(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("order_id"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		ParaMap qryMap = new ParaMap();
		qryMap.put("order_id", inMap.getString("order_id"));
		ParaMap resultMap = orderService.getOrder(qryMap);
		if (resultMap.isEmpty()) {
			return RespUtil.setResp(RespState.FAIL, "road.order.notexist");
		}
		if (RoadConsts.ORDER_STATUS_FINISHED == resultMap.getInt("status")) {
			ParaMap outMap = RespUtil.setResp(resultMap);
			return outMap;
		}
		// 正在进行中的订单，从服务商查询，同步到本地
		RoadAdaptor roadAdaptor = AdaptorUtils.getRoadAdaptor(inMap);
		ParaMap sendMap = new ParaMap();
		sendMap.put("provider_order_id", resultMap.getString("provider_order_id"));
		resultMap = roadAdaptor.getOrder(sendMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap orderMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		orderService.updateOrder(orderMap);
		DataSourceManager.commit();

		resultMap = orderService.getOrder(qryMap);
		ParaMap outMap = RespUtil.setResp(resultMap);
		return outMap;
	}

	/**
	 * 查询已完成的订单
	 */
	public ParaMap getFinishedOrderList(ParaMap inMap) throws Exception {
		inMap.put("status", RoadConsts.ORDER_STATUS_FINISHED);
		ParaMap resultMap = orderService.getOrderList(inMap);
		return resultMap;
	}

	/**
	 * 订单结算推送
	 */
	public ParaMap endOrder(ParaMap inMap) throws Exception {
		RoadAdaptor roadAdaptor = AdaptorUtils.getRoadAdaptor(inMap);
		ParaMap orderMap = roadAdaptor.endOrder(inMap);
		if (orderMap == null || orderMap.isEmpty()) {
			return RespUtil.setResp(RespState.FAIL, "param.error");
		}
		ParaMap qryMap = new ParaMap();
		qryMap.put("provider_order_id", orderMap.getString("provider_order_id"));
		ParaMap resultMap = orderService.getOrder(qryMap);// 校验推送订单信息
		if (resultMap == null || resultMap.isEmpty()) {
			return RespUtil.setResp(RespState.FAIL, "road.order.notexist");
		}
		if (RoadConsts.ORDER_STATUS_FINISHED == resultMap.getInt("status")) {
			return RespUtil.setResp(); // 已经结束的订单，不在执行后面的业务，防止多次结算推送，导致商户余额多次更改
		}
		orderMap.put("status", RoadConsts.ORDER_STATUS_FINISHED);
		orderService.updateOrder(orderMap);// 更新本地订单信息

		orderMap.put("order_id", resultMap.getString("order_id"));
		orderMap.put("ticket", resultMap.getString("ticket"));
		orderMap.put("merchant_id", resultMap.getString("merchant_id"));

		RouteManager.route("road.push.endOrder", orderMap);// 推送到数据中心
		return RespUtil.setResp();
	}

}
