package com.ercar.control;

import com.base.utils.ParaMap;
import com.ercar.service.CarService;

/**
 * 车辆管理服务
 * 
 * @author 吴财宾
 * @date 20170508
 * @version 1.1.0
 *
 */
public class CarControl {

	CarService service = new CarService();
	
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
		return service.addCar(inMap);
	}
	
	/**
	 * 获取车辆基本信息
	 * 
	 * @author 吴财宾
	 * @date 20170508
	 * @param inMap car_id-车牌id
	 * @return
	 * 			car_id-车辆id,mer_car_id-厂商推的车辆,brand-车辆品牌,status-车辆状态(1-使用中，2-故障，3-空闲，4-其他),
	 * 			capacity-电池容量,soc-剩余电池百分比,merchant_id-商户id,use_date-投放日期,
	 * 			branch-投放网点,lasttime-最后更新日期,car_plate-车牌号,
	 * 			state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getCar(ParaMap inMap) throws Exception {
		return service.getCar(inMap);
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
		return service.updateCar(inMap);
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
		return service.getCarList(inMap);
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
		return service.updateCarRealtime(inMap);
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
		return service.getCarRealtime(inMap);
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
		return service.getCarPosition(inMap);
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
		ParaMap resultMap = service.getCarCount(inMap);
		return resultMap;
	}
	
}
