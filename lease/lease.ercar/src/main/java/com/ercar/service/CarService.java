package com.ercar.service;

import java.util.List;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;
import com.common.util.StringUtil;
import com.ercar.consts.DataDict.CarStatus;
import com.ercar.dao.CarDao;

/**
 * 车辆管理服务
 * 
 * @author 吴财宾
 * @date 20170508
 * @version 1.1.0
 *
 */
public class CarService {
	
	CarDao dao = new CarDao();
	
	/**
	 * 新增车辆信息
	 * 
	 * @author 吴财宾
	 * @date 20170508
	 * @param inMap
	 * 			mer_car_id-厂商推送车辆ID，car_plate-车牌号(必须)，brand-车辆品牌(必须)，merchant_id-商户id（必须）
	 *          use_date-投放日期，branch-投放网点
	 * @return state-响应状态,code-响应码,msg-响应描述,car_id-车辆id
	 */
	public ParaMap addCar(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		
		if (StringUtil.isEmpty(inMap.getString("car_plate")) ||
				StringUtil.isEmpty(inMap.getString("brand")) ||
				StringUtil.isEmpty(inMap.getString("merchant_id"))) {
			return RespUtil.setResp(RespState.FAIL, "request.param.missing");
		}
		ParaMap car = dao.getCarByPlate(inMap);
		if (car.getRecordCount() > 0) {
			return RespUtil.setResp(RespState.FAIL, "car.check.plate.repeat");
		}
		String carId = IDGenerator.newGUID();
		inMap.put("car_id", carId);
		ParaMap resultMap = dao.addCar(inMap);
		if (resultMap.getInt("num") < 1) {
			return RespUtil.setResp(RespState.FAIL, "car.add.fail");
		}
		outMap.put("car_id", carId);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}
	
	/**
	 * 获取车辆基本信息
	 * 
	 * @author 吴财宾
	 * @date 20170508
	 * @param inMap car_id-车牌id,car_plate-车牌号
	 * @return
	 * 			car_id-车辆id,mer_car_id-厂商推的车辆,brand-车辆品牌,status-车辆状态(1-使用中，2-故障，3-空闲，4-其他),
	 * 			capacity-电池容量,soc-剩余电池百分比,merchant_id-商户id,use_date-投放日期,
	 * 			branch-投放网点,lasttime-最后更新日期,car_plate-车牌号,
	 * 			state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getCar(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		if (StrUtils.isNull(inMap.getString("car_id")) &&
				StrUtils.isNull(inMap.getString("car_plate"))) {
			return RespUtil.setResp(RespState.FAIL, "request.param.missing");
		}
		ParaMap resultMap = dao.getCar(inMap);
		outMap = ApiUtil.format(resultMap);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}
	
	/**
	 * 修改车辆信息
	 * 
	 * @author 吴财宾
	 * @date 20170508
	 * @param inMap
	 *              car_no-车辆编号,car_plate-车牌号,brand-车辆品牌,merchant_id-商户id,car_id-车辆ID
	 * 				use_date-投放日期，branch-投放网点
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateCar(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		if (StringUtil.isEmpty(inMap.getString("car_id")) ||
				StringUtil.isEmpty(inMap.getString("car_plate")) ||
				StringUtil.isEmpty(inMap.getString("brand")) ||
				StringUtil.isEmpty(inMap.getString("merchant_id"))) {
			outMap = RespUtil.setResp(RespState.FAIL, "request.param.missing");
			return outMap;
		}
		
		ParaMap car = dao.getCarByPlate(inMap);
		if (car.getRecordCount() > 0) {
			outMap = RespUtil.setResp(RespState.FAIL, "car.check.plate.repeat");
			return outMap;
		}
		
		ParaMap resultMap = dao.updateCar(inMap);
		if (resultMap.getInt("num") < 1) {
			outMap = RespUtil.setResp(RespState.FAIL, "car.update.fail");
			return outMap;
		}
		outMap = RespUtil.setResp();
		return outMap;
	}
	
	/**
	 * 查询车辆信息列表
	 * 
	 * @author 吴财宾
	 * @date 20170508
	 * @param inMap
	 *              car_plate-车牌号,merchant_id-商户id,begin_time-创建时间开始,end_time-创建时间结束
	 *              page_index-当前页,page_size-每页记录数
	 * @return
	 */
	public ParaMap getCarList(ParaMap inMap) throws Exception {
		ParaMap resultMap = dao.getCarList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
	}
	
