package com.center.control;

import com.base.utils.ParaMap;
import com.center.service.ParamService;
import com.common.util.RespUtil;

/**
 * 参数控制器
 * 
 * @author 唐宗鸿
 * @date 20170516
 * @version 1.1.0
 */
public class ParamControl {

	private ParamService paramService = new ParamService();

	/**
	 * 获取参数类型列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170516
	 * @param inMap
	 *            is_page-是否分页（1-不分页，0-分页）,page_size-每页记录数,page_index-第几页
	 * @return param_id-参数类型id,para_name-参数类型名称,para_code-参数类型编码,creator-操作员,
	 *         createtime-创建时间,updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getParamList(ParaMap inMap) throws Exception {
		return paramService.getParameterList(inMap);
	}

	/**
	 * 新增参数类型
	 * 
	 * @author 唐宗鸿
	 * @date 20170516
	 * @param inMap
	 *            para_code-参数类型编码,para_name-参数类型名称,user_id-操作员id
	 * @return param_id-参数类型id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addParam(ParaMap inMap) throws Exception {
		return paramService.addParam(inMap);
	}

	/**
	 * 获取参数类型信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170516
	 * @param inMap
	 *            param_id-参数类型id,para_code-参数类型编码,para_name-参数类型名称
	 * @return param_id-参数类型id,para_name-参数类型名称,para_code-参数类型编码,creator-操作员,
	 *         createtime-创建时间,updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getParam(ParaMap inMap) throws Exception {
		return paramService.getParam(inMap);
	}

	/**
	 * 获取参数值列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170516
	 * @param inMap
	 *            para_code-参数类型编码,para_name-参数名称,para_value-参数值,
	 *            is_page-是否分页（1-不分页，0-分页）,page_size-每页记录数,page_index-第几页
	 * @return 
	 *         parameter_id-参数值记录id,para_name-参数值类型名称,para_value-参数值,para_code-参数类型编码
	 *         ,remark-备注,status-状态，0-停用，1-启用,creator-操作员,createtime-创建时间,
	 *         updatetime -更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getParameterList(ParaMap inMap) throws Exception {
		return paramService.getParameterList(inMap);
	}

	/**
	 * 新增参数值
	 * 
	 * @author 唐宗鸿
	 * @date 20170516
	 * @param inMap
	 *            para_code-参数类型编码,para_name-参数值类型,para_value-参数值,user_id-操作员id
	 * @return parameter_id-参数值记录id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addParameter(ParaMap inMap) throws Exception {
		return paramService.addParameter(inMap);
	}

	/**
	 * 获取参数值详情
	 * 
	 * @author 唐宗鸿
	 * @date 20170516
	 * @param inMap
	 *            parameter_id-参数值记录id,para_code-参数类型编码,para_name-参数名称,
	 *            para_value-参数值
	 * @return 
	 *         parameter_id-参数值记录id,para_name-参数值类型名称,para_value-参数值,para_code-参数类型编码
	 *         ,remark-备注,status-状态，0-停用，1-启用,creator-操作员,createtime-创建时间,
	 *         updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getParameter(ParaMap inMap) throws Exception {
		ParaMap resultMap = paramService.getParameter(inMap);
		return RespUtil.setResp(resultMap);
	}

	/**
	 * 修改参数值
	 * 
	 * @author 唐宗鸿
	 * @date 20170516
	 * @param inMap
	 *            parameter_id-参数值记录id,para_code-参数类型编码,para_name-参数值类型,
	 *            para_value-参数值,user_id-操作员id,remark-备注
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateParameter(ParaMap inMap) throws Exception {
		return paramService.updateParameter(inMap);
	}

	/**
	 * 启用/禁用参数值
	 * 
	 * @author 唐宗鸿
	 * @date 20170516
	 * @param inMap
	 *            parameter_id-参数值记录id,status-参数状态，0-禁用，默认，1-启用
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap enableParameter(ParaMap inMap) throws Exception {
		return paramService.enableParameter(inMap);
	}
}
