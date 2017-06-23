package com.center.service;

import java.util.List;

import com.base.utils.IDGenerator;
import com.base.utils.MathUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.consts.CenterConsts;
import com.center.dao.MerchantDao;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;

/**
 * 商户服务
 * 
 * @author 唐宗鸿
 * @date 20170503
 * @version 1.1.0
 */
public class MerchantService {

	private MerchantDao merchantDao = new MerchantDao();

	/**
	 * 商户列表查询
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            merchant_name-商户名称，支持模糊查询,is_page-是否分页(1-不分页，0-分页),page_index-
	 *            第几页, page_size-每页记录数
	 * @return 
	 *         merchant_id-商户id,merchant_name-商户名称,card_name-法人姓名,card_no-法人身份证号，
	 *         first_amount
	 *         -首次充值金额,alarm_balance-预警余额,balance-余额,buss_license-营业执照
	 *         ,app_id-商户号,app_secret-商户秘钥, push_url
	 *         -推送地址,admin_name-管理员姓名,tel-手机号码,phone-联系电话，非必须,address-地址,
	 *         bank_name-银行开户行, account_no-银行账户,account_name-账户名称,bill_type-对账模式
	 *         ，0-固定日期，1-固定周期,bill_cycle-具体账期,bill_start-开始对账日期,status-商户状态，0-
	 *         待审核 1-正常 2-已禁用,enable_time-启用时间 ,createtime-创建时间, updatetime-更新时间
	 *         state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getMerchantList(ParaMap inMap) throws Exception {
		ParaMap resultMap = merchantDao.getMerchantList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}

	/**
	 * 商户列表查询
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param idList
	 *            商户id集合
	 * @return 
	 *         merchant_id-商户id,merchant_name-商户名称,card_name-法人姓名,card_no-法人身份证号，
	 *         first_amount
	 *         -首次充值金额,alarm_balance-预警余额,balance-余额,buss_license-营业执照
	 *         ,app_id-商户号,app_secret-商户秘钥, push_url
	 *         -推送地址,admin_name-管理员姓名,tel-手机号码,phone-联系电话，非必须,address-地址,
	 *         bank_name-银行开户行, account_no-银行账户,account_name-账户名称,bill_type-对账模式
	 *         ，0-固定日期，1-固定周期,bill_cycle-具体账期,bill_start-开始对账日期,status-商户状态，0-
	 *         待审核 1-正常 2-已禁用,enable_time-启用时间 ,createtime-创建时间, updatetime-更新时间
	 *         state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getMerchantList(List<String> idList) throws Exception {
		ParaMap resultMap = merchantDao.getMerchantList(idList);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		return outMap;
	}

	/**
	 * 添加商户信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            merchant_name-商户名称,card_name-法人姓名,card_no-法人身份证号，first_amount-
	 *            首次充值金额
	 *            ,alarm_balance-预警余额,buss_license-营业执照,app_id-商户号,app_secret
	 *            -商户秘钥, push_url-推送地址
	 *            ,admin_name-管理员姓名,tel-手机号码,phone-联系电话，非必须,address-地址
	 * @return merchant_id-商户id,state -响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addMerchant(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("merchant_name")) || StrUtils.isNull(inMap.getString("card_name"))
				|| StrUtils.isNull(inMap.getString("card_no")) || StrUtils.isNull(inMap.getString("first_amount"))
				|| StrUtils.isNull(inMap.getString("alarm_balance"))
				|| StrUtils.isNull(inMap.getString("buss_license")) || StrUtils.isNull(inMap.getString("app_id"))
				|| StrUtils.isNull(inMap.getString("app_secret")) || StrUtils.isNull(inMap.getString("push_url"))
				|| StrUtils.isNull(inMap.getString("admin_name")) || StrUtils.isNull(inMap.getString("tel"))
				|| StrUtils.isNull(inMap.getString("address"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		int num = merchantDao.checkMerchant(null, inMap.getString("merchant_name"), null);
		if (num > 0) {
			return RespUtil.setResp(RespState.FAIL, "merchant.name.exist");
		}
		num = merchantDao.checkMerchant(null, null, inMap.getString("app_id"));
		if (num > 0) {
			return RespUtil.setResp(RespState.FAIL, "merchant.appid.exist");
		}
		inMap.put("merchant_id", IDGenerator.newGUID());
		inMap.put("balance", inMap.getString("first_amount"));
		ParaMap resultMap = merchantDao.addMerchant(inMap);
		if (resultMap.getInt("num") < 1) {
			return RespUtil.setResp(RespState.FAIL, "merchant.add.fail");
		}
		ParaMap outMap = new ParaMap();
		outMap.put("merchant_id", inMap.getString("merchant_id"));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 商户信息详情查询
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            merchant_id-商户id,merchant_name-商户名称,app_id-商户号
	 * @return 
	 *         merchant_id-商户id,merchant_name-商户名称,card_name-法人姓名,card_no-法人身份证号，
	 *         first_amount
	 *         -首次充值金额,alarm_balance-预警余额,balance-余额,buss_license-营业执照
	 *         ,app_id-商户号,app_secret-商户秘钥, push_url
	 *         -推送地址,admin_name-管理员姓名,tel-手机号码,phone-联系电话，非必须,address-地址,
	 *         bank_name-银行开户行, account_no-银行账户,account_name-账户名称,bill_type-对账模式
	 *         ，0-固定日期，1-固定周期,bill_cycle-具体账期,bill_start-开始对账日期,status-商户状态，0-
	 *         待审核 1-正常 2-已禁用,enable_time-启用时间 ,createtime-创建时间,
	 *         updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getMerchant(ParaMap inMap) throws Exception {
		ParaMap resultMap = merchantDao.getMerchant(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		return outMap;
	}

	/**
	 * 商户信息更改
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            merchant_id-商户id,merchant_name-商户名称,card_name-法人姓名,card_no-
	 *            法人身份证号，first_amount-首次充值金额,contact_number-法人联系电话,alarm_balance
	 *            -预警余额,buss_license-营业执照 ,app_id-商户号,app_secret-商户秘钥,
	 *            push_url-推送地址
	 *            ,admin_name-管理员姓名,tel-手机号码,phone-联系电话，非必须,address-地址
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateMerchant(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("merchant_id")) || StrUtils.isNull(inMap.getString("merchant_name"))
				|| StrUtils.isNull(inMap.getString("card_name")) || StrUtils.isNull(inMap.getString("card_no"))
				|| StrUtils.isNull(inMap.getString("alarm_balance"))
				|| StrUtils.isNull(inMap.getString("buss_license")) || StrUtils.isNull(inMap.getString("app_id"))
				|| StrUtils.isNull(inMap.getString("app_secret")) || StrUtils.isNull(inMap.getString("push_url"))
				|| StrUtils.isNull(inMap.getString("admin_name")) || StrUtils.isNull(inMap.getString("tel"))
				|| StrUtils.isNull(inMap.getString("address"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		int num = merchantDao.checkMerchant(inMap.getString("merchant_id"), inMap.getString("merchant_name"), null);
		if (num > 0) {
			return RespUtil.setResp(RespState.FAIL, "merchant.name.exist");
		}
		num = merchantDao.checkMerchant(inMap.getString("merchant_id"), null, inMap.getString("app_id"));
		if (num > 0) {
			return RespUtil.setResp(RespState.FAIL, "merchant.appid.exist");
		}
		ParaMap resultMap = merchantDao.updateMerchant(inMap);
		if (resultMap.getInt("num") < 1) {
			return RespUtil.setResp(RespState.FAIL, "merchant.update.fail");
		}
		return RespUtil.setResp();
	}

	/**
	 * 禁用商户信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            merchant_id-商户id,status-状态，1-启用,2-禁用
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap enableMerchant(ParaMap inMap) throws Exception {
		int status = inMap.getInt("status");
		if (!(CenterConsts.MERCHANT_STATUS_DISABLED == status || CenterConsts.MERCHANT_STATUS_NORMAL == status)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.error");
			return outMap;
		}
		ParaMap resultMap = merchantDao.enableMerchant(inMap.getString("merchant_id"), status);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "merchant.enable.fail");
			return outMap;
		}
		return RespUtil.setResp();
	}

	/**
	 * 商户余额更改
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            merchant_id-商户id,amount-金额,type-类型，1-扣费，2-充值
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateMerchantBalance(ParaMap inMap) throws Exception {
		if (inMap.getInt("type") == CenterConsts.BALANCE_TYPE_CUSTOMER) {
			inMap.put("amount", MathUtils.subtract(0, inMap.getDouble("amount")));
		}
		ParaMap resultMap = merchantDao.updateMerchantBalance(inMap);
		if (resultMap.getInt("num") < 1) {
			return RespUtil.setResp(RespState.FAIL, "merchant.balance.update.fail");
		}
		return RespUtil.setResp();
	}
}
