package com.center.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;

/**
 * 组织机构数据操作类
 * 
 * @author 唐宗鸿
 * @date 20170509
 * @version 1.1.0
 */
public class OrgDao extends BaseDataSetDao {

	/**
	 * 组织机构列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            sys_id-系统id, org_name-机构名称 支持模糊查询, org_code-机构编码 支持模糊查询,
	 *            is_page-是否分页(1-不分页，0-分页),page_size-每页记录数,page_index-第几页
	 * @return sys_id-系统id, sys_code-系统编号, sys_name-系统名称,org_id-机构id,
	 *         org_name-机构名称, org_code-机构编码, easy_name-机构简称, contacts-联系人,
	 *         tel-手机号码, phone-联系电话, email-联系邮箱, address-联系地址, sort_num-排序号,
	 *         status-状态，0-禁用，1-启用, createtime-创建时间, updatetime-更新时间
	 */
	public ParaMap getOrgList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("org", "getOrgList");
		String dynamicSql = "";
		String orgname = inMap.getString("org_name");
		if (StrUtils.isNotNull(orgname)) {
			dynamicSql += " AND o.org_name LIKE ?";
			sqlMap.addParam("%" + orgname + "%");
		}
		String orgcode = inMap.getString("org_code");
		if (StrUtils.isNotNull(orgcode)) {
			dynamicSql += " AND o.org_code LIKE ?";
			sqlMap.addParam("%" + orgcode + "%");
		}
		String sysId = inMap.getString("sys_id");
		if (StrUtils.isNotNull(sysId)) {
			dynamicSql += " AND s.id = ?";
			sqlMap.addParam(sysId);
		}
		if (inMap.getInt("is_page") == SqlConsts.NOT_PAGE) {
			dynamicSql += " AND o.status = 1";
			sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
			return query(sqlMap);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		sqlMap.put("pageIndex", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}

	/**
	 * 新增组织机构信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            sys_id-系统id, org_name-机构名称, org_code-机构编码, easy_name-机构简称,
	 *            contacts-机构联系人, tel-手机号码, phone-联系电话, email-邮箱地址,
	 *            address-联系地址, sort_num-排序号
	 * @return
	 */
	public ParaMap addOrg(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("org", "addOrg");
		sqlMap.addParam(inMap.getString("org_id"));
		sqlMap.addParam(inMap.getString("sys_id"));
		sqlMap.addParam(inMap.getString("org_name"));
		sqlMap.addParam(inMap.getString("org_code"));
		sqlMap.addParam(inMap.getString("easy_name"));
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
	 * 校验组织机构名称和编码的唯一性
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param id
	 *            机构id
	 * @param code
	 *            机构编码
	 * @param name
	 *            机构名称
	 * @return
	 */
	public ParaMap checkOrg(String id, String code, String name) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("org", "getOrg");
		String dynamicSql = "";
		if (StrUtils.isNotNull(id)) {
			dynamicSql += " AND o.id != ? ";
			sqlMap.addParam(id);
		}
		if (StrUtils.isNotNull(code)) {
			dynamicSql += " AND o.org_code = ? ";
			sqlMap.addParam(code);
		}
		if (StrUtils.isNotNull(name)) {
			dynamicSql += " AND o.org_name = ? ";
			sqlMap.addParam(name);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 获取组织机构基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            org_id-机构id,org_code-机构编码,org_name-机构名称
	 * @return org_id-机构id, sys_id-系统id, sys_code-系统编号, sys_name-系统名称,
	 *         org_name-机构名称, org_code-机构编码, easy_name-机构简称, contacts-联系人,
	 *         tel-手机号码, phone-联系电话, email-邮箱地址, address-联系地址, sort_num-排序号,
	 *         status-状态，0-禁用，1-启用, createtime-创建时间, updatetime-更新时间
	 */
	public ParaMap getOrg(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("org", "getOrg");
		String dynamicSql = "";
		String orgid = inMap.getString("org_id");
		if (StrUtils.isNotNull(orgid)) {
			dynamicSql += " AND o.id = ? ";
			sqlMap.addParam(orgid);
		}
		String orgcode = inMap.getString("org_code");
		if (StrUtils.isNotNull(orgcode)) {
			dynamicSql += " AND o.org_code = ? ";
			sqlMap.addParam(orgcode);
		}
		String orgname = inMap.getString("org_name");
		if (StrUtils.isNotNull(orgname)) {
			dynamicSql += " AND o.org_name = ? ";
			sqlMap.addParam(orgname);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 修改组织机构信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            org_id-机构id,org_name-机构名称, org_code-机构编码, easy_name-机构简称,
	 *            contacts-机构负责人, tel-手机号码, phone-联系电话, email-邮箱地址,
	 *            address-联系地址, sort_num-排序号
	 * @return
	 */
	public ParaMap updateOrg(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("org", "updateOrg");
		sqlMap.addParam(inMap.getString("org_name"));
		sqlMap.addParam(inMap.getString("org_code"));
		sqlMap.addParam(inMap.getString("easy_name"));
		sqlMap.addParam(inMap.getString("contacts"));
		sqlMap.addParam(inMap.getString("tel"));
		sqlMap.addParam(inMap.getString("phone"));
		sqlMap.addParam(inMap.getString("email"));
		sqlMap.addParam(inMap.getString("address"));
		sqlMap.addParam(inMap.getInt("sort_num"));
		sqlMap.addParam(inMap.getString("org_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 禁用/启动组织机构信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param orgId
	 *            机构id
	 * @param status
	 *            状态
	 * @return
	 */
	public ParaMap enableOrg(String orgId, Integer status) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("org", "enableOrg");
		sqlMap.addParam(status);
		sqlMap.addParam(orgId);
		ParaMap outMap = update(sqlMap);
		return outMap;
	}
}
