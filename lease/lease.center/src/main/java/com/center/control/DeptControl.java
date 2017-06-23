package com.center.control;

import com.base.utils.ParaMap;
import com.center.service.DeptService;

/**
 * 部门服务控制器
 * 
 * @author 唐宗鸿
 * @date 20170512
 * @version 1.1.0
 */
public class DeptControl {

	private DeptService deptService = new DeptService();

	/**
	 * 查询部门列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            org_id-机构id, dept_name-部门名称 支持模糊查询, dept_code-部门编码 支持模糊查询,
	 *            is_page-是否分页(1-不分页，0-分页),page_size-每页记录数,page_index-第几页
	 * @return dept_id-部门id, org_id-组织机构id, org_code-组织机构编号, org_name-组织机构名称,
	 *         dept_name-部门名称, dept_code-部门编码, easy_name-部门简称, parent_id-部门父id,
	 *         contacts-部门负责人, tel-手机号码, phone-联系电话, email-邮箱地址, address-联系地址,
	 *         sort_num-排序号,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getDeptList(ParaMap inMap) throws Exception {
		return deptService.getDeptList(inMap);
	}

	/**
	 * 新增部门信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            org_id-机构id, dept_name-部门名称, dept_code-部门编码, easy_name-部门简称,
	 *            parent_id-部门父id, contacts-机构负责人, tel-手机号码, phone-联系电话,
	 *            email-邮箱地址, address-联系地址, sort_num-排序号
	 * @return dept_id-部门id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addDept(ParaMap inMap) throws Exception {
		return deptService.addDept(inMap);
	}

	/**
	 * 获取部门基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            dept_id-部门id,dept_code-部门编码,dept_name-部门名称
	 * @return dept_id-部门id, org_id-组织机构id, org_code-组织机构编号, org_name-组织机构名称,
	 *         dept_name-部门名称, dept_code-部门编码, easy_name-部门简称, parent_id-部门父id,
	 *         contacts-机构负责人, tel-手机号码, phone-联系电话, email-邮箱地址, address-联系地址,
	 *         sort_num-排序号,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getDept(ParaMap inMap) throws Exception {
		return deptService.getDept(inMap);
	}

	/**
	 * 修改部门信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            dept_id-部门id, org_id-机构id, dept_name-部门名称, dept_code-部门编码,
	 *            easy_name-部门简称, parent_id-部门父id, contacts-机构负责人, tel-手机号码,
	 *            phone-联系电话, email-邮箱地址, address-联系地址, sort_num-排序号
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateDept(ParaMap inMap) throws Exception {
		return deptService.updateDept(inMap);
	}

	/**
	 * 禁用/启用该部门
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            dept_id-机构id, status-状态，0-禁用，默认，1-启用
	 * @return
	 */
	public ParaMap enableDept(ParaMap inMap) throws Exception {
		return deptService.enableDept(inMap);
	}
}
