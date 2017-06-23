package com.common.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.base.service.BaseService;
import com.base.utils.CharsetUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespState;
import com.common.util.RespUtil;
import com.common.util.StringUtil;

public abstract class CommonService extends BaseService {

	/**
	 * 内部统一入口
	 * 
	 * @param inMap
	 * @return
	 * @throws
	 * @author 唐宗鸿
	 */
	protected ParaMap execute(ParaMap inMap) throws Exception {
		return null;
	}

	/**
	 * 外部统一入口
	 * 
	 * @param inMap
	 * @return
	 * @throws
	 * @author 唐宗鸿
	 */
	protected void accept(ParaMap inMap) throws Exception {

	}

	/**
	 * 外部推送统一出口
	 * 
	 * @param inMap
	 * @return
	 * @throws
	 * @author 唐宗鸿
	 */
	protected void dispatch(ParaMap inMap) throws Exception {

	}

	/**
	 * 解析处理类和方法
	 * 
	 * @param action
	 *            需要解析的url
	 * @param packagePrefix
	 *            包前缀
	 * @return String[] 处理类和方法
	 * @author 唐宗鸿
	 */
	protected String[] parseUrl(String action, String packagePrefix) {
		String[] actionInfo = action.split("[.]");
		String className = packagePrefix;
		for (int i = 0; i < actionInfo.length - 1; i++) {
			className += StringUtil.toUpperCaseFirst(actionInfo[i]);
		}
		// 避免每个action后面都带有control参数
		className = className + "Control";
		String methodName = actionInfo[actionInfo.length - 1];
		return new String[] { className, methodName };
	}

	/**
	 * 业务处理
	 * 
	 * @param inMap
	 *            请求参数
	 * @param packagePrefix
	 *            包前缀
	 * @return
	 * @author 唐宗鸿
	 */
	public ParaMap process(ParaMap inMap, String packagePrefix) {
		String action = inMap.getString("action");
		if (StrUtils.isNull(action) || action.indexOf(".") <= 0) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "invalid.action",
					"action不存在或者有误");
			return outMap;
		}
		String[] url = parseUrl(action, packagePrefix);
		try {
			Object object = Class.forName(url[0]).newInstance();
			Method method = object.getClass().getDeclaredMethod(url[1],
					ParaMap.class);
			ParaMap outMap = (ParaMap) method.invoke(object, inMap);
			return outMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RespUtil.setResp(RespState.FAIL, "error", "sys.error");
	}

	/**
	 * 从request中获取参数
	 * 
	 * @return
	 * @throws
	 * @author 唐宗鸿
	 */
	protected ParaMap getParamater() throws Exception {
		HttpServletRequest request = super.getRequest();
		ParaMap paraMap = new ParaMap();
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String name = String.valueOf(enumeration.nextElement());
			String value = request.getParameter(name);
			if (CharsetUtils.getEncoding(value).equals("ISO-8859-1")) {
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			}
			value = value.replaceAll("%", "%25");
			value = value.replaceAll("\\+", "%2B");
			value = URLDecoder.decode(value, "UTF-8");
			paraMap.put(name, value);
		}
		return paraMap;
	}

	/**
	 * 响应
	 * 
	 * @param outMap
	 * @throws IOException
	 * @author 唐宗鸿
	 */
	protected void response(ParaMap outMap) throws IOException {
		HttpServletResponse response = getResponse();
		PrintWriter out = response.getWriter();
		out.write(outMap.toString());
		out.flush();
	}
}
