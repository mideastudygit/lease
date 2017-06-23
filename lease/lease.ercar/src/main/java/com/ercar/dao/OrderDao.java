package com.ercar.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.util.DateUtil;
import com.ercar.util.DateTimeUtil;

/**
 * 租车订单类
 * 
 * @author 吴财宾
 * @date 20170510
 * @version 1.1.0
 *
 */
public class OrderDao extends BaseDataSetDao {

	/**
	 * 新增租车订单
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 *            id-主键，order_id-订单ID,order_code-订单号，car_id-车辆ID，merchant_id-商户id，
	 *            status-订单状态(1-进行中，2-已完成)，take_branch-取车网点,take_berth-取车泊位，
	 * @return  
	 */
	public ParaMap addOrder(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "addOrder");
		sqlMap.addParam(inMap.getString("id"));
		sqlMap.addParam(inMap.getString("mer_order_id"));
		sqlMap.addParam(inMap.getString("order_code"));
		sqlMap.addParam(inMap.getString("car_id"));
		sqlMap.addParam(inMap.getString("merchant_id"));
		sqlMap.addParam(inMap.getString("status"));
		sqlMap.addParam(inMap.getString("amount"));
		sqlMap.addParam(inMap.getString("duration"));
		sqlMap.addParam(inMap.getString("take_branch"));
		sqlMap.addParam(inMap.getString("take_berth"));
		sqlMap.addParam(inMap.getString("park_branch"));
		sqlMap.addParam(inMap.getString("park_berth"));
		sqlMap.addParam(inMap.getString("longitude"));
		sqlMap.addParam(inMap.getString("latitude"));
		String begin_time = inMap.getString("begin_time");
		if (StrUtils.isNull(begin_time)) {
			begin_time = DateUtil.nowTime()+"";
		}
		sqlMap.addParam(begin_time);
		String end_time = inMap.getString("end_time");
		if (StrUtils.isNull(end_time)) {
			end_time = DateUtil.nowTime()+"";
		}
		sqlMap.addParam(end_time);
		ParaMap outMap = insert(sqlMap);
		return outMap;
	}
	
	/**
	 * 金额统计
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 *           merchant_id-商户id
	 * @return 
	 */
	public ParaMap getAmount(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "getAmount");
		String dynamicSql = "";
		String merchant_id = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchant_id)) {
			dynamicSql += " AND merchant_id = ?";
			sqlMap.addParam(merchant_id);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}
	
	/**
	 * 今日金额统计
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 *           merchant_id-商户id
	 * @return 
	 */
	public ParaMap getTodayAmount(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "getAmount");
		String dynamicSql = "";
		dynamicSql += " AND end_time >= ?";
		sqlMap.addParam(DateTimeUtil.getTimesmorning());
		dynamicSql += " AND end_time <= ?";
		sqlMap.addParam(DateTimeUtil.getTimesnight());
		String merchant_id = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchant_id)) {
			dynamicSql += " AND merchant_id = ?";
			sqlMap.addParam(merchant_id);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 租车次数
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 * 				merchant_id-厂商ID
	 * @return
	 */
	public ParaMap getFre(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "getFre");
		String dynamicSql = "";
		String merchant_id = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchant_id)) {
			dynamicSql += " AND merchant_id = ?";
			sqlMap.addParam(merchant_id);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}
	
	/**
	 * 今日租车次数
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 *           merchant_id-商户id
	 * @return 
	 */
	public ParaMap getTodayFre(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "getFre");
		String dynamicSql = "";
		dynamicSql += " AND end_time >= ?";
		sqlMap.addParam(DateTimeUtil.getTimesmorning());
		dynamicSql += " AND end_time <= ?";
		sqlMap.addParam(DateTimeUtil.getTimesnight());
		String merchant_id = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchant_id)) {
			dynamicSql += " AND merchant_id = ?";
			sqlMap.addParam(merchant_id);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
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
	 */
	public ParaMap updateOrderRealtime(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "updateOrderRealtime");
		String dynamicSql = "";
		String status = inMap.getString("status");
		if (StrUtils.isNotNull(status)) {
			dynamicSql += ", status = ?";
			sqlMap.addParam(status);
		}
		String duration = inMap.getString("duration");
		if (StrUtils.isNotNull(duration)) {
			dynamicSql += ", duration = ?";
			sqlMap.addParam(duration);
		}
		String amount = inMap.getString("amount");
		if (StrUtils.isNotNull(amount)) {
			dynamicSql += ", amount = ?";
			sqlMap.addParam(amount);
		}
		sqlMap.addParam(inMap.getString("mer_order_id"));
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql.substring(1));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 修改租车订单
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 *            duration-租车时长，amount-订单金额,status-订单状态(1-进行中，2-已完成)，
	 *            park_branch-还车网点,park_berth-还车泊位，mer_order_id-厂商推的订单ID
	 * @return  
	 */
	public ParaMap updateOrder(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "updateOrder");
		String dynamicSql = "";
		String duration = inMap.getString("duration");
		if (StrUtils.isNotNull(duration)) {
			dynamicSql += ", duration = ?";
			sqlMap.addParam(duration);
		}
		String amount = inMap.getString("amount");
		if (StrUtils.isNotNull(amount)) {
			dynamicSql += ", amount = ?";
			sqlMap.addParam(amount);
		}
		String status = inMap.getString("status");
		if (StrUtils.isNotNull(status)) {
			dynamicSql += ", status = ?";
			sqlMap.addParam(status);
		}
		String park_branch = inMap.getString("park_branch");
		if (StrUtils.isNotNull(park_branch)) {
			dynamicSql += ", park_branch = ?";
			sqlMap.addParam(park_branch);
		}
		String park_berth = inMap.getString("park_berth");
		if (StrUtils.isNotNull(park_berth)) {
			dynamicSql += ", park_berth = ?";
			sqlMap.addParam(park_berth);
		}
		String end_time = inMap.getString("end_tiem");
		if (StrUtils.isNull(end_time)) {
			end_time = DateUtil.nowTime() + "";
		} 
		dynamicSql += ", end_time = ?";
		sqlMap.addParam(end_time);
		sqlMap.addParam(inMap.getString("mer_order_id"));
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql.substring(1));
		ParaMap outMap = update(sqlMap);
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
	 */
	public ParaMap getOrder(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "getOrder");
		sqlMap.addParam(inMap.getString("order_id"));
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 获取订单列表
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 *            order_id-订单ID
	 * @return 
	 */
	public ParaMap getOrderList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "getOrderList");
		String dynamicSql = "";
		int status = inMap.getInt("status",1);
		dynamicSql += " AND o.status = ?";
		sqlMap.addParam(status);
		String car_keyword = inMap.getString("car_keyword");
		if (StrUtils.isNotNull(car_keyword)) {
			dynamicSql += " AND ( c.car_no LIKE ? OR c.car_plate LIKE ? )";
			sqlMap.addParam("%"+car_keyword+"%");
			sqlMap.addParam("%"+car_keyword+"%");
		}
		String merchant_id = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchant_id)) {
			dynamicSql += " AND o.merchant_id = ?";
			sqlMap.addParam(merchant_id);
		}
		String begin_time = inMap.getString("begin_time");
		if (StrUtils.isNotNull(begin_time)) {
			dynamicSql += " AND o.begin_time >= ?";
			sqlMap.addParam(begin_time);
		}
		String end_time = inMap.getString("end_time");
		if (StrUtils.isNotNull(end_time)) {
			dynamicSql += " AND o.end_time <= ?";
			sqlMap.addParam(end_time);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		sqlMap.put("pageIndex", inMap.getInt("page_index", 1));
		sqlMap.put("pageSize", inMap.getInt("page_size", 10));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}

	/**
	 * 某月金额统计
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 *           merchant_id-商户id
	 * @return 
	 */
	public ParaMap getMonthAmount(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "getAmount");
		String dynamicSql = "";
		long month = DateUtil.nowTime();
		if (StrUtils.isNotNull(inMap.getString("month"))) {
			month = inMap.getLong("month");
		}
		dynamicSql += " AND end_time >= ?";
		sqlMap.addParam(DateTimeUtil.getFirstday(month));
		dynamicSql += " AND end_time <= ?";
		sqlMap.addParam(DateTimeUtil.getLastday(month));
		String merchant_id = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchant_id)) {
			dynamicSql += " AND merchant_id = ?";
			sqlMap.addParam(merchant_id);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 某月租车次数
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 *           merchant_id-商户id
	 * @return 
	 */
	public ParaMap getMonthFre(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "getFre");
		String dynamicSql = "";
		long month = DateUtil.nowTime();
		if (StrUtils.isNotNull(inMap.getString("month"))) {
			month = inMap.getLong("month");
		}
		dynamicSql += " AND end_time >= ?";
		sqlMap.addParam(DateTimeUtil.getFirstday(month));
		dynamicSql += " AND end_time <= ?";
		sqlMap.addParam(DateTimeUtil.getLastday(month));
		String merchant_id = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchant_id)) {
			dynamicSql += " AND merchant_id = ?";
			sqlMap.addParam(merchant_id);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
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
	 */
	public ParaMap getCarUseCount(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "getCarUseCount");
		String dynamicSql = "";
		String merchant_id = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchant_id)) {
			dynamicSql += " AND merchant_id = ?";
			sqlMap.addParam(merchant_id);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
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
	 */
	public ParaMap getOrderAnalysis(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "getOrderAnalysis");
		String dynamicSql = "";
		long month = 0L;
		if (StrUtils.isNotNull(inMap.getString("month"))) {
			month = inMap.getLong("month");
		}else {
			month = DateUtil.nowTime();
		}
		month =  month / 1000;
		sqlMap.addParam(month);
		String merchant_id = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchant_id)) {
			dynamicSql += " AND merchant_id = ?";
			sqlMap.addParam(merchant_id);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 租车网点本月收入前十路段名称
	 * 
	 * @author 吴财宾
	 * @date 20170512
	 * @param inMap
	 *           month-月份（时间戳）, merchant_id-商户id
	 * @return 
	 */
	public ParaMap getRoad(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("order", "getRoad");
		String dynamicSql = "";
		long month = DateUtil.nowTime();
		if (StrUtils.isNotNull(inMap.getString("month"))) {
			month = inMap.getLong("month");
		}
		month =  month / 1000;
		sqlMap.addParam(month);
		String merchant_id = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchant_id)) {
			dynamicSql += " AND merchant_id = ?";
			sqlMap.addParam(merchant_id);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

}
