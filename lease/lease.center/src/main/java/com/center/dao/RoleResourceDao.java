package com.center.dao;

import java.util.ArrayList;
import java.util.List;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;
import com.common.util.DateUtil;

/**
 * 角色关联资源数据操作类
 * 
 * @author 唐宗鸿
 * @date 20170510
 * @version 1.1.0
 */
public class RoleResourceDao extends BaseDataSetDao {

	/**
	 * 查询角色关联资源列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param inMap
	 *            role_id-角色id
	 * @return role_res_id-角色关联资源id,role_id-角色id,res_id-资源id,show_name-资源显示名称
	 */
	public ParaMap getRoleResourceList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("resource", "getRoleResourceList");
		sqlMap.addParam(inMap.getString("role_id"));
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 新增角色关联资源信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param roleId
	 *            角色id
	 * @param resId
	 *            资源id，多个中间用逗号隔开
	 * @return
	 */
	public ParaMap addRoleResource(String roleId, String resId)
			throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("resource", "addRoleResource");
		String[] resIds = resId.split(",");
		for (int i = 0; i < resIds.length; i++) {
			List<Object> list = new ArrayList<Object>();
			list.add(IDGenerator.newGUID());
			list.add(roleId);
			list.add(resIds[i]);
			list.add(DateUtil.nowTime());
			list.add(DateUtil.nowTime());
			sqlMap.addBatchParam(list);
		}
		ParaMap outMap = batch(sqlMap);
		return outMap;
	}

	/**
	 * 删除角色关联资源信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param roleId
	 *            角色id
	 * @param resId
	 *            资源id，多个中间用逗号隔开
	 * @return
	 */
	public ParaMap delRoleResource(String roleId, String resId)
			throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("resource", "delRoleResource");
		String[] resIds = resId.split(",");
		for (int i = 0; i < resIds.length; i++) {
			List<Object> list = new ArrayList<Object>();
			list.add(roleId);
			list.add(resIds[i]);
			sqlMap.addBatchParam(list);
		}
		ParaMap outMap = batch(sqlMap);
		return outMap;
	}

	/**
	 * 批量查询角色关联的资源
	 * 
	 * @author 唐宗鸿
	 * @date 20170510
	 * @param roleId
	 *            角色id
	 * @param resId
	 *            资源id，多个中间用逗号隔开
	 * @return role_res_id-角色关联资源id,role_id-角色id,res_id-资源id
	 */
	public ParaMap getRoleResourceList(String roleId, String resId)
			throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("resource", "getRoleResource");
		sqlMap.addParam(roleId);
		StringBuffer dynamicSql = new StringBuffer();
		if (StrUtils.isNotNull(resId)) {
			dynamicSql.append(" AND res_id IN ( ");
			String[] resIds = resId.split(",");
			for (int i = 0; i < resIds.length; i++) {
				dynamicSql.append("?,");
				sqlMap.addParam(resIds[i]);
			}
			dynamicSql.replace(dynamicSql.length() - 1, dynamicSql.length(), ")");
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql.toString());
		ParaMap outMap = query(sqlMap);
		return outMap;
	}
}
