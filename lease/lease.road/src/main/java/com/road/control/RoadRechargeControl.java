package com.road.control;

import com.base.utils.ParaMap;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.util.RespUtil;
import com.road.entity.RoadAdaptor;
import com.road.util.AdaptorUtils;

/**
 * 路外充值记录查询控制器
 * 
 * @author 唐宗鸿
 * @date 20170510
 * @version 1.1.0
 */
public class RoadRechargeControl {

	public ParaMap getRoadRechargeList(ParaMap inMap) throws Exception {
		RoadAdaptor roadAdaptor = AdaptorUtils.getRoadAdaptor(inMap);
		ParaMap resultMap = roadAdaptor.getRechargeList(inMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap dataMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		ParaMap outMap = RespUtil.setResp(dataMap.get("data"));
		outMap.put("page_index", inMap.getInt("page_index"));
		outMap.put("page_size", inMap.getInt("page_size"));
		outMap.put("total_count", dataMap.getInt("total_count"));
		return outMap;
	}

}
