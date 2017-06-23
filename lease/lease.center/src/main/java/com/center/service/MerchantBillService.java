package com.center.service;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.dao.MerchantBillDao;
import com.common.consts.RespConsts.RespState;
import com.common.util.RespUtil;

/**
 * 商户对账服务
 * 
 * @author 唐宗鸿
 * @date 20170511
 * @version 1.1.0
 */
public class MerchantBillService {

	private MerchantBillDao merchantBillDao = new MerchantBillDao();

	/**
	 * 添加商户对账信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170511
	 * @param inMap
	 *            merchant_id-商户id,bank_name-银行开户行, account_no
	 *            -银行账户,account_name-账户名称,bill_type-对账模式，0-固定日期，1-固定周期
	 *            ,bill_cycle-具体账期 ,bill_start-开始对账日期
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addMerchantBill(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("merchant_id"))
				|| StrUtils.isNull(inMap.getString("bank_name"))
				|| StrUtils.isNull(inMap.getString("account_no"))
				|| StrUtils.isNull(inMap.getString("account_name"))
				|| StrUtils.isNull(inMap.getString("bill_type"))
				|| StrUtils.isNull(inMap.getString("bill_cycle"))
				|| StrUtils.isNull(inMap.getString("bill_start"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		inMap.put("merchant_bill_id", IDGenerator.newGUID());
		merchantBillDao.addMerchantBill(inMap);
		return RespUtil.setResp();
	}

	/**
	 * 商户对账信息更改
	 * 
	 * @author 唐宗鸿
	 * @date 20170511
	 * @param inMap
	 *            merchant_id-商户id,bank_name-银行开户行, account_no
	 *            -银行账户,account_name-账户名称,bill_type-对账模式，0-固定日期，1-固定周期
	 *            ,bill_cycle-具体账期
	 * @return state -响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateMerchantBill(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("merchant_id"))
				|| StrUtils.isNull(inMap.getString("bank_name"))
				|| StrUtils.isNull(inMap.getString("account_no"))
				|| StrUtils.isNull(inMap.getString("account_name"))
				|| StrUtils.isNull(inMap.getString("bill_type"))
				|| StrUtils.isNull(inMap.getString("bill_cycle"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		merchantBillDao.updateMerchantBill(inMap);
		return RespUtil.setResp();
	}
}
