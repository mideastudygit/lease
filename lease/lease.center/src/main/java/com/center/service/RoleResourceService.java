package com.center.service;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.dao.RoleResourceDao;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;

/**
 * 角色关联资源服务
 * 
 * @author 唐宗鸿
 * @date 20170510
 * @version 1.1.0
 */
public class RoleResourceService {

	private RoleResourceDao roleResourceDao = new RoleResourceDao();

	/**
	 * 查询角色关联资源列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            role_id-角色id
	 * @return role_res_id-主键id, role_id-角色id, res_id-资源id,
	 *         show_name-资源显示名称,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getRoleResourceList(ParaMap inMap) throws Exception {
		ParaMap resultMap = roleResourceDao.getRoleResourceList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		return outMap;
	}

	/**
	 * 新增角色关联资源信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            role_id-角色id, res_id-资源id，多个时，中间用逗号隔开
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addRoleResource(ParaMap inMap) throws Exception {
		String roleId = inMap.getString("role_id");
		String resId = inMap.getString("res_id");
		if (StrUtils.isNull(roleId) || StrUtils.isNull(resId)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		String[] resIds = resId.split(",");
		ArrayList<String> resList = new ArrayList<String>();
		for (String r : resIds) {
			resList.add(r);
		}
		ParaMap resultMap = roleResourceDao.getRoleResourceList(roleId, null);
		ArrayList<String> sqlResList = new ArrayList<String>();
		for (int i = 0, len = resultMap.getRecordCount(); i < len; i++) {
			String id = resultMap.getRecordString(i, "res_id");
			sqlResList.add(id);
		}
		ArrayList<String> difList = new ArrayList<String>();
		difList.addAll(sqlResList);
		difList.removeAll(resList);
		if (difList.size() > 0) {
			roleResourceDao.delRoleResource(roleId, StringUtils.join(difList.toArray(), ","));
		}
		
		ArrayList<String> difResList = new ArrayList<String>();
		difResList.addAll(resList);
		difResList.removeAll(sqlResList);
		if (difResList.size() > 0) {
			roleResourceDao.addRoleResource(roleId, StringUtils.join(difResList.toArray(), ","));
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}

	/**
	 * 删除角色关联资源信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            role_id-角色id,res_id-资源id，多个时，中间用逗号隔开
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap delRoleResource(ParaMap inMap) throws Exception {
		String roleId = inMap.getString("role_id");
		String resId = inMap.getString("res_id");
		if (StrUtils.isNull(roleId) || StrUtils.isNull(resId)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		roleResourceDao.delRoleResource(roleId, resId);
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}
}
