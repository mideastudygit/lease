package com.center.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;

/**
 * 系统数据操作类
 * 
 * @author 唐宗鸿
 * @date 20170509
 * @version 1.1.0
 */
public class SystemDao extends BaseDataSetDao {

	/**
	 * 查询系统列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            sys_name-系统名称，支持模糊查询, sys_code-系统编码 ，支持模糊查询,
	 *            is_page-是否分页，1-不分页，0-分页,page_size-每页记录数,page_index-第几页
	 * @return sys_id-系统id, sys_name-系统名称, sys_code-系统编码, easy_name-系统简称,
	 *         url-访问路径, contacts-联系人, tel-手机号码, phone-联系电话, email-联系邮箱,
	 *         address-联系地址, sort_num-排序号, status-状态,0-禁用，1-启用, createtime-创建时间,
	 *         updatetime-更新时间
	 */
	public ParaMap getSystemList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("system", "getSystemList");
		String dynamicSql = "";
		String sysName = inMap.getString("sys_name");
		if (StrUtils.isNotNull(sysName)) {
			dynamicSql += " AND sys_name LIKE ?";
			sqlMap.addParam("%" + sysName + "%");
		}
		String sysCode = inMap.getString("sys_code");
		if (StrUtils.isNotNull(sysCode)) {
			dynamicSql += " AND sys_code LIKE ?";
			sqlMap.addParam("%" + sysCode + "%");
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		if (inMap.getInt("is_page") == SqlConsts.NOT_PAGE) {
			return query(sqlMap);
		}
		sqlMap.put("pageIndex", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}

	/**
	 * 新增系统信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            sys_name-名称, sys_code-编码, easy_name-简称, url-访问路径,
	 *            contacts-负责人, tel-手机号码, phone-联系电话, email-邮箱地址, address-联系地址,
	 *            sort_num-排序号
	 * @return
	 */
	public ParaMap addSystem(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("system", "addSystem");
		sqlMap.addParam(inMap.getString("sys_id"));
		sqlMap.addParam(inMap.getString("sys_name"));
		sqlMap.addParam(inMap.getString("sys_code"));
		sqlMap.addParam(inMap.getString("easy_name"));
		sqlMap.addParam(inMap.getString("url"));
		sqlMap.addParam(inMap.getString("contacts"));
		sqlMap.addParam(inMap.getString("tel"));
		sqlMap.addParam(inMap.getString("phone"));
		sqlMap.addParam(inMap.getString("email"));
		sqlMap.addParam(inMap.getString("address"));
		sqlMap.addParam(inMap.getInt("sort_num"));
		sqlMap.addParam(inMap.getString("u"));
		ParaMap outMap = insert(sqlMap);
		return outMap;
	}

	/**
	 * 校验系统名称和编码的唯一性
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param sysId
	 *            系统id
	 * @param sysCode
	 *            系统编码
	 * @param sysName
	 *            系统名称
	 * @return
	 */
	public ParaMap checkSystem(String sysId, String sysCode, String sysName)
			throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("system", "getSystem");
		String dynamicSql = "";
		if (StrUtils.isNotNull(sysId)) {
			dynamicSql += " AND id != ? ";
			sqlMap.addParam(sysId);
		}
		if (StrUtils.isNotNull(sysCode)) {
			dynamicSql += " AND sys_code = ? ";
			sqlMap.addParam(sysCode);
		}
		if (StrUtils.isNotNull(sysName)) {
			dynamicSql += " AND sys_name = ? ";
			sqlMap.addParam(sysName);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 获取系统基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            sys_id-系统id,sys_code-系统编号,sys_name-系统名称
	 * @return sys_id-系统id, sys_name-系统名称, sys_code-系统编码, easy_name-系统简称,
	 *         url-访问路径, contacts-联系人, tel-手机号码, phone-联系电话, email-邮箱地址,
	 *         address-联系地址, sort_num-排序号, status-状态,0-禁用,1-启用, createtime-创建时间,
	 *         updatetime-更新时间
	 */
	public ParaMap getSystem(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("system", "getSystem");
		String dynamicSql = "";
		String platId = inMap.getString("sys_id");
		if (StrUtils.isNotNull(platId)) {
			dynamicSql += " AND id = ? ";
			sqlMap.addParam(platId);
		}
		String sysCode = inMap.getString("sys_code");
		if (StrUtils.isNotNull(sysCode)) {
			dynamicSql += " AND sys_code = ? ";
			sqlMap.addParam(sysCode);
		}
		String sysName = inMap.getString("sys_name");
		if (StrUtils.isNotNull(sysName)) {
			dynamicSql += " AND sys_name = ? ";
			sqlMap.addParam(sysName);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 修改系统信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            sys_id-系统id, sys_name-系统名称, sys_code-系统编码, easy_name-系统简称,
	 *            url-访问路径, contacts-负责人, tel-手机号码, phone-联系电话, email-邮箱地址,
	 *            address-联系地址, sort_num-排序号
	 * @return
	 */
	public ParaMap updateSystem(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("system", "updateSystem");
		sqlMap.addParam(inMap.getString("sys_name"));
		sqlMap.addParam(inMap.getString("sys_code"));
		sqlMap.addParam(inMap.getString("easy_name"));
		sqlMap.addParam(inMap.getString("url"));
		sqlMap.addParam(inMap.getString("contacts"));
		sqlMap.addParam(inMap.getString("tel"));
		sqlMap.addParam(inMap.getString("phone"));
		sqlMap.addParam(inMap.getString("email"));
		sqlMap.addParam(inMap.getString("address"));
		sqlMap.addParam(inMap.getInt("sort_num"));
		sqlMap.addParam(inMap.getString("sys_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 禁用/启用系统信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param sysId
	 *            系统id
	 * @param status
	 *            系统状态， 0-停用，1-启用
	 * @return
	 */
	public ParaMap enableSystem(String sysId, Integer status) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("system", "enableSystem");
		sqlMap.addParam(status);
		sqlMap.addParam(sysId);
		ParaMap outMap = update(sqlMap);
		return outMap;
	}
}
