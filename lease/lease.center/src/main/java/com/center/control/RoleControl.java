package com.center.control;

import com.base.utils.ParaMap;
import com.center.service.RoleResourceService;
import com.center.service.RoleService;

/**
 * 角色控制器
 * 
 * @author 唐宗鸿
 * @date 20170512
 * @version 1.1.0
 */
public class RoleControl {

	private RoleService roleService = new RoleService();

	private RoleResourceService roleResourceService = new RoleResourceService();

	/**
	 * 查询角色列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            sys_id-系统id, org_id-组织机构id, role_name-角色名称 支持模糊查询,
	 *            is_page-是否分页(1-不分页，0-分页),page_size-每页记录数,page_index-第几页
	 * @return role_id-角色id, sys_id-系统id, sys_code-系统编号, sys_name-系统名称,
	 *         org_id-组织机构id, org_code-机构编号, org_name-机构名称, role_name-角色名称,
	 *         status-状态,0-禁用,1-启用, sort_num-排序号, createtime-创建时间,
	 *         updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getRoleList(ParaMap inMap) throws Exception {
		return roleService.getRoleList(inMap);
	}

	/**
	 * 新增角色信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            plat_id-系统id, org_id-机构id, role_name-角色名称, sort_num-排序号
	 * @return role_id-角色id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addRole(ParaMap inMap) throws Exception {
		return roleService.addRole(inMap);
	}

	/**
	 * 获取角色基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            role_id-角色id,role_name-角色名称
	 * @return role_id-角色id, sys_id-系统id, sys_code-系统编号, sys_name-系统名称,
	 *         org_id-机构id, role_name-角色名称, sort_num-排序号, status-状态,0-禁用,1-启用,
	 *         createtime-创建时间, updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getRole(ParaMap inMap) throws Exception {
		return roleService.getRole(inMap);
	}

	/**
	 * 修改角色信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            sys_id-系统id, org_id-机构id, role_name-角色名称, sort_num-排序号
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateRole(ParaMap inMap) throws Exception {
		return roleService.updateRole(inMap);
	}

	/**
	 * 禁用该角色
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            role_id-角色id, status-状态，0-禁用，1-启用
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap enableRole(ParaMap inMap) throws Exception {
		return roleService.enableRole(inMap);
	}

	/**
	 * 查询角色关联资源列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            role_id-角色id
	 * @return role_res_id-主键id, role_id-角色id, res_id-资源id,
	 *         show_name-资源显示名称,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getRoleResourceList(ParaMap inMap) throws Exception {
		return roleResourceService.getRoleResourceList(inMap);
	}

	/**
	 * 新增角色关联资源信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            role_id-角色id, res_id-资源id，多个时，中间用逗号隔开
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addRoleResource(ParaMap inMap) throws Exception {
		return roleResourceService.addRoleResource(inMap);
	}

	/**
	 * 删除角色关联资源信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            role_id-角色id,res_id-资源id，多个时，中间用逗号隔开
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap delRoleResource(ParaMap inMap) throws Exception {
		return roleResourceService.delRoleResource(inMap);
	}
}
