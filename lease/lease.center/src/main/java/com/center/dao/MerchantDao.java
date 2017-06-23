package com.center.dao;

import java.util.Iterator;
import java.util.List;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.consts.CenterConsts;
import com.common.consts.SqlConsts;

/**
 * 商户数据操作服务
 * 
 * @author 唐宗鸿
 * @date 20170503
 * @version 1.1.0
 */
public class MerchantDao extends BaseDataSetDao {

	/**
	 * 商户列表查询
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            merchant_name-商户名称，支持模糊查询,page_index-第几页,page_size-每页记录数
	 * @return merchant_id-商户id,merchant_name-商户名称,card_name-法人姓名,card_no-
	 *         法人身份证号， first_amount
	 *         -首次充值金额,alarm_balance-预警余额,balance-余额,buss_license-营业执照
	 *         ,app_id-商户号,app_secret-商户秘钥, push_url
	 *         -推送地址,admin_name-管理员姓名,tel-手机号码,phone-联系电话，非必须,address-地址,
	 *         bank_name-银行开户行, account_no-银行账户,account_name-账户名称,bill_type-对账模式
	 *         ，0-固定日期，1-固定周期 ,bill_cycle-具体账期,bill_start-开始对账日期,status-商户状态，0-
	 *         待审核 1-正常 2-已禁用,enable_time-启用时间 ,createtime-创建时间, updatetime-更新时间
	 */
	public ParaMap getMerchantList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("merchant", "getMerchantList");
		String dynamicSql = "";
		String merchantName = inMap.getString("merchant_name");
		if (StrUtils.isNotNull(merchantName)) {
			dynamicSql += " AND m.merchant_name LIKE ? ";
			sqlMap.addParam("%" + merchantName + "%");
		}
		if (inMap.getInt("is_page") == SqlConsts.NOT_PAGE) {
			dynamicSql += " AND m.status = ? ";
			sqlMap.addParam(CenterConsts.MERCHANT_STATUS_NORMAL);
			sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
			return query(sqlMap);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		sqlMap.put("pageIndex", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}

	/**
	 * 商户列表查询
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param idList
	 *            商户id集合
	 * @return merchant_id-商户id,merchant_name-商户名称,card_name-法人姓名,card_no-
	 *         法人身份证号， first_amount
	 *         -首次充值金额,alarm_balance-预警余额,balance-余额,buss_license-营业执照
	 *         ,app_id-商户号,app_secret-商户秘钥, push_url
	 *         -推送地址,admin_name-管理员姓名,tel-手机号码,phone-联系电话，非必须,address-地址,
	 *         bank_name-银行开户行, account_no-银行账户,account_name-账户名称,bill_type-对账模式
	 *         ，0-固定日期，1-固定周期 ,bill_cycle-具体账期,bill_start-开始对账日期,status-商户状态，0-
	 *         待审核 1-正常 2-已禁用,enable_time-启用时间 ,createtime-创建时间, updatetime-更新时间
	 */
	public ParaMap getMerchantList(List<String> idList) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("merchant", "getMerchantList");
		StringBuffer dynamicSql = new StringBuffer(" AND m.id IN ( ");
		Iterator<String> it = idList.iterator();
		while (it.hasNext()) {
			dynamicSql.append(" ?,");
			sqlMap.addParam(it.next());
		}
		dynamicSql.replace(dynamicSql.length() - 1, dynamicSql.length(), ")");
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql.toString());
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 添加商户信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            merchant_id-商户id,merchant_name-商户名称,card_name-法人姓名,card_no-
	 *            法人身份证号，first_amount- 首次充值金额
	 *            ,alarm_balance-预警余额,buss_license-营业执照,app_id-商户号,app_secret
	 *            -商户秘钥, push_url-推送地址
	 *            ,admin_name-管理员姓名,tel-手机号码,phone-联系电话，非必须,address-地址
	 * @return
	 */
	public ParaMap addMerchant(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("merchant", "addMerchant");
		sqlMap.addParam(inMap.getString("merchant_id"));
		sqlMap.addParam(inMap.getString("merchant_name"));
		sqlMap.addParam(inMap.getString("card_name"));
		sqlMap.addParam(inMap.getString("card_no"));
		sqlMap.addParam(inMap.getString("contact_number"));
		sqlMap.addParam(inMap.getString("first_amount"));
		sqlMap.addParam(inMap.getString("alarm_balance"));
		sqlMap.addParam(inMap.getInt("balance"));
		sqlMap.addParam(inMap.getString("buss_license"));
		sqlMap.addParam(inMap.getString("app_id"));
		sqlMap.addParam(inMap.getString("app_secret"));
		sqlMap.addParam(inMap.getString("push_url"));
		sqlMap.addParam(inMap.getString("admin_name"));
		sqlMap.addParam(inMap.getString("tel"));
		sqlMap.addParam(inMap.getString("phone"));
		sqlMap.addParam(inMap.getString("address"));
		sqlMap.addParam(DateUtils.nowTime());
		ParaMap outMap = insert(sqlMap);
		return outMap;
	}

