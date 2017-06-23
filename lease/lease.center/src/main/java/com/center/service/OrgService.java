package com.center.service;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.consts.CenterConsts;
import com.center.dao.OrgDao;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;

/**
 * 组织机构服务
 * 
 * @author 唐宗鸿
 * @date 20170509
 * @version 1.1.0
 */
public class OrgService {

	private OrgDao orgDao = new OrgDao();

	/**
	 * 查询机构列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
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
		ParaMap resultMap = orgDao.getOrgList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}

	/**
	 * 新增机构信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            sys_id-系统id, org_name-机构名称, org_code-机构编码, easy_name-机构简称,
	 *            contacts-机构联系人, tel-手机号码, phone-联系电话, email-邮箱地址,
	 *            address-联系地址, sort_num-排序号
	 * @return org_id-机构id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addOrg(ParaMap inMap) throws Exception {
		String orgName = inMap.getString("org_name");
		String orgCode = inMap.getString("org_code");
		if (StrUtils.isNull(inMap.getString("sys_id")) || StrUtils.isNull(orgName) || StrUtils.isNull(orgCode)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = orgDao.checkOrg(null, orgCode, null);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "org.code.exist");
			return outMap;
		}
		resultMap = orgDao.checkOrg(null, null, orgName);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "org.name.exist");
			return outMap;
		}
		inMap.put("org_id", IDGenerator.newGUID());
		resultMap = orgDao.addOrg(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "org.add.fail");
			return outMap;
		}
		ParaMap outMap = new ParaMap();
		outMap.put("org_id", inMap.getString("org_id"));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 获取机构基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            org_id-机构id,org_code-机构编码,org_name-机构名称
	 * @return org_id-机构id, sys_id-系统id, sys_code-系统编号, sys_name-系统名称,
	 *         org_name-机构名称, org_code-机构编码, easy_name-机构简称, contacts-联系人,
	 *         tel-手机号码, phone-联系电话, email-邮箱地址, address-联系地址, sort_num-排序号,
	 *         status-状态，0-禁用，1-启用, createtime-创建时间,
	 *         updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getOrg(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("org_id")) && StrUtils.isNull(inMap.getString("org_code"))
				&& StrUtils.isNull(inMap.getString("org_name"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = orgDao.getOrg(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 修改机构信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            org_id-机构id, org_name-机构名称, org_code-机构编码, easy_name-机构简称,
	 *            contacts-机构负责人, tel-手机号码, phone-联系电话, email-邮箱地址,
	 *            address-联系地址, sort_num-排序号
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateOrg(ParaMap inMap) throws Exception {
		String orgId = inMap.getString("org_id");
		String orgCode = inMap.getString("org_code");
		String orgName = inMap.getString("org_name");
		if (StrUtils.isNull(inMap.getString("org_id")) || StrUtils.isNull(inMap.getString("org_code"))
				|| StrUtils.isNull(inMap.getString("org_name"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = orgDao.checkOrg(orgId, orgCode, null);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "org.code.exist");
			return outMap;
		}
		resultMap = orgDao.checkOrg(orgId, null, orgName);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "org.name.exist");
			return outMap;
		}
		resultMap = orgDao.updateOrg(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "org.update.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}

	/**
	 * 禁用/启用该机构
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            org_id-机构id, status-状态，0-禁用，1-启用
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap enableOrg(ParaMap inMap) throws Exception {
		String orgId = inMap.getString("org_id");
		if (StrUtils.isNull(orgId) || StrUtils.isNull(inMap.getString("status"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		int status = inMap.getInt("status");
		if (!(CenterConsts.DISABLED == status || CenterConsts.ENABLED == status)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.error");
			return outMap;
		}
		ParaMap resultMap = orgDao.enableOrg(orgId, status);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "org.enable.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}
}
