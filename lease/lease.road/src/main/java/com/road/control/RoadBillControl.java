package com.road.control;

import java.util.ArrayList;
import java.util.List;

import com.base.utils.IDGenerator;
import com.base.utils.MathUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.consts.SqlConsts;
import com.common.util.DateUtil;
import com.common.util.RespUtil;
import com.road.consts.RoadConsts;
import com.road.service.MerchantBillService;
import com.road.service.OrderService;
import com.road.service.RefundService;
import com.road.service.SystemBillService;
import com.road.util.BillUtil;

/**
 * 对账服务类
 * 
 * @author 唐宗鸿
 * @date 20170524
 * @version 1.1.0
 */
public class RoadBillControl {

	private MerchantBillService merchantBillService = new MerchantBillService();
	private SystemBillService systemBillService = new SystemBillService();

	/**
	 * 
	 * 查询对账列表
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 *            merchant_id-商户id,start_time-对账起始时间,end_time-对账结束时间,status-
	 *            对账单状态，1-待确认，2-待付款，3-已付款
	 * @return 
	 *         bill_id-账单id,bill_date-账单日期,start_time-对账开始时间,end_time-对账结束时间,status
	 *         -对账状态，1-待确认，2-待付款，3-已付款,order_count-订单总数,refund_count-退款总数
	 *         ,total_amount
	 *         -订单总额,refund_amount-退款总额,merchant_id-商户id,create_time
	 *         -创建时间,update_time-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getMerchantBillList(ParaMap inMap) throws Exception {
		ParaMap outMap = merchantBillService.getMerchantBillList(inMap);
		return outMap;
	}

	/**
	 * 
	 * 获取对账单详情
	 * 
	 * @author 唐宗鸿
	 * @param billId
	 *            对账单id
	 * @return 
	 *         bill_id-账单id,bill_date-账单日期,start_time-对账开始时间,end_time-对账结束时间,status
	 *         -对账状态，1-待确认，2-待付款，3-已付款,order_count-订单总数,refund_count-退款总数
	 *         ,total_amount
	 *         -订单总额,refund_amount-退款总额,merchant_id-商户id,create_time
	 *         -创建时间,update_time-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getMerchantBill(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("bill_id"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = merchantBillService.getMerchantBill(inMap.getString("bill_id"));
		return resultMap;
	}

	/**
	 * 
	 * 对账单状态更改
	 * 
	 * @author 唐宗鸿
	 * @param billId
	 *            对账单id,status-状态，2-确认，3-提交付款,4-完成付款
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateMerchantBill(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("bill_id")) || StrUtils.isNull(inMap.getString("status"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		// 校验状态
		int status = inMap.getInt("status");
		if (status != RoadConsts.BILL_STATUS_CONFIRMED && status != RoadConsts.BILL_STATUS_PAY
				&& status != RoadConsts.BILL_STATUS_PAYED) {
			return RespUtil.setResp(RespState.FAIL, "param.error");
		}
		ParaMap resultMap = merchantBillService.getMerchantBill(inMap.getString("bill_id"));
		ParaMap dataMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		if (dataMap.isEmpty()) {
			return RespUtil.setResp(RespState.FAIL, "bill.notexist");
		}
		// 操作校验，待确认账单只能确认；已确认账单只能提交付款；已付款账单只能完成付款
		int billStatus = dataMap.getInt("status");
		if (status == RoadConsts.BILL_STATUS_CONFIRMED && billStatus != RoadConsts.BILL_STATUS_CONFIRM) {
			return RespUtil.setResp(RespState.FAIL, "bill.operate.fail");
		}
		if (status == RoadConsts.BILL_STATUS_PAY && billStatus != RoadConsts.BILL_STATUS_CONFIRMED) {
			return RespUtil.setResp(RespState.FAIL, "bill.operate.fail");
		}
		if (status == RoadConsts.BILL_STATUS_PAYED && billStatus != RoadConsts.BILL_STATUS_PAY) {
			return RespUtil.setResp(RespState.FAIL, "bill.operate.fail");
		}
		resultMap = merchantBillService.updateMerchantBill(inMap.getString("bill_id"), status);
		return resultMap;
	}

	/**
	 * 
	 * 获取对账单数据
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 *            bill_id-商户对账单id
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getBillDataList(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("bill_id"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap merchantMap = merchantBillService.getMerchantBill(inMap.getString("bill_id"));
		ParaMap sendMap = new ParaMap();
		sendMap.put("merchant_id", merchantMap.getString("merchant_id"));
		sendMap.put("start_date", merchantMap.getString("start_date"));
		sendMap.put("end_date", merchantMap.getString("end_date"));
		sendMap.put("is_page", SqlConsts.NOT_PAGE);
		// 根据商户对账单id，查询在此账单内，系统对账单信息
		List<ParaMap> systemBillList = systemBillService.getSystemBillList(sendMap);
		List<String> billIdList = new ArrayList<String>();
		for (ParaMap systemBill : systemBillList) {
			billIdList.add(systemBill.getString("bill_id"));
		}
		sendMap.put("bill_list", billIdList);
		ParaMap resultMap = systemBillService.getBillDataList(sendMap);
		List<ParaMap> billDataList = (List<ParaMap>) resultMap.get(RespKey.DATA.getValue());
		ParaMap outMap = RespUtil.setResp(billDataList);
		return outMap;
	}

	/**
	 * 
	 * 生成系统对账单
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 *            merchant_id-商户id,bill_date-对账日期，格式为yyyyMMdd
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addSystemBill(ParaMap inMap) throws Exception {
		ParaMap systemBillMap = new ParaMap();
		systemBillMap.put("bill_id", IDGenerator.newGUID());

		ParaMap qryMap = new ParaMap();
		qryMap.put("is_page", SqlConsts.NOT_PAGE);
		qryMap.put("status", RoadConsts.ORDER_STATUS_FINISHED);
		qryMap.put("bill_time", DateUtil.getTime(inMap.getString("bill_date"), DateUtil.TO_DAY));
		qryMap.put("merchant_id", inMap.getString("merchant_id"));

		// 复制当天的订单信息
		OrderService orderService = new OrderService();
		List<ParaMap> orderList = (List<ParaMap>) orderService.getOrderList(qryMap).get(RespKey.DATA.getValue());
		if (orderList.size() > 0) {
			systemBillService.addOrderBillData(orderList, systemBillMap.getString("bill_id"));
		}

		ParaMap orderTotalMap = this.calculateOrder(orderList);
		systemBillMap.putAll(orderTotalMap);

		// 复制当天的退款单信息
		qryMap.put("refund_status", RoadConsts.REFUND_STATUS_REFUNDED);
		RefundService refundService = new RefundService();
		List<ParaMap> refundList = (List<ParaMap>) refundService.getRefundOrderList(qryMap)
				.get(RespKey.DATA.getValue());
		if (refundList.size() > 0) {
			systemBillService.addRefundBillData(refundList, systemBillMap.getString("bill_id"));
		}

		ParaMap refundTotalMap = this.calculateRefundOrder(refundList);
		systemBillMap.putAll(refundTotalMap);

		systemBillMap.put("merchant_id", inMap.getString("merchant_id"));
		systemBillMap.put("bill_date", DateUtil.getTime(inMap.getString("bill_date"), DateUtil.TO_DAY));
		// 生成当天对账记录
		systemBillService.addSystemBill(systemBillMap);
		return RespUtil.setResp();
	}

	/**
	 * 
	 * 生成商户对账单
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 *            merchant_id-商户id,bill_date-对账日期，yyyyMMdd,start_date-对账开始日期，
	 *            yyyyMMdd,end_date-对账结束日期，yyyyMMdd
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addMerchantBill(ParaMap inMap) throws Exception {
		// 查询商户对账周期内，所有的系统对账单信息
		ParaMap merchantBillMap = new ParaMap();
		merchantBillMap.put("merchant_id", inMap.getString("merchant_id"));
		merchantBillMap.put("start_date", DateUtil.getTime(inMap.getString("start_date"), DateUtil.TO_DAY));
		merchantBillMap.put("end_date", DateUtil.getEndTimeOfDay(inMap.getString("end_date"), DateUtil.TO_DAY));
		ParaMap totalMap = this.calculateSystemBill(merchantBillMap);
		merchantBillMap.putAll(totalMap);
		merchantBillMap.put("bill_id", IDGenerator.newGUID());
		merchantBillMap.put("bill_date", DateUtil.getTime(inMap.getString("bill_date"), DateUtil.TO_DAY));
		// 生成商户对账记录
		merchantBillService.addMerchantBill(merchantBillMap);
		return RespUtil.setResp();
	}

	/**
	 * 
	 * 重新对系统对账
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 *            merchant_id-商户id,bill_id-商户对账单id,bill_date-对账日期，格式为yyyyMMdd,
	 *            start_date-开始对账日期,end_date-结束对账日期
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap repeatSystemBill(ParaMap inMap) throws Exception {
		ParaMap resultMap = systemBillService.getSystemBill(inMap);
		if (resultMap == null || resultMap.isEmpty()) {
			BillUtil.updateBillTaskCount(inMap.getString("merchant_id"), inMap.getString("end_date"));
			return RespUtil.setResp();
		}
		ParaMap systemBillMap = new ParaMap();
		systemBillMap.put("bill_id", resultMap.getString("bill_id"));// 系统对账单id
		systemBillMap.put("merchant_id", inMap.getString("merchant_id"));
		// 删除对账原数据
		systemBillService.delBillData(systemBillMap);

		// 复制当天的订单信息
		ParaMap qryMap = new ParaMap();
		qryMap.put("is_page", SqlConsts.NOT_PAGE);
		qryMap.put("status", RoadConsts.ORDER_STATUS_FINISHED);
		qryMap.put("bill_time", DateUtil.getTime(inMap.getString("bill_date"), DateUtil.TO_DAY));
		qryMap.put("merchant_id", inMap.getString("merchant_id"));

		OrderService orderService = new OrderService();
		List<ParaMap> orderList = (List<ParaMap>) orderService.getOrderList(qryMap).get(RespKey.DATA.getValue());
		if (orderList.size() > 0) {
			systemBillService.addOrderBillData(orderList, systemBillMap.getString("bill_id"));
		}

		ParaMap orderTotalMap = this.calculateOrder(orderList);
		systemBillMap.putAll(orderTotalMap);

		// 复制当天的退费单信息
		qryMap.put("refund_status", RoadConsts.REFUND_STATUS_REFUNDED);
		RefundService refundService = new RefundService();
		List<ParaMap> refundList = (List<ParaMap>) refundService.getRefundOrderList(qryMap)
				.get(RespKey.DATA.getValue());
		if (refundList.size() > 0) {
			systemBillService.addRefundBillData(refundList, systemBillMap.getString("bill_id"));
		}
		ParaMap refundTotalMap = this.calculateRefundOrder(refundList);
		systemBillMap.putAll(refundTotalMap);

		// 修改系统对账记录
		systemBillService.updateSystemBill(systemBillMap);

		int billTaskCount = BillUtil.updateBillTaskCount(inMap.getString("merchant_id"), inMap.getString("end_date"));
		if (billTaskCount == 0) {
			// 商户重对账
			ParaMap sendMap = new ParaMap();
			sendMap.put("merchant_id", inMap.getString("merchant_id"));
			sendMap.put("start_date", DateUtil.getTime(inMap.getString("start_date"), DateUtil.TO_DAY));
			sendMap.put("end_date", DateUtil.getEndTimeOfDay(inMap.getString("end_date"), DateUtil.TO_DAY));

			ParaMap totalMap = this.calculateSystemBill(sendMap);
			ParaMap merchantMap = new ParaMap(totalMap);
			merchantMap.put("merchant_id", inMap.getString("merchant_id"));
			merchantMap.put("bill_id", inMap.getString("bill_id"));// 商户对账单id
			merchantBillService.updateMerchantBill(merchantMap);
		}
		return RespUtil.setResp();
	}

	// 统计订单数据
	private ParaMap calculateOrder(List<ParaMap> orderList) {
		ParaMap resultMap = new ParaMap();
		resultMap.put("order_count", orderList.size());
		if (orderList.size() > 0) {
			// 统计订单数据
			double orderAmount = 0.0;
			for (ParaMap order : orderList) {
				orderAmount = MathUtils.add(order.getDouble("should_pay"), orderAmount, 2);
			}
			resultMap.put("order_amount", orderAmount);
		} else {
			resultMap.put("order_amount", 0);
		}
		return resultMap;
	}

	// 统计退费订单数据
	private ParaMap calculateRefundOrder(List<ParaMap> refundList) {
		ParaMap resultMap = new ParaMap();
		resultMap.put("refund_count", refundList.size());
		if (refundList.size() > 0) {
			double refundAmount = 0.0;
			for (ParaMap refund : refundList) {
				refundAmount = MathUtils.add(refund.getDouble("refund_amount"), refundAmount, 2);
			}
			resultMap.put("refund_amount", refundAmount);
		} else {
			resultMap.put("refund_amount", 0);
		}
		return resultMap;
	}

	// 系统对账单统计
	private ParaMap calculateSystemBill(ParaMap inMap) throws Exception {
		// 查询商户对账周期内，所有的系统对账单信息
		ParaMap sendMap = new ParaMap();
		sendMap.put("start_date", inMap.getString("start_date"));
		sendMap.put("end_date", inMap.getString("end_date"));
		sendMap.put("is_page", SqlConsts.NOT_PAGE);
		sendMap.put("merchant_id", inMap.getString("merchant_id"));
		List<ParaMap> systemBillList = systemBillService.getSystemBillList(sendMap);
		// 统计系统对账单数据
		long orderCount = 0;
		long refundCount = 0;
		double orderAmount = 0.0;
		double refundAmount = 0.0;
		for (ParaMap bill : systemBillList) {
			orderCount += bill.getInt("order_count");
			refundCount += bill.getInt("refund_count");
			orderAmount = MathUtils.add(bill.getDouble("order_amount"), orderAmount, 2);
			refundAmount = MathUtils.add(bill.getDouble("refund_amount"), refundAmount, 2);
		}
		ParaMap resultMap = new ParaMap();
		resultMap.put("order_count", orderCount);
		resultMap.put("refund_count", refundCount);
		resultMap.put("order_amount", orderAmount);
		resultMap.put("refund_amount", refundAmount);
		return resultMap;
	}
}
