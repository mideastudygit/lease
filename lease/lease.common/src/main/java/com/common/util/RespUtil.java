package com.common.util;

import com.base.utils.ParaMap;
import com.common.conf.MsgConfig;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;

/**
 * 
 * @author 唐宗鸿
 *
 */
public class RespUtil {

	private RespUtil() {
	}

	/**
	 * 接口成功，不需要返回data数据
	 * 
	 * @return
	 * @author 唐宗鸿
	 */
	public static ParaMap setResp() {
		return setResp(RespState.SUCCESS, "success", MsgConfig.get("success"));
	}

	/**
	 * 接口成功，需要返回data数据
	 * 
	 * @param data
	 * @return
	 * @author 唐宗鸿
	 */
	public static ParaMap setResp(Object data) {
		return setResp(RespState.SUCCESS, "success", MsgConfig.get("success"), data);
	}

	/**
	 * 接口失败，不需要返回data数据
	 * 
	 * @param state
	 * @param code
	 * @return
	 * @author 唐宗鸿
	 */
	public static ParaMap setResp(RespState state, String code) {
		return setResp(state, code, MsgConfig.get(code));
	}

	/**
	 * 接口失败，不需要返回data数据
	 * 
	 * @param state
	 * @param code
	 * @return
	 * @author 唐宗鸿
	 */
	public static ParaMap setResp(int state, String code) {
		return setResp(state, code, MsgConfig.get(code));
	}

	/**
	 * 
	 * @param state
	 * @param code
	 * @return
	 * @author 唐宗鸿
	 */
	public static ParaMap setResp(RespState state, String code, Object data) {
		return setResp(state.getValue(), code, MsgConfig.get(code), data);
	}

	/**
	 * 
	 * @param state
	 * @param code
	 * @return
	 * @author 唐宗鸿
	 */
	public static ParaMap setResp(RespState state, String code, String msg) {
		return setResp(state.getValue(), code, msg);
	}

	/**
	 * 
	 * @param state
	 * @param code
	 * @return
	 * @author 唐宗鸿
	 */
	public static ParaMap setResp(int state, String code, String msg) {
		ParaMap result = new ParaMap();
		result.put(RespKey.STATE.getValue(), state);
		result.put(RespKey.CODE.getValue(), code);
		result.put(RespKey.MSG.getValue(), msg);
		return result;
	}

	/**
	 * 
	 * @param state
	 * @param code
	 * @return
	 * @author 唐宗鸿
	 */
	public static ParaMap setResp(RespState state, String code, String msg, Object data) {
		return setResp(state.getValue(), code, msg, data);
	}

	/**
	 * 
	 * @param state
	 * @param code
	 * @return
	 * @author 唐宗鸿
	 */
	public static ParaMap setResp(int state, String code, String msg, Object data) {
		ParaMap result = new ParaMap();
		result.put(RespKey.STATE.getValue(), state);
		result.put(RespKey.CODE.getValue(), code);
		result.put(RespKey.MSG.getValue(), msg);
		result.put(RespKey.DATA.getValue(), data);
		return result;
	}
	
	/**
	 * 接口成功，需要返回data数据
	 * 
	 * @param data
	 * @return
	 * @author 唐宗鸿
	 */
	public static ParaMap fillResp(ParaMap data) {
		data.put(RespKey.STATE.getValue(), RespState.SUCCESS);
		data.put(RespKey.CODE.getValue(), "success");
		data.put(RespKey.MSG.getValue(), MsgConfig.get("success"));
		return data;
	}
}
