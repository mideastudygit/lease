package com.road.control;

import java.util.List;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespState;
import com.common.util.RespUtil;
import com.road.entity.RoadAdaptor;
import com.road.service.WorkBenchService;
import com.road.util.AdaptorUtils;

/**
 * 工作台模块
 * 
 * @author 唐宗鸿
 * @date 20170502
 * @version 1.1.0
 */
public class RoadWorkbenchControl {

	private WorkBenchService workbenchService = new WorkBenchService();

	/**
	 * 附近路段信息，剩余泊位查询
	 */
	public ParaMap getNearbyBerthList(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("longitude")) || StrUtils.isNull(inMap.getString("latitude"))
				|| StrUtils.isNull(inMap.getString("distance"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		RoadAdaptor roadAdaptor = AdaptorUtils.getRoadAdaptor(inMap);
		ParaMap resultMap = roadAdaptor.getNearbyBerthList(inMap);
		return resultMap;
	}

	/**
	 * 附近路段信息，剩余泊位查询
	 */
	public ParaMap getSectionList(ParaMap inMap) throws Exception {
		RoadAdaptor roadAdaptor = AdaptorUtils.getRoadAdaptor(inMap);
		ParaMap resultMap = roadAdaptor.getSectionList(inMap);
		return resultMap;
	}

	/**
	 * 获取路边当天及累计指标
	 */
	public ParaMap getOrderTotal(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		ParaMap todayMap = workbenchService.getOrderTodayTotal(inMap);
		outMap.put("park_count", todayMap.getString("order_count"));
		outMap.put("park_amount", todayMap.getString("order_amount"));
		ParaMap totalMap = workbenchService.getOrderTotal(inMap);
		outMap.put("park_total_count", totalMap.getString("order_count"));
		outMap.put("park_total_amount", totalMap.getString("order_amount"));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 路边泊位指标统计数据
	 */
	public ParaMap getBerthUseTotal(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		ParaMap todayMap = workbenchService.getOrderTodayTotal(inMap);
		int duration = todayMap.getInt("duration");
		ParaMap totalMap = workbenchService.getOrderTotal(inMap);
		int orderCount = totalMap.getInt("order_count");
		if (orderCount <= 0) {
			outMap.put("duration", 0);
		} else {
			outMap.put("duration", duration / orderCount);// 当天停车时长/总订单数
		}
		outMap.put("order_count", todayMap.getInt("order_count"));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 获取路段收费Top
	 */
	public ParaMap getOrderFeeTop(ParaMap inMap) throws Exception {
		List<ParaMap> dataList = (List<ParaMap>) workbenchService.getOrderFeeTop(inMap);
		ParaMap outMap = RespUtil.setResp(dataList);
		return outMap;
	}

	/**
	 * 获取停车收费统计按月，按厂商
	 */
	public ParaMap getOrderAnalysis(ParaMap inMap) throws Exception {
		List<ParaMap> outList = workbenchService.getOrderAnalysis(inMap);
		ParaMap outMap = RespUtil.setResp(outList);
		return outMap;
	}

	/**
	 * 获取路边当月及累计指标
	 */
	public ParaMap getOrderMonthTotal(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		ParaMap monthMap = workbenchService.getOrderMonthTotal(inMap);
		outMap.put("park_month_count", monthMap.getString("order_count"));
		outMap.put("park_month_amount", monthMap.getString("order_amount"));
		ParaMap totalMap = workbenchService.getOrderTotal(inMap);
		outMap.put("park_total_count", totalMap.getString("order_count"));
		outMap.put("park_total_amount", totalMap.getString("order_amount"));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}
}
