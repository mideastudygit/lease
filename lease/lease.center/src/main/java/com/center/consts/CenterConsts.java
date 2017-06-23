package com.center.consts;

public final class CenterConsts {

	/**
	 * 状态：0-禁用，1-启用
	 */
	public static final int DISABLED = 0;
	public static final int ENABLED = 1;

	/**
	 * 商户状态：1-正常，2-禁用
	 */
	public static final int MERCHANT_STATUS_NORMAL = 1;
	public static final int MERCHANT_STATUS_DISABLED = 2;

	/**
	 * 充值状态：0-充值中，1-充值成功，2-充值失败
	 */
	public static final int RECHARGE_STATUS_CHARGING = 0;
	public static final int RECHARGE_STATUS_SUCCESS = 1;
	public static final int RECHARGE_STATUS_FAIL = 2;

	/**
	 * 充值类型：1-厂商充值，2-平台充值
	 */
	public static final int RECHARGE_TYPE_MERCHANT = 1;
	public static final int RECHARGE_TYPE_PROVIDER = 2;

	/**
	 * 对账类型：0-固定日期，1-固定周期
	 */
	public static final int BILL_TYPE_DATE = 0;
	public static final int BILL_TYPE_CYCLE = 1;

	/**
	 * 余额更改类型：1-扣费，2-充值
	 */
	public static final int BALANCE_TYPE_CUSTOMER = 1;
	public static final int BALANCE_TYPE_RECHARGE = 2;

	/**
	 * 服务商类型：1-路边泊位 2-停车场 3-充电桩
	 */
	public static final int PROVIDER_TYPE_ROAD = 1;
	public static final int PROVIDER_TYPE_PARK = 2;
	public static final int PROVIDER_TYPE_CHARGE = 3;

}
