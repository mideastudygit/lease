package com.road.service;

import java.util.List;

import com.base.utils.ParaMap;
import com.common.util.ApiUtil;
import com.common.util.DateUtil;
import com.common.util.RespUtil;
import com.road.dao.SystemBillDao;

/**
 * 系统对账服务类
 * 
 * @author 唐宗鸿
 * @date 20170525
 * @version 1.1.0
 */
public class SystemBillService {

	private SystemBillDao systemBillDao = new SystemBillDao();

	/**
	 * 
	 * 复制订单数据
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 * 
	 * @return
	 */
	public ParaMap addOrderBillData(List<ParaMap> orderList, String billId) throws Exception {
		ParaMap outMap = systemBillDao.addOrderBillData(orderList, billId);
		return outMap;
	}

	/**
	 * 
	 * 复制退费数据
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 * 
	 * @return
	 */
	public ParaMap addRefundBillData(List<ParaMap> refundList, String billId) throws Exception {
		ParaMap outMap = systemBillDao.addRefundBillData(refundList, billId);
		return outMap;
	}

	/**
	 * 
	 * 添加系统对账单
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 *            merchant_id-商户id,bill_date-对账日期，格式为yyyyMMdd
	 * @return
	 */
	public ParaMap addSystemBill(ParaMap inMap) throws Exception {
		ParaMap outMap = systemBillDao.addSystemBill(inMap);
		return outMap;
	}

	/**
	 * 
	 * 查询系统对账单
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 *            merchant_id-商户id,start_date-开始时间，时间戳,end_date-结束时间，时间戳
	 * @return
	 */
	public List<ParaMap> getSystemBillList(ParaMap inMap) throws Exception {
		ParaMap outMap = systemBillDao.getSystemBillList(inMap);
		List<ParaMap> billList = ApiUtil.formatList(outMap);
		return billList;
	}

	/**
	 * 
	 * 查询具体系统对账单
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 *            merchant_id-商户id,bill_date-对账日期，格式为yyyyMMdd
	 * @return
	 */
	public ParaMap getSystemBill(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("merchant_id", inMap.getString("merchant_id"));
		sendMap.put("bill_date", DateUtil.getTime(inMap.getString("bill_date"), DateUtil.TO_DAY));
		ParaMap resultMap = systemBillDao.getSystemBill(sendMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		return outMap;
	}

	/**
	 * 
	 * 获取对账单数据
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 *            merchant_id-商户id,bill_list-系统对账单id集合
	 * @return
	 * 
	 */
	public ParaMap getBillDataList(ParaMap inMap) throws Exception {
		ParaMap resultMap = systemBillDao.getBillDataList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}

	/**
	 * 
	 * 删除对账单数据
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 *            merchant_id-商户id,bill_id-系统账单id
	 * @return
	 * 
	 */
	public ParaMap delBillData(ParaMap inMap) throws Exception {
		ParaMap resultMap = systemBillDao.delBillData(inMap);
		return resultMap;
	}

	/**
	 * 
	 * 更新系统对账单
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 *            merchant_id-商户id,bill_id-对账单id,order_count-订单总数,refund_count-
	 *            退费单总数,order_amount-订单总额, refund_amount-退费总额
	 * @return
	 */
	public ParaMap updateSystemBill(ParaMap inMap) throws Exception {
		ParaMap outMap = systemBillDao.updateSystemBill(inMap);
		return outMap;
	}
}
