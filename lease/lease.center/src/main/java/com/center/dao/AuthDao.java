package com.center.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;

public class AuthDao extends BaseDataSetDao {

	/**
	 * 修改组织机构信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param org_name
	 *            机构名称
	 * @param org_code
	 *            机构编码
	 * @return
	 */
	public ParaMap updateOrg(String orgCode, String orgName) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("auth", "updateOrgName");
		sqlMap.addParam(orgName);
		sqlMap.addParam(DateUtils.nowTime());
		sqlMap.addParam(orgCode);
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 修改部门信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param dept_name
	 *            部门名称
	 * @param dept_code
	 *            部门编码
	 * @return
	 */
	public ParaMap updateDept(String deptCode, String deptName) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("auth", "updateDeptName");
		sqlMap.addParam(deptName);
		sqlMap.addParam(DateUtils.nowTime());
		sqlMap.addParam(deptCode);
		ParaMap outMap = update(sqlMap);
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
	public ParaMap updateRole(String roleName, String oldRoleName) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("auth", "updateRoleName");
		sqlMap.addParam(roleName);
		sqlMap.addParam(DateUtils.nowTime());
		sqlMap.addParam(oldRoleName);
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 修改用户信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 * @return
	 * 
	 */
	public ParaMap updateUser(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("auth", "updateUserName");
		sqlMap.addParam(DateUtils.nowTime());
		String dynamicSql = "";
		String userName = inMap.getString("user_name");
		if (StrUtils.isNotNull(userName)) {
			dynamicSql += " ,user_name = ?";
			sqlMap.addParam(userName);
		}
		String userPwd = inMap.getString("user_pwd");
		if (StrUtils.isNotNull(userPwd)) {
			dynamicSql += " ,user_pwd = ?";
			sqlMap.addParam(userPwd);
		}
		String realName = inMap.getString("real_name");
		if (StrUtils.isNotNull(realName)) {
			dynamicSql += " ,real_name = ?";
			sqlMap.addParam(realName);
		}
		sqlMap.addParam(inMap.getString("old_user_name"));
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = update(sqlMap);
		return outMap;
	}
}
