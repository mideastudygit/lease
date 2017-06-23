package com.center.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;

/**
 * 银行数据操作类
 * 
 * @author 唐宗鸿
 * @date 20170523
 * @version 1.1.0
 */
public class BankDao extends BaseDataSetDao {

	/**
	 * 银行列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170523
	 * @param inMap
	 *            bank_no-银行编号, bank_name-银行名称，
	 *            支持模糊查询,is_page-是否分页(1-不分页，0-分页),page_size-每页记录数,page_index-第几页
	 * @return bank_id-银行id, bank_name-银行名称, bank_no-银行编号
	 */
	public ParaMap getBankList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("bank", "getBankList");
		String dynamicSql = "";
		String bankName = inMap.getString("bank_name");
		if (StrUtils.isNotNull(bankName)) {
			dynamicSql += " AND bank_name LIKE ?";
			sqlMap.addParam("%" + bankName + "%");
		}
		String bankNo = inMap.getString("bank_no");
		if (StrUtils.isNotNull(bankNo)) {
			dynamicSql += " AND bank_no = ?";
			sqlMap.addParam(bankNo);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		if (inMap.getInt("is_page") == SqlConsts.NOT_PAGE) {
			return query(sqlMap);
		}
		sqlMap.put("pageIndex", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}
}
