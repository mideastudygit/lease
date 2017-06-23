package com.ercar.control;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespState;
import com.common.util.RespUtil;
import com.ercar.service.CarService;
import com.ercar.service.OrderService;


/**
 * 租车订单服务
 * 
 * @author 吴财宾
 * @date 20170510
 * @version 1.1.0
 */
public class ErcarOrderControl {

OrderService service = new OrderService();
	
	/**
	 * 新增租车订单
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 *            mer_order_id-厂商推送的订单ID，car_plate-车牌号（插入车辆ID）,merchant_id-商户id，status-订单状态(1-进行中，2-已完成)，
	 *            duration-租车时长，amount-订单金额，，begin_time-开始时间，end_time-结束时间，
	 *            take_branch-取车网点,park_branch-还车网点，take_berth-取车泊位，park_berth-还车泊位
	 *            longitude-经度，latitude-纬度，
	 * @return  order_id-订单ID,state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap addOrder(ParaMap inMap) throws Exception {
		ParaMap outMap = new ParaMap();
		if (StrUtils.isNull(inMap.getString("mer_order_id")) ||
				StrUtils.isNull(inMap.getString("car_plate")) ||
				StrUtils.isNull(inMap.getString("merchant_id"))) {
			outMap = RespUtil.setResp(RespState.FAIL, "request.param.missing");
			return outMap;
		}
		CarService carService = new CarService();
		ParaMap carMap = carService.getCarByPlate(inMap);
		if (carMap.getRecordCount() < 1) {
			return RespUtil.setResp(RespState.FAIL, "car.check.plate.fail");
		}
		inMap.put("car_id", carMap.getRecordString(0,"car_id"));
		outMap = service.addOrder(inMap);
		return outMap;
	}
	
	/**
	 * 修改租车订单（结束-推送）
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @para inMap
	 *             mer_order_id-厂商推送的订单ID，car_plate-车牌号（插入车辆ID）,merchant_id-商户id，status-订单状态(1-进行中，2-已完成)，
	 *            duration-租车时长，amount-订单金额，，begin_time-开始时间，end_time-结束时间，
	 *            take_branch-取车网点,park_branch-还车网点，take_berth-取车泊位，park_berth-还车泊位
	 *            longitude-经度，latitude-纬度，
	 * @return  state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateOrder(ParaMap inMap) throws Exception {
		return service.updateOrder(inMap);
	}
	
	/**
	 * 租车订单实时信息更新
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 *            mer_order_id-厂商推送的订单ID，status-订单状态(1-进行中，2-已完成)，
	 *            duration-租车时长，amount-订单金额，
	 * @return state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap updateOrderRealtime(ParaMap inMap) throws Exception {
		return service.updateOrderRealtime(inMap);
	}
	
	/**
	 * 今日数据统计（次数、收费）
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 *           merchant_id-商户id
	 * @return 
	 * 			todayamount-今日租车收费,amount-累计租车收费,todayfrequency-今日租车次数,frequency-累计租车次数 
	 * 			state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getTodayCount(ParaMap inMap) throws Exception {
		return service.getTodayCount(inMap);
	}
	
	/**
	 * 获取订单详情
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 *            order_id-订单ID
	 * @return 
	 * 			order_code-订单编号，car_no-车辆编号,car_plate-车牌号，duration-租车时长，amount-订单金额，
	 *          merchant_id-商户id，status-订单状态(1-进行中，2-已完成)，take_branch-取车网点,park_branch-还车网点，
	 *          take_berth-取车泊位，park_berth-还车泊位,begin_time-开始时间,end_time-结束时间
	 */
	public ParaMap getOrder(ParaMap inMap) throws Exception {
		return service.getOrder(inMap);
	}
	
	/**
	 * 获取订单列表
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 *            car_keyword-车牌号或车辆编号,支持模糊查询,status-订单状态(1-进行中，2-已完成),merchant_id-商户id
	 *            begin_time-创建时间开始,end_time-创建时间结束,page_index-当前页,page_size-每页记录数
	 * @return 
	 * 			order_id-订单ID,order_code-订单编号，car_no-车辆编号,car_plate-车牌号，duration-租车时长，amount-订单金额，
	 *          merchant_id-商户id，status-订单状态(1-进行中，2-已完成)， begin_time-开始时间
	 */
	public ParaMap getOrderList(ParaMap inMap) throws Exception {
		return service.getOrderList(inMap);
	}
	
	/**
	 * 本月数据统计（次数、收费）
	 * 
	 * @author 吴财宾
	 * @date 20170511
	 * @param inMap
	 *           merchant_id-商户id
	 * @return 
	 * 			monthamount-本月租车收费,amount-累计租车收费，monthfrequency-本月租车次数,frequency-累计租车次数 
	 * 			state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getMonthCount(ParaMap inMap) throws Exception {
		return service.getMonthCount(inMap);
	}
	
	/**
	 * 车辆使用率
	 * 
	 * @author 吴财宾
	 * @date 20170511
	 * @param inMap
	 *           merchant_id-商户id
	 * @return 
	 * 			use_rate-使用率，state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getCarUseRate(ParaMap inMap) throws Exception {
		CarService carService = new CarService();
		ParaMap carCount = carService.getCarCount(inMap);
		double car_count = 1L;
		if (carCount.getRecordInt(0, "car_count") != 0) {
			car_count = carCount.getRecordInt(0, "car_count");
		}
		inMap.put("car_count", car_count);
		return service.getCarUseRate(inMap);
	}
	
	/**
	 * 租车收费数据分析(月份，厂商)
	 * 
	 * @author 吴财宾
	 * @date 20170511
	 * @param inMap
	 *           merchant_id-商户id，month-年月（时间戳）
	 * @return 
	 * 			count-次数，date-日期，amount-金额，state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getOrderAnalysis(ParaMap inMap) throws Exception {
		return service.getOrderAnalysis(inMap);
	}
	
	/**
	 * 租车网点本月收入前十路段名称
	 * 
	 * @author 吴财宾
	 * @date 20170512
	 * @param inMap
	 *           month-月份（时间戳）, merchant_id-商户id
	 * @return 
	 * 			road-路段名称,amount-租车费用，state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getRoad(ParaMap inMap) throws Exception {
		return service.getRoad(inMap);
	}
	
}
