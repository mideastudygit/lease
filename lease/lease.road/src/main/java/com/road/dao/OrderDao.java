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

public class OrderDao extends BaseDataSetDao {

	public ParaMap getOrderList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "getOrderList");
		StringBuffer dynamicSql = new StringBuffer();
		String startTime = inMap.getString("start_time");
		if (StrUtils.isNotNull(startTime)) {
			dynamicSql.append(" AND createtime >= ?");
			sqlMap.addParam(startTime);
		}
		String endTime = inMap.getString("end_time");
		if (StrUtils.isNotNull(endTime)) {
			dynamicSql.append(" AND createtime <= ?");
			sqlMap.addParam(endTime);
		}
		String billTime = inMap.getString("bill_time");// 对账以结束时间为准
		if (StrUtils.isNotNull(billTime)) {
			dynamicSql.append(" AND end_park_time >= ? AND end_park_time <= ?");
			sqlMap.addParam(DateUtil.getStartTimeOfDay(inMap.getLong("bill_time")));
			sqlMap.addParam(DateUtil.getEndTimeOfDay(inMap.getLong("bill_time")));
		}
		String merchantId = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchantId)) {
			dynamicSql.append(" AND merchant_id = ?");
			sqlMap.addParam(merchantId);
		}
		String status = inMap.getString("status");
		if (StrUtils.isNotNull(status)) {
			dynamicSql.append(" AND status= ? ");
			sqlMap.addParam(status);
		}
		String keyword = inMap.getString("keyword");
		if (StrUtils.isNotNull(keyword)) {
			dynamicSql.append(" AND (berth_code = ? OR car_plate LIKE ? OR section LIKE ? ) ");
			sqlMap.addParam(keyword);
			sqlMap.addParam("%" + keyword + "%");
			sqlMap.addParam("%" + keyword + "%");
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
	 * 查询某个订单详细数据
	 */
	public ParaMap getOrder(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "getOrder");
		String dynamicSql = "";
		String orderId = inMap.getString("order_id");
		if (StrUtils.isNotNull(orderId)) {
			dynamicSql += "and id = ?";
			sqlMap.addParam(orderId);
		}
		String providerOrderId = inMap.getString("provider_order_id");
		if (StrUtils.isNotNull(providerOrderId)) {
			dynamicSql += "and provider_order_id = ?";
			sqlMap.addParam(providerOrderId);
		}
		String berthCode = inMap.getString("berth_code");
		if (StrUtils.isNotNull(berthCode)) {
			dynamicSql += "and berth_code = ?";
			sqlMap.addParam(berthCode);
		}
		String status = inMap.getString("status");
		if (StrUtils.isNotNull(status)) {
			dynamicSql += "and status = ?";
			sqlMap.addParam(status);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 添加订单信息
	 * 
	 */
	public ParaMap addOrder(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "addOrder");
		sqlMap.addParam(inMap.getString("order_id"));
		sqlMap.addParam(inMap.getString("ticket"));
		sqlMap.addParam(inMap.getString("berth_code"));
		sqlMap.addParam(inMap.getString("car_plate"));
		sqlMap.addParam(inMap.getString("pay_type"));
		sqlMap.addParam(inMap.getString("provider_order_id"));
		sqlMap.addParam(inMap.getString("merchant_id"));
		sqlMap.addParam(inMap.getString("provider_id"));
		ParaMap outMap = insert(sqlMap);
		return outMap;
	}

	/**
	 * 批量修改订单信息
	 */
	public ParaMap batchUpdateOrder(List<ParaMap> orderList) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "batchUpdateRoadOrder");
		for (ParaMap order : orderList) {
			List<Object> list = new ArrayList<Object>();
			list.add(order.getString("berth_code"));
			list.add(order.getString("canton"));
			list.add(order.getString("area"));
			list.add(order.getString("section"));
			list.add(order.getString("should_pay"));
			list.add(order.getString("actual_pay"));
			list.add(order.getString("start_park_time"));
			list.add(order.getString("end_park_time"));
			list.add(order.getString("start_time"));
			list.add(order.getString("end_time"));
			list.add(order.getString("apply_duration"));
			list.add(order.getString("actual_duration"));
			list.add(DateUtils.nowTime());
			list.add(order.getString("provider_order_id"));
			sqlMap.addBatchParam(list);
		}
		ParaMap outMap = batch(sqlMap);
		return outMap;
	}

	/**
	 * 修改订单信息
	 */
	public ParaMap updateOrder(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "updateOrder");
		sqlMap.addParam(DateUtils.nowTime());

		StringBuffer dynamicSql = new StringBuffer();
		String actualPay = inMap.getString("actual_pay");
		if (StrUtils.isNotNull(actualPay)) {
			dynamicSql.append(",actual_pay = ?");
			sqlMap.addParam(actualPay);
		}
		String actualDuration = inMap.getString("actual_duration");
		if (StrUtils.isNotNull(actualDuration)) {
			dynamicSql.append(",actual_duration = ?");
			sqlMap.addParam(actualDuration);
		}
		String applyDuration = inMap.getString("apply_duration");
		if (StrUtils.isNotNull(applyDuration)) {
			dynamicSql.append(",apply_duration = ?");
			sqlMap.addParam(applyDuration);
		}
		String berthCode = inMap.getString("berth_code");
		if (StrUtils.isNotNull(berthCode)) {
			dynamicSql.append(",berth_code = ?");
			sqlMap.addParam(berthCode);
		}
		String canton = inMap.getString("canton");
		if (StrUtils.isNotNull(canton)) {
			dynamicSql.append(",canton = ?");
			sqlMap.addParam(canton);
		}
		String section = inMap.getString("section");
		if (StrUtils.isNotNull(section)) {
			dynamicSql.append(",section = ?");
			sqlMap.addParam(section);
		}
		String area = inMap.getString("area");
		if (StrUtils.isNotNull(area)) {
			dynamicSql.append(",area = ?");
			sqlMap.addParam(area);
		}
		String startParkTime = inMap.getString("start_park_time");
		if (StrUtils.isNotNull(startParkTime)) {
			dynamicSql.append(",start_park_time = ?");
			sqlMap.addParam(startParkTime);
		}
		String endParkTime = inMap.getString("end_park_time");
		if (StrUtils.isNotNull(endParkTime)) {
			dynamicSql.append(",end_park_time = ?");
			sqlMap.addParam(endParkTime);
		}
		String startTime = inMap.getString("start_time");
		if (StrUtils.isNotNull(startTime)) {
			dynamicSql.append(",start_time = ?");
			sqlMap.addParam(startTime);
		}
		String endTime = inMap.getString("end_time");
		if (StrUtils.isNotNull(endTime)) {
			dynamicSql.append(",end_time = ?");
			sqlMap.addParam(endTime);
		}
		String shouldPay = inMap.getString("should_pay");
		if (StrUtils.isNotNull(shouldPay)) {
			dynamicSql.append(",should_pay = ?");
			sqlMap.addParam(shouldPay);
		}
		String status = inMap.getString("status");
		if (StrUtils.isNotNull(status)) {
			dynamicSql.append(",status = ?");
			sqlMap.addParam(status);
		}
		sqlMap.addParam(inMap.getString("provider_order_id"));
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql.toString());
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	public ParaMap getOrderList(List<ParaMap> inList) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "getOrderList");
		StringBuffer dynamicSql = new StringBuffer(" AND provider_order_id IN ( ");
		Iterator<ParaMap> it = inList.iterator();
		while (it.hasNext()) {
			dynamicSql.append(" ?,");
			sqlMap.addParam(it.next().getString("provider_order_id"));
		}
		dynamicSql.replace(dynamicSql.length() - 1, dynamicSql.length(), ")");
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql.toString());
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

}
