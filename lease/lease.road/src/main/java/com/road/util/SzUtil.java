package com.road.util;

import java.util.ArrayList;
import java.util.List;

import com.base.utils.ParaMap;
import com.common.consts.RespConsts.RespKey;
import com.common.util.DateUtil;
import com.road.consts.RoadConsts;

public class SzUtil {

	/**
	 * 当泊位空闲，且是全日制泊位时，该泊位才是可用状态
	 */
	public static int getBerthStatus(ParaMap inMap) {
		String code = inMap.getString("code");
		if ("is_parking".equals(code)) {
			return RoadConsts.BERTH_STATUS_NOTAVAILABLE;
		}
		if ("no_parking".equals(code)) {
			ParaMap dataMap = (ParaMap) inMap.get(RespKey.DATA.getValue());
			if ("全日停车".equals(dataMap.getString("berthtypename"))) {
				return RoadConsts.BERTH_STATUS_AVAILABLE;
			}
		}
		return RoadConsts.BERTH_STATUS_NOTAVAILABLE;
	}

	/**
	 * 申请停车返回字段转化
	 */
	public static ParaMap applyOrderConvert(ParaMap inMap) {
		ParaMap orderMap = new ParaMap();
		ParaMap dataMap = (ParaMap) inMap.get("data");
		if (dataMap == null || dataMap.isEmpty()) {
			return orderMap;
		}
		orderMap.put("provider_order_id", dataMap.getString("ordercode"));
		orderMap.put("price", dataMap.getString("price"));
		orderMap.put("end_park_time", dataMap.getString("EndParkTime"));
		return orderMap;
	}
	
	/**
	 * 将宜停车订单字段转化为系统字段格式 ；1.没有items，进行中，已完成（免费的，不会产生欠费单）； 2.有items，只有一条记录的是欠费补缴；
	 * 3.有items，有多条记录的是有欠费补缴完成和退费 ；
	 * 4.实付金额-ActualPrice，当BillDetailsType=3，DetailsPrice
	 * -应付金额，BillDetailsType=2，DetailsPrice-退费金额
	 */
	public static ParaMap orderConvert(ParaMap inMap) {
		ParaMap orderMap = new ParaMap();
		ParaMap dataMap = (ParaMap) inMap.get("data");
		if (dataMap == null || dataMap.isEmpty()) {
			return orderMap;
		}
		List<ParaMap> itemList = (List<ParaMap>) dataMap.get("items");
		if (!(itemList == null || itemList.isEmpty())) {
			if (itemList.size() > 1) {
				for (ParaMap tempMap : itemList) {
					if (RoadConsts.BILL_TYPE_ARREARS == tempMap.getInt("BillDetailsType")) {
						dataMap = tempMap;
						break;
					}
				}
			} else {
				dataMap = itemList.get(0);
			}
		}
		orderMap.put("provider_order_id", dataMap.getString("BargainOrderCode"));
		orderMap.put("start_park_time",
				DateUtil.getTime(dataMap.getString("StartParkingTime"), DateUtil.TO_SECOND_LINE));
		orderMap.put("end_park_time", DateUtil.getTime(dataMap.getString("EndParkingTime"), DateUtil.TO_SECOND_LINE));
		orderMap.put("actual_duration", dataMap.getString("ActualDuration"));
		orderMap.put("actual_pay", dataMap.getString("ActualPrice"));
		// 当路边返回字段没有items时，DetailsPrice字段并不存在
		orderMap.put("should_pay", dataMap.getString("DetailsPrice") == null ? 0.00 : dataMap.getString("DetailsPrice"));
		orderMap.put("apply_duration", dataMap.getString("ApplyDuration"));
		orderMap.put("berth_code", dataMap.getString("BerthCode"));
		orderMap.put("canton", RoadConsts.SZ_CANTON);
		orderMap.put("section", dataMap.getString("SectionName"));
		orderMap.put("area", dataMap.getString("AreaName"));
		orderMap.put("start_time",
				DateUtil.getTime(dataMap.getString("BerthStartParkingTime"), DateUtil.TO_SECOND_LINE));
		orderMap.put("end_time", DateUtil.getTime(dataMap.getString("BerthEndParkingTime"), DateUtil.TO_SECOND_LINE));
		return orderMap;
	}

