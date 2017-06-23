package com.open.control;

import java.util.ArrayList;
import java.util.List;

import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.base.web.AppConfig;
import com.common.conf.MsgConfig;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.consts.SqlConsts;
import com.common.route.RouteManager;
import com.common.util.DateUtil;
import com.common.util.RespUtil;
import com.open.consts.OpenConsts;

public class BillControl {

	public ParaMap getBillList(ParaMap inMap) throws Exception {
		int pageIndex = inMap.getInt("page_index", SqlConsts.PAGE_INDEX);
		if (pageIndex < 1) {
			return RespUtil.setResp(RespState.FAIL, "param.error");
		}
		int pageSize = inMap.getInt("page_size", SqlConsts.PAGE_SIZE);
		if (pageSize > OpenConsts.MAX_QUERY_COUNT) {
			return RespUtil.setResp(RespState.FAIL, "param.error");
		}
		if (StrUtils.isNull(inMap.getString("start_time"))) {
			inMap.put("start_time", DateUtil.prevMonth(AppConfig.getIntPro("default.delay.month")).getTime()); // 默认当天前推一个月时间
		}
		if (StrUtils.isNull(inMap.getString("end_time"))) {
			inMap.put("end_time", DateUtils.nowTime());
		}

		// 校验时间查询的时间跨度，不能超出一年
		int periodYear = AppConfig.getIntPro("year.period.max");
		if (DateUtil.periodYearDate(inMap.getLong("start_time"), inMap.getLong("end_time")) > periodYear) {
			return RespUtil.setResp(RespState.FAIL, MsgConfig.get("invalid.year.period", periodYear));
		}

		ParaMap sendMap = new ParaMap();
		sendMap.put("merchant_id", inMap.getString("merchant_id"));
		sendMap.put("bill_id", inMap.getString("bill_id"));
		sendMap.put("start_time", inMap.getString("start_time"));
		sendMap.put("end_time", inMap.getString("end_time"));
		sendMap.put("page_index", pageIndex);
		sendMap.put("page_size", pageSize);
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), sendMap);
		resultMap = this.dealResponListMap(resultMap);
		return resultMap;
	}

	public ParaMap getBill(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("bill_id"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		ParaMap sendMap = new ParaMap();
		sendMap.put("bill_id", inMap.getString("bill_id"));
		sendMap.put("merchant_id", inMap.getString("merchant_id"));
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), sendMap);
		resultMap = this.dealResponMap(resultMap);
		return resultMap;
	}

	private ParaMap dealResponListMap(ParaMap inMap) {
		if (RespState.SUCCESS.getValue() != inMap.getInt(RespKey.STATE.getValue())) {
			return inMap;
		}
		List<ParaMap> billConvertList = new ArrayList<ParaMap>();
		List<ParaMap> billList = (List<ParaMap>) inMap.get(RespKey.DATA.getValue());
		if (billList == null || billList.isEmpty()) {
			inMap.put(RespKey.DATA.getValue(), billConvertList);
			return inMap;
		}
		for (ParaMap billMap : billList) {
			ParaMap convertBillMap = this.billConvert(billMap);
			billConvertList.add(convertBillMap);
		}
		inMap.put(RespKey.DATA.getValue(), billConvertList);
		return inMap;
	}

	/**
	 * 将单条订单信息，转化成数组的统一返回格式
	 */
	private ParaMap dealResponMap(ParaMap inMap) {
		if (RespState.SUCCESS.getValue() != inMap.getInt(RespKey.STATE.getValue())) {
			return inMap;
		}
		ParaMap billMap = (ParaMap) inMap.get(RespKey.DATA.getValue());
		if (billMap == null || billMap.isEmpty()) {
			return inMap;
		}
		ParaMap convertBillMap = this.billConvert(billMap);
		inMap.put(RespKey.DATA.getValue(), convertBillMap);
		return inMap;
	}

	/**
	 * 将返回的订单信息过滤
	 * 
	 * @param 原订单信息
	 * @return
	 */
	private ParaMap billConvert(ParaMap orderMap) {
		ParaMap convertBillMap = new ParaMap();
		convertBillMap.put("bill_id", orderMap.getString("bill_id"));
		convertBillMap.put("bill_date", orderMap.getString("bill_date"));
		convertBillMap.put("start_time", orderMap.getString("start_time"));
		convertBillMap.put("end_time", orderMap.getString("end_time"));
		convertBillMap.put("bill_status", orderMap.getString("status"));
		convertBillMap.put("order_count", orderMap.getString("order_count"));
		convertBillMap.put("order_amount", String.format("%.2f", orderMap.getDouble("order_amount")));
		convertBillMap.put("refund_count", orderMap.getString("refund_count"));
		convertBillMap.put("refund_amount", String.format("%.2f", orderMap.getDouble("refund_amount")));
		return convertBillMap;
	}
}
