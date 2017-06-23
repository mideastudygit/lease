package com.center.control;

import java.util.List;

import com.base.utils.ParaMap;
import com.center.consts.CenterConsts;
import com.center.service.MerchantService;
import com.center.service.ProviderService;
import com.center.service.RechargeService;
import com.center.util.ProviderUtil;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;

/**
 * 充值记录控制器
 * 
 * @author 唐宗鸿
 * @date 20170512
 * @version 1.1.0
 */
public class RechargeControl {

	private RechargeService rechargeService = new RechargeService();

	/**
	 * 充值记录列表查询
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            merchant_name-商户名称，支持模糊查询,start_time-开始时间，end_time-结束时间,
	 *            page_index-第几页，默认1,page_size-每页记录数，默认10
	 * @return 
	 *         recharge_id-充值记录id,merchant_id-商户id,merchant_name-商户名称,amount-充值金额
	 *         ,createtime-创建时间
	 *         ,updatetime-更新时间,user_name-操作员,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getRechargeList(ParaMap inMap) throws Exception {
		return rechargeService.getRechargeList(inMap);
	}

	/**
	 * 添加充值记录
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            merchant_id-商户id,amount-充值金额,user_id-操作员id
	 * @return recharge_id-充值记录id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addRecharge(ParaMap inMap) throws Exception {
		ParaMap resultMap = rechargeService.addRecharge(inMap);
		if (resultMap.getInt(RespKey.STATE.getValue()) != RespState.SUCCESS.getValue()) {
			return resultMap;
		}
		// 更改商户余额
		MerchantService merchantService = new MerchantService();
		ParaMap sendMap = new ParaMap();
		sendMap.put("amount", inMap.getString("amount"));
		sendMap.put("merchant_id", inMap.getString("merchant_id"));
		sendMap.put("type", CenterConsts.BALANCE_TYPE_RECHARGE);
		merchantService.updateMerchantBalance(sendMap);
		return resultMap;
	}

	/**
	 * 服务商充值记录列表查询
	 * 
	 * @author 唐宗鸿
	 * @date 20170516
	 * @param inMap
	 *            provider_id-服务商名称,start_time-开始时间,end_time-结束时间,recharge_type-
	 *            充值渠道 ,page_index-第几页，默认1,page_size-每页记录数，默认10
	 * @return 
	 *         recharge_id-充值记录id,provider_id-服务商id,provider_name-服务商名称,amount-充值金额
	 *         ,channel-充值渠道,
	 *         status-充值状态,recharge_time-创建时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getProviderRechargeList(ParaMap inMap) throws Exception {
		ProviderService providerService = new ProviderService();
		ParaMap providerMap = providerService.getProvider(inMap);
		String action = ProviderUtil.getAction(providerMap.getInt("provider_type"));
		ParaMap resultMap = RouteManager.route(action, inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		List<ParaMap> rechargeList = ((List<ParaMap>) resultMap.get(RespKey.DATA.getValue()));
		if (rechargeList.isEmpty()) {
			return resultMap;
		}
		for (ParaMap recharge : rechargeList) {
			recharge.put("provider_id", providerMap.getString("provider_id"));
			recharge.put("provider_name", providerMap.getString("provider_name"));
		}
		return resultMap;
	}
}