	/**
	 * 将宜停车结算订单字段转化为系统字段格式
	 */
	public static ParaMap endOrderConvert(ParaMap inMap) {
		ParaMap orderMap = new ParaMap();
		if (inMap == null || inMap.isEmpty()) {
			return orderMap;
		}
		orderMap.put("provider_order_id", inMap.getString("BargainOrderCode"));
		orderMap.put("start_park_time", DateUtil.getTime(inMap.getString("StartParkingTime"), DateUtil.TO_SECOND_LINE));
		orderMap.put("end_park_time", DateUtil.getTime(inMap.getString("EndParkingTime"), DateUtil.TO_SECOND_LINE));
		orderMap.put("actual_duration", inMap.getString("ActualDuration"));
		orderMap.put("actual_pay", inMap.getString("ActualPrice"));// 实付金额
		orderMap.put("should_pay", inMap.getString("DetailsPrice"));// 应付金额
		orderMap.put("apply_duration", inMap.getString("ApplyDuration"));
		orderMap.put("berth_code", inMap.getString("BerthCode"));
		orderMap.put("canton", RoadConsts.SZ_CANTON);
		orderMap.put("section", inMap.getString("SectionName"));
		orderMap.put("area", inMap.getString("AreaName"));
		orderMap.put("start_time", DateUtil.getTime(inMap.getString("BerthStartParkingTime"), DateUtil.TO_SECOND_LINE));
		orderMap.put("end_time", DateUtil.getTime(inMap.getString("BerthEndParkingTime"), DateUtil.TO_SECOND_LINE));
		return orderMap;
	}

	/**
	 * 将宜停车充值记录字段转化为系统字段格式
	 */
	public static ParaMap rechargeConvert(ParaMap inMap) {
		ParaMap outMap = new ParaMap();
		List<ParaMap> rechargeList = new ArrayList<ParaMap>();
		ParaMap dataMap = (ParaMap) inMap.get("data");
		if (dataMap == null || dataMap.isEmpty()) {
			outMap.put("data", rechargeList);
			outMap.put("total_count", 0);
			return outMap;
		}
		List<ParaMap> dataList = (List<ParaMap>) dataMap.getList("items");
		if (dataList == null || dataList.isEmpty()) {
			outMap.put("data", rechargeList);
			outMap.put("total_count", 0);
			return outMap;
		}
		for (ParaMap temp : dataList) {
			ParaMap recharge = new ParaMap();
			recharge.put("recharge_price", temp.getString("RechargePrice"));
			recharge.put("recharge_type", temp.getString("RechargeType"));
			recharge.put("recharge_time", DateUtil.getTime(temp.getString("RechargeTime"), DateUtil.TO_SECOND_SLASH));
			recharge.put("recharge_status", temp.getString("RechargeStatus"));
			recharge.put("mobile_number", temp.getString("MobileNumber"));
			rechargeList.add(recharge);
		}
		outMap.put("data", rechargeList);
		outMap.put("total_count", dataMap.getString("count"));
		return outMap;
	}

	/**
	 * 将宜停车退费字段转化为系统字段格式
	 */
	public static List<ParaMap> refundConvert(ParaMap inMap) {
		List<ParaMap> refundList = new ArrayList<ParaMap>();
		ParaMap dataMap = (ParaMap) inMap.get("data");
		if (dataMap == null || dataMap.isEmpty()) {
			return refundList;
		}
		List<ParaMap> dataList = (List<ParaMap>) dataMap.getList("items");
		if (dataList == null || dataList.isEmpty()) {
			return refundList;
		}
		for (ParaMap tempMap : dataList) {
			ParaMap refundMap = new ParaMap();
			refundMap.put("provider_refund_id", tempMap.getString("RefundOrderId"));
			refundMap.put("provider_order_id", tempMap.getString("BargainOrderCode"));
			refundMap.put("user_id", tempMap.getString("ParkUserId"));
			refundMap.put("provider_refund_code", tempMap.getString("RefundOrderCode"));
			refundMap.put("berth_code", tempMap.getString("BerthCode"));
			refundMap.put("refund_amount", tempMap.getString("RefundPrice"));
			refundMap.put("add_time", DateUtil.getTime(tempMap.getString("AddTime"), DateUtil.TO_SECOND_SLASH));
			refundMap.put("provider_refund_time",
					DateUtil.getTime(tempMap.getString("RefundTime"), DateUtil.TO_SECOND_SLASH));
			refundMap.put("provider_refund_status", tempMap.getString("RefundStatus"));
			refundMap.put("refund_status", RoadConsts.REFUND_STATUS_NOTREFUND);// 服务商已退费，对应系统未退费状态
			refundMap.put("start_park_time",
					DateUtil.getTime(tempMap.getString("StartParkingTime"), DateUtil.TO_SECOND_SLASH));
			refundMap.put("end_park_time",
					DateUtil.getTime(tempMap.getString("EndParkingTime"), DateUtil.TO_SECOND_SLASH));
			refundMap.put("actual_duration", tempMap.getString("ActualDuration"));
			refundMap.put("total_duration", tempMap.getString("TotalDuration"));
			refundList.add(refundMap);
		}
		return refundList;
	}

