package com.road.service;

import java.util.List;

import com.base.http.HttpManager;
import com.base.log.Logging;
import com.base.utils.JsonUtils;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.base.web.AppConfig;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.consts.SqlConsts;
import com.common.util.DateUtil;
import com.common.util.HttpUtil;
import com.common.util.MD5SignUtil;
import com.common.util.RespUtil;
import com.road.entity.RoadAdaptor;
import com.road.util.SzUtil;
import com.road.consts.RoadConsts;

public class SzRoadService extends RoadAdaptor {

	private static final Logging log = Logging.getLogging("shenzhenlog");
	private static final String SHENZHEN_URL = AppConfig.getStringPro("shenzhen.url");

	/**
	 * 发起宜停车请求
	 */
	private ParaMap getShenZhenData(ParaMap inMap) {
		String url = SHENZHEN_URL.replace("#module#", inMap.getString("module")) + "?";
		inMap.remove("module");

		String requestParam = HttpUtil.getJoinUrl(inMap);
		String sign = MD5SignUtil.sign(inMap, this.getAppSecret());
		requestParam = requestParam + "&sign=" + sign;

		log.info("【" + inMap.getString("method") + "】--请求：" + url + requestParam);
		try {
			String response = HttpManager.getData(url, requestParam);
			log.info("【" + inMap.getString("method") + "】--响应：" + response);

			ParaMap dataMap = JsonUtils.strToMap(response);
			if (RespState.SUCCESS.getValue() == dataMap.getInt(RespKey.STATUS.getValue())) {
				return RespUtil.setResp(dataMap);
			}
			return RespUtil.setResp(RespState.FAIL, dataMap.getString(RespKey.CODE.getValue()),
					dataMap.getString(RespKey.MSG.getValue()));

		} catch (Exception e) {
			log.error("错误信息为：" + e.getMessage());
			return RespUtil.setResp(RespState.FAIL, "fail");
		}
	}

