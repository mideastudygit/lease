package com.center.service;

import com.base.utils.MD5;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.base.web.AppConfig;
import com.center.dao.AuthDao;
import com.common.consts.RespConsts.RespKey;
import com.common.util.RespUtil;

public class AuthService {

	private AuthDao authDao = new AuthDao();

	/**
	 * 添加商户权限信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170530
	 * @param inMap
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addMerchantAuth(ParaMap inMap) throws Exception {
		String merchantName = inMap.getString("merchant_name");
		String merchantId = inMap.getString("merchant_id");
		String tel = inMap.getString("tel");
		ParaMap sendMap = new ParaMap();
		sendMap.put("sys_id", AppConfig.getStringPro("default.system.id"));
		sendMap.put("org_name", merchantName);
		sendMap.put("org_code", merchantId);
		sendMap.put("dept_name", merchantName);
		sendMap.put("dept_code", merchantId);
		sendMap.put("user_name", tel);
		sendMap.put("real_name", inMap.getString("admin_name"));
		sendMap.put("tel", tel);
		// 新增默认机构
		OrgService orgService = new OrgService();
		ParaMap resultMap = orgService.addOrg(sendMap);
		sendMap.put("org_id", ((ParaMap) resultMap.get(RespKey.DATA.getValue())).getString("org_id"));
		// 新增默认部门
		DeptService deptService = new DeptService();
		deptService.addDept(sendMap);
		// 新增默认角色
		sendMap.put("role_name", merchantName);
		RoleService roleService = new RoleService();
		resultMap = roleService.addRole(sendMap);
		sendMap.put("role_id", ((ParaMap) resultMap.get(RespKey.DATA.getValue())).getString("role_id"));
		// 新增用户信息
		sendMap.put("user_pwd", MD5.MD5Encode(inMap.getString("password")));
		UserService userService = new UserService();
		userService.addUser(sendMap);
		return RespUtil.setResp();
	}

	/**
	 * 修改商户权限信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170530
	 * @param inMap
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateMerchantAuth(ParaMap inMap) throws Exception {
		String merchantName = inMap.getString("merchant_name");
		String merchantId = inMap.getString("merchant_id");
		String oldMerchantName = inMap.getString("old_merchant_name");
		authDao.updateOrg(merchantId, merchantName);// 修改机构名称
		authDao.updateDept(merchantId, merchantName);// 修改部门名称
		authDao.updateRole(merchantName, oldMerchantName);// 修改角色名称
		return RespUtil.setResp();
	}

	/**
	 * 修改商户用户信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170530
	 * @param inMap
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateMerchantUser(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("real_name", inMap.getString("admin_name"));
		sendMap.put("user_name", inMap.getString("tel"));
		String password = inMap.getString("password");
		if (StrUtils.isNotNull(password)) {
			sendMap.put("user_pwd", MD5.MD5Encode(password));
		}
		sendMap.put("old_user_name", inMap.getString("old_tel"));
		authDao.updateUser(sendMap);
		return RespUtil.setResp();
	}
}
