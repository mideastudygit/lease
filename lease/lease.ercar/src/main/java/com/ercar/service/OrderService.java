package com.ercar.service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespState;
import com.common.util.ApiUtil;
import com.common.util.RespUtil;
import com.ercar.dao.OrderDao;
import com.ercar.util.DateTimeUtil;


/**
 * 租车订单服务
 * 
 * @author 吴财宾
 * @date 20170510
 * @version 1.1.0
 */
public class OrderService {
    
	OrderDao dao = new OrderDao();
	
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
		if (StrUtils.isNull(inMap.getString("mer_order_id")) ||
				StrUtils.isNull(inMap.getString("car_plate")) ||
				StrUtils.isNull(inMap.getString("merchant_id"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "request.param.missing");
			return outMap;
		}
		inMap.put("id", IDGenerator.newGUID());
		inMap.put("order_code", IDGenerator.newGUID());
		ParaMap resultMap = dao.addOrder(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "order.add.fail");
			return outMap;
		}
		ParaMap outMap = new ParaMap();
		outMap.put("order_id", inMap.getString("id"));
		outMap = RespUtil.setResp(outMap);
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
		if (StrUtils.isNull(inMap.getString("mer_order_id")) ||
				StrUtils.isNull(inMap.getString("duration")) ||
				StrUtils.isNull(inMap.getString("amount"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "request.param.missing");
			return outMap;
		}
		ParaMap resultMap = dao.updateOrder(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "order.update.fail");
			return outMap;
		}
		ParaMap outMap = new ParaMap();
		outMap = RespUtil.setResp(outMap);
		return outMap;
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
		if (StrUtils.isNull(inMap.getString("mer_order_id"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "request.param.missing");
			return outMap;
		}
		ParaMap resultMap = dao.updateOrderRealtime(inMap);
		if (resultMap.getInt("num") < 1) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL,
					"order.update.realtime.fail");
			return outMap;
		}
		ParaMap outMap = RespUtil.setResp();
		return outMap;
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
		
		ParaMap amountMap = dao.getAmount(inMap);
		String amount = amountMap.getRecordString(0,"amount");
		ParaMap todayAmountMap = dao.getTodayAmount(inMap);
		String todayAmount = todayAmountMap.getRecordString(0,"amount");
		ParaMap freMap = dao.getFre(inMap);
		String frequency = freMap.getRecordString(0,"frequency");
		ParaMap todayFreMap = dao.getTodayFre(inMap);
		String todayFre = todayFreMap.getRecordString(0,"frequency");
		ParaMap outMap = new ParaMap();
		outMap.put("amount", amount);
		outMap.put("today_amount", todayAmount);
		outMap.put("frequency", frequency);
		outMap.put("today_frequency", todayFre);
		outMap = RespUtil.setResp(outMap);
		return outMap;
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
		if (StrUtils.isNull(inMap.getString("order_id"))) {
			ParaMap outMap = RespUtil.setResp(RespState.FAIL, "request.param.missing");
			return outMap;
		}
		ParaMap resultMap = dao.getOrder(inMap);
		ParaMap outMap = ApiUtil.format(resultMap);
		outMap = RespUtil.setResp(outMap);
		return outMap;
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
		ParaMap resultMap = dao.getOrderList(inMap);
		ParaMap outMap = RespUtil.setResp(ApiUtil.formatList(resultMap));
		outMap.put("page_index", resultMap.getInt("pageIndex"));
		outMap.put("page_size", resultMap.getInt("pageSize"));
		outMap.put("total_count", resultMap.getInt("totalCount"));
		return outMap;
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
		
		ParaMap amountMap = dao.getAmount(inMap);
		String amount = amountMap.getRecordString(0,"amount");
		ParaMap monthAmountMap = dao.getMonthAmount(inMap);
		String monthAmount = monthAmountMap.getRecordString(0,"amount");
		ParaMap freMap = dao.getFre(inMap);
		String frequency = freMap.getRecordString(0,"frequency");
		ParaMap monthFreMap = dao.getMonthFre(inMap);
		String monthFre = monthFreMap.getRecordString(0,"frequency");
		ParaMap outMap = new ParaMap();
		outMap.put("amount", amount);
		outMap.put("month_amount", monthAmount);
		outMap.put("frequency", frequency);
		outMap.put("month_frequency", monthFre);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}
	
	/**
	 * 车辆使用率
	 * 
	 * @author 吴财宾
	 * @date 20170511
	 * @param inMap
	 *           merchant_id-商户id
	 * @return 
	 * 			use_rate-使用率,car_use_count-租车订单的车辆总数，car_count-总车辆，state-响应状态,code-响应码,msg-响应描述
	 */
	public ParaMap getCarUseRate(ParaMap inMap) throws Exception {
		double car_count = inMap.getDouble("car_count");
		ParaMap carUseCount = dao.getCarUseCount(inMap);
		double car_use_count = carUseCount.getRecordInt(0, "car_use_count");
		ParaMap outMap = new ParaMap();
		NumberFormat numFormat = NumberFormat.getPercentInstance();
		numFormat.setMinimumFractionDigits(2);
		String use_rate = numFormat.format(car_use_count / car_count);
		outMap.put("car_use_count",car_use_count);
		outMap.put("car_count",car_count);
		outMap.put("use_rate",use_rate);
		outMap = RespUtil.setResp(outMap);
		return outMap;
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
		ParaMap reslutMap = dao.getOrderAnalysis(inMap);
		List<ParaMap> data = ApiUtil.formatList(reslutMap);
		for (int i = 0; i < 30; i++) {
			ParaMap map = new ParaMap();
			map.put("days",i);
			map.put("amount",i);
			map.put("frequency",i);
			data.add(map);
		}
		int day = DateTimeUtil.getCurrentMonthDay();
		if (StrUtils.isNotNull(inMap.getString("month"))) {
			day = DateTimeUtil.getDaysOfMonth(inMap.getLong("month"));
		}
		Integer [] days = new Integer[day];
		Double [] amount = new Double[day];
		Integer [] frequency = new Integer[day];
		int d = 0;
		int k = 0;
		for (int i = 0; i < day;i++) {
			boolean f = true;
			for (int j = k; j < data.size();) {
				ParaMap dayMap = data.get(j);
				d = dayMap.getInt("days");
				if (d == (i + 1)) {
					k++;
					f = false;
					days[i] = i + 1;
					amount[i] = dayMap.getDouble("amount", 0.0);
					frequency[i] = dayMap.getInt("frequency",0);
				}
				break;
			}
			if (f) {
				days[i] = i + 1;
				amount[i] = 0.0;
				frequency[i] = 0;
			}
		}
		ParaMap monthAmountMap = dao.getMonthAmount(inMap);
		String monthAmount = monthAmountMap.getRecordString(0,"amount");
		ParaMap monthFreMap = dao.getMonthFre(inMap);
		String monthFre = monthFreMap.getRecordString(0,"frequency");
		ParaMap outMap = new ParaMap();
		outMap.put("days", days);
		outMap.put("amount", amount);
		outMap.put("frequency", frequency);
		outMap.put("month_amount", monthAmount);
		outMap.put("month_frequency", monthFre);
		ParaMap respMap = RespUtil.setResp(outMap);
		return respMap;
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
		ParaMap resultMap = dao.getRoad(inMap);
		List<ParaMap> data = ApiUtil.formatList(resultMap);
		ArrayList<String> amount = new ArrayList<String>();
		ArrayList<String> road = new ArrayList<String>();
		for (ParaMap roadMap : data) {
			amount.add(roadMap.getString("amount"));
			road.add(roadMap.getString("road"));
		}
		ParaMap outMap = new ParaMap();
		outMap.put("amount", amount);
		outMap.put("road", road);
		outMap = RespUtil.setResp(outMap);
		return outMap;
	}
	
	
}
