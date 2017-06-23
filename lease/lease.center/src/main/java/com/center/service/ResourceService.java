package com.center.service;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.consts.CenterConsts;
import com.center.dao.ResourceDao;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;

/**
 * 菜单资源服务
 * 
 * @author 唐宗鸿
 * @date 20170509
 * @version 1.1.0
 */
public class ResourceService {

	private ResourceDao resourceDao = new ResourceDao();

	/**
	 * 查询菜单资源列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            sys_id-系统id, res_name-资源名称 支持模糊查询, status-状态,0-禁用,1-启用,
	 *            is_page-是否分页(1-不分页，0-分页), page_index-第几页, page_size-每页记录数
	 * @return res_id-资源id, sys_id-系统id, sys_code-系统编号, sys_name-系统名称,
	 *         res_name-资源名称, show_name-资源显示名称, res_type-资源类型 1-页面元素,
	 *         res_icon-资源标识, url-资源访问路径, parent_id-父资源id, status-状态,0-禁用,1-启用,
	 *         sort_num-排序号,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getResourceList(ParaMap inMap) throws Exception {
		ParaMap resultMap = resourceDao.getResourceList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}

	/**
	 * 新增菜单资源信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            sys_id-系统id, res_name-资源名称,show_name-显示名称,
	 *            res_type-资源类型，1-页面元素, res_icon-资源标识, url-访问路径,
	 *            parent_id-父资源id, sort_num-排序号
	 * @return res_id-资源id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addResource(ParaMap inMap) throws Exception {
		String sysId = inMap.getString("sys_id");
		String resName = inMap.getString("res_name");
		if (StrUtils.isNull(sysId) || StrUtils.isNull(resName)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = resourceDao.checkResource(null, sysId, resName);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "resource.name.exist");
			return outMap;
		}
		inMap.put("res_id", IDGenerator.newGUID());
		resultMap = resourceDao.addResource(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "resource.add.fail");
			return outMap;
		}
		ParaMap outMap = new ParaMap();
		outMap.put("res_id", inMap.getString("res_id"));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 获取菜单资源基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            res_id-资源id,res_name-资源名称
	 * @return res_id-资源id, sys_id-系统id, sys_code-系统编号, sys_name-系统名称,
	 *         res_name-资源名称, show_name-显示名称, res_type-资源类型, res_icon-资源标识,
	 *         url-访问路径, parent_id-父资源id, status-状态,0-禁用,1-启用, sort_num-排序号,
	 *         createtime-创建时间, updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getResource(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("res_id")) && StrUtils.isNull(inMap.getString("res_name"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = resourceDao.getResource(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 修改菜单资源信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            res_id-资源id, sys_id-系统id, res_name-资源名称, show_name-显示名称,
	 *            res_type-资源类型, res_icon-资源标识, url-访问路径, parent_id-父资源id,
	 *            sort_num-排序号
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateResource(ParaMap inMap) throws Exception {
		String resId = inMap.getString("res_id");
		String sysId = inMap.getString("sys_id");
		String resName = inMap.getString("res_name");
		if (StrUtils.isNull(resId) || StrUtils.isNull(sysId) || StrUtils.isNull(resName)
				|| StrUtils.isNull(inMap.getString("show_name"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = resourceDao.checkResource(resId, sysId, resName);
		if (resultMap.getRecordCount() > 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "resource.name.exist");
			return outMap;
		}
		resultMap = resourceDao.updateResource(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "sys.update.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}

	/**
	 * 禁用/启用该菜单资源
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            res_id-资源id, status-状态，0-禁用，默认，1-启用
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap enableResource(ParaMap inMap) throws Exception {
		String resid = inMap.getString("res_id");
		if (StrUtils.isNull(resid)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		int status = inMap.getInt("status");
		if (!(CenterConsts.DISABLED == status || CenterConsts.ENABLED == status)) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.error");
			return outMap;
		}
		ParaMap resultMap = resourceDao.enableResource(resid, status);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "resource.enable.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}
}