	/**
	 * 将宜停车欠费订单字段转化为系统字段格式
	 */
	public static List<ParaMap> arrearsConvert(ParaMap inMap) {
		List<ParaMap> arrearsList = new ArrayList<ParaMap>();
		ParaMap dataMap = (ParaMap) inMap.get("data");
		if (dataMap == null || dataMap.isEmpty()) {
			return arrearsList;
		}
		List<ParaMap> dataList = (List<ParaMap>) dataMap.getList("items");
		if (dataList == null || dataList.isEmpty()) {
			return arrearsList;
		}
		for (ParaMap tempMap : dataList) {
			ParaMap arrearsMap = new ParaMap();
			arrearsMap.put("provider_arrears_id", tempMap.getString("ArrearsOrderId"));
			arrearsMap.put("provider_order_id", tempMap.getString("BargainOrderCode"));
			arrearsMap.put("user_id", tempMap.getString("ParkUserId"));
			arrearsMap.put("provider_arrears_code", tempMap.getString("ArrearsOrderCode"));
			arrearsMap.put("berth_code", tempMap.getString("BerthCode"));
			arrearsMap.put("arrears_amount", tempMap.getString("ArrearsPrice"));
			arrearsMap.put("arrears_time", DateUtil.getTime(tempMap.getString("AddTime"), DateUtil.TO_SECOND_SLASH));
			arrearsMap.put("arrears_status", tempMap.getString("ArrearsStatus"));
			arrearsList.add(arrearsMap);
		}
		return arrearsList;
	}

	/**
	 * 将宜停车路段信息字段转化为系统字段格式
	 */
	public static List<ParaMap> sectionConvert(ParaMap inMap) {
		List<ParaMap> sectionList = new ArrayList<ParaMap>();
		ParaMap dataMap = (ParaMap) inMap.get("data");
		if (dataMap == null || dataMap.isEmpty()) {
			return sectionList;
		}
		List<ParaMap> dataList = (List<ParaMap>) dataMap.getList("items");
		if (dataList == null || dataList.isEmpty()) {
			return sectionList;
		}
		for (ParaMap tempMap : dataList) {
			ParaMap sectionMap = new ParaMap();
			sectionMap.put("section_id", tempMap.getString("SectionId"));
			sectionMap.put("section_name", tempMap.getString("SectionName"));
			sectionMap.put("img_type", tempMap.getString("imgtype"));
			sectionMap.put("berth_total", tempMap.getString("BerthTotal"));
			sectionMap.put("berth_vacant", tempMap.getString("BerthVacant"));
			sectionMap.put("latitude", tempMap.getString("Latitude"));
			sectionMap.put("longitude", tempMap.getString("Longitude"));
			sectionMap.put("area_name", tempMap.getString("AreaName"));
			sectionMap.put("canton_name", tempMap.getString("CantonName"));
			sectionMap.put("charge_rule", tempMap.getString("ChargingRules"));
			sectionList.add(sectionMap);
		}
		return sectionList;
	}

	/**
	 * 将宜停车泊位字段转化为系统字段格式
	 */
	public static List<ParaMap> berthConvert(ParaMap inMap) {
		List<ParaMap> berthList = new ArrayList<ParaMap>();
		ParaMap dataMap = (ParaMap) inMap.get("data");
		if (dataMap == null || dataMap.isEmpty()) {
			return berthList;
		}
		List<ParaMap> dataList = (List<ParaMap>) dataMap.getList("items");
		if (dataList == null || dataList.isEmpty()) {
			return berthList;
		}
		for (ParaMap tempMap : dataList) {
			ParaMap berthMap = new ParaMap();
			berthMap.put("section_id", tempMap.getString("SectionId"));
			berthMap.put("section_name", tempMap.getString("SectionName"));
			berthMap.put("img_type", tempMap.getString("imgtype"));
			berthMap.put("berth_total", tempMap.getString("BerthTotal"));
			berthMap.put("berth_vacant", tempMap.getString("BerthVacant"));
			berthMap.put("latitude", tempMap.getString("Latitude"));
			berthMap.put("longitude", tempMap.getString("Longitude"));
			berthMap.put("charge_rule", tempMap.getString("ChargingRules"));
			berthList.add(berthMap);
		}
		return berthList;
	}
}
