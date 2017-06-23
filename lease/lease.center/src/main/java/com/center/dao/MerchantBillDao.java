package com.center.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.DateUtils;
import com.base.utils.ParaMap;

/**
 * 商户对账数据操作服务
 * 
 * @author 唐宗鸿
 * @date 20170511
 * @version 1.1.0
 */
public class MerchantBillDao extends BaseDataSetDao {

	/**
	 * 添加商户对账信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170511
	 * @param inMap
	 *            merchant_bill_id-主键id,merchant_id-商户id,bank_name-银行开户行,
	 *            account_no
	 *            -银行账户,account_name-账户名称,bill_type-对账模式，0-固定日期，1-固定周期
	 *            ,bill_cycle-具体账期 ,bill_start-开始对账日期
	 * @return
	 */
	public ParaMap addMerchantBill(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("merchant", "addMerchantBill");
		sqlMap.addParam(inMap.getString("merchant_bill_id"));
		sqlMap.addParam(inMap.getString("merchant_id"));
		sqlMap.addParam(inMap.getString("bank_name"));
		sqlMap.addParam(inMap.getString("account_no"));
		sqlMap.addParam(inMap.getString("account_name"));
		sqlMap.addParam(inMap.getString("bill_type"));
		sqlMap.addParam(inMap.getString("bill_cycle"));
		sqlMap.addParam(inMap.getString("bill_start"));
		ParaMap outMap = insert(sqlMap);
		return outMap;
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
	 * @return
	 */
	public ParaMap updateMerchantBill(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("merchant", "updateMerchantBill");
		sqlMap.addParam(inMap.getString("bank_name"));
		sqlMap.addParam(inMap.getString("account_no"));
		sqlMap.addParam(inMap.getString("account_name"));
		sqlMap.addParam(inMap.getString("bill_type"));
		sqlMap.addParam(inMap.getString("bill_cycle"));
		sqlMap.addParam(DateUtils.nowTime());
		sqlMap.addParam(inMap.getString("merchant_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}
}
