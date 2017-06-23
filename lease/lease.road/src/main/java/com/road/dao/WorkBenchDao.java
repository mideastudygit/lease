package com.road.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;
import com.common.util.DateUtil;

public class WorkBenchDao extends BaseDataSetDao {

	/**
	 * 获取路边订单累计指标
	 */
	public ParaMap getOrderTotal(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("workbench", "getOrderTotal");
		String dynamicSql = "";
		String merchantId = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchantId)) {
			dynamicSql += " AND merchant_id = ? ";
			sqlMap.addParam(merchantId);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 获取路边当月订单指标
	 */
	public ParaMap getOrderMonthTotal(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("workbench", "getOrderTotal");
		String dynamicSql = "";
		String merchantId = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchantId)) {
			dynamicSql += "AND merchant_id = ?";
			sqlMap.addParam(merchantId);
		}
		dynamicSql += " AND createtime >= ? AND createtime <= ?";
		sqlMap.addParam(inMap.getString("start_time"));
		sqlMap.addParam(inMap.getString("end_time"));
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 获取路边当天指标
	 */
	public ParaMap getOrderTodayTotal(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("workbench", "getOrderTotal");
		String dynamicSql = "";
		String merchantId = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchantId)) {
			dynamicSql += " AND merchant_id = ? ";
			sqlMap.addParam(merchantId);
		}
		dynamicSql += " AND createtime >= ? AND createtime <= ?";
		sqlMap.addParam(DateUtil.getTodayStartTime());
		sqlMap.addParam(DateUtil.getTodayEndTime());
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 统计路段收费
	 */
	public ParaMap getOrderFeeTop(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("workbench", "getOrderFeeTop");
		String dynamicSql = "";
		dynamicSql += " AND createtime >= ? AND createtime <= ?";
		sqlMap.addParam(inMap.getString("start_time"));
		sqlMap.addParam(inMap.getString("end_time"));
		String merchantId = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchantId)) {
			dynamicSql += "AND merchant_id = ? ";
			sqlMap.addParam(merchantId);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 获取订单停车次数和收费数据集合
	 */
	public ParaMap getOrderAnalysis(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("workbench", "getOrderAnalysis");
		String dynamicSql = "";
		String merchantId = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchantId)) {
			dynamicSql += " and merchant_id = ? ";
			sqlMap.addParam(merchantId);
		}
		dynamicSql += " AND createtime >= ? AND createtime <= ?";
		sqlMap.addParam(inMap.getString("start_time"));
		sqlMap.addParam(inMap.getString("end_time"));
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql.toString());
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

}
