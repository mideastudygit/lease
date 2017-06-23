package com.road.service;

import com.base.utils.ParaMap;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;
import com.road.dao.MerchantBillDao;

/**
 * 对账服务类
 * 
 * @author 唐宗鸿
 * @date 20170524
 * @version 1.1.0
 */
public class MerchantBillService {

	private MerchantBillDao merchantBillDao = new MerchantBillDao();

	/**
	 * 
	 * 获取对账单列表
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 *            merchant_id-商户id,start_time-对账起始时间,end_time-对账结束时间,status-
	 *            对账单状态，1-待确认，2-已确认，3-待付款，4-已付款
	 * @return 
	 *         bill_id-账单id,bill_date-账单日期,start_time-对账开始时间,end_time-对账结束时间,status
	 *         -对账状态，1-待确认，2-待付款，3-已付款,order_count-订单总数,refund_count-退款总数
	 *         ,total_amount
	 *         -订单总额,refund_amount-退款总额,merchant_id-商户id,create_time
	 *         -创建时间,update_time-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getMerchantBillList(ParaMap inMap) throws Exception {
		ParaMap resultMap = merchantBillDao.getMerchantBillList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}

	/**
	 * 
	 * 获取对账单详情
	 * 
	 * @author 唐宗鸿
	 * @param billId
	 *            对账单id
	 * @return 
	 *         bill_id-账单id,bill_date-账单日期,start_time-对账开始时间,end_time-对账结束时间,status
	 *         -对账状态，1-待确认，2-待付款，3-已付款,order_count-订单总数,refund_count-退款总数
	 *         ,order_amount
	 *         -订单总额,refund_amount-退款总额,merchant_id-商户id,create_time
	 *         -创建时间,update_time-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getMerchantBill(String billId) throws Exception {
		ParaMap resultMap = merchantBillDao.getMerchantBill(billId);
		ParaMap outMap = ApiUtil.format(resultMap);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 
	 * 更改对账单状态
	 * 
	 * @author 唐宗鸿
	 * @param billId
	 *            对账单id,status-状态，2-确认，3-提交付款
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateMerchantBill(String billId, Integer status) throws Exception {
		ParaMap resultMap = merchantBillDao.updateMerchantBill(billId, status);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "bill.update.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}

	/**
	 * 
	 * 添加商户对账单
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 *            merchant_id-商户id,bill_date-对账日期，yyyyMMdd,start_date-对账开始日期，
	 *            yyyyMMdd,end_date-对账结束日期，yyyyMMdd
	 * @return
	 */
	public ParaMap addMerchantBill(ParaMap inMap) throws Exception {
		ParaMap outMap = merchantBillDao.addMerchantBill(inMap);
		return outMap;
	}

	/**
	 * 
	 * 更改商户对账单
	 * 
	 * @author 唐宗鸿
	 * @param inMap
	 *            bill_id-对账单id,merchant_id-商户id,order_count-订单总数,refund_count-
	 *            退费单总数,order_amount-订单总金额,refund_amount-退费总金额,
	 *            bill_date-账单日期,start_date-开始日期,end_date-结束日期
	 * @return
	 */
	public ParaMap updateMerchantBill(ParaMap inMap) throws Exception {
		ParaMap resultMap = merchantBillDao.updateMerchantBill(inMap);
		return resultMap;
	}

}
