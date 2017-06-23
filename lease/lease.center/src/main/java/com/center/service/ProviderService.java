package com.center.service;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.center.dao.ProviderDao;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;

/**
 * 服务商功能服务
 * 
 * @author 唐宗鸿
 * @date 20170509
 * @version 1.1.0
 */
public class ProviderService {

	private ProviderDao providerDao = new ProviderDao();

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
	 *         对接地址,city-所属城市编码,createtime-创建时间,
	 *         updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getProviderList(ParaMap inMap) throws Exception {
		ParaMap resultMap = providerDao.getProviderList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
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
	 * @return provider_id-服务商id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addProvider(ParaMap inMap) throws Exception {
		inMap.put("provider_id", IDGenerator.newGUID());
		ParaMap resultMap = providerDao.addProvider(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "provider.add.fail");
			return outMap;
		}
		ParaMap outMap = new ParaMap();
		outMap.put("provider_id", inMap.getString("provider_id"));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 获取服务商信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170509
	 * @param inMap
	 *            provider_id-服务商id,provider_code-服务商编号,provider_type-服务商类型,tel-
	 *            注册手机号,app_id-注册商户号,city-城市代码
	 * @return 
	 *         provider_id-服务商id,provider_name-服务商名称,provider_code-服务商编号,provider_type
	 *         -服务商类型,tel-注册手机号,app_id-商户号,app_secret-商户密钥,password-支付密码,clazz-
	 *         对接地址,city-所属城市编码,createtime-创建时间, updatetime-更新时间
	 */
	public ParaMap getProvider(ParaMap inMap) throws Exception {
		ParaMap resultMap = providerDao.getProvider(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
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
	public int updateProvider(ParaMap inMap) throws Exception {
		ParaMap resultMap = providerDao.updateProvider(inMap);
		return resultMap.getInt("num");
	}
}
