package com.center.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;

/**
 * 充值记录数据库操作功能类
 * 
 * @author 唐宗鸿
 * @date 20170503
 * @version 1.1.0
 */
public class RechargeDao extends BaseDataSetDao {

	/**
	 * 充值记录列表查询
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            merchant_name-商户名称，start_time-开始时间，end_time-结束时间
	 * @return 
	 *         recharge_id-充值记录id,merchant_id-商户id,merchant_name-商户名称,type-充值类型，1
	 *         -厂商充值，2-平台充值,amount-充值金额,createtime-创建时间
	 *         ,updatetime-更新时间,creator-操作员
	 */
	public ParaMap getRechargeList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("recharge", "getRechargeList");
		String dynamicSql = "";
		String merchantName = inMap.getString("merchant_name");
		if (StrUtils.isNotNull(merchantName)) {
			dynamicSql += " AND m.merchant_name LIKE ? ";
			sqlMap.addParam("%" + merchantName + "%");
		}
		String type = inMap.getString("type");
		if (StrUtils.isNotNull(type)) {
			dynamicSql += " AND r.type = ? ";
			sqlMap.addParam(type);
		}
		String startTime = inMap.getString("start_time");
		if (StrUtils.isNotNull(startTime)) {
			dynamicSql += " AND r.createtime >= ? ";
			sqlMap.addParam(startTime);
		}
		String endTime = inMap.getString("end_time");
		if (StrUtils.isNotNull(endTime)) {
			dynamicSql += " AND r.createtime <= ? ";
			sqlMap.addParam(endTime);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		if (inMap.getInt("is_page") == SqlConsts.NOT_PAGE) {
			return query(sqlMap);
		}
		sqlMap.put("pageIndex", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}

	/**
	 * 添加充值记录
	 * 
	 * @author 唐宗鸿
	 * @date 20170503
	 * @param inMap
	 *            recharge_id-充值记录id,merchant_id-商户id,amount-充值金额,type-充值类型，
	 *            status-充值状态,user_id-操作员id
	 * @return
	 */
	public ParaMap addRecharge(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("recharge", "addRecharge");
		sqlMap.addParam(inMap.getString("recharge_id"));
		sqlMap.addParam(inMap.getString("merchant_id"));
		sqlMap.addParam(inMap.getString("provider_id"));
		sqlMap.addParam(inMap.getString("channel"));
		sqlMap.addParam(inMap.getString("amount"));
		sqlMap.addParam(inMap.getString("time"));
		sqlMap.addParam(inMap.getString("status"));
		sqlMap.addParam(inMap.getString("type"));
		sqlMap.addParam(inMap.getString("user_id"));
		ParaMap outMap = insert(sqlMap);
		return outMap;
	}
}
