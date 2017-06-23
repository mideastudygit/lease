package com.center.control;

import com.base.utils.ParaMap;
import com.center.service.BankService;

/**
 * 银行服务控制器
 * 
 * @author 唐宗鸿
 * @date 20170523
 * @version 1.1.0
 */
public class BankControl {

	private BankService bankService = new BankService();

	/**
	 * 查询部门列表
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
		return bankService.getBankList(inMap);
	}
}
