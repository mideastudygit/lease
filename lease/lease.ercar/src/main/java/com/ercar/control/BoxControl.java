package com.ercar.control;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespState;
import com.common.util.RespUtil;
import com.ercar.service.BoxService;
import com.ercar.service.CarService;

/**
 * 车载盒子服务
 * 
 * @author 吴财宾
 * @date 20170509
 * @version 1.1.0
 *
 */
public class BoxControl {
	
	BoxService service = new BoxService();
	
	CarService carService = new CarService();
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
		ParaMap car = carService.getCarByPlate(inMap);
		if (car.getRecordCount() < 1) {
			outMap = RespUtil.setResp(RespState.FAIL, "car.check.plate.fail");
			return outMap;
		}
		inMap.put("car_id", car.getRecordString(0,"car_id"));
		outMap = service.addBox(inMap);
		return outMap;
	}
	
	/**
	 * 获取车载盒子详情
	 * 
	 * @author 吴财宾
	 * @date 20170509
	 * @param inMap
	 * 				box_id-盒子ID
	 * @return
	 * 			state-响应状态,code-响应码,msg-响应描述
	 * 			box_no-设备状态(1-正常，0-故障)，use_date-投放日期，network-联网状态（1-在线，0-离线），
	 * 			start-启用状态（1-启用中），car_id-所属车辆ID，car_plate-车牌号，longitude-经度，
	 * 			latitude-纬度，lasttime-最后更新时间，dynamic_code-当前动态码，merchant_id-厂商ID
	 * 			brand-车辆品牌，status-车辆状态(1-使用中，2-故障，3-空闲，4-其他)，car_no-车辆编号
	 */
	public ParaMap getBox(ParaMap inMap) throws Exception {
		return service.getBox(inMap);
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
		ParaMap car = carService.getCarByPlate(inMap);
		if (car.getRecordCount() < 1) {
			outMap = RespUtil.setResp(RespState.FAIL, "car.check.plate.fail");
			return outMap;
		}
		inMap.put("car_id", car.getRecordString(0,"car_id"));
		outMap = service.updateBox(inMap);
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
		return service.getBoxList(inMap);
	}
	
	/**
	 * 车载盒子实时信息更新
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 * 				box_no-设备编号,network-联网状态（1-在线，0-离线），start-启用状态（1-启用中），
	 * 				longitude-经度，lasttime-纬度，status-设备状态，address-地址,dynamic_code-动态码'
	 * @return state-响应状态,code-响应码,msg-响应描述
	 * @throws Exception
	 */
	public ParaMap updateBoxRealtime(ParaMap inMap) throws Exception {
		return service.updateBoxRealtime(inMap);
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
		return service.getBoxRealtime(inMap);
	}

}
