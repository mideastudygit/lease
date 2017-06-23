package com.center.service;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.consts.CenterConsts;
import com.center.dao.RoleDao;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;

/**
 * 角色服务
 * 
 * @author 唐宗鸿
 * @date 20170510
 * @version 1.1.0
 */
public class RoleService {

	private RoleDao roleDao = new RoleDao();

	/**
	 * 查询角色列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            sys_id-系统id, org_id-组织机构id, role_name-角色名称 支持模糊查询,
	 *            is_page-是否分页(1-不分页，0-分页),page_size-每页记录数,page_index-第几页
	 * @return role_id-角色id, sys_id-系统id, sys_code-系统编号, sys_name-系统名称,
	 *         org_id-组织机构id, org_code-机构编号, org_name-机构名称, role_name-角色名称,
	 *         status-状态,0-禁用,1-启用, sort_num-排序号, createtime-创建时间,
	 *         updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getRoleList(ParaMap inMap) throws Exception {
		ParaMap resultMap = roleDao.getRoleList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}

	/**
	 * 新增角色信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            sys_id-系统id, org_id-机构id, role_name-角色名称, sort_num-排序号
	 * @return role_id-角色id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addRole(ParaMap inMap) throws Exception {
		String orgId = inMap.getString("org_id");
		String roleName = inMap.getString("role_name");
		if (StrUtils.isNull(inMap.getString("sys_id")) || StrUtils.isNull(orgId) || StrUtils.isNull(roleName)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = roleDao.checkRole(null, roleName, orgId);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "role.name.exist");
			return outMap;
		}
		inMap.put("role_id", IDGenerator.newGUID());
		resultMap = roleDao.addRole(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "role.add.fail");
			return outMap;
		}
		ParaMap outMap = new ParaMap();
		outMap.put("role_id", inMap.getString("role_id"));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 获取角色基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            role_id-角色id,role_name-角色名称
	 * @return role_id-角色id, sys_id-系统id, sys_code-系统编号, sys_name-系统名称,
	 *         org_id-机构id, role_name-角色名称, sort_num-排序号, status-状态,0-禁用,1-启用,
	 *         createtime-创建时间, updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getRole(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("role_id")) && StrUtils.isNull(inMap.getString("role_name"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = roleDao.getRole(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 修改角色信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            sys_id-系统id, org_id-机构id, role_name-角色名称, sort_num-排序号
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateRole(ParaMap inMap) throws Exception {
		String roleId = inMap.getString("role_id");
		String orgId = inMap.getString("org_id");
		String roleName = inMap.getString("role_name");
		if (StrUtils.isNull(roleId) || StrUtils.isNull(roleName) || StrUtils.isNull(inMap.getString("sys_id"))
				|| StrUtils.isNull(orgId)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = roleDao.checkRole(roleId, roleName, orgId);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "role.name.exist");
			return outMap;
		}
		resultMap = roleDao.updateRole(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "role.update.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}

	/**
	 * 禁用该角色
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            role_id-角色id, status-状态，0-禁用，1-启用
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap enableRole(ParaMap inMap) throws Exception {
		String roleId = inMap.getString("role_id");
		if (StrUtils.isNull(roleId) || StrUtils.isNull(inMap.getString("status"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		int status = inMap.getInt("status");
		if (!(CenterConsts.DISABLED == status || CenterConsts.ENABLED == status)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.error");
			return outMap;
		}
		ParaMap resultMap = roleDao.enableRole(roleId, status);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "role.enable.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}
}
