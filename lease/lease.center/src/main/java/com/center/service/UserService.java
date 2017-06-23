package com.center.service;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.consts.CenterConsts;
import com.center.dao.UserDao;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;

/**
 * 用户服务
 * 
 * @author 唐宗鸿
 * @date 20170510
 * @version 1.1.0
 */
public class UserService {

	private UserDao userDao = new UserDao();

	/**
	 * 查询用户列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            org_id-机构id, dept_id-部门id, user_name-用户账户, real_name-用户名称
	 * @return user_id-用户id, org_id-机构id, org_name-机构名称, dept_id-部门id,
	 *         dept_name-部门名称, role_id-角色id, role_name-角色名称, user_name-用户账号,
	 *         real_name-用户名称, sex-用户性别，0-女，1-男, tel-手机号码, phone-联系电话,
	 *         email-联系邮箱, address-联系地址, status-状态,0-禁用,1-启用, sort_num-排序号,
	 *         createtime-创建时间, updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getUserList(ParaMap inMap) throws Exception {
		ParaMap resultMap = userDao.getUserList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
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
	 * @return user_id-用户id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addUser(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("user_name")) || StrUtils.isNull(inMap.getString("user_pwd"))
				|| StrUtils.isNull(inMap.getString("org_id")) || StrUtils.isNull(inMap.getString("dept_id"))
				|| StrUtils.isNull(inMap.getString("role_id")) || StrUtils.isNull(inMap.getString("real_name"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = userDao.checkUser(null, inMap.getString("user_name"));
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "user.name.exist");
			return outMap;
		}
		inMap.put("user_id", IDGenerator.newGUID());
		resultMap = userDao.addUser(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "user.add.fail");
			return outMap;
		}
		ParaMap outMap = new ParaMap();
		outMap.put("user_id", inMap.getString("user_id"));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 获取用户基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            user_id-用户id
	 * @return user_id-用户id, org_id-机构id, dept_id-部门id, role_id-角色id,
	 *         user_name-用户账号, real_name-用户名称, sex-用户性别，0-女，1-男, tel-手机号码,
	 *         phone-联系电话, email-联系邮箱, address-联系地址, sort_num-排序号,
	 *         status-状态，0-禁用，1-启用, createtime-创建时间,
	 *         updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getUser(ParaMap inMap) throws Exception {
		ParaMap resultMap = userDao.getUser(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		return outMap;
	}

	/**
	 * 修改用户信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            user_id-用户id, org_id-机构id, dept_id-部门id, role_id-角色id,
	 *            user_name-用户账号, real_name-用户名称, sex-用户性别，0-女，1-男, tel-手机号码,
	 *            phone-联系电话, email-邮箱地址, address-联系地址, sort_num-排序号
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateUser(ParaMap inMap) throws Exception {
		String userId = inMap.getString("user_id");
		String userName = inMap.getString("user_name");
		if (StrUtils.isNull(userId) || StrUtils.isNull(userName) || StrUtils.isNull(inMap.getString("org_id"))
				|| StrUtils.isNull(inMap.getString("dept_id")) || StrUtils.isNull(inMap.getString("role_id"))
				|| StrUtils.isNull(inMap.getString("real_name"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = userDao.checkUser(userId, userName);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "user.name.exist");
			return outMap;
		}
		resultMap = userDao.updateUser(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "user.update.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}

	/**
	 * 禁用/启用该用户
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            user_id-用户id, status-状态，0-禁用，默认，1-启用
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap enableUser(ParaMap inMap) throws Exception {
		String userId = inMap.getString("user_id");
		if (StrUtils.isNull(userId)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		int status = inMap.getInt("status");
		if (!(CenterConsts.DISABLED == status || CenterConsts.ENABLED == status)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.error");
			return outMap;
		}
		ParaMap resultMap = userDao.enableUser(userId, status);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "org.enable.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}

	/**
	 * 修改用户密码
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            user_id-用户id, user_pwd-用户原密码, new_pwd-用户新密码
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateUserPassword(ParaMap inMap) throws Exception {
		String userId = inMap.getString("user_id");
		String userPwd = inMap.getString("user_pwd");
		String newPwd = inMap.getString("new_pwd");
		if (StrUtils.isNull(userId) || StrUtils.isNull(userPwd) || StrUtils.isNull(newPwd)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = userDao.checkUserPassword(userId, userPwd);
		if (resultMap.getRecordInt(0, "num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "user.pwd.not.right");
			return outMap;
		}
		resultMap = userDao.updateUserPassword(userId, newPwd);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "user.pwd.update.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}

	/**
	 * 重置用户密码
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            user_id-用户id
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap resetUserPassword(ParaMap inMap) throws Exception {
		String userId = inMap.getString("user_id");
		String newPwd = inMap.getString("new_pwd");
		if (StrUtils.isNull(userId) || StrUtils.isNull(newPwd)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = userDao.updateUserPassword(userId, newPwd);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "user.pwd.reset.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}

	/**
	 * 校验用户账号
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param user_name
	 *            用户账号
	 * @return num-用户账号数量，0-不存在，大于0账号已经存在,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap checkUserName(String userName) throws Exception {
		ParaMap resultMap = userDao.checkUser(null, userName);
		ParaMap outMap = new ParaMap();
		outMap.put("num", resultMap.getRecordCount());
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 用户登录
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            sys_code-系统编码, user_name-用户账号, user_pwd-用户密码
	 * @return real_name-用户名称, org_id-机构id, org_code-机构编码, org_name-机构名称,
	 *         dept_id-部门id, dept_code-部门编码,
	 *         dept_name-部门名称,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap userLogin(String userName, String userPwd, String sysCode) throws Exception {
		ParaMap resultMap = userDao.userLogin(userName, userPwd, sysCode);
		ParaMap outMap = RespUtil.setResp(ApiUtil.format(resultMap));
		return outMap;
	}

	/**
	 * 查询用户拥有资源
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            user_id-用户id
	 * @return res_id-资源id, res_name-资源名称, show_name-显示名称, parent_id-资源父id,
	 *         res_type-资源类型 0-菜单 1-页面元素, res_icon-资源标识, url-访问路径,
	 *         status-状态，0-禁用，1-启用, sort_num-排序号,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getUserResource(String userid) throws Exception {
		ParaMap resultMap = userDao.getUserResource(userid);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		return outMap;
	}

}
