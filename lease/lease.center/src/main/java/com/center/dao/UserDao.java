package com.center.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;

/**
 * 用户数据操作类
 * 
 * @author 唐宗鸿
 * @date 20170510
 * @version 1.1.0
 */
public class UserDao extends BaseDataSetDao {

	/**
	 * 查询用户列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            sys_id-系统id,org_id-机构id, dept_id-部门id, user_name-用户账户,
	 *            real_name-用户名称
	 * @return user_id-用户id, org_id-机构id, org_name-机构名称, dept_id-部门id,
	 *         dept_name-部门名称, role_id-角色id, role_name-角色名称, user_name-用户账号,
	 *         real_name-用户名称, sex-用户性别，0-女，1-男, tel-手机号码, phone-联系电话,
	 *         email-联系邮箱, address-联系地址, status-状态，0-禁用，1-启用, sort_num-排序号,
	 *         createtime-创建时间, updatetime-更新时间
	 */
	public ParaMap getUserList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("user", "getUserList");
		String dynamicSql = "";
		String username = inMap.getString("user_name");
		if (StrUtils.isNotNull(username)) {
			dynamicSql += " AND u.user_name LIKE ?";
			sqlMap.addParam("%" + username + "%");
		}
		String realname = inMap.getString("real_name");
		if (StrUtils.isNotNull(realname)) {
			dynamicSql += " AND u.real_name LIKE ?";
			sqlMap.addParam("%" + realname + "%");
		}
		String roleid = inMap.getString("role_id");
		if (StrUtils.isNotNull(roleid)) {
			dynamicSql += " AND u.role_id = ?";
			sqlMap.addParam(roleid);
		}
		String sysId = inMap.getString("sys_id");
		if (StrUtils.isNotNull(sysId)) {
			dynamicSql += " AND s.id = ?";
			sqlMap.addParam(sysId);
		}
		String orgid = inMap.getString("org_id");
		if (StrUtils.isNotNull(orgid)) {
			dynamicSql += " AND u.org_id = ?";
			sqlMap.addParam(orgid);
		}
		String deptid = inMap.getString("dept_id");
		if (StrUtils.isNotNull(deptid)) {
			dynamicSql += " AND u.dept_id = ?";
			sqlMap.addParam(deptid);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		sqlMap.put("pageIndex", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}

	/**
	 * 新增用户信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            org_id-机构id, dept_id-部门id, role_id-角色id, user_name-用户账号,
	 *            user_pwd-用户密码, real_name-用户名称, sex-用户性别，0-女，1-男, tel-手机号码,
	 *            phone-联系电话, email-联系邮箱, address-联系地址, sort_num-排序号
	 * @return
	 */
	public ParaMap addUser(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("user", "addUser");
		sqlMap.addParam(inMap.getString("user_id"));
		sqlMap.addParam(inMap.getString("org_id"));
		sqlMap.addParam(inMap.getString("dept_id"));
		sqlMap.addParam(inMap.getString("role_id"));
		sqlMap.addParam(inMap.getString("user_name"));
		sqlMap.addParam(inMap.getString("user_pwd"));
		sqlMap.addParam(inMap.getString("real_name"));
		sqlMap.addParam(inMap.getString("sex"));
		sqlMap.addParam(inMap.getString("tel"));
		sqlMap.addParam(inMap.getString("phone"));
		sqlMap.addParam(inMap.getString("email"));
		sqlMap.addParam(inMap.getString("address"));
		sqlMap.addParam(inMap.getInt("sort_num"));
		ParaMap outMap = insert(sqlMap);
		return outMap;
	}

	/**
	 * 校验用户账号的唯一性
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param userId
	 *            用户id
	 * @param userName
	 *            用户账号
	 * @return
	 */
	public ParaMap checkUser(String userId, String username) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("user", "getUser");
		String dynamicSql = "";
		if (StrUtils.isNotNull(userId)) {
			dynamicSql += " AND u.id != ? ";
			sqlMap.addParam(userId);
		}
		if (StrUtils.isNotNull(username)) {
			dynamicSql += " AND u.user_name = ? ";
			sqlMap.addParam(username);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 获取用户基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 * @return user_id-用户id, org_id-机构id, dept_id-部门id, role_id-角色id,
	 *         user_name-用户账号, real_name-用户名称, sex-用户性别，0-女，1-男, tel-手机号码,
	 *         phone-联系电话, email-联系邮箱, address-联系地址, sort_num-排序号,
	 *         status-状态，0-禁用，1-启用, createtime-创建时间, updatetime-更新时间
	 */
	public ParaMap getUser(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("user", "getUser");
		String dynamicSql = "";
		String userid = inMap.getString("user_id");
		if (StrUtils.isNotNull(userid)) {
			dynamicSql += " AND u.id = ? ";
			sqlMap.addParam(userid);
		}
		String username = inMap.getString("user_name");
		if (StrUtils.isNotNull(username)) {
			dynamicSql += " AND u.user_name = ? ";
			sqlMap.addParam(username);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 修改用户信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 * @return user_id-用户id, org_id-机构id, dept_id-部门id, role_id-角色id,
	 *         user_name-用户账号, real_name-用户名称, sex-用户性别，0-女，1-男, tel-手机号码,
	 *         phone-联系电话, email-邮箱地址, address-联系地址, sort_num-排序号
	 */
	public ParaMap updateUser(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("user", "updateUser");
		sqlMap.addParam(inMap.getString("org_id"));
		sqlMap.addParam(inMap.getString("dept_id"));
		sqlMap.addParam(inMap.getString("role_id"));
		sqlMap.addParam(inMap.getString("user_name"));
		sqlMap.addParam(inMap.getString("real_name"));
		sqlMap.addParam(inMap.getString("sex"));
		sqlMap.addParam(inMap.getString("tel"));
		sqlMap.addParam(inMap.getString("phone"));
		sqlMap.addParam(inMap.getString("email"));
		sqlMap.addParam(inMap.getString("address"));
		sqlMap.addParam(inMap.getInt("sort_num"));
		sqlMap.addParam(inMap.getString("user_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 禁用/启用用户
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param userId
	 *            用户id
	 * @param status
	 *            状态
	 * @return
	 */
	public ParaMap enableUser(String userId, Integer status) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("user", "enableUser");
		sqlMap.addParam(status);
		sqlMap.addParam(userId);
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 修改用户密码
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param userId
	 *            用户id
	 * @param newPwd
	 *            新密码
	 * @return
	 */
	public ParaMap updateUserPassword(String userId, String newPwd) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("user", "updateUserPassword");
		sqlMap.addParam(newPwd);
		sqlMap.addParam(userId);
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 校验用户密码
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param userId
	 *            用户id
	 * @param userPwd
	 *            -密码
	 * @return
	 */
	public ParaMap checkUserPassword(String userId, String userPwd) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("user", "checkUserPassword");
		sqlMap.addParam(userPwd);
		sqlMap.addParam(userId);
		return query(sqlMap);
	}

	/**
	 * 用户登录
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param userName
	 *            用户账号
	 * @param userPwd
	 *            用户密码
	 * @param sysCode
	 *            系统编号
	 * @return real_name-用户名称, org_id-机构id, org_code-机构编码, org_name-机构名称,
	 *         dept_id-部门id, dept_code-部门编码, dept_name-部门名称,state-响应状态
	 */
	public ParaMap userLogin(String userName, String userPwd, String sysCode) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("user", "userLogin");
		sqlMap.addParam(userName);
		sqlMap.addParam(userPwd);
		sqlMap.addParam(sysCode);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 查询用户拥有资源
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param userId
	 *            用户id
	 * @return res_id-资源id, res_name-资源名称, show_name-显示名称, parent_id-资源父id,
	 *         res_type-资源类型 0-菜单 1-页面元素, res_icon-资源标识, url-访问路径,
	 *         status-状态，0-禁用，1-启用, sort_num-排序号
	 */
	public ParaMap getUserResource(String userId) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("user", "getUserResource");
		sqlMap.addParam(userId);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

}
