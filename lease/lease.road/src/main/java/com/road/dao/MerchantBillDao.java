package com.road.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;

public class MerchantBillDao extends BaseDataSetDao {

	public ParaMap getMerchantBillList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("bill", "getMerchantBillList");
		StringBuffer dynamicSql = new StringBuffer();
		String startTime = inMap.getString("start_time");
		if (StrUtils.isNotNull(startTime)) {
			dynamicSql.append(" AND start_time >= ?");
			sqlMap.addParam(startTime);
		}
		String endTime = inMap.getString("end_time");
		if (StrUtils.isNotNull(endTime)) {
			dynamicSql.append(" AND end_time <= ?");
			sqlMap.addParam(endTime);
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
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql.toString());
		if (inMap.getInt("is_page") == SqlConsts.NOT_PAGE) {
			return query(sqlMap);
		}
		sqlMap.put("pageIndex", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}

	public ParaMap getMerchantBill(String billId) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("bill", "getMerchantBill");
		sqlMap.addParam(billId);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	public ParaMap updateMerchantBill(String billId, Integer status) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("bill", "updateMerchantBillStatus");
		sqlMap.addParam(status);
		sqlMap.addParam(billId);
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	public ParaMap addMerchantBill(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("bill", "addMerchantBill");
		sqlMap.addParam(inMap.getString("bill_id"));
		sqlMap.addParam(inMap.getString("bill_date"));
		sqlMap.addParam(inMap.getString("start_date"));
		sqlMap.addParam(inMap.getString("end_date"));
		sqlMap.addParam(inMap.getString("order_count"));
		sqlMap.addParam(inMap.getString("refund_count"));
		sqlMap.addParam(inMap.getString("order_amount"));
		sqlMap.addParam(inMap.getString("refund_amount"));
		sqlMap.addParam(inMap.getString("merchant_id"));
		ParaMap outMap = insert(sqlMap);
		return outMap;
	}

	public ParaMap updateMerchantBill(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("bill", "updateMerchantBill");
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