	/**
	 * 商户详情查询
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
	 *         待审核 1-正常 2-已禁用,enable_time-启用时间 ,createtime-创建时间, updatetime-更新时间
	 */
	public ParaMap getMerchant(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("merchant", "getMerchant");
		String dynamicSql = "";
		String merchantId = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchantId)) {
			dynamicSql += " AND m.id = ?";
			sqlMap.addParam(merchantId);
		}
		String merchantName = inMap.getString("merchant_name");
		if (StrUtils.isNotNull(merchantName)) {
			dynamicSql += " AND m.merchant_name = ?";
			sqlMap.addParam(merchantName);
		}
		String appId = inMap.getString("app_id");
		if (StrUtils.isNotNull(appId)) {
			dynamicSql += " AND m.app_id = ?";
			sqlMap.addParam(appId);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 商户信息校验
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            merchant_id-商户id,merchant_name-商户名称,app_id-商户号
	 * @return num,0-表示不存在，大于0已经存在
	 * 
	 */
	public int checkMerchant(String merchantId, String merchantName, String appId) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("merchant", "checkMerchant");
		String dynamicSql = "";
		if (StrUtils.isNotNull(merchantId)) {
			dynamicSql += " AND id != ?";
			sqlMap.addParam(merchantId);
		}
		if (StrUtils.isNotNull(merchantName)) {
			dynamicSql += " AND merchant_name = ?";
			sqlMap.addParam(merchantName);
		}
		if (StrUtils.isNotNull(appId)) {
			dynamicSql += " AND app_id = ?";
			sqlMap.addParam(appId);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap.getRecordInt(0, "num");
	}

	/**
	 * 商户信息更改
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            merchant_id-商户id,merchant_name-商户名称,card_name-法人姓名,card_no-
	 *            法人身份证号，alarm_balance-预警余额,buss_license-营业执照
	 *            ,app_id-商户号,app_secret-商户秘钥, push_url-推送地址
	 *            ,admin_name-管理员姓名,tel-手机号码,phone-联系电话，非必须,address-地址
	 * @return
	 */
	public ParaMap updateMerchant(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("merchant", "updateMerchant");
		sqlMap.addParam(inMap.getString("merchant_name"));
		sqlMap.addParam(inMap.getString("card_name"));
		sqlMap.addParam(inMap.getString("card_no"));
		sqlMap.addParam(inMap.getString("contact_number"));
		sqlMap.addParam(inMap.getString("alarm_balance"));
		sqlMap.addParam(inMap.getString("buss_license"));
		sqlMap.addParam(inMap.getString("app_id"));
		sqlMap.addParam(inMap.getString("app_secret"));
		sqlMap.addParam(inMap.getString("push_url"));
		sqlMap.addParam(inMap.getString("admin_name"));
		sqlMap.addParam(inMap.getString("tel"));
		sqlMap.addParam(inMap.getString("phone"));
		sqlMap.addParam(inMap.getString("address"));
		sqlMap.addParam(DateUtils.nowTime());
		sqlMap.addParam(inMap.getString("merchant_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 禁用/启用商户
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param merchant_id
	 *            商户id
	 * @param status
	 *            状态，1-启用,2-禁用
	 * @return
	 */
	public ParaMap enableMerchant(String merchantId, Integer status) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("merchant", "enableMerchant");
		sqlMap.addParam(status);
		sqlMap.addParam(DateUtils.nowTime());
		sqlMap.addParam(merchantId);
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 商户余额更改
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            merchant_id-商户id,amount-金额,type-类型，1-扣费，2-充值
	 * @return
	 */
	public ParaMap updateMerchantBalance(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("merchant", "updateMerchantBalance");
		sqlMap.addParam(inMap.getDouble("amount"));
		sqlMap.addParam(DateUtils.nowTime());
		sqlMap.addParam(inMap.getString("merchant_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}
}
