package com.center.control;

import java.util.ArrayList;
import java.util.List;

import com.base.utils.ParaMap;
import com.common.consts.RespConsts.RespKey;
import com.common.route.RouteManager;
import com.common.util.RespUtil;

/**
 * 路外工作台服务控制器
 * 
 * @author 吴财宾
 * @date 20170527
 * @version 1.1.0
 *
 */
public class RoadWorkbenchControl {

	public ParaMap getBerthUseTotal(ParaMap inMap) throws Exception {
		ParaMap roadMap = RouteManager.route("road.workbench.getBerthUseTotal", inMap);
		ParaMap ercarMap = RouteManager.route("ercar.order.getCarUseRate", inMap);
		ParaMap dataRoadMap = (ParaMap) roadMap.get(RespKey.DATA.getValue());
		ParaMap dataCarMap = (ParaMap) ercarMap.get(RespKey.DATA.getValue());

		ParaMap outMap = new ParaMap();
		outMap.put("use_rate", dataCarMap.getString("use_rate"));
		outMap.put("duration", dataRoadMap.getString("duration"));
		int carCount = dataCarMap.getInt("car_count");
		if (carCount <= 0) {
			outMap.put("count", 0);
		} else {
			int orderCount = dataRoadMap.getInt("order_count");
			outMap.put("count", orderCount / carCount);
		}
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	public ParaMap getOrderFeeTop(ParaMap inMap) throws Exception {
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
		List<ParaMap> dataList = (List<ParaMap>) resultMap.get(RespKey.DATA.getValue());
		if (dataList == null || dataList.size() == 0) {
			return resultMap;
		}
		ArrayList<String> section = new ArrayList<String>();
		ArrayList<Double> totalFee = new ArrayList<Double>();
		for (ParaMap roadMap : dataList) {
			section.add(roadMap.getString("section"));
			totalFee.add(roadMap.getDouble("total_fee"));
		}
		ParaMap outMap = new ParaMap();
		outMap.put("section", section);
		outMap.put("total_fee", totalFee);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}
}
