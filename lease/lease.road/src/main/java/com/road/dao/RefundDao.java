package com.road.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;
import com.common.util.DateUtil;

public class RefundDao extends BaseDataSetDao {

	/**
	 * 获取退款订单列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170526
	 * @param inMap
	 * 
	 * @return
	 */
	public ParaMap getRefundOrderList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("refund", "getRefundOrderList");
		StringBuffer dynamicSql = new StringBuffer();
		String merchantId = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchantId)) {
			dynamicSql.append(" AND r.merchant_id = ?");
			sqlMap.addParam(merchantId);
		}
		String orderId = inMap.getString("order_id");
		if (StrUtils.isNotNull(orderId)) {
			dynamicSql.append(" AND r.order_id = ?");
			sqlMap.addParam(orderId);
		}
		String providerOrderId = inMap.getString("provider_order_id");
		if (StrUtils.isNotNull(providerOrderId)) {
			dynamicSql.append(" AND r.provider_order_id = ?");
			sqlMap.addParam(providerOrderId);
		}
		String startTime = inMap.getString("start_time");
		if (StrUtils.isNotNull(startTime)) {
			dynamicSql.append(" AND r.createtime >= ?");
			sqlMap.addParam(startTime);
		}
		String endTime = inMap.getString("end_time");
		if (StrUtils.isNotNull(endTime)) {
			dynamicSql.append(" AND r.createtime <= ?");
			sqlMap.addParam(endTime);
		}
		String billTime = inMap.getString("bill_time");// 对账以结束时间为准
		if (StrUtils.isNotNull(billTime)) {
			dynamicSql.append(" AND r.refund_time >= ? AND r.refund_time <= ?");
			sqlMap.addParam(DateUtil.getStartTimeOfDay(inMap.getLong("bill_time")));
			sqlMap.addParam(DateUtil.getEndTimeOfDay(inMap.getLong("bill_time")));
		}
		String refundStatus = inMap.getString("refund_status");
		if (StrUtils.isNotNull(refundStatus)) {
			dynamicSql.append(" AND r.refund_status = ?");
			sqlMap.addParam(refundStatus);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql.toString());
		if (inMap.getInt("is_page") == SqlConsts.NOT_PAGE) {
			return query(sqlMap);
		}
		sqlMap.put("pageIndex", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}

	/**
	 * 批量添加退费订单
	 */
	public ParaMap batchAddRefundOrder(List<ParaMap> refundOrderList) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("refund", "addRefundOrder");
		for (ParaMap refundMap : refundOrderList) {
			List<Object> list = new ArrayList<Object>();
			list.add(refundMap.getString("refund_id"));
			list.add(refundMap.getString("order_id"));
			list.add(refundMap.getString("merchant_id"));
			list.add(refundMap.getString("refund_amount"));
			list.add(refundMap.getString("refund_status"));
			list.add(refundMap.getString("provider_refund_id"));
			list.add(refundMap.getString("provider_refund_code"));
			list.add(refundMap.getString("provider_order_id"));
			list.add(DateUtils.nowTime());
			list.add(DateUtils.nowTime());
			sqlMap.addBatchParam(list);
		}
		ParaMap outMap = batch(sqlMap);
		return outMap;
	}

	/**
	 * 批量查询退费记录
	 */
	public ParaMap getRefundOrderList(List<ParaMap> inList) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("refund", "batchGetRefundOrder");
		StringBuffer dynamicSql = new StringBuffer(" AND provider_refund_code IN ( ");
		Iterator<ParaMap> it = inList.iterator();
		while (it.hasNext()) {
			dynamicSql.append(" ?,");
			sqlMap.addParam(it.next().getString("provider_refund_code"));
		}
		dynamicSql.replace(dynamicSql.length() - 1, dynamicSql.length(), ")");
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql.toString());
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 获取退费详情
	 */
	public ParaMap getRefundOrder(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("refund", "getRefundOrder");
		String dynamicSql = "";
		String refundId = inMap.getString("refund_id");
		if (StrUtils.isNull(refundId)) {
			dynamicSql += " AND id = ? ";
			sqlMap.addParam(refundId);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 修改退费信息表
	 */
	public ParaMap updateRefundOrderStatus(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("refund", "updateRefundOrderStatus");
		sqlMap.addParam(inMap.getString("status"));
		sqlMap.addParam(DateUtils.nowTime());
		sqlMap.addParam(DateUtils.nowTime());
		sqlMap.addParam(inMap.getString("refund_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}
}
