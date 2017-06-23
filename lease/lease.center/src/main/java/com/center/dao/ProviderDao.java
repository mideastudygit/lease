package com.center.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.SqlConsts;
import com.common.util.DateUtil;

/**
 * 服务商数据库操作功能类
 * 
 * @author 唐宗鸿
 * @date 20170509
 * @version 1.1.0
 */
public class ProviderDao extends BaseDataSetDao {

	/**
	 * 获取服务商列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            provider_name-服务商名称，支持模糊查询,provider_type-服务商类型,provider_code
	 *            -服务商编号，支持模糊查询,
	 *            is_page-是否分页（1-不分页，0-分页）,page_size-每页记录数,page_index-第几页
	 * @return 
	 *         provider_id-服务商id,provider_name-服务商名称,provider_code-服务商编号,provider_type
	 *         -服务商类型,tel-注册手机号,app_id-商户号,app_secret-商户密钥,password-支付密码,clazz-
	 *         对接地址,city-所属城市编码,createtime-创建时间, updatetime-更新时间
	 */
	public ParaMap getProviderList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("provider", "getProviderList");
		String dynamicSql = "";
		String providerType = inMap.getString("provider_type");
		if (StrUtils.isNotNull(providerType)) {
			dynamicSql += " AND provider_type = ?";
			sqlMap.addParam(providerType);
		}
		String providerName = inMap.getString("provider_name");
		if (StrUtils.isNotNull(providerName)) {
			dynamicSql += " AND provider_name LIKE ?";
			sqlMap.addParam("%" + providerName + "%");
		}
		String providerCode = inMap.getString("provider_code");
		if (StrUtils.isNotNull(providerCode)) {
			dynamicSql += " AND provider_code LIKE ?";
			sqlMap.addParam("%" + providerCode + "%");
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
	 * 添加服务商
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            provider_name-服务商名称,provider_code-服务商编号,provider_type-服务商类型,
	 *            tel-注册手机号,app_id-商户号,app_secret-商户密钥,password-支付密码,clazz-对接地址,
	 *            city-所属城市编码,user_id-操作员id
	 * @return
	 */
	public ParaMap addProvider(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("provider", "addProvider");
		sqlMap.addParam(inMap.getString("provider_id"));
		sqlMap.addParam(inMap.getString("provider_name"));
		sqlMap.addParam(inMap.getString("provider_code"));
		sqlMap.addParam(inMap.getString("provider_type"));
		sqlMap.addParam(inMap.getString("tel"));
		sqlMap.addParam(inMap.getString("app_id"));
		sqlMap.addParam(inMap.getString("app_secret"));
		sqlMap.addParam(inMap.getString("password"));
		sqlMap.addParam(inMap.getString("clazz"));
		sqlMap.addParam(inMap.getString("city"));
		sqlMap.addParam(inMap.getString("user_id"));
		ParaMap outMap = insert(sqlMap);
		return outMap;
	}

	/**
	 * 获取服务商信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            provider_id-服务商id,provider_code-服务商编号,provider_type-服务商类型,tel-
	 *            注册手机号,app_id-注册商户号
	 * @return 
	 *         provider_id-服务商id,provider_name-服务商名称,provider_code-服务商编号,provider_type
	 *         -服务商类型,tel-注册手机号,app_id-商户号,app_secret-商户密钥,password-支付密码,clazz-
	 *         对接地址,city-所属城市编码,createtime-创建时间, updatetime-更新时间
	 */
	public ParaMap getProvider(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("provider", "getProvider");
		String dynamicSql = "";
		String providerId = inMap.getString("provider_id");
		if (StrUtils.isNotNull(providerId)) {
			dynamicSql += " AND id = ?";
			sqlMap.addParam(providerId);
		}
		String providerCode = inMap.getString("provider_code");
		if (StrUtils.isNotNull(providerCode)) {
			dynamicSql += " AND provider_code = ?";
			sqlMap.addParam(providerCode);
		}
		String providerType = inMap.getString("provider_type");
		if (StrUtils.isNotNull(providerType)) {
			dynamicSql += " AND provider_type = ?";
			sqlMap.addParam(providerType);
		}
		String tel = inMap.getString("tel");
		if (StrUtils.isNotNull(tel)) {
			dynamicSql += " AND tel = ?";
			sqlMap.addParam(tel);
		}
		String appId = inMap.getString("app_id");
		if (StrUtils.isNotNull(appId)) {
			dynamicSql += " AND app_id = ?";
			sqlMap.addParam(appId);
		}
		String city = inMap.getString("city");
		if (StrUtils.isNotNull(city)) {
			dynamicSql += " AND city = ?";
			sqlMap.addParam(city);
		}
		sqlMap.setPlaceHolder(SqlConsts.DYNAMIC_SQL, dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 修改服务商信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            provider_id-服务商id,provider_name-服务商名称,provider_code-服务商编号,
	 *            provider_type
	 *            -服务商类型,tel-注册手机号,app_id-商户号,app_secret-商户密钥,password
	 *            -支付密码,clazz-对接地址,city-所属城市编码
	 * @return
	 */
	public ParaMap updateProvider(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("provider", "updateProvider");
		sqlMap.addParam(inMap.getString("provider_name"));
		sqlMap.addParam(inMap.getString("provider_code"));
		sqlMap.addParam(inMap.getString("provider_type"));
		sqlMap.addParam(inMap.getString("tel"));
		sqlMap.addParam(inMap.getString("app_id"));
		sqlMap.addParam(inMap.getString("app_secret"));
		sqlMap.addParam(inMap.getString("password"));
		sqlMap.addParam(inMap.getString("clazz"));
		sqlMap.addParam(inMap.getString("city"));
		sqlMap.addParam(DateUtil.nowTime());
		sqlMap.addParam(inMap.getString("provider_id"));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}
}
