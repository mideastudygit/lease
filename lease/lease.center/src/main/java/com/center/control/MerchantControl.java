package com.center.control;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.consts.CenterConsts;
import com.center.service.AuthService;
import com.center.service.MerchantBillService;
import com.center.service.MerchantService;
import com.center.service.SmsService;
import com.center.service.UserService;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.util.RespUtil;

/**
 * 商户服务控制器
 * 
 * @author 唐宗鸿
 * @date 20170512
 * @version 1.1.0
 */
public class MerchantControl {

	MerchantService merchantService = new MerchantService();

	MerchantBillService merchantBillService = new MerchantBillService();

	/**
	 * 商户列表查询
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            merchant_name-商户名称，支持模糊查询,page_index-第几页,page_size-每页记录数
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
		return merchantService.getMerchantList(inMap);
	}

	/**
	 * 添加商户信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            merchant_name-商户名称,card_name-法人姓名,card_no-法人身份证号，first_amount-
	 *            首次充值金额,contact_number-法人联系电话
	 *            ,alarm_balance-预警余额,buss_license-营业执照,app_id-商户号,app_secret
	 *            -商户秘钥, push_url-推送地址
	 *            ,admin_name-管理员姓名,tel-手机号码,phone-联系电话，非必须,address-地址,
	 *            bank_name-银行开户行,
	 *            account_no-银行账户,account_name-账户名称,bill_type-对账模式
	 *            ，0-固定日期，1-固定周期,bill_cycle-具体账期 ,bill_start-开始对账日期
	 * @return merchant_id-商户id,state -响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addMerchant(ParaMap inMap) throws Exception {
		ParaMap resultMap = merchantService.addMerchant(inMap);
		if (resultMap.getInt(RespKey.STATE.getValue()) != RespState.SUCCESS.getValue()) {
			return resultMap;
		}
		ParaMap dataMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		inMap.put("merchant_id", dataMap.getString("merchant_id"));
		// 新增商户对账信息，不成功则回滚事务
		resultMap = merchantBillService.addMerchantBill(inMap);
		if (resultMap.getInt(RespKey.STATE.getValue()) != RespState.SUCCESS.getValue()) {
			DataSourceManager.rollback();
			return resultMap;
		}
		String tel = inMap.getString("tel");
		// 取手机号后六位为登录密码
		inMap.put("password", tel.substring(tel.length() - 6, tel.length()));
		// 添加商户登录权限信息，不成功则回滚事务
		AuthService authService = new AuthService();
		resultMap = authService.addMerchantAuth(inMap);
		if (resultMap.getInt(RespKey.STATE.getValue()) != RespState.SUCCESS.getValue()) {
			DataSourceManager.rollback();
			return resultMap;
		}
		// 发送短信信息
		SmsService smsService = new SmsService();
		smsService.notifyAddMerchant(inMap);
		return RespUtil.setResp(dataMap);
	}

	/**
	 * 商户信息详情查询
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
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
		if (StrUtils.isNull(inMap.getString("merchant_id")) && StrUtils.isNull(inMap.getString("merchant_name"))
				&& StrUtils.isNull(inMap.getString("app_id"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		ParaMap resultMap = merchantService.getMerchant(inMap);
		ParaMap outMap = RespUtil.setResp(resultMap);
		return outMap;
	}

	/**
	 * 商户信息更改
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            merchant_id-商户id,merchant_name-商户名称,card_name-法人姓名,card_no-
	 *            法人身份证号，first_amount-
	 *            首次充值金额,alarm_balance-预警余额,buss_license-营业执照
	 *            ,app_id-商户号,app_secret-商户秘钥, push_url-推送地址
	 *            ,admin_name-管理员姓名,tel-手机号码,phone-联系电话，非必须,address-地址,
	 *            bank_name-银行开户行,
	 *            account_no-银行账户,account_name-账户名称,bill_type-对账模式
	 *            ，0-固定日期，1-固定周期,bill_cycle-具体账期
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateMerchant(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("merchant_id", inMap.getString("merchant_id"));
		ParaMap merchantMap = merchantService.getMerchant(sendMap);
		if (merchantMap.isEmpty()) {
			return RespUtil.setResp(RespState.FAIL, "merchant.notexist");
		}
		// 修改商户信息
		merchantService.updateMerchant(inMap);
		merchantBillService.updateMerchantBill(inMap);

		// 添加商户登录权限信息，不成功则回滚事务
		AuthService authService = new AuthService();
		ParaMap resultMap = authService.updateMerchantAuth(inMap);
		if (resultMap.getInt(RespKey.STATE.getValue()) != RespState.SUCCESS.getValue()) {
			DataSourceManager.rollback();
			return resultMap;
		}

		// 商户修改了手机号码，管理员名称，则需要更改用户信息，修改手机号，需要发送短信
		sendMap.put("admin_name", inMap.getString("admin_name"));
		sendMap.put("old_tel", merchantMap.getString("tel"));
		boolean flag = false;
		if (!inMap.getString("admin_name").equals(merchantMap.getString("admin_name"))) {
			flag = true;
		}
		if (!inMap.getString("tel").equals(merchantMap.getString("tel"))) {
			String tel = inMap.getString("tel");
			sendMap.put("tel", tel);
			sendMap.put("password", tel.substring(tel.length() - 6, tel.length()));// 取手机号后六位为登录密码
			flag = true;
		}
		if (flag) {
			resultMap = authService.updateMerchantUser(sendMap);
			if (resultMap.getInt(RespKey.STATE.getValue()) != RespState.SUCCESS.getValue()) {
				DataSourceManager.rollback();
				return resultMap;
			}
			if (StrUtils.isNotNull(sendMap.getString("tel"))) {
				// 发送短信信息
				SmsService smsService = new SmsService();
				smsService.notifyUpdateMerchant(sendMap);
			}
		}
		return RespUtil.setResp();
	}

	/**
	 * 禁用/启用商户信息，关联禁用/启用商户用户
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            merchant_id-商户id,status-状态，1-启用,2-禁用
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap enableMerchant(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("merchant_id")) || StrUtils.isNull(inMap.getString("status"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		ParaMap merchantMap = merchantService.getMerchant(inMap);
		if (merchantMap.isEmpty()) {
			return RespUtil.setResp(RespState.FAIL, "merchant.notexist");
		}
		ParaMap resultMap = merchantService.enableMerchant(inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		// 禁用/启用用户
		UserService userService = new UserService();
		inMap.put("user_name", merchantMap.getString("tel"));
		ParaMap userMap = userService.getUser(inMap);
		inMap.put("status", this.statusConvert(inMap.getInt("status")));

		// 商户启用时，用户已经启用，则不处理，否则启用用户；商户禁用时，用户已经禁用，则不处理，否则禁用用户
		if (inMap.getInt("status") == userMap.getInt("status")) {
			return RespUtil.setResp();
		}
		inMap.put("user_id", userMap.getString("user_id"));
		userService.enableUser(inMap);
		return RespUtil.setResp();
	}

	/**
	 * 商户状态与用户状态的转化
	 */
	private int statusConvert(int status) {
		if (status == CenterConsts.MERCHANT_STATUS_DISABLED) {
			return CenterConsts.DISABLED;
		} else if (status == CenterConsts.MERCHANT_STATUS_NORMAL) {
			return CenterConsts.ENABLED;
		}
		return CenterConsts.DISABLED;
	}

	/**
	 * 商户余额更改
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            merchant_id-商户id,amount-金额,type-类型，1-扣费，2-充值
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateMerchantBalance(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("merchant_id")) || StrUtils.isNull(inMap.getString("amount"))
				|| StrUtils.isNull(inMap.getString("type"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		ParaMap resultMap = merchantService.updateMerchantBalance(inMap);
		return resultMap;
	}
}
