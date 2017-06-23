package com.road.service;

import java.util.List;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;
import com.road.dao.OrderDao;

/**
 * 停车订单服务类
 * 
 * @author 唐宗鸿
 * @date 20170502
 * @version 1.1.0
 */
public class OrderService {

	private OrderDao orderDao = new OrderDao();

	/**
	 * 获取订单列表
	 */
	public ParaMap getOrderList(ParaMap inMap) throws Exception {
		ParaMap resultMap = orderDao.getOrderList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}

	/**
	 * 批量修改订单
	 */
	public ParaMap batchUpdateRoadOrder(List<ParaMap> orderList) throws Exception {
		ParaMap outMap = orderDao.batchUpdateOrder(orderList);
		return outMap;
	}

	/**
	 * 新增订单
	 */
	public ParaMap addOrder(ParaMap inMap) throws Exception {
		inMap.put("order_id", IDGenerator.newGUID());
		ParaMap outMap = orderDao.addOrder(inMap);
		outMap.put("order_id", inMap.getString("order_id"));
		return outMap;
	}

	/**
	 * 查询订单详情
	 */
	public ParaMap getOrder(ParaMap inMap) throws Exception {
		ParaMap outMap = orderDao.getOrder(inMap);
		outMap = ApiUtil.format(outMap);
		return outMap;
	}

	/**
	 * 修改订单信息
	 */
	public ParaMap updateOrder(ParaMap inMap) throws Exception {
		ParaMap outMap = orderDao.updateOrder(inMap);
		return outMap;
	}

	/**
	 * 批量查询订单信息
	 */
	public List<ParaMap> getOrderList(List<ParaMap> inList) throws Exception {
		ParaMap outMap = orderDao.getOrderList(inList);
		List<ParaMap> orderList = ApiUtil.formatList(outMap);
		return orderList;
	}
}
