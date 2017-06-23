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
import com.road.consts.RoadConsts;
import com.road.entity.RoadAdaptor;
import com.road.service.ArrearsService;
import com.road.service.OrderService;
import com.road.util.AdaptorUtils;

public class RoadArrearsControl {

	private ArrearsService arrearsService = new ArrearsService();

	/**
	 * 从服务商获取欠费订单列表，如果没有记录，直接从本地查询，否则先同步数据到本地
	 * 
	 */
	public ParaMap getArrearsOrderList(ParaMap inMap) throws Exception {
		RoadAdaptor roadAdaptor = AdaptorUtils.getRoadAdaptor(inMap);
		ParaMap resultMap = roadAdaptor.getArrearsList(inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}

		List<ParaMap> arrearsList = (List<ParaMap>) resultMap.get(RespKey.DATA.getValue());

		if (arrearsList.size() > 0) {
			// 检查本地已经存在的退费单，并去重
			List<ParaMap> existArrearsList = arrearsService.getArrearsOrderList(arrearsList);
			arrearsList = this.removeMixed(arrearsList, existArrearsList);

			if (arrearsList.size() > 0) {
				// 根据欠费订单中的服务商订单id关联订单列表
				OrderService orderService = new OrderService();
				List<ParaMap> orderList = orderService.getOrderList(arrearsList);

				// 从本地订单中获取，订单id，商户id等信息，填充到服务商查询出来的退款记录
				this.fillArrearsData(arrearsList, orderList);

				// 将从服务商查询的退费记录，同步到本地
				arrearsService.batchAddArrearsOrder(arrearsList);
				DataSourceManager.commit();
			}
		}

		// 提交事务后，从本地查询
		ParaMap outMap = arrearsService.getArrearsOrderList(inMap);
		return outMap;
	}

	/**
	 * 申请补缴操作
	 */
	public ParaMap applyPayArrears(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("arrears_id"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		RoadAdaptor roadAdaptor = AdaptorUtils.getRoadAdaptor(inMap);
		// 从本地查询补缴单信息，组装数据，用于发起申请补缴
		ParaMap arrearsMap = arrearsService.getArrearsOrder(inMap);
		if (RoadConsts.ARREARS_STATUS_HAD_PAY == arrearsMap.getInt("arrears_status")) {
			return RespUtil.setResp(RespState.FAIL, "road.arrears.had.pay");
		}
		ParaMap sendMap = new ParaMap();
		sendMap.put("provider_arrears_code", arrearsMap.getString("provider_arrears_code"));

		ParaMap resultMap = roadAdaptor.payArrears(sendMap);
		if (RespState.SUCCESS.getValue() == resultMap.getInt(RespKey.STATE.getValue())) {
			arrearsService.updateArrearsOrderStatus(inMap);
			return RespUtil.setResp();
		}
		return resultMap;
	}

	/**
	 * 获取退费详情
	 */
	public ParaMap getArrearsOrder(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("arrears_id"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		ParaMap outMap = arrearsService.getArrearsOrder(inMap);
		outMap = RespUtil.setResp(outMap);
		return outMap;
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
	private void fillArrearsData(List<ParaMap> arrearsList, List<ParaMap> orderList) {
		for (int i = 0; i < arrearsList.size(); i++) {
			ParaMap arrearsMap = arrearsList.get(i);
			ParaMap tempMap = null;
			boolean flag = false;
			for (int j = 0; j < orderList.size(); j++) {
				ParaMap orderMap = orderList.get(j);
				if (arrearsMap.getString("provider_order_id").equals(orderMap.getString("provider_order_id"))) {
					flag = true;
					tempMap = orderMap;
					break;
				}
			}
			// 欠费单关联的订单，在本地查询不到，也要新增一条记录，防止代码出错；这种情况是由于，本地没有该订单，但是路边存在订单引起
			arrearsMap.put("arrears_id", IDGenerator.newGUID());
			if (flag) {
				arrearsMap.put("order_id", tempMap.getString("order_id"));
				arrearsMap.put("merchant_id", tempMap.getString("merchant_id"));
			} else {
				arrearsMap.put("order_id", "");
				arrearsMap.put("merchant_id", "");
			}
		}
	}

}