	@Override
	public ParaMap getOrder(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("appkey", this.getAppId());
		sendMap.put("method", "parkingrecord");
		sendMap.put("module", RoadConsts.MODULE_TYPE_TRANSACTION);
		sendMap.put("bargainorder", inMap.getString("provider_order_id"));
		ParaMap resultMap = getShenZhenData(sendMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap dataMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		ParaMap orderMap = SzUtil.orderConvert(dataMap);
		ParaMap outMap = RespUtil.setResp(orderMap);
		return outMap;
	}

	@Override
	public ParaMap endOrder(ParaMap inMap) throws Exception {
		ParaMap orderMap = SzUtil.endOrderConvert(inMap);
		return orderMap;
	}

	@Override
	public ParaMap getRechargeList(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("appkey", this.getAppId());
		sendMap.put("method", "getrechargelist");
		sendMap.put("module", RoadConsts.MODULE_TYPE_TRANSACTION);
		sendMap.put("pageIndex", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		sendMap.put("pageSize", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		String rechargeType = inMap.getString("recharge_type");
		if (StrUtils.isNotNull(rechargeType)) {
			sendMap.put("RechargeType", rechargeType);
		}
		String startTime = inMap.getString("start_time");
		if (StrUtils.isNotNull(startTime)) {
			sendMap.put("Stime", DateUtil.format(startTime, DateUtil.TO_SECOND_LINE));
		}
		String endTime = inMap.getString("end_time");
		if (StrUtils.isNotNull(endTime)) {
			sendMap.put("Etime", DateUtil.format(endTime, DateUtil.TO_SECOND_LINE));
		}
		ParaMap resultMap = getShenZhenData(sendMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap dataMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		ParaMap outMap = SzUtil.rechargeConvert(dataMap);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	@Override
	public ParaMap getNearbyBerthList(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("appkey", this.getAppId());
		sendMap.put("method", "getbearbyberth");
		sendMap.put("module", RoadConsts.MODULE_TYPE_BERTH);
		sendMap.put("Longitude", inMap.getString("longitude"));
		sendMap.put("Latitude", inMap.getString("latitude"));
		sendMap.put("Distance", inMap.getString("distance"));
		ParaMap resultMap = getShenZhenData(sendMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap dataMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		List<ParaMap> berthList = SzUtil.berthConvert(dataMap);
		ParaMap outMap = RespUtil.setResp(berthList);
		return outMap;
	}

	@Override
	public ParaMap getSectionList(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("appkey", this.getAppId());
		sendMap.put("method", "getsectionbykeywords");
		sendMap.put("module", RoadConsts.MODULE_TYPE_BERTH);
		sendMap.put("keywords", inMap.getString("keywords"));
		ParaMap resultMap = getShenZhenData(sendMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap dataMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		List<ParaMap> sectionList = SzUtil.sectionConvert(dataMap);
		ParaMap outMap = RespUtil.setResp(sectionList);
		return outMap;
	}

	@Override
	public ParaMap getBerthStatus(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("method", "berthstatus");
		sendMap.put("module", RoadConsts.MODULE_TYPE_TRANSACTION);
		sendMap.put("berthcode", inMap.getString("berth_code"));
		ParaMap resultMap = getShenZhenData(sendMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap dataMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		ParaMap outMap = new ParaMap();
		outMap.put("berth_code", inMap.getString("berth_code"));
		outMap.put("berth_status", SzUtil.getBerthStatus(dataMap));
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}

	@Override
	public ParaMap applyPark(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("appkey", this.getAppId());
		sendMap.put("method", "newparkapply");
		sendMap.put("module", RoadConsts.MODULE_TYPE_TRANSACTION);
		sendMap.put("parkuserid", this.getUserId());
		sendMap.put("ordertype", inMap.getString("pay_type"));
		sendMap.put("berthcode", inMap.getString("berth_code"));
		sendMap.put("platenumber", inMap.getString("car_plate"));
		ParaMap resultMap = getShenZhenData(sendMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap dataMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		ParaMap orderMap = SzUtil.applyOrderConvert(dataMap);
		ParaMap outMap = RespUtil.setResp(orderMap);
		return outMap;
	}

	@Override
	public ParaMap getArrearsList(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("appkey", this.getAppId());
		sendMap.put("method", "getarrearslist");
		sendMap.put("module", RoadConsts.MODULE_TYPE_TRANSACTION);
		sendMap.put("parkuserid", this.getUserId());
		sendMap.put("arrearsStatus", RoadConsts.ARREARS_STATUS_NO_PAY);// 查询未补缴状态的欠费单
		String startTime = inMap.getString("start_time");
		if (StrUtils.isNotNull(startTime)) {
			sendMap.put("Stime", DateUtil.format(startTime, DateUtil.TO_SECOND_LINE));
		}
		String endTime = inMap.getString("end_time");
		if (StrUtils.isNotNull(endTime)) {
			sendMap.put("Etime", DateUtil.format(endTime, DateUtil.TO_SECOND_LINE));
		}
		sendMap.put("pageIndex", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		sendMap.put("pageSize", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		ParaMap resultMap = getShenZhenData(sendMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap dataMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		List<ParaMap> arrearsList = SzUtil.arrearsConvert(dataMap);
		ParaMap outMap = RespUtil.setResp(arrearsList);
		return outMap;
	}

	@Override
	public ParaMap payArrears(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("appkey", this.getAppId());
		sendMap.put("method", "arrearspay");
		sendMap.put("module", RoadConsts.MODULE_TYPE_TRANSACTION);
		sendMap.put("paypwd", this.getPassword());// 必须添加支付密码
		sendMap.put("parkuserid", this.getUserId());
		sendMap.put("ordercode", inMap.getString("provider_arrears_code"));
		ParaMap resultMap = getShenZhenData(sendMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
	}

	@Override
	public ParaMap getRefundList(ParaMap inMap) throws Exception {
		ParaMap sendMap = new ParaMap();
		sendMap.put("appkey", this.getAppId());
		sendMap.put("method", "getrefundorderlist");
		sendMap.put("module", RoadConsts.MODULE_TYPE_TRANSACTION);
		sendMap.put("packuserid", this.getUserId());
		sendMap.put("refundstatus", RoadConsts.REFUND_STATUS_REFUNDED);
		String startTime = inMap.getString("start_time");
		if (StrUtils.isNotNull(startTime)) {
			sendMap.put("Stime", DateUtil.format(startTime, DateUtil.TO_SECOND_LINE));
		}
		String endTime = inMap.getString("end_time");
		if (StrUtils.isNotNull(endTime)) {
			sendMap.put("Etime", DateUtil.format(endTime, DateUtil.TO_SECOND_LINE));
		}
		sendMap.put("pageIndex", inMap.getInt("page_index", SqlConsts.PAGE_INDEX));
		sendMap.put("pageSize", inMap.getInt("page_size", SqlConsts.PAGE_SIZE));
		ParaMap resultMap = getShenZhenData(sendMap);
		if (RespState.SUCCESS.getValue() != resultMap.getInt(RespKey.STATE.getValue())) {
			return resultMap;
		}
		ParaMap dataMap = (ParaMap) resultMap.get(RespKey.DATA.getValue());
		List<ParaMap> refundList = SzUtil.refundConvert(dataMap);
		ParaMap outMap = RespUtil.setResp(refundList);
		return outMap;
	}

}
