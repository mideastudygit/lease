package com.center.control;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.base.web.AppConfig;
import com.center.consts.CenterConsts;
import com.center.service.UserService;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.util.RespUtil;

public class UserControl {

	private UserService userService = new UserService();

	/**
	 * 查询用户列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            org_id-机构id, dept_id-部门id, user_name-用户账户, real_name-用户名称
	 * @return user_id-用户id, org_id-机构id, org_name-机构名称, dept_id-部门id,
	 *         dept_name-部门名称, role_id-角色id, role_name-角色名称, user_name-用户账号,
	 *         real_name-用户名称, sex-用户性别，0-女，1-男, tel-手机号码, phone-联系电话,
	 *         email-联系邮箱, address-联系地址, status-状态,0-禁用,1-启用, sort_num-排序号,
	 *         createtime-创建时间, updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getUserList(ParaMap inMap) throws Exception {
		return userService.getUserList(inMap);
	}

	/**
	 * 新增用户信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            org_id-机构id, dept_id-部门id, role_id-角色id, user_name-用户账号,
	 *            user_pwd-用户密码, real_name-用户名称, sex-用户性别，0-女，1-男, tel-手机号码,
	 *            phone-联系电话, email-联系邮箱, address-联系地址, sort_num-排序号
	 * @return user_id-用户id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addUser(ParaMap inMap) throws Exception {
		return userService.addUser(inMap);
	}

	/**
	 * 获取用户基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            user_id-用户id
	 * @return user_id-用户id, org_id-机构id, dept_id-部门id, role_id-角色id,
	 *         user_name-用户账号, real_name-用户名称, sex-用户性别，0-女，1-男, tel-手机号码,
	 *         phone-联系电话, email-联系邮箱, address-联系地址, sort_num-排序号,
	 *         status-状态，0-禁用，1-启用, createtime-创建时间,
	 *         updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getUser(ParaMap inMap) throws Exception {
		ParaMap resultMap = userService.getUser(inMap);
		ParaMap outMap = RespUtil.setResp(resultMap);
		return outMap;
	}

	/**
	 * 修改用户信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            user_id-用户id, org_id-机构id, dept_id-部门id, role_id-角色id,
	 *            user_name-用户账号, real_name-用户名称, sex-用户性别，0-女，1-男, tel-手机号码,
	 *            phone-联系电话, email-邮箱地址, address-联系地址, sort_num-排序号
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateUser(ParaMap inMap) throws Exception {
		return userService.updateUser(inMap);
	}

	/**
	 * 禁用/启用该用户
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            user_id-用户id, status-状态，0-禁用，1-启用
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap enableUser(ParaMap inMap) throws Exception {
		return userService.enableUser(inMap);
	}

	/**
	 * 修改用户密码
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            user_id-用户id, user_pwd-用户原密码, new_pwd-用户新密码
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateUserPassword(ParaMap inMap) throws Exception {
		return userService.updateUserPassword(inMap);
	}

	/**
	 * 重置用户密码
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            user_id-用户id
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap resetUserPassword(ParaMap inMap) throws Exception {
		String defaultPwd = AppConfig.getStringPro("user.default.password");
		inMap.put("new_pwd", defaultPwd);
		return userService.resetUserPassword(inMap);
	}

	/**
	 * 用户登录
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            sys_code-系统编码, user_name-用户账号, user_pwd-用户密码
	 * @return real_name-用户名称, org_id-机构id, org_code-机构编码, org_name-机构名称,
	 *         dept_id-部门id, dept_code-部门编码,
	 *         dept_name-部门名称,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap userLogin(ParaMap inMap) throws Exception {
		String sysCode = inMap.getString("sys_code");
		String userName = inMap.getString("user_name");
		String userPwd = inMap.getString("user_pwd");
		if (StrUtils.isNull(sysCode) || StrUtils.isNull(userName) || StrUtils.isNull(userPwd)) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		ParaMap resultMap = userService.checkUserName(userName);
		if (((ParaMap) resultMap.get(RespKey.DATA.getValue())).getInt("num") < 1) {
			return RespUtil.setResp(RespState.FAIL, "user.name.notexist");
		}
		resultMap = userService.userLogin(userName, userPwd, sysCode);
		ParaMap dataMap = ((ParaMap) resultMap.get(RespKey.DATA.getValue()));
		if (dataMap.isEmpty()) {
			return RespUtil.setResp(RespState.FAIL, "user.pwd.notright");
		}
		if (dataMap.getInt("status") == CenterConsts.DISABLED) {
			return RespUtil.setResp(RespState.FAIL, "user.status.notright");
		}
		return resultMap;
	}

	/**
	 * 查询用户拥有资源
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            user_id-用户id
	 * @return res_id-资源id, res_name-资源名称, show_name-显示名称, parent_id-资源父id,
	 *         res_type-资源类型 0-菜单 1-页面元素, res_icon-资源标识, url-访问路径,
	 *         status-状态，0-禁用，1-启用, sort_num-排序号,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getUserResource(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("user_id"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		return userService.getUserResource(inMap.getString("user_id"));
	}

}