	/**
	 * 车辆实时信息更新
	 * 
	 * @author 吴财宾
	 * @date 20170509
	 * @param inMap
	 *              car_plate-车牌号,status-车辆状态(1-使用中，2-故障，3-空闲，4-其他),capacity-电池容量
	 *              soc-剩余电池百分比
	 * 
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateCarRealtime(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		if (StringUtil.isEmpty(inMap.getString("car_plate"))) {
			outMap = RespUtil.setResp(RespState.FAIL, "request.param.missing");
			return outMap;
		}
		ParaMap car = dao.getCarByPlate(inMap);
		if (car.getRecordCount() < 1) {
			outMap = RespUtil.setResp(RespState.FAIL, "car.check.plate.fail");
			return outMap;
		}
		ParaMap resultMap = dao.updateCarRealtime(inMap);
		if (resultMap.getInt("num") < 1) {
			outMap = RespUtil.setResp(RespState.FAIL, "car.update.realtiem.fail");
			return outMap;
		}
		outMap = RespUtil.setResp();
		return outMap;
	}
	
	/**
	 * 查询车辆使用状态实时数据
	 * 
	 * @author 吴财宾
	 * @date 20170509
	 * @param inMap merchant_id-商户id
	 * @return
	 * 			use-使用中，fault-故障，free-空闲，other-其他
	 * 			state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getCarRealtime(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		ParaMap resultMap = dao.getCarRealtime(inMap);
		List<ParaMap> data = ApiUtil.formatList(resultMap);
		int use = 0;
		int fault = 0;
		int free = 0;
		int other = 0;
		for (ParaMap statusMap : data) {
			if (statusMap.getInt("status") == CarStatus.USE.getValue().intValue()) {
				use += statusMap.getInt("car_status",0);
			}else if (statusMap.getInt("status") ==  CarStatus.FAULT.getValue().intValue()) {
				fault += statusMap.getInt("car_status",0);
			}else if (statusMap.getInt("status") ==  CarStatus.FREE.getValue().intValue()) {
				free += statusMap.getInt("car_status",0);
			}else {
				other += statusMap.getInt("car_status",0);
			}
		}
		outMap.put("use", use);
		outMap.put("fault", fault);
		outMap.put("free", free);
		outMap.put("other", other);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}
	
	/**
	 * 获取车辆基本信息通过车牌号
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap car_plate-车牌号
	 * @return
	 * 			car_id-车辆id,brand-车辆品牌,status-车辆状态(1-使用中，2-故障，3-空闲，4-其他),
	 * 			capacity-电池容量,soc-剩余电池百分比,merchant_id-商户id,use_date-投放日期,
	 * 			branch-投放网点,lasttime-最后更新日期,car_plate-车牌号,
	 */
	public ParaMap getCarByPlate(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("car_plate"))) {
			return RespUtil.setResp(RespState.FAIL, "request.param.missing");
		}
		ParaMap outMap = dao.getCarByPlate(inMap);
		return outMap;
	}
	
	/**
	 * 获取车辆总数
	 * 
	 * @author 吴财宾
	 * @date 20170511
	 * @param inMap merchant_id 厂商ID
	 * @return
	 * 			car_count 车辆总数
	 * 			
	 */
	public ParaMap getCarCount(ParaMap inMap) throws Exception {
		ParaMap resultMap = dao.getCarCount(inMap);
		return resultMap;
	}
	
	/**
	 * 车辆位置分布
	 * 
	 * @author 吴财宾
	 * @date 20170512
	 * @param inMap
	 *              status-车辆状态(1-使用中，2-故障，3-空闲，4-其他),merchant_id-商户id,
	 *              keywork-关键字（模糊查询）
	 * @return
	 */
	public ParaMap getCarPosition(ParaMap inMap) throws Exception {
		ParaMap resultMap = dao.getCarPosition(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		return outMap;
	}
	
}
