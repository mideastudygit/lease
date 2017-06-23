package com.road.dao;

import java.util.ArrayList;
import java.util.List;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.DateUtils;
import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;

public class SystemBillDao extends BaseDataSetDao {

	public ParaMap addSystemBill(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("bill", "addSystemBill");
		sqlMap.addParam(inMap.getString("bill_id"));
		sqlMap.addParam(inMap.getString("bill_date"));
		sqlMap.addParam(inMap.getString("order_count"));
		sqlMap.addParam(inMap.getString("refund_count"));
		sqlMap.addParam(inMap.getString("order_amount"));
		sqlMap.addParam(inMap.getString("refund_amount"));
		sqlMap.addParam(inMap.getString("merchant_id"));
		ParaMap outMap = insert(sqlMap);
		return outMap;
	}

	public ParaMap addOrderBillData(List<ParaMap> orderList, String billId) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("bill", "addOrderBillData");
		for (int i = 0; i < orderList.size(); i++) {
			List<Object> list = new ArrayList<Object>();
			list.add(IDGenerator.newGUID());
			list.add(billId);
			list.add(orderList.get(i).getString("berth_code"));
			list.add(orderList.get(i).getString("car_plate"));
			list.add(orderList.get(i).getString("area"));
			list.add(orderList.get(i).getString("canton"));
			list.add(orderList.get(i).getString("section"));
			list.add(orderList.get(i).getString("start_park_time"));
			list.add(orderList.get(i).getString("end_park_time"));
			list.add(orderList.get(i).getString("should_pay"));
			list.add(orderList.get(i).getString("actual_duration"));
			list.add(orderList.get(i).getString("actual_pay"));
			list.add(orderList.get(i).getString("provider_order_id"));
			list.add(orderList.get(i).getString("order_id"));
			list.add(orderList.get(i).getString("merchant_id"));
			list.add(orderList.get(i).getString("create_time"));// 订单创建时间
			list.add(orderList.get(i).getString("end_park_time"));// 订单结束时间
			list.add(DateUtils.nowTime());
			list.add(DateUtils.nowTime());
			sqlMap.addBatchParam(list);
		}
		ParaMap outMap = batch(sqlMap);
		return outMap;
	}

	public ParaMap addRefundBillData(List<ParaMap> refundList, String billId) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("bill", "addRefundBillData");
		for (int i = 0; i < refundList.size(); i++) {
			List<Object> list = new ArrayList<Object>();
			list.add(IDGenerator.newGUID());
			list.add(billId);
			list.add(refundList.get(i).getString("berth_code"));
			list.add(refundList.get(i).getString("car_plate"));
			list.add(refundList.get(i).getString("area"));
			list.add(refundList.get(i).getString("canton"));
			list.add(refundList.get(i).getString("section"));
			list.add(refundList.get(i).getString("start_park_time"));
			list.add(refundList.get(i).getString("end_park_time"));
			list.add(refundList.get(i).getString("should_pay"));
			list.add(refundList.get(i).getString("actual_duration"));
			list.add(refundList.get(i).getString("actual_pay"));
			list.add(refundList.get(i).getString("provider_order_id"));
			list.add(refundList.get(i).getString("refund_amount"));
			list.add(refundList.get(i).getString("order_id"));
			list.add(refundList.get(i).getString("refund_id"));
			list.add(refundList.get(i).getString("merchant_id"));
			list.add(refundList.get(i).getString("create_time"));// 退费单创建时间
			list.add(refundList.get(i).getString("refund_time"));// 退费时间
			list.add(DateUtils.nowTime());
			list.add(DateUtils.nowTime());
			sqlMap.addBatchParam(list);
		}
		ParaMap outMap = batch(sqlMap);
		return outMap;
	}

	public ParaMap getSystemBillList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("bill", "getSystemBillList");
		String dynamicSql = "";
		String merchantId = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchantId)) {
			dynamicSql += " AND merchant_id = ?";
			sqlMap.addParam(merchantId);
		}
		String startDate = inMap.getString("start_date");
		if (StrUtils.isNotNull(startDate)) {
			dynamicSql += " AND bill_date >= ?";
			sqlMap.addParam(startDate);
		}
		String endDate = inMap.getString("end_date");
		if (StrUtils.isNotNull(endDate)) {
			dynamicSql += " AND bill_date <= ?";
			sqlMap.addParam(endDate);
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

	public ParaMap getSystemBill(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("bill", "getSystemBill");
		String dynamicSql = "";
		String merchantId = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchantId)) {
			dynamicSql += " AND merchant_id = ?";
			sqlMap.addParam(merchantId);
		}
		String billDate = inMap.getString("bill_date");
		if (StrUtils.isNotNull(billDate)) {
			dynamicSql += " AND bill_date = ?";
			sqlMap.addParam(billDate);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	public ParaMap getBillDataList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("bill", "getBillDataList");
		StringBuffer dynamicSql = new StringBuffer();
		String merchantId = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchantId)) {
			dynamicSql.append(" AND merchant_id = ?");
			sqlMap.addParam(merchantId);
		}
		List<String> billList = inMap.getList("bill_list");
		if (billList.size() > 0) {
			dynamicSql.append(" AND bill_id IN ( ");
		}
		for (int i = 0; i < billList.size(); i++) {
			dynamicSql.append("?,");
			sqlMap.addParam(billList.get(i));
		}
		if (billList.size() > 0) {
			dynamicSql.replace(dynamicSql.length() - 1, dynamicSql.length(), ")");
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

	public ParaMap delBillData(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("bill", "delBillData");
		sqlMap.addParam(inMap.getString("bill_id"));
		sqlMap.addParam(inMap.getString("merchant_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	public ParaMap updateSystemBill(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("bill", "updateSystemBill");
		sqlMap.addParam(inMap.getString("order_count"));
		sqlMap.addParam(inMap.getString("refund_count"));
		sqlMap.addParam(inMap.getString("order_amount"));
		sqlMap.addParam(inMap.getString("refund_amount"));
		sqlMap.addParam(DateUtils.nowTime());
		sqlMap.addParam(inMap.getString("merchant_id"));
		sqlMap.addParam(inMap.getString("bill_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}
}
