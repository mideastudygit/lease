package com.road.consts;

/**
 * 常量定义类
 * 
 * @author 唐宗鸿
 * @date 20170508
 * @version 1.1.0
 */
public class RoadConsts {

	/**
	 * 订单对账相关 1-待确认，2-已确认，3-待付款，4-已付款
	 */
	public static final int BILL_STATUS_CONFIRM = 1;
	public static final int BILL_STATUS_CONFIRMED = 2;
	public static final int BILL_STATUS_PAY = 3;
	public static final int BILL_STATUS_PAYED = 4;

	/**
	 * 订单状态，1-进行中，2-已完成
	 */
	public static final int ORDER_STATUS_ONGOING = 1;
	public static final int ORDER_STATUS_FINISHED = 2;

	/**
	 * 充值状态，1-支付成功，2-支付失败
	 */

	public static final int RECHARGE_STATUS_SUCCESS = 1;
	public static final int RECHARGE_STATUS_FAIL = 2;

	/**
	 * 充值渠道
	 */
	public static final int RECHARGE_TYPE_CARD = 1;
	public static final int RECHARGE_TYPE_THRID = 2;
	public static final int RECHARGE_TYPE_CONS = 3;

	/**
	 * 申请停车流程相关 2 已满 3较少 4充足
	 */
	public static final int BERTH_STATUS_FULLED = 2;
	public static final int BERTH_STATUS_LESS = 3;
	public static final int BERTH_STATUS_ENOUGH = 4;

	/**
	 * 欠费状态，1-未补缴，2-已缴清
	 */
	public static final int ARREARS_STATUS_NO_PAY = 1;
	public static final int ARREARS_STATUS_HAD_PAY = 2;

	/**
	 * 退费状态，1-未退费， 2-已退费
	 */
	public static final int REFUND_STATUS_NOTREFUND = 1;
	public static final int REFUND_STATUS_REFUNDED = 2;

	/**
	 * 泊位状态，1-可用， 2-不可用
	 */
	public static final int BERTH_STATUS_AVAILABLE = 1;
	public static final int BERTH_STATUS_NOTAVAILABLE = 2;

	/**
	 * 付费类型，1-预付费缴费， 2-预付费续费，3-后付费申请，4-后付费手动结束
	 */
	public static final int PAY_TYPE_PREPAY = 1;
	public static final int PAY_TYPE_PRERENEWAL = 2;
	public static final int PAY_TYPE_APPLY_PAY = 3;
	public static final int PAY_TYPE_PAY_MANUAL = 4;

	/**
	 * 路外对接交易类型
	 */
	public static final String MODULE_TYPE_BERTH = "berth";
	public static final String MODULE_TYPE_TRANSACTION = "transaction";

	/**
	 * 路外行政区信息
	 */
	public static final String SZ_CANTON = "深圳市";
	
	/**
	 * 交易类型，2-退款，3-补缴
	 */
	public static final int BILL_TYPE_REFUND = 2;
	public static final int BILL_TYPE_ARREARS = 3;
}
