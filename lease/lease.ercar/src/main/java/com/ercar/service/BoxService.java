package com.ercar.service;

import java.util.List;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;
import com.ercar.consts.DataDict.BoxStatus;
import com.ercar.consts.DataDict.NetworkStatus;
import com.ercar.dao.BoxDao;

/**
 * 车载盒子服务
 * 
 * @author 吴财宾
 * @date 20170509
 * @version 1.1.0
 *
 */
public class BoxService {
	
	BoxDao dao = new BoxDao();
	
	/**
	 * 新增车载盒子
	 * 
	 * @author 吴财宾
	 * @date 20170509
	 * @param inMap
	 * 				box_no-设备编号,car_plate-所属车牌号,use_date-投放日期
	 * @return box_id-盒子id,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addBox(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		if (StrUtils.isNull(inMap.getString("box_no")) ||
				StrUtils.isNull(inMap.getString("car_plate"))) {
			outMap = RespUtil.setResp(RespState.FAIL, "request.param.missing");
			return outMap;
		}
		ParaMap checkBox = dao.getBoxByNo(inMap);
		if (checkBox.getRecordCount() > 0) {
			outMap = RespUtil.setResp(RespState.FAIL, "box.check.no.fail");
			return outMap;
		}
		ParaMap box = dao.getBoxByCarId(inMap);
		if (box.getRecordCount() > 0) {
			outMap = RespUtil.setResp(RespState.FAIL, "box.check.carid.fail");
			return outMap;
		}
		String boxId = IDGenerator.newGUID();
		inMap.put("box_id", boxId);
		ParaMap resultMap = dao.addBox(inMap);
		if (resultMap.getInt("num") < 1) {
			outMap = RespUtil.setResp(RespState.FAIL, "box.update.fail");
			return outMap;
		}
		outMap.put("box_id", boxId);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}
	
	/**
	 * 获取车载盒子详情
	 * 
	 * @author 吴财宾
	 * @date 20170509
	 * @param inMap
	 * 				box_id-盒子ID,box_no-盒子编号
	 * @return
	 * 			state-响应状态,code-响应码,msg-响应描述
	 * 			box_no-设备状态(1-正常，0-故障)，use_date-投放日期，network-联网状态（1-在线，0-离线），
	 * 			start-启用状态（1-启用中），car_id-所属车辆ID，car_plate-车牌号，longitude-经度，
	 * 			latitude-纬度，lasttime-最后更新时间，dynamic_code-当前动态码，merchant_id-厂商ID
	 * 			brand-车辆品牌，status-车辆状态(1-使用中，2-故障，3-空闲，4-其他)，car_no-车辆编号
	 */
	public ParaMap getBox(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		if (StrUtils.isNull(inMap.getString("box_id")) &&
				StrUtils.isNull(inMap.getString("box_no"))) {
			outMap = RespUtil.setResp(RespState.FAIL, "request.param.missing");
			return outMap;
		}
		ParaMap resultMap = dao.getBox(inMap);
		outMap = ApiUtil.format(resultMap);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}
	
	/**
	 * 修改盒子信息
	 * 
	 * @author 吴财宾
	 * @date 20170509
	 * @param inMap
	 * 				box_id-设备ID,box_no-设备编号,car_plate-所属车牌号,merchant_id-商户id,use_date-投入日期
	 * @return state-响应状态,code-响应码,msg-响应描述
	 * @throws Exception
	 */
	public ParaMap updateBox(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		if (StrUtils.isNull(inMap.getString("box_id")) ||
				StrUtils.isNull(inMap.getString("box_no")) ||
				StrUtils.isNull(inMap.getString("car_plate"))) {
			outMap = RespUtil.setResp(RespState.FAIL, "request.param.missing");
			return outMap;
		}
		ParaMap checkBox = dao.getBoxByNo(inMap);
		if (checkBox.getRecordCount() > 0) {
			outMap = RespUtil.setResp(RespState.FAIL, "box.check.no.fail");
			return outMap;
		}
		
		ParaMap box = dao.getBoxByCarId(inMap);
		if (box.getRecordCount() > 0) {
			outMap = RespUtil.setResp(RespState.FAIL, "box.check.carid.fail");
			return outMap;
		}
		ParaMap resultMap = dao.updateBox(inMap);
		if (resultMap.getInt("num") < 1) {
			return RespUtil.setResp(RespState.FAIL, "box.update.fail");
		}
		outMap = RespUtil.setResp();
		return outMap;
	}
	
	/**
	 * 查询盒子信息列表
	 * 
	 * @author 吴财宾
	 * @date 20170509
	 * @param inMap
	 * 				box_no-设备编号，merchant_id-厂商ID，begin_date-开始日期，page_index-当前页，page_size-每页记录数
	 * @return
	 * 			state-响应状态,code-响应码,msg-响应描述
	 * 			box_no-设备状态(1-正常，0-故障)，use_date-投放日期，network-联网状态（1-在线，0-离线），
	 * 			start-启用状态（1-启用中），car_id-所属车辆ID，car_plate-车牌号，longitude-经度，
	 * 			latitude-纬度，lasttime-最后更新时间，dynamic_code-当前动态码，merchant_id-厂商ID
	 * 			brand-车辆品牌，status-车辆状态(1-使用中，2-故障，3-空闲，4-其他)，car_no-车辆编号
	 */
	public ParaMap getBoxList(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		ParaMap resultMap = dao.getBoxList(inMap);
		outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}
	
	/**
	 * 车载盒子实时信息更新
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 * 				box_no-设备编号,network-联网状态（1-在线，0-离线），start-启用状态（1-启用中），
	 * 				longitude-经度，lasttime-纬度，status-设备状态，address-地址,dynamic_code-动态码',use_date-投放日期
	 * @return state-响应状态,code-响应码,msg-响应描述
	 * @throws Exception
	 */
	public ParaMap updateBoxRealtime(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		if (StrUtils.isNull(inMap.getString("box_no"))) {
			outMap = RespUtil.setResp(RespState.FAIL, "request.param.missing");
			return outMap;
		}
		
		ParaMap resultMap = dao.updateBoxRealtime(inMap);
		if (resultMap.getInt("num") < 1) {
			return RespUtil.setResp(RespState.FAIL, "box.update.realtiem.fail");
		}
		outMap = RespUtil.setResp();
		return outMap;
	}
	
	/**
	 * 随车设备使用状态实时数据
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 * 				merchant_id-商户id
	 * @return
	 * 			state-响应状态,code-响应码,msg-响应描述
	 * 			normal-正常，fault-故障，online-在线，offline-离线
	 */
	public ParaMap getBoxRealtime(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		ParaMap network = dao.getBoxNetwork(inMap);
		ParaMap start = dao.getBoxStatus(inMap);
		List<ParaMap> networkList = ApiUtil.formatList(network);
		List<ParaMap> startList = ApiUtil.formatList(start);
		int normal = 0;
		int fault = 0;
		int online = 0;
		int offline = 0;
		for (ParaMap statusMap : networkList) {
			if (statusMap.getInt("network") == NetworkStatus.ONLINE.getValue().intValue()) {
				online += statusMap.getInt("network_count",0);
			}else {
				offline += statusMap.getInt("network_count",0);
			}
		}
		for (ParaMap statusMap : startList) {
			if (statusMap.getInt("status") == (BoxStatus.NOMAL.getValue().intValue())) {
				normal += statusMap.getInt("status_count",0);
			}else {
				fault += statusMap.getInt("status_count",0);
			}
		}
		outMap.put("normal", normal);
		outMap.put("fault", fault);
		outMap.put("online", online);
		outMap.put("offline", offline);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}
	

}
