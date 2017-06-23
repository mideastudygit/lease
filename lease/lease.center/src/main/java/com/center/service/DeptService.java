package com.center.service;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.consts.CenterConsts;
import com.center.dao.DeptDao;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;

/**
 * 部门服务
 * 
 * @author 唐宗鸿
 * @date 20170509
 * @version 1.1.0
 */
public class DeptService {

	private DeptDao deptDao = new DeptDao();

	/**
	 * 查询部门列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            org_id-机构id, dept_name-部门名称 支持模糊查询, dept_code-部门编码 支持模糊查询,
	 *            is_page-是否分页(1-不分页，0-分页),page_size-每页记录数,page_index-第几页
	 * @return dept_id-部门id, org_id-组织机构id, org_code-组织机构编号, org_name-组织机构名称,
	 *         dept_name-部门名称, dept_code-部门编码, easy_name-部门简称, parent_id-部门父id,
	 *         contacts-部门负责人, tel-手机号码, phone-联系电话, email-邮箱地址, address-联系地址,
	 *         sort_num-排序号,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getDeptList(ParaMap inMap) throws Exception {
		ParaMap resultMap = deptDao.getDeptList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}

	/**
	 * 新增部门信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            org_id-机构id, dept_name-部门名称, dept_code-部门编码, easy_name-部门简称,
	 *            parent_id-部门父id, contacts-机构负责人, tel-手机号码, phone-联系电话,
	 *            email-邮箱地址, address-联系地址, sort_num-排序号
	 * @return dept_id-部门id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addDept(ParaMap inMap) throws Exception {
		String orgId = inMap.getString("org_id");
		String deptCode = inMap.getString("dept_code");
		String deptName = inMap.getString("dept_name");
		if (StrUtils.isNull(orgId) || StrUtils.isNull(deptCode) || StrUtils.isNull(deptName)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = deptDao.checkDept(null, orgId, deptCode, null);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "dept.code.exist");
			return outMap;
		}
		resultMap = deptDao.checkDept(null, orgId, null, deptName);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "dept.name.exist");
			return outMap;
		}
		inMap.put("dept_id", IDGenerator.newGUID());
		resultMap = deptDao.addDept(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "dept.add.fail");
			return outMap;
		}
		ParaMap outMap = new ParaMap();
		outMap.put("dept_id", inMap.getString("dept_id"));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 获取部门基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            dept_id-部门id,dept_code-部门编码,dept_name-部门名称
	 * @return dept_id-部门id, org_id-组织机构id, org_code-组织机构编号, org_name-组织机构名称,
	 *         dept_name-部门名称, dept_code-部门编码, easy_name-部门简称, parent_id-部门父id,
	 *         contacts-机构负责人, tel-手机号码, phone-联系电话, email-邮箱地址, address-联系地址,
	 *         sort_num-排序号,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getDept(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("dept_id")) && StrUtils.isNull(inMap.getString("dept_code"))
				&& StrUtils.isNull(inMap.getString("dept_name"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = deptDao.getDept(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 修改部门信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            dept_id-部门id, org_id-机构id, dept_name-部门名称, dept_code-部门编码,
	 *            easy_name-部门简称, parent_id-部门父id, contacts-机构负责人, tel-手机号码,
	 *            phone-联系电话, email-邮箱地址, address-联系地址, sort_num-排序号
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateDept(ParaMap inMap) throws Exception {
		String deptId = inMap.getString("dept_id");
		String orgId = inMap.getString("org_id");
		String deptCode = inMap.getString("dept_code");
		String deptName = inMap.getString("dept_name");
		if (StrUtils.isNull(orgId) || StrUtils.isNull(deptId) || StrUtils.isNull(deptCode) || StrUtils.isNull(deptName)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = deptDao.checkDept(deptId, orgId, deptCode, null);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "dept.code.exist");
			return outMap;
		}
		resultMap = deptDao.checkDept(deptId, orgId, null, deptName);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "dept.name.exist");
			return outMap;
		}
		resultMap = deptDao.updateDept(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "dept.update.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}

	/**
	 * 禁用/启用该部门
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            dept_id-机构id, status-状态，0-禁用，默认，1-启用
	 * @return
	 */
	public ParaMap enableDept(ParaMap inMap) throws Exception {
		String deptId = inMap.getString("dept_id");
		if (StrUtils.isNull(deptId)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		int status = inMap.getInt("status");
		if (!(CenterConsts.DISABLED == status || CenterConsts.ENABLED == status)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.error");
			return outMap;
		}
		ParaMap resultMap = deptDao.enableDept(deptId, status);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "dept.enable.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}
}
