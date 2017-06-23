package com.center.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;

/**
 * 部门数据操作类
 * 
 * @author 唐宗鸿
 * @date 20170509
 * @version 1.1.0
 */
public class DeptDao extends BaseDataSetDao {

	/**
	 * 部门列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            org_id-机构id, dept_name-部门名称 支持模糊查询, dept_code-部门编码 支持模糊查询,
	 *            is_page-是否分页(1-不分页，0-分页),page_size-每页记录数,page_index-第几页
	 * @return dept_id-部门id, org_id-组织机构id, org_code-组织机构编号, org_name-组织机构名称,
	 *         dept_name-部门名称, dept_code-部门编码, easy_name-部门简称, parent_id-部门父id,
	 *         contacts-部门负责人, tel-手机号码, phone-联系电话, email-邮箱地址, address-联系地址,
	 *         sort_num-排序号
	 */
	public ParaMap getDeptList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("dept", "getDeptList");
		String dynamicSql = "";
		String deptname = inMap.getString("dept_name");
		if (StrUtils.isNotNull(deptname)) {
			dynamicSql += " AND d.dept_name LIKE ?";
			sqlMap.addParam("%" + deptname + "%");
		}
		String deptcode = inMap.getString("dept_code");
		if (StrUtils.isNotNull(deptcode)) {
			dynamicSql += " AND d.dept_code LIKE ?";
			sqlMap.addParam("%" + deptcode + "%");
		}
		String sysId = inMap.getString("sys_id");
		if (StrUtils.isNotNull(sysId)) {
			dynamicSql += " AND p.id = ?";
			sqlMap.addParam(sysId);
		}
		String orgid = inMap.getString("org_id");
		if (StrUtils.isNotNull(orgid)) {
			dynamicSql += " AND d.org_id = ?";
			sqlMap.addParam(orgid);
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
	 * 新增部门信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 * @return
	 */
	public ParaMap addDept(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("dept", "addDept");
		sqlMap.addParam(inMap.getString("dept_id"));
		sqlMap.addParam(inMap.getString("org_id"));
		sqlMap.addParam(inMap.getString("dept_name"));
		sqlMap.addParam(inMap.getString("dept_code"));
		sqlMap.addParam(inMap.getString("easy_name"));
		sqlMap.addParam(inMap.getString("parent_id"));
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
	 * 校验机构下的部门名称和编码的唯一性
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param id
	 *            部门id
	 * @param orgid
	 *            机构id
	 * @param code
	 *            机构编码
	 * @param name
	 *            机构名称
	 * @return
	 */
	public ParaMap checkDept(String id, String orgid, String code, String name) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("dept", "getDept");
		String dynamicSql = "";
		if (StrUtils.isNotNull(id)) {
			dynamicSql += " AND d.id != ? ";
			sqlMap.addParam(id);
		}
		if (StrUtils.isNotNull(code)) {
			dynamicSql += " AND d.dept_code = ? ";
			sqlMap.addParam(code);
		}
		if (StrUtils.isNotNull(name)) {
			dynamicSql += " AND d.dept_name = ? ";
			sqlMap.addParam(name);
		}
		if (StrUtils.isNotNull(orgid)) {
			dynamicSql += " AND d.org_id = ? ";
			sqlMap.addParam(orgid);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 获取部门基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            dept_id-部门id,dept_code-部门编码,dept_name-部门名称
	 * @return dept_id-部门id, org_id-组织机构id, org_code-组织机构编号, org_name-组织机构名称,
	 *         dept_name-部门名称, dept_code-部门编码, easy_name-部门简称, parent_id-部门父id,
	 *         contacts-机构负责人, tel-手机号码, phone-联系电话, email-邮箱地址, address-联系地址,
	 *         sort_num-排序号
	 */
	public ParaMap getDept(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("dept", "getDept");
		String dynamicSql = "";
		String deptId = inMap.getString("dept_id");
		if (StrUtils.isNotNull(deptId)) {
			dynamicSql += " AND d.id = ? ";
			sqlMap.addParam(deptId);
		}
		String deptCode = inMap.getString("dept_code");
		if (StrUtils.isNotNull(deptCode)) {
			dynamicSql += " AND d.dept_code = ? ";
			sqlMap.addParam(deptCode);
		}
		String deptName = inMap.getString("dept_name");
		if (StrUtils.isNotNull(deptName)) {
			dynamicSql += " AND d.dept_name = ? ";
			sqlMap.addParam(deptName);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 修改部门信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            dept_id-部门id, org_id-机构id, dept_name-部门名称, dept_code-部门编码,
	 *            easy_name-部门简称, parent_id-部门父id, contacts-机构负责人, tel-手机号码,
	 *            phone-联系电话, email-邮箱地址, address-联系地址, sort_num-排序号
	 * @return
	 */
	public ParaMap updateDept(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("dept", "updateDept");
		sqlMap.addParam(inMap.getString("dept_name"));
		sqlMap.addParam(inMap.getString("dept_code"));
		sqlMap.addParam(inMap.getString("easy_name"));
		sqlMap.addParam(inMap.getString("parent_id"));
		sqlMap.addParam(inMap.getString("contacts"));
		sqlMap.addParam(inMap.getString("tel"));
		sqlMap.addParam(inMap.getString("phone"));
		sqlMap.addParam(inMap.getString("email"));
		sqlMap.addParam(inMap.getString("address"));
		sqlMap.addParam(inMap.getInt("sort_num"));
		sqlMap.addParam(inMap.getString("dept_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 禁用/启动部门信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param deptId
	 *            部门id
	 * @param status
	 *            状态
	 * @return
	 */
	public ParaMap enableDept(String deptId, Integer status) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("dept", "enableDept");
		sqlMap.addParam(status);
		sqlMap.addParam(deptId);
		ParaMap outMap = update(sqlMap);
		return outMap;
	}
}
