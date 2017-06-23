package com.center.service;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.consts.CenterConsts;
import com.center.dao.SystemDao;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;

/**
 * 系统服务
 * 
 * @author 唐宗鸿
 * @date 20170509
 * @version 1.1.0
 */
public class SystemService {

	private SystemDao sysDao = new SystemDao();

	/**
	 * 查询系统列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            sys_name-系统名称，支持模糊查询, sys_code-系统编码 ，支持模糊查询,
	 *            is_page-是否分页，1-不分页，0-分页,page_size-每页记录数,page_index-第几页
	 * @return sys_id-系统id, sys_name-系统名称, sys_code-系统编码, easy_name-系统简称,
	 *         url-访问路径, contacts-联系人, tel-手机号码, phone-联系电话, email-联系邮箱,
	 *         address-联系地址, sort_num-排序号, status-状态,0-禁用，1-启用, createtime-创建时间,
	 *         updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getSystemList(ParaMap inMap) throws Exception {
		ParaMap resultMap = sysDao.getSystemList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}

	/**
	 * 新增系统信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            sys_name-名称, sys_code-编码, easy_name-简称, url-访问路径,
	 *            contacts-负责人, tel-手机号码, phone-联系电话, email-邮箱地址, address-联系地址,
	 *            sort_num-排序号
	 * @return sys_id-系统id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addSystem(ParaMap inMap) throws Exception {
		String sysCode = inMap.getString("sys_code");
		String sysName = inMap.getString("sys_name");
		if (StrUtils.isNull(sysCode) || StrUtils.isNull(sysName)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = sysDao.checkSystem(null, sysCode, null);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "sys.code.exist");
			return outMap;
		}
		resultMap = sysDao.checkSystem(null, null, sysName);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "sys.name.exist");
			return outMap;
		}
		inMap.put("sys_id", IDGenerator.newGUID());
		resultMap = sysDao.addSystem(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "sys.add.fail");
			return outMap;
		}
		ParaMap outMap = new ParaMap();
		outMap.put("sys_id", inMap.getString("sys_id"));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 获取系统基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            sys_id-系统id,sys_code-系统编号,sys_name-系统名称
	 * @return sys_id-系统id, sys_name-系统名称, sys_code-系统编码, easy_name-系统简称,
	 *         url-访问路径, contacts-联系人, tel-手机号码, phone-联系电话, email-邮箱地址,
	 *         address-联系地址, sort_num-排序号, status-状态,0-禁用,1-启用, createtime-创建时间,
	 *         updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getSystem(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("sys_id")) && StrUtils.isNull(inMap.getString("sys_code"))
				&& StrUtils.isNull(inMap.getString("sys_name"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = sysDao.getSystem(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 修改系统信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            sys_id-系统id, sys_name-系统名称, sys_code-系统编码, easy_name-系统简称,
	 *            url-访问路径, contacts-负责人, tel-手机号码, phone-联系电话, email-邮箱地址,
	 *            address-联系地址, sort_num-排序号
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateSystem(ParaMap inMap) throws Exception {
		String sysId = inMap.getString("sys_id");
		String sysCode = inMap.getString("sys_code");
		String sysName = inMap.getString("sys_name");
		if (StrUtils.isNull(inMap.getString("sys_id")) || StrUtils.isNull(inMap.getString("sys_code"))
				|| StrUtils.isNull(inMap.getString("sys_name"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = sysDao.checkSystem(sysId, sysCode, null);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "sys.code.exist");
			return outMap;
		}
		resultMap = sysDao.checkSystem(sysId, null, sysName);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "sys.name.exist");
			return outMap;
		}
		resultMap = sysDao.updateSystem(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "sys.update.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}

	/**
	 * 禁用/启用该系统
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            sys_id-系统id, status-状态,0-禁用,1-启用
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap enableSystem(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("sys_id")) || StrUtils.isNull(inMap.getString("status"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		int status = inMap.getInt("status");
		if (!(CenterConsts.DISABLED == status || CenterConsts.ENABLED == status)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.error");
			return outMap;
		}
		ParaMap resultMap = sysDao.enableSystem(inMap.getString("sys_id"), status);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "sys.enable.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}
}
