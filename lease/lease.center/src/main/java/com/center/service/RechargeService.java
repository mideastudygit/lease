package com.center.service;

import java.util.List;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.consts.CenterConsts;
import com.center.dao.RechargeDao;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;
import com.common.util.StringUtil;

/**
 * 充值记录服务
 * 
 * @author 唐宗鸿
 * @date 20170503
 * @version 1.1.0
 */
public class RechargeService {

	private RechargeDao rechargeDao = new RechargeDao();

	/**
	 * 充值记录列表查询
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            merchant_name-商户名称，支持模糊查询,start_time-开始时间，end_time-结束时间,
	 *            page_index-第几页，默认1,page_size-每页记录数，默认10
	 * @return 
	 *         recharge_id-充值记录id,merchant_id-商户id,merchant_name-商户名称,type-充值类型，1
	 *         -厂商充值，2-平台充值,amount-充值金额,createtime-创建时间
	 *         ,updatetime-更新时间,creator-操作员,state -响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getRechargeList(ParaMap inMap) throws Exception {
		ParaMap resultMap = rechargeDao.getRechargeList(inMap);
		List<ParaMap> list = ApiUtil.formatList(resultMap);
		ParaMap outMap = RespUtil.setResp(list);
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}

	/**
	 * 添加充值记录
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            merchant_id-商户id,amount-充值金额,user_id-操作员id
	 * @return recharge_id-充值记录id,state -响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addRecharge(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("merchant_id")) || StrUtils.isNull(inMap.getString("amount"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		if (!StringUtil.isNumber(inMap.getString("amount"))) {
			return RespUtil.setResp(RespState.FAIL, "recharge.add.amount.fail");
		}
		inMap.put("recharge_id", IDGenerator.newGUID());
		inMap.put("type", CenterConsts.RECHARGE_TYPE_MERCHANT);
		inMap.put("status", CenterConsts.RECHARGE_STATUS_SUCCESS);
		ParaMap resultMap = rechargeDao.addRecharge(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "recharge.add.fail");
			return outMap;
		}
		ParaMap outMap = new ParaMap();
		outMap.put("recharge_id", inMap.getString("recharge_id"));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}
}
