package com.center.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;

/**
 * 资源数据操作类
 * 
 * @author 唐宗鸿
 * @date 20170510
 * @version 1.1.0
 */
public class ResourceDao extends BaseDataSetDao {

	/**
	 * 查询资源列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            sys_id-系统id, res_name-资源名称 支持模糊查询, status-状态,0-禁用,1-启用,
	 *            is_page-是否分页(1-不分页，0-分页), page_index-第几页, page_size-每页记录数
	 * @return res_id-资源id, sys_id-系统id, sys_code-系统编号, sys_name-系统名称,
	 *         res_name-资源名称, show_name-资源显示名称, res_type-资源类型 1-页面元素,
	 *         res_icon-资源标识, url-资源访问路径, parent_id-父资源id, status-状态,0-禁用,1-启用,
	 *         sort_num-排序号
	 */
	public ParaMap getResourceList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("resource", "getResourceList");
		String dynamicSql = "";
		String resname = inMap.getString("res_name");
		if (StrUtils.isNotNull(resname)) {
			dynamicSql += " AND r.res_name like ?";
			sqlMap.addParam("%" + resname + "%");
		}
		String platid = inMap.getString("sys_id");
		if (StrUtils.isNotNull(platid)) {
			dynamicSql += " AND r.sys_id = ?";
			sqlMap.addParam(platid);
		}
		String status = inMap.getString("status");
		if (StrUtils.isNotNull(status) && StrUtils.isNotNull(status.trim())) {
			dynamicSql += " AND r.status = ?";
			sqlMap.addParam(status);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		if (inMap.getInt("is_page") == SqlConsts.NOT_PAGE) {
			return query(sqlMap);
		}
		sqlMap.put("pageIndex", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}

	/**
	 * 新增资源信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            sys_id-系统id, res_name-资源名称,show_name-显示名称,
	 *            res_type-资源类型，1-页面元素, res_icon-资源标识, url-访问路径,
	 *            parent_id-父资源id, sort_num-排序号
	 * @return
	 */
	public ParaMap addResource(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("resource", "addResource");
		sqlMap.addParam(inMap.getString("res_id"));
		sqlMap.addParam(inMap.getString("sys_id"));
		sqlMap.addParam(inMap.getString("res_name"));
		sqlMap.addParam(inMap.getString("show_name"));
		sqlMap.addParam(inMap.getString("res_type"));
		sqlMap.addParam(inMap.getString("res_icon"));
		sqlMap.addParam(inMap.getString("url"));
		sqlMap.addParam(inMap.getString("parent_id"));
		sqlMap.addParam(inMap.getInt("sort_num"));
		ParaMap outMap = insert(sqlMap);
		return outMap;
	}

	/**
	 * 校验同一系统资源名称的唯一性
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param resId
	 *            资源id
	 * @param sysId
	 *            系统id
	 * @param resName
	 *            资源名称
	 * @return
	 */
	public ParaMap checkResource(String resId, String sysId, String resName) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("resource", "getResource");
		String dynamicSql = "";
		if (StrUtils.isNotNull(resId)) {
			dynamicSql += " AND r.id != ? ";
			sqlMap.addParam(resId);
		}
		if (StrUtils.isNotNull(sysId)) {
			dynamicSql += " AND r.sys_id = ? ";
			sqlMap.addParam(sysId);
		}
		if (StrUtils.isNotNull(resName)) {
			dynamicSql += " AND r.res_name = ? ";
			sqlMap.addParam(resName);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 获取资源基本信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            res_id-资源id,res_name-资源名称
	 * @return res_id-资源id, sys_id-系统id, sys_code-系统编号, sys_name-系统名称,
	 *         res_name-资源名称, show_name-显示名称, res_type-资源类型, res_icon-资源标识,
	 *         url-访问路径, parent_id-父资源id, status-状态,0-禁用,1-启用, sort_num-排序号,
	 *         createtime-创建时间, updatetime-更新时间
	 */
	public ParaMap getResource(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("resource", "getResource");
		String dynamicSql = "";
		String resid = inMap.getString("res_id");
		if (StrUtils.isNotNull(resid)) {
			dynamicSql += " AND r.id = ? ";
			sqlMap.addParam(resid);
		}
		String resname = inMap.getString("res_name");
		if (StrUtils.isNotNull(resname)) {
			dynamicSql += " AND r.res_name = ? ";
			sqlMap.addParam(resname);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 修改资源信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            res_id-资源id, sys_id-系统id, res_name-资源名称, show_name-显示名称,
	 *            res_type-资源类型, res_icon-资源标识, url-访问路径, parent_id-父资源id,
	 *            sort_num-排序号
	 * @return
	 */
	public ParaMap updateResource(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("resource", "updateResource");
		sqlMap.addParam(inMap.getString("sys_id"));
		sqlMap.addParam(inMap.getString("res_name"));
		sqlMap.addParam(inMap.getString("show_name"));
		sqlMap.addParam(inMap.getString("res_type"));
		sqlMap.addParam(inMap.getString("res_icon"));
		sqlMap.addParam(inMap.getString("url"));
		sqlMap.addParam(inMap.getString("parent_id"));
		sqlMap.addParam(inMap.getInt("sort_num"));
		sqlMap.addParam(DateUtils.nowTime());
		sqlMap.addParam(inMap.getString("res_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 禁用/启用资源信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param resId
	 *            资源id
	 * @param status
	 *            状态
	 * @return
	 */
	public ParaMap enableResource(String resId, Integer status) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("resource", "enableResource");
		sqlMap.addParam(status);
		sqlMap.addParam(resId);
		ParaMap outMap = update(sqlMap);
		return outMap;
	}
}
