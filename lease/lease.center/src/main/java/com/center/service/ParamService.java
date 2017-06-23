package com.center.service;

import java.util.List;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.dao.ParamDao;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;

/**
 * 参数服务
 * 
 * @author 唐宗鸿
 * @date 20170508
 * @version 1.1.0
 */
public class ParamService {

	private ParamDao paramDao = new ParamDao();

	/**
	 * 获取参数类型列表
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            is_page-是否分页（1-不分页，0-分页）,page_size-每页记录数,page_index-第几页
	 * @return param_id-参数类型id,para_name-参数类型名称,para_code-参数类型编码,creator-操作员,
	 *         createtime-创建时间,updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getParamList(ParaMap inMap) throws Exception {
		ParaMap resultMap = paramDao.getParamList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}

	/**
	 * 新增参数类型
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            para_code-参数类型编码,para_name-参数类型名称,user_id-操作员id
	 * @return param_id-参数类型id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addParam(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("para_code")) || StrUtils.isNull(inMap.getString("para_name"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		inMap.put("param_id", IDGenerator.newGUID());
		ParaMap resultMap = paramDao.addParam(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.add.fail");
			return outMap;
		}
		ParaMap outMap = new ParaMap();
		outMap.put("param_id", inMap.getString("param_id"));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 获取参数类型信息
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            param_id-参数类型id,para_code-参数类型编码,para_name-参数类型名称
	 * @return param_id-参数类型id,para_name-参数类型名称,para_code-参数类型编码,creator-操作员,
	 *         createtime-创建时间,updatetime-更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getParam(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("para_code")) && StrUtils.isNull(inMap.getString("para_name"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = paramDao.getParam(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		outMap = RespUtil.setResp(outMap);
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
	 *         updatetime -更新时间,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getParameterList(ParaMap inMap) throws Exception {
		ParaMap resultMap = paramDao.getParameterList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}

	/**
	 * 新增参数值
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            para_code-参数类型编码,para_name-参数值类型,para_value-参数值,user_id-操作员id
	 * @return parameter_id-参数值记录id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addParameter(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("para_code")) || StrUtils.isNull(inMap.getString("para_name"))
				|| StrUtils.isNull(inMap.getString("para_value"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		inMap.put("parameter_id", IDGenerator.newGUID());
		ParaMap resultMap = paramDao.addParameter(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "parameter.add.fail");
			return outMap;
		}
		ParaMap outMap = new ParaMap();
		outMap.put("parameter_id", inMap.getString("parameter_id"));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	/**
	 * 获取参数值详情
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            parameter_id-参数值记录id,para_code-参数类型编码,para_name-参数名称,
	 *            para_value-参数值
	 * @return 
	 *         parameter_id-参数值记录id,para_name-参数值类型名称,para_value-参数值,para_code-参数类型编码
	 *         ,remark-备注,status-状态，0-停用，1-启用,creator-操作员,createtime-创建时间,
	 *         updatetime-更新时间
	 */
	public ParaMap getParameter(ParaMap inMap) throws Exception {
		ParaMap resultMap = paramDao.getParameter(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		return outMap;
	}

	/**
	 * 修改参数值
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            parameter_id-参数值记录id,para_code-参数类型编码,para_name-参数值类型,
	 *            para_value-参数值,user_id-操作员id,remark-备注
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateParameter(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("parameter_id")) || StrUtils.isNull(inMap.getString("para_code"))
				|| StrUtils.isNull(inMap.getString("para_name")) || StrUtils.isNull(inMap.getString("para_value"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = paramDao.updateParameter(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "parameter.update.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}

	/**
	 * 修改参数值
	 * 
	 * @author 唐宗鸿
	 * @date 20170508
	 * @param inMap
	 *            parameter_id-参数值记录id,status-参数状态，0-禁用，默认，1-启用
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap enableParameter(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("parameter_id"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "param.missing");
			return outMap;
		}
		ParaMap resultMap = paramDao.enableParameter(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "parameter.enable.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}

	/**
	 * 获取参数说明，格式化后的Map
	 * 
	 * @author 唐宗鸿
	 * @date 20170530
	 * @param paraCodeList
	 *            参数类型集合
	 * @return
	 */
	public ParaMap getParameterConvertMap(List<String> paraCodeList) throws Exception {
		ParaMap resultMap = paramDao.getParameter(paraCodeList);
		ParaMap parameterMap = new ParaMap();
		for (int i = 0; i < resultMap.getRecordCount(); i++) {
			String paraCode = resultMap.getRecordString(i, "para_code");
			String paraValue = resultMap.getRecordString(i, "para_value");
			String paraName = resultMap.getRecordString(i, "para_name");
			parameterMap.put(paraCode + "_" + paraName, paraValue);
		}
		return parameterMap;
	}
}
