package com.road.util;

import com.base.utils.ParaMap;
import com.common.consts.RespConsts.RespKey;
import com.common.route.RouteManager;
import com.road.entity.RoadAdaptor;

/**
 * 路边停车适配器工具类
 *
 */
public class AdaptorUtils {

	/**
	 * 获取具体路边停车适配器
	 * 
	 */
	public static RoadAdaptor getRoadAdaptor(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("provider_id", inMap.getString("provider_id"));
		ParaMap resultMap = RouteManager.route("provider.getProvider", sendMap);
		ParaMap providerMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		RoadAdaptor roadAdaptor = (RoadAdaptor) Class.forName(providerMap.getString("clazz")).newInstance();
		roadAdaptor.setUserId(providerMap.getString("provider_code"));
		roadAdaptor.setAppId(providerMap.getString("app_id"));
		roadAdaptor.setAppSecret(providerMap.getString("app_secret"));
		roadAdaptor.setPassword(providerMap.getString("password"));
		return roadAdaptor;
	}

}
