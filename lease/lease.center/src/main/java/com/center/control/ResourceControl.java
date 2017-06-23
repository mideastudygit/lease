package com.center.control;

import com.base.utils.ParaMap;
import com.center.service.ResourceService;

/**
 * 资源控制器
 * 
 * @author 唐宗鸿
 * @date 20170512
 * @version 1.1.0
 */
public class ResourceControl {

	private ResourceService resourceService = new ResourceService();

	/**
	 * 查询菜单资源列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            sys_id-系统id, res_name-资源名称 支持模糊查询, status-状态,0-禁用,1-启用,
	 *            is_page-是否分页(1-不分页，0-分页), page_index-第几页, page_size-每页记录数
	 * @return res_id-资源id, sys_id-系统id, sys_code-系统编号, sys_name-系统名称,
	 *         res_name-资源名称, show_name-资源显示名称, res_type-资源类型 1-页面元素,
	 *         res_icon-资源标识, url-资源访问路径, parent_id-父资源id, status-状态,0-禁用,1-启用,
	 *         sort_num-排序号,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getResourceList(ParaMap inMap) throws Exception {
		return resourceService.getResourceList(inMap);
	}

	/**
	 * 新增菜单资源信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            sys_id-系统id, res_name-资源名称,show_name-显示名称,
	 *            res_type-资源类型，1-页面元素, res_icon-资源标识, url-访问路径,
	 *            parent_id-父资源id, sort_num-排序号
	 * @return res_id-资源id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addResource(ParaMap inMap) throws Exception {
		return resourceService.addResource(inMap);
	}

	/**
	 * 获取菜单资源基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            res_id-资源id,res_name-资源名称
	 * @return res_id-资源id, sys_id-系统id, sys_code-系统编号, sys_name-系统名称,
	 *         res_name-资源名称, show_name-显示名称, res_type-资源类型, res_icon-资源标识,
	 *         url-访问路径, parent_id-父资源id, status-状态,0-禁用,1-启用, sort_num-排序号,
	 *         createtime-创建时间, updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getResource(ParaMap inMap) throws Exception {
		return resourceService.getResource(inMap);
	}

	/**
	 * 修改菜单资源信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            res_id-资源id, sys_id-系统id, res_name-资源名称, show_name-显示名称,
	 *            res_type-资源类型, res_icon-资源标识, url-访问路径, parent_id-父资源id,
	 *            sort_num-排序号
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateResource(ParaMap inMap) throws Exception {
		return resourceService.updateResource(inMap);
	}

	/**
	 * 禁用/启用该菜单资源
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            res_id-资源id, status-状态，0-禁用，默认，1-启用
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap enableResource(ParaMap inMap) throws Exception {
		return resourceService.enableResource(inMap);
	}
}
