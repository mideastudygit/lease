package com.road.entity;

import com.base.utils.ParaMap;

/**
 * 供应商适配器类
 * 
 * @author 唐宗鸿
 * @date 20170509
 * @version 1.1.0
 */
public abstract class RoadAdaptor {

	/**
	 * 会员id
	 */
	private String userId;

	/**
	 * appId
	 */
	private String appId;

	/**
	 * 密钥
	 */
	private String appSecret;

	/**
	 * 支付密码
	 */
	private String password;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取每条实时进行中的订单信息
	 */
	public abstract ParaMap getOrder(ParaMap inMap) throws Exception;

	/**
	 * 结束订单推送信息
	 */
	public abstract ParaMap endOrder(ParaMap inMap) throws Exception;
	
	/**
	 * 获取每条实时进行中的订单信息
	 */
	public abstract ParaMap getRechargeList(ParaMap inMap) throws Exception;

	/**
	 * 附近泊位信息查询
	 */
	public abstract ParaMap getNearbyBerthList(ParaMap inMap) throws Exception;

	/**
	 * 路段列表查询
	 */
	public abstract ParaMap getSectionList(ParaMap inMap) throws Exception;

	/**
	 * 用户停车前检查泊位停车状态
	 */
	public abstract ParaMap getBerthStatus(ParaMap inMap) throws Exception;

	/**
	 * 停车申请
	 */
	public abstract ParaMap applyPark(ParaMap inMap) throws Exception;

	/**
	 * 补缴单查询
	 */
	public abstract ParaMap getArrearsList(ParaMap inMap) throws Exception;

	/**
	 * 申请补缴
	 */
	public abstract ParaMap payArrears(ParaMap inMap) throws Exception;

	/**
	 * 查询退费
	 */
	public abstract ParaMap getRefundList(ParaMap inMap) throws Exception;

}