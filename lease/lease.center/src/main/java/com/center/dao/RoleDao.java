package com.center.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;

/**
 * 角色数据操作类
 * 
 * @author 唐宗鸿
 * @date 20170315
 * @version 1.1.0
 */
public class RoleDao extends BaseDataSetDao {

	/**
	 * 查询角色列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170315
	 * @param inMap
	 * @return
	 */
	public ParaMap getRoleList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("role", "getRoleList");
		String dynamicSql = "";
		String platid = inMap.getString("sys_id");
		if (StrUtils.isNotNull(platid)) {
			dynamicSql += " AND r.sys_id = ?";
			sqlMap.addParam(platid);
		}
		String orgid = inMap.getString("org_id");
		if (StrUtils.isNotNull(orgid)) {
			dynamicSql += " AND r.org_id = ?";
			sqlMap.addParam(orgid);
		}
		String rolename = inMap.getString("role_name");
		if (StrUtils.isNotNull(rolename)) {
			dynamicSql += " AND r.role_name LIKE ?";
			sqlMap.addParam("%" + rolename + "%");
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
	 * 新增角色信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170315
	 * @param inMap
	 * @return
	 */
	public ParaMap addRole(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("role", "addRole");
		sqlMap.addParam(inMap.getString("role_id"));
		sqlMap.addParam(inMap.getString("sys_id"));
		sqlMap.addParam(inMap.getString("org_id"));
		sqlMap.addParam(inMap.getString("role_name"));
		sqlMap.addParam(inMap.getInt("sort_num"));
		ParaMap outMap = insert(sqlMap);
		return outMap;
	}

	/**
	 * 校验相同机构下的角色名称的唯一性
	 * 
	 * @author 唐宗鸿
	 * @date 20170315
	 * @param roleId
	 *            角色id
	 * @param roleName
	 *            角色名称
	 * @param orgId
	 *            机构id
	 * @return
	 */
	public ParaMap checkRole(String roleId, String roleName, String orgId) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("role", "getRole");
		String dynamicSql = "";
		if (StrUtils.isNotNull(roleId)) {
			dynamicSql += " AND r.id != ? ";
			sqlMap.addParam(roleId);
		}
		if (StrUtils.isNotNull(roleName)) {
			dynamicSql += " AND r.role_name = ? ";
			sqlMap.addParam(roleName);
		}
		if (StrUtils.isNotNull(orgId)) {
			dynamicSql += " AND r.org_id = ? ";
			sqlMap.addParam(orgId);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 获取角色基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170315
	 * @param inMap
	 * @return
	 */
	public ParaMap getRole(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("role", "getRole");
		String dynamicSql = "";
		String roleid = inMap.getString("role_id");
		if (StrUtils.isNotNull(roleid)) {
			dynamicSql += " AND r.id = ? ";
			sqlMap.addParam(roleid);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 修改角色信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170315
	 * @param inMap
	 * @return
	 */
	public ParaMap updateRole(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("role", "updateRole");
		sqlMap.addParam(inMap.getString("sys_id"));
		sqlMap.addParam(inMap.getString("org_id"));
		sqlMap.addParam(inMap.getString("role_name"));
		sqlMap.addParam(inMap.getInt("sort_num"));
		sqlMap.addParam(inMap.getString("role_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 禁用/启用角色信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170315
	 * @param id
	 *            角色id
	 * @param status
	 *            状态
	 * @return
	 */
	public ParaMap enableRole(String id, Integer status) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("role", "enableRole");
		sqlMap.addParam(status);
		sqlMap.addParam(id);
		ParaMap outMap = update(sqlMap);
		return outMap;
	}
}
