package com.center.control;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.consts.CenterConsts;
import com.center.service.ProviderService;
import com.common.consts.RespConsts.RespState;
import com.common.util.RespUtil;

/**
 * 服务商控制器
 * 
 * @author 唐宗鸿
 * @date 20170512
 * @version 1.1.0
 */
public class ProviderControl {

	private ProviderService providerService = new ProviderService();

	/**
	 * 获取服务商列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
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
		return providerService.getProviderList(inMap);
	}

	/**
	 * 添加服务商
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            provider_name-服务商名称,provider_code-服务商编号,provider_type-服务商类型,
	 *            tel-注册手机号,app_id-商户号,app_secret-商户密钥,password-支付密码,clazz-对接地址,
	 *            city-所属城市编码
	 * @return provider_id-服务商id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addProvider(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("provider_name")) || StrUtils.isNull(inMap.getString("provider_type"))
				|| StrUtils.isNull(inMap.getString("clazz")) || StrUtils.isNull(inMap.getString("app_id"))
				|| StrUtils.isNull(inMap.getString("app_secret")) || StrUtils.isNull(inMap.getString("provider_code"))
				|| StrUtils.isNull(inMap.getString("tel"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		if (inMap.getInt("provider_type") == CenterConsts.PROVIDER_TYPE_ROAD) {
			if (StrUtils.isNull("city") || StrUtils.isNull("password")) {
				return RespUtil.setResp(RespState.FAIL, "param.missing");
			}
		}
		ParaMap resultMap = providerService.addProvider(inMap);
		return resultMap;
	}

	/**
	 * 获取服务商信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            provider_id-服务商id,provider_code-服务商编号,provider_type-服务商类型,tel-
	 *            注册手机号,app_id-注册商户号,city-城市代码
	 * @return 
	 *         provider_id-服务商id,provider_name-服务商名称,provider_code-服务商编号,provider_type
	 *         -服务商类型,tel-注册手机号,app_id-商户号,app_secret-商户密钥,password-支付密码,clazz-
	 *         对接地址,city-所属城市编码,createtime-创建时间,
	 *         updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getProvider(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("provider_id")) && StrUtils.isNull(inMap.getString("provider_code"))
				&& StrUtils.isNull(inMap.getString("provider_type")) && StrUtils.isNull(inMap.getString("tel"))
				&& StrUtils.isNull(inMap.getString("city")) && StrUtils.isNull(inMap.getString("app_id"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		ParaMap resultMap = providerService.getProvider(inMap);
		ParaMap outMap = RespUtil.setResp(resultMap);
		return outMap;
	}

	/**
	 * 修改服务商信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170512
	 * @param inMap
	 *            provider_id-服务商id,provider_name-服务商名称,provider_code-服务商编号,
	 *            provider_type
	 *            -服务商类型,tel-注册手机号,app_id-商户号,app_secret-商户密钥,password
	 *            -支付密码,clazz-对接地址,city-所属城市编码
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateProvider(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("provider_id")) || StrUtils.isNull(inMap.getString("provider_name"))
				|| StrUtils.isNull(inMap.getString("provider_type")) || StrUtils.isNull(inMap.getString("clazz"))
				|| StrUtils.isNull(inMap.getString("app_secret")) || StrUtils.isNull(inMap.getString("app_id"))
				|| StrUtils.isNull(inMap.getString("provider_code")) || StrUtils.isNull(inMap.getString("tel"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		if (inMap.getInt("provider_type") == CenterConsts.PROVIDER_TYPE_ROAD) {
			if (StrUtils.isNull("city") || StrUtils.isNull("password")) {
				return RespUtil.setResp(RespState.FAIL, "param.missing");
			}
		}
		int num = providerService.updateProvider(inMap);
		if (num < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "provider.update.fail");
			return outMap;
		}
		return RespUtil.setResp();
	}
}
