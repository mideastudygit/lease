package com.road.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.DataDict.PageStatus;
import com.common.consts.SqlConsts;
import com.common.util.DateUtil;

public class ArrearsDao extends BaseDataSetDao {

	/**
	 * 批量添加补缴订单
	 */
	public ParaMap batchAddArrearsOrder(List<ParaMap> arrearsOrderList) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("arrears", "addArrearsOrder");
		for (ParaMap arrearsMap : arrearsOrderList) {
			List<Object> list = new ArrayList<Object>();
			list.add(arrearsMap.getString("arrears_id"));
			list.add(arrearsMap.getString("order_id"));
			list.add(arrearsMap.getString("merchant_id"));
			list.add(arrearsMap.getString("arrears_amount"));
			list.add(arrearsMap.getString("arrears_status"));
			list.add(arrearsMap.getString("provider_order_id"));
			list.add(arrearsMap.getString("provider_arrears_id"));
			list.add(arrearsMap.getString("provider_arrears_code"));
			list.add(arrearsMap.getString("arrears_time"));
			list.add(DateUtils.nowTime());
			list.add(DateUtils.nowTime());
			sqlMap.addBatchParam(list);
		}
		ParaMap outMap = batch(sqlMap);
		return outMap;
	}

	/**
	 * 获取补缴单列表
	 */
	public ParaMap getArrearsOrderList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("arrears", "getArrearsList");
		StringBuffer dynamicSql = new StringBuffer();
		String merchantId = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchantId)) {
			dynamicSql.append(" AND o.merchant_id = ?");
			sqlMap.addParam(merchantId.trim());
		}
		String orderId = inMap.getString("order_id");
		if (StrUtils.isNotNull(orderId)) {
			dynamicSql.append(" AND r.order_id  = ?");
			sqlMap.addParam(orderId.trim());
		}
		String providerOrderId = inMap.getString("provider_order_id");
		if (StrUtils.isNotNull(providerOrderId)) {
			dynamicSql.append(" AND o.provider_order_id  = ?");
			sqlMap.addParam(providerOrderId.trim());
		}
		String startTime = inMap.getString("start_time");
		if (StrUtils.isNotNull(startTime)) {
			dynamicSql.append(" AND r.createtime >= ?");
			sqlMap.addParam(startTime.trim());
		}
		String endTime = inMap.getString("end_time");
		if (StrUtils.isNotNull(endTime)) {
			dynamicSql.append(" AND r.createtime <= ?");
			sqlMap.addParam(endTime.trim());
		}
		String arrearsStatus = inMap.getString("arrears_status");
		if (StrUtils.isNotNull(arrearsStatus)) {
			dynamicSql.append(" AND r.arrears_status = ? ");
			sqlMap.addParam(arrearsStatus.trim());
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql.toString());
		if (inMap.getInt("is_page") == PageStatus.NOTPAGE.getValue()) {
			return query(sqlMap);
		}
		sqlMap.put("pageIndex", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}

	/**
	 * 获取欠费详情
	 */
	public ParaMap getArrearsOrder(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("arrears", "getArrearsOrder");
		String dynamicSql = "";
		String arrearsId = inMap.getString("arrears_id");
		if (StrUtils.isNotNull(arrearsId)) {
			dynamicSql += " AND r.id = ?";
			sqlMap.addParam(arrearsId);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 修改欠费状态
	 */
	public ParaMap updateArrearsOrderStatus(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("arrears", "updateArrearsOrderStatus");
		sqlMap.addParam(inMap.getString("arrears_status"));
		sqlMap.addParam(DateUtil.nowTime());
		sqlMap.addParam(DateUtil.nowTime());
		sqlMap.addParam(inMap.getString("arrears_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 批量查询欠费列表
	 */
	public ParaMap getArrearsOrderList(List<ParaMap> inList) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("arrears", "getArrearsOrder");
		StringBuffer dynamicSql = new StringBuffer(" AND r.provider_arrears_id IN ( ");
		Iterator<ParaMap> it = inList.iterator();
		while (it.hasNext()) {
			dynamicSql.append(" ?,");
			sqlMap.addParam(it.next().getString("provider_arrears_id"));
		}
		dynamicSql.replace(dynamicSql.length() - 1, dynamicSql.length(), ")");
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql.toString());
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

}
