package com.ercar.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.util.DateUtil;

/**
 * 车辆管理类
 * 
 * @author 吴财宾
 * @date 20170508
 * @version 1.1.0
 *
 */
public class CarDao extends BaseDataSetDao{

	/**
	 * 新增车辆信息
	 * 
	 * @author 吴财宾
	 * @date 20170508
	 * @param inMap
	 * @return
	 */
	public ParaMap addCar(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("car", "addCar");
		sqlMap.addParam(inMap.getString("car_id"));
		sqlMap.addParam(inMap.getString("mer_car_id"));
		sqlMap.addParam(inMap.getString("car_plate"));
		sqlMap.addParam(inMap.getString("brand"));
		sqlMap.addParam(inMap.getString("merchant_id"));
		sqlMap.addParam(inMap.getString("use_date"));
		sqlMap.addParam(inMap.getString("branch"));
		ParaMap outMap = insert(sqlMap);
		return outMap;
	}

	/**
	 * 获取车辆基本信息
	 * 
	 * @author 吴财宾
	 * @date 20170508
	 * @param inMap
	 * @return
	 */
	public ParaMap getCar(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("car", "getCar");
		String dynamicSql = "";
		String car_id = inMap.getString("car_id");
		if (StrUtils.isNotNull(car_id)) {
			dynamicSql += " AND id = ?";
			sqlMap.addParam(car_id);
		}
		String car_plate = inMap.getString("car_plate");
		if (StrUtils.isNotNull(car_plate)) {
			dynamicSql += " AND car_plate = ?";
			sqlMap.addParam(car_plate);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}
	
	/**
	 * 获取车辆基本信息通过车牌
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 * @return
	 */
	public ParaMap getCarByPlate(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("car", "getCarByPlate");
		sqlMap.addParam(inMap.getString("car_plate"));
		String dynamicSql = "";
		String car_id = inMap.getString("car_id");
		if (StrUtils.isNotNull(car_id)) {
			dynamicSql += " AND id != ?";
			sqlMap.addParam(car_id);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 修改车辆信息
	 * 
	 * @author 吴财宾
	 * @date 20170508
	 * @param inMap
	 * @return
	 */
	public ParaMap updateCar(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("car", "updateCar");
		String dynamicSql = "";
		String mer_car_id = inMap.getString("mer_car_id");
		if (StrUtils.isNotNull(mer_car_id)) {
			dynamicSql += ", mer_car_id = ?";
			sqlMap.addParam(mer_car_id);
		}
		String car_plate = inMap.getString("car_plate");
		if (StrUtils.isNotNull(car_plate)) {
			dynamicSql += ", car_plate = ?";
			sqlMap.addParam(car_plate);
		}
		String brand = inMap.getString("brand");
		if (StrUtils.isNotNull(brand)) {
			dynamicSql += ", brand = ?";
			sqlMap.addParam(brand);
		}
		String merchant_id = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchant_id)) {
			dynamicSql += ", merchant_id = ?";
			sqlMap.addParam(merchant_id);
		}
		String use_date = inMap.getString("use_date");
		if (StrUtils.isNotNull(use_date)) {
			dynamicSql += ", use_date = ?";
			sqlMap.addParam(use_date);
		}
		String branch = inMap.getString("branch");
		if (StrUtils.isNotNull(branch)) {
			dynamicSql += ", branch = ?";
			sqlMap.addParam(branch);
		}
		sqlMap.addParam(inMap.getString("car_id"));
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql.substring(1));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 查询车辆信息列表
	 * 
	 * @author 吴财宾
	 * @date 20170508
	 * @param inMap
	 * @return
	 */
	public ParaMap getCarList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("car", "getCarList");
		String dynamicSql = "";
		String car_plate = inMap.getString("car_plate");
		if (StrUtils.isNotNull(car_plate)) {
			dynamicSql += " AND car_plate LIKE ?";
			sqlMap.addParam("%"+car_plate+"%");
		}
		String merchant_id = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchant_id)) {
			dynamicSql += " AND merchant_id = ?";
			sqlMap.addParam(merchant_id);
		}
		String begin_time = inMap.getString("begin_time");
		if (StrUtils.isNotNull(begin_time)) {
			dynamicSql += " AND use_date >= ?";
			sqlMap.addParam(begin_time);
		}
		String end_time = inMap.getString("end_time");
		if (StrUtils.isNotNull(end_time)) {
			dynamicSql += " AND use_date <= ?";
			sqlMap.addParam(end_time);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		sqlMap.put("pageIndex", inMap.getInt("page_index", 1));
		sqlMap.put("pageSize", inMap.getInt("page_size", 10));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}

	public ParaMap updateCarRealtime(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("car", "updateCarRealtime");
		String dynamicSql = "";
		String status = inMap.getString("status");
		if (StrUtils.isNotNull(status)) {
			dynamicSql += ", status = ?";
			sqlMap.addParam(status);
		}
		String capacity = inMap.getString("capacity");
		if (StrUtils.isNotNull(capacity)) {
			dynamicSql += ", capacity = ?";
			sqlMap.addParam(capacity);
		}
		String soc = inMap.getString("soc");
		if (StrUtils.isNotNull(soc)) {
			dynamicSql += ", soc = ?";
			sqlMap.addParam(soc);
		}
		String use_date = inMap.getString("use_date");
		if (StrUtils.isNotNull(use_date)) {
			dynamicSql += ", use_date = ?";
			sqlMap.addParam(use_date);
		}
		String branch = inMap.getString("branch");
		if (StrUtils.isNotNull(branch)) {
			dynamicSql += ", branch = ?";
			sqlMap.addParam(branch);
		}
		dynamicSql += ", lasttime = ?";
		sqlMap.addParam(DateUtil.nowTime());
		sqlMap.addParam(inMap.getString("car_plate"));
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql.substring(1));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 查询车辆使用状态实时数据
	 * 
	 * @author 吴财宾
	 * @date 20170509
	 * @param inMap merchant_id-商户id
	 * @return
	 */
	public ParaMap getCarRealtime(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("car", "getCarRealtime");
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
	 * 获取车辆总数
	 * 
	 * @author 吴财宾
	 * @date 20170511
	 * @param inMap merchant_id 厂商ID
	 * @return
	 * 			
	 */
	public ParaMap getCarCount(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("car", "getCarCount");
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
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("car", "getCarPosition");
		String dynamicSql = "";
		String merchant_id = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchant_id)) {
			dynamicSql += " AND c.merchant_id = ?";
			sqlMap.addParam(merchant_id);
		}
		String status = inMap.getString("status");
		if (StrUtils.isNotNull(status)) {
			dynamicSql += " AND c.status = ?";
			sqlMap.addParam(status);
		}
		String keyword = inMap.getString("keyword");
		if (StrUtils.isNotNull(keyword)) {
			dynamicSql += " AND ( b.address LIKE ? OR c.brand LIKE ? OR c.car_no LIKE ? OR c.car_plate LIKE ?) ";
			sqlMap.addParam("%" + keyword + "%");
			sqlMap.addParam("%" + keyword + "%");
			sqlMap.addParam("%" + keyword + "%");
			sqlMap.addParam("%" + keyword + "%");
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}
	
}
