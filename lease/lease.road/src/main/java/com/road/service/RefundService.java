package com.road.service;

import java.util.List;

import com.base.utils.ParaMap;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;
import com.road.consts.RoadConsts;
import com.road.dao.RefundDao;

/**
 * 退费服务
 * 
 * @author 唐宗鸿
 * @date 20170526
 * @version 1.1.0
 */
public class RefundService {

	private RefundDao refundDao = new RefundDao();

	/**
	 * 查询退费列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170526
	 * @param inMap
	 *            merchant_id-商户id, start_time-开始时间,
	 *            end_time-结束时间,refund_status-退费状态
	 *            is_page-是否分页(1-不分页，0-分页),page_size-每页记录数,page_index-第几页
	 * @return
	 */
	public ParaMap getRefundOrderList(ParaMap inMap) throws Exception {
		ParaMap resultMap = refundDao.getRefundOrderList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}

	/**
	 * 批量添加退费订单记录
	 */
	public ParaMap batchAddRefundOrder(List<ParaMap> refundOrderList) throws Exception {
		ParaMap outMap = refundDao.batchAddRefundOrder(refundOrderList);
		return outMap;
	}

	/**
	 * 批量查询退费记录
	 */
	public List<ParaMap> getRefundOrderList(List<ParaMap> inList) throws Exception {
		ParaMap outMap = refundDao.getRefundOrderList(inList);
		List<ParaMap> refundList = ApiUtil.formatList(outMap);
		return refundList;
	}

	public ParaMap getRefundOrder(ParaMap inMap) throws Exception {
		ParaMap outMap = refundDao.getRefundOrder(inMap);
		outMap = ApiUtil.format(outMap);
		return outMap;
	}

	public ParaMap updateRefundOrderStatus(ParaMap inMap) throws Exception {
		inMap.put("status", RoadConsts.REFUND_STATUS_REFUNDED);
		ParaMap outMap = refundDao.updateRefundOrderStatus(inMap);
		return outMap;
	}
}
