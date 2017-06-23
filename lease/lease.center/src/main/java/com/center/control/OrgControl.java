package com.center.control;

import com.base.utils.ParaMap;
import com.center.service.OrgService;

/**
 * 组织机构控制器
 * 
 * @author 唐宗鸿
 * @date 20170512
 * @version 1.1.0
 */
public class OrgControl {

	private OrgService orgService = new OrgService();

	/**
	 * 查询机构列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            sys_id-系统id, org_name-机构名称 支持模糊查询, org_code-机构编码 支持模糊查询,
	 *            is_page-是否分页(1-不分页，0-分页),page_size-每页记录数,page_index-第几页
	 * @return sys_id-系统id, sys_code-系统编号, sys_name-系统名称,org_id-机构id,
	 *         org_name-机构名称, org_code-机构编码, easy_name-机构简称, contacts-联系人,
	 *         tel-手机号码, phone-联系电话, email-联系邮箱, address-联系地址, sort_num-排序号,
	 *         status-状态，0-禁用，1-启用, createtime-创建时间,
	 *         updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getOrgList(ParaMap inMap) throws Exception {
		return orgService.getOrgList(inMap);
	}

	/**
	 * 新增机构信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            sys_id-系统id, org_name-机构名称, org_code-机构编码, easy_name-机构简称,
	 *            contacts-机构联系人, tel-手机号码, phone-联系电话, email-邮箱地址,
	 *            address-联系地址, sort_num-排序号
	 * @return org_id-机构id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addOrg(ParaMap inMap) throws Exception {
		return orgService.addOrg(inMap);
	}

	/**
	 * 获取机构基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            org_id-机构id,org_code-机构编码,org_name-机构名称
	 * @return org_id-机构id, sys_id-系统id, sys_code-系统编号, sys_name-系统名称,
	 *         org_name-机构名称, org_code-机构编码, easy_name-机构简称, contacts-联系人,
	 *         tel-手机号码, phone-联系电话, email-邮箱地址, address-联系地址, sort_num-排序号,
	 *         status-状态，0-禁用，1-启用, createtime-创建时间,
	 *         updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getOrg(ParaMap inMap) throws Exception {
		return orgService.getOrg(inMap);
	}

	/**
	 * 修改机构信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            org_id-机构id, sys_id-系统id, org_name-机构名称, org_code-机构编码,
	 *            easy_name-机构简称, contacts-机构负责人, tel-手机号码, phone-联系电话,
	 *            email-邮箱地址, address-联系地址, sort_num-排序号
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateOrg(ParaMap inMap) throws Exception {
		return orgService.updateOrg(inMap);
	}

	/**
	 * 禁用/启用该机构
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            org_id-机构id, status-状态，0-禁用，1-启用
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap enableOrg(ParaMap inMap) throws Exception {
		return orgService.enableOrg(inMap);
	}
}
