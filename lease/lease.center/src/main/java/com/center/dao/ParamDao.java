package com.center.dao;

import java.util.List;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;
import com.common.util.DateUtil;

/**
 * 参数数据库操作功能类
 * 
 * @author 唐宗鸿
 * @date 20170509
 * @version 1.1.0
 */
public class ParamDao extends BaseDataSetDao {

	/**
	 * 获取参数类型列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            is_page-是否分页（1-不分页，0-分页）,page_size-每页记录数,page_index-第几页
	 * @return param_id-参数类型id,param_name-参数类型名称,param_code-参数类型编码,creator-操作员,
	 *         createtime-创建时间,updatetime-更新时间
	 */
	public ParaMap getParamList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("param", "getParamList");
		if (inMap.getInt("is_page") == SqlConsts.NOT_PAGE) {
			return query(sqlMap);
		}
		sqlMap.put("pageIndex", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		sqlMap.put("pageSize", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}

	/**
	 * 添加参数类型信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            para_code-参数类型编码,para_name-参数类型名称,user_id-操作员id
	 * @return
	 */
	public ParaMap addParam(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("param", "addParam");
		sqlMap.addParam(inMap.getString("param_id"));
		sqlMap.addParam(inMap.getString("para_name"));
		sqlMap.addParam(inMap.getString("para_code"));
		sqlMap.addParam(inMap.getString("user_id"));
		ParaMap outMap = insert(sqlMap);
		return outMap;
	}

	/**
	 * 获取参数类型信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            param_id-参数类型id,para_code-参数类型编码,para_name-参数类型名称
	 * @return param_id-参数类型id,param_name-参数类型名称,param_code-参数类型编码,creator-操作员,
	 *         createtime-创建时间,updatetime-更新时间
	 */
	public ParaMap getParam(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("param", "getParam");
		String dynamicSql = "";
		String paramId = inMap.getString("param_id");
		if (StrUtils.isNotNull(paramId)) {
			dynamicSql += " AND id = ?";
			sqlMap.addParam(paramId);
		}
		String paraName = inMap.getString("para_name");
		if (StrUtils.isNotNull(paraName)) {
			dynamicSql += " AND para_name = ?";
			sqlMap.addParam(paraName);
		}
		String paraCode = inMap.getString("para_code");
		if (StrUtils.isNotNull(paraCode)) {
			dynamicSql += " AND para_code = ?";
			sqlMap.addParam(paraCode);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 获取参数值列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            para_code-参数类型编码,para_name-参数名称,para_value-参数值,
	 *            is_page-是否分页（1-不分页，0-分页）,page_size-每页记录数,page_index-第几页
	 * @return 
	 *         parameter_id-参数值记录id,para_name-参数值类型名称,para_value-参数值,para_code-参数类型编码
	 *         ,remark-备注,status-状态，0-停用，1-启用,creator-操作员,createtime-创建时间,
	 *         updatetime-更新时间
	 */
	public ParaMap getParameterList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("param", "getParameterList");
		String dynamicSql = "";
		String paraCode = inMap.getString("para_code");
		if (StrUtils.isNotNull(paraCode)) {
			dynamicSql += " AND para_code = ?";
			sqlMap.addParam(paraCode);
		}
		String paraName = inMap.getString("para_name");
		if (StrUtils.isNotNull(paraName)) {
			dynamicSql += " AND para_name LIKE ?";
			sqlMap.addParam("%" + paraName + "%");
		}
		String paraValue = inMap.getString("para_value");
		if (StrUtils.isNotNull(paraValue)) {
			dynamicSql += " AND para_value LIKE ?";
			sqlMap.addParam("%" + paraValue + "%");
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
	 * 添加参数值
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            para_code-参数类型编码,para_name-参数值类型,para_value-参数值,user_id-操作员id
	 * @return
	 */
	public ParaMap addParameter(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("param", "addParameter");
		sqlMap.addParam(inMap.getString("parameter_id"));
		sqlMap.addParam(inMap.getString("para_code"));
		sqlMap.addParam(inMap.getString("para_name"));
		sqlMap.addParam(inMap.getString("para_value"));
		sqlMap.addParam(inMap.getString("user_id"));
		ParaMap outMap = insert(sqlMap);
		return outMap;
	}

	/**
	 * 获取参数值信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            parameter_id-参数值记录id,para_code-参数类型编码,para_name-参数值名称,
	 *            para_value-参数值
	 * @return 
	 *         parameter_id-参数值记录id,para_code-参数类型编码,para_name-参数值类型名称,para_value
	 *         -参数值,remark-备注,status-状态，0-停用，1-启用,creator-操作员,createtime-创建时间,
	 *         updatetime-更新时间
	 */
	public ParaMap getParameter(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("param", "getParameter");
		String dynamicSql = "";
		String parameterId = inMap.getString("parameter_id");
		if (StrUtils.isNotNull(parameterId)) {
			dynamicSql += " AND id = ?";
			sqlMap.addParam(parameterId);
		}
		String paraCode = inMap.getString("para_code");
		if (StrUtils.isNotNull(paraCode)) {
			dynamicSql += " AND para_code = ?";
			sqlMap.addParam(paraCode);
		}
		String paraName = inMap.getString("para_name");
		if (StrUtils.isNotNull(paraName)) {
			dynamicSql += " AND para_name = ?";
			sqlMap.addParam(paraName);
		}
		String paraValue = inMap.getString("para_value");
		if (StrUtils.isNotNull(paraValue)) {
			dynamicSql += " AND para_value = ?";
			sqlMap.addParam(paraValue);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 修改参数值
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            parameter_id-参数值记录id,para_code-参数类型编码,para_name-参数值类型,
	 *            para_value-参数值,user_id-操作员id,remark-备注,status-状态
	 * @return
	 */
	public ParaMap updateParameter(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("param", "updateParameter");
		sqlMap.addParam(inMap.getString("para_code"));
		sqlMap.addParam(inMap.getString("para_name"));
		sqlMap.addParam(inMap.getString("para_value"));
		sqlMap.addParam(inMap.getString("status"));
		sqlMap.addParam(inMap.getString("user_id"));
		sqlMap.addParam(inMap.getString("remark"));
		sqlMap.addParam(DateUtil.nowTime());
		sqlMap.addParam(inMap.getString("parameter_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 启用/禁用参数值
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            parameter_id-参数值记录id,status-参数状态，0-禁用，1-启用
	 * @return
	 */
	public ParaMap enableParameter(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("param", "enableParameter");
		sqlMap.addParam(inMap.getInt("status"));
		sqlMap.addParam(DateUtil.nowTime());
		sqlMap.addParam(inMap.getString("parameter_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 获取参数值信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170530
	 * @param paraCodeList
	 *            para_code-参数类型集合
	 * @return 
	 *         parameter_id-参数值记录id,para_code-参数类型编码,para_name-参数值类型名称,para_value
	 *         -参数值,remark-备注,status-状态，0-停用，1-启用,creator-操作员,create_time-创建时间,
	 *         update_time-更新时间
	 */
	public ParaMap getParameter(List<String> paraCodeList) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("param", "getParameter");
		StringBuffer dynamicSql = new StringBuffer(" AND para_code in ( ");
		for (int i = 0; i < paraCodeList.size(); i++) {
			dynamicSql.append(" ?,");
			sqlMap.addParam(paraCodeList.get(i));
		}
		dynamicSql.replace(dynamicSql.length() - 1, dynamicSql.length(), ")");
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql.toString());
		ParaMap outMap = query(sqlMap);
		return outMap;
	}
}
