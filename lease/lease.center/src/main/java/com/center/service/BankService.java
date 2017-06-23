package com.center.service;

import com.base.utils.ParaMap;
import com.center.dao.BankDao;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;

/**
 * 银行服务
 * 
 * @author 唐宗鸿
 * @date 20170523
 * @version 1.1.0
 */
public class BankService {

	private BankDao bankDao = new BankDao();

	/**
	 * 查询银行列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170523
	 * @param inMap
	 *            bank_no-银行编号, bank_name-银行名称，
	 *            支持模糊查询,is_page-是否分页(1-不分页，0-分页),page_size-每页记录数,page_index-第几页
	 * @return bank_id-银行id, bank_name-银行名称,
	 *         bank_no-银行编号,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getBankList(ParaMap inMap) throws Exception {
		ParaMap resultMap = bankDao.getBankList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}
}
