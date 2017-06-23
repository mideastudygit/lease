package com.road.service;

import java.util.List;

import com.base.utils.ParaMap;
import com.common.util.ApiUtil;
import com.road.dao.WorkBenchDao;

public class WorkBenchService {

	private WorkBenchDao workbenchDao = new WorkBenchDao();

	/**
	 * 获取路边累计指标
	 */
	public ParaMap getOrderTotal(ParaMap inMap) throws Exception {
		ParaMap resultMap = workbenchDao.getOrderTotal(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		return outMap;
	}

	/**
	 * 获取路边当天指标
	 */
	public ParaMap getOrderTodayTotal(ParaMap inMap) throws Exception {
		ParaMap resultMap = workbenchDao.getOrderTodayTotal(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		return outMap;
	}

	/**
	 * 获取路段收费Top
	 */
	public List<ParaMap> getOrderFeeTop(ParaMap inMap) throws Exception {
		ParaMap resultMap = workbenchDao.getOrderFeeTop(inMap);
		List<ParaMap> outList = ApiUtil.formatList(resultMap);
		return outList;
	}

	/**
	 * 获取停车收费分析
	 */
	public List<ParaMap> getOrderAnalysis(ParaMap inMap) throws Exception {
		ParaMap resultMap = workbenchDao.getOrderAnalysis(inMap);
		List<ParaMap> outList = ApiUtil.formatList(resultMap);
		return outList;
	}

	/**
	 * 路边当月停车总数停车收费总数
	 */
	public ParaMap getOrderMonthTotal(ParaMap inMap) throws Exception {
		ParaMap resultMap = workbenchDao.getOrderMonthTotal(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		return outMap;
	}
}
