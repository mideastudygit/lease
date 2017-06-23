package com.road.service;

import java.util.List;

import com.base.utils.ParaMap;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;
import com.road.consts.RoadConsts;
import com.road.dao.ArrearsDao;

public class ArrearsService {

	private ArrearsDao arrearsDao = new ArrearsDao();

	/**
	 * 批量添加欠费订单记录
	 */
	public ParaMap batchAddArrearsOrder(List<ParaMap> arrearsOrderList) throws Exception {
		ParaMap outMap = arrearsDao.batchAddArrearsOrder(arrearsOrderList);
		return outMap;
	}

	/**
	 * 查询欠费订单列表
	 */
	public ParaMap getArrearsOrderList(ParaMap inMap) throws Exception {
		ParaMap resultMap = arrearsDao.getArrearsOrderList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}

	/**
	 * 查询欠费订单详情
	 */
	public ParaMap getArrearsOrder(ParaMap inMap) throws Exception {
		ParaMap resultMap = arrearsDao.getArrearsOrder(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		return outMap;
	}

	/**
	 * 更新欠费订单状态
	 */
	public ParaMap updateArrearsOrderStatus(ParaMap inMap) throws Exception {
		inMap.put("arrears_status", RoadConsts.ARREARS_STATUS_HAD_PAY);
		ParaMap outMap = arrearsDao.updateArrearsOrderStatus(inMap);
		return outMap;
	}

	/**
	 * 批量查询欠费订单
	 */
	public List<ParaMap> getArrearsOrderList(List<ParaMap> inList) throws Exception {
		ParaMap outMap = arrearsDao.getArrearsOrderList(inList);
		List<ParaMap> arrearsList = ApiUtil.formatList(outMap);
		return arrearsList;
	}
}
