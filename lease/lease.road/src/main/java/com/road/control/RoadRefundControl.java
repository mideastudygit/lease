package com.road.control;

import java.util.ArrayList;
import java.util.List;

import com.base.ds.DataSourceManager;
import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.util.RespUtil;
import com.road.entity.RoadAdaptor;
import com.road.service.OrderService;
import com.road.service.RefundService;
import com.road.util.AdaptorUtils;

public class RoadRefundControl {

	private RefundService refundService = new RefundService();

	/**
	 * 获取退款订单详情
	 * 
	 */
	public ParaMap getRefundOrder(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("refund_id"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		ParaMap outMap = refundService.getRefundOrder(inMap);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 从服务商查询退费记录，如果没有记录，直接从本地查询，否则先同步数据到本地
	 * 
	 */
	public ParaMap getRefundOrderList(ParaMap inMap) throws Exception {
		RoadAdaptor roadAdaptor = AdaptorUtils.getRoadAdaptor(inMap);
		ParaMap resultMap = roadAdaptor.getRefundList(inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		List<ParaMap> refundList = (List<ParaMap>) resultMap.get(RespKey.DATA.getValue());

		if (refundList.size() > 0) {
			// 检查本地已经存在的退费单，并去除掉
			List<ParaMap> existRefundList = refundService.getRefundOrderList(refundList);
			refundList = this.removeMixed(refundList, existRefundList);

			if (refundList.size() > 0) {
				// 根据欠费订单中的服务商订单id关联订单列表
				OrderService orderService = new OrderService();
				List<ParaMap> orderList = orderService.getOrderList(refundList);

				// 从本地订单中获取，订单id，商户id等信息，填充到服务商查询出来的退款记录
				this.fillRefundData(refundList, orderList);

				// 将从服务商查询的退费记录，同步到本地
				refundService.batchAddRefundOrder(refundList);
				DataSourceManager.commit();
			}
		}

		// 提交事务后，从本地查询
		ParaMap outMap = refundService.getRefundOrderList(inMap);
		return outMap;
	}

	/**
	 * 申请退费操作
	 * 
	 */
	public ParaMap applyRefund(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("refund_id"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		ParaMap resultMap = refundService.updateRefundOrderStatus(inMap);
		if (resultMap.getInt("num") <= 0) {
			return RespUtil.setResp(RespState.FAIL, "apply.refund.fail");
		}
		return RespUtil.setResp();
	}

	/**
	 * 去除掉srcList集合中，包含的targetList元素
	 * 
	 */
	private List<ParaMap> removeMixed(List<ParaMap> srcList, List<ParaMap> targetList) {
		if (targetList == null || targetList.size() == 0) {
			return srcList;
		}
		List<ParaMap> outList = new ArrayList<ParaMap>();
		for (int i = 0; i < srcList.size(); i++) {
			boolean flag = true;
			ParaMap srcMap = srcList.get(i);
			for (int j = 0; j < targetList.size(); j++) {
				ParaMap targetMap = targetList.get(j);
				if (srcMap.getString("provider_order_id").equals(targetMap.getString("provider_order_id"))) {
					flag = false;
					break;
				}
			}
			if (flag) {
				outList.add(srcMap);
			}
		}
		return outList;
	}

	/**
	 * 将本地订单中的商户，订单id等信息填充填充到服务商查询出来的退款记录
	 * 
	 */
	private void fillRefundData(List<ParaMap> refundList, List<ParaMap> orderList) {
		for (int i = 0; i < refundList.size(); i++) {
			ParaMap refundMap = refundList.get(i);
			ParaMap tempMap = null;
			boolean flag = false;
			for (int j = 0; j < orderList.size(); j++) {
				ParaMap orderMap = orderList.get(j);
				if (refundMap.getString("provider_order_id").equals(orderMap.getString("provider_order_id"))) {
					flag = true;
					tempMap = orderMap;
					break;
				}
			}
			refundMap.put("refund_id", IDGenerator.newGUID());
			// 退费单关联的订单，在本地查询不到，也要新增一条记录，防止代码出错；这种情况是由于，本地没有该订单，但是路边存在订单引起
			if (flag) {
				refundMap.put("order_id", tempMap.getString("order_id"));
				refundMap.put("merchant_id", tempMap.getString("merchant_id"));
			} else {
				refundMap.put("order_id", "");
				refundMap.put("merchant_id", "");
			}
		}
	}

}
