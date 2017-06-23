package com.ercar.dao;

import com.base.dao.BaseDataSetDao;
import com.base.dao.SQLMap;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.util.DateUtil;

/**
 * 车载盒子类
 * 
 * @author 吴财宾
 * @date 20170509
 * @version 1.1.0 
 *
 */
public class BoxDao extends BaseDataSetDao{

	/**
	 * 新增车载盒子
	 * 
	 * @author 吴财宾
	 * @date 20170509
	 * @param inMap
	 * 				box_no-设备编号,car_id-所属车辆id,merchant_id-商户id
	 * @return 
	 */
	public ParaMap addBox(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("box", "addBox");
		sqlMap.addParam(inMap.getString("box_id"));
		sqlMap.addParam(inMap.getString("box_no"));
		sqlMap.addParam(inMap.getString("car_id"));
		sqlMap.addParam(inMap.getString("use_date"));
		ParaMap outMap = insert(sqlMap);
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
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("box", "getBox");
		String dynamicSql = "";
		String box_id = inMap.getString("box_id");
		if (StrUtils.isNotNull(box_id)) {
			dynamicSql += " AND b.id = ?";
			sqlMap.addParam(box_id);
		}
		String box_no = inMap.getString("box_no");
		if (StrUtils.isNotNull(box_no)) {
			dynamicSql += " AND b.box_no = ?";
			sqlMap.addParam(box_no);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 修改盒子信息
	 * 
	 * @author 吴财宾
	 * @date 20170509
	 * @param inMap
	 * 				box_id-设备ID,box_no-设备编号,car_id-所属车辆id
	 * @return 
	 */
	public ParaMap updateBox(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("box", "updateBox");
		sqlMap.addParam(inMap.getString("box_no"));
		sqlMap.addParam(inMap.getString("car_id"));
		String dynamicSql = "";
		String use_date = inMap.getString("use_date");
		if (StrUtils.isNotNull(use_date)) {
			dynamicSql += " , use_date = ?";
			sqlMap.addParam(use_date);
		}
		sqlMap.addParam(inMap.getString("box_id"));
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = update(sqlMap);
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
	 * 			box_no-设备状态(1-正常，0-故障)，use_date-投放日期，network-联网状态（1-在线，0-离线），
	 * 			start-启用状态（1-启用中），car_id-所属车辆ID，car_plate-车牌号，longitude-经度，
	 * 			latitude-纬度，lasttime-最后更新时间，dynamic_code-当前动态码，merchant_id-厂商ID
	 * 			brand-车辆品牌，status-车辆状态(1-使用中，2-故障，3-空闲，4-其他)，car_no-车辆编号
	 */
	public ParaMap getBoxList(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("box", "getBoxList");
		String dynamicSql = "";
		String box_no = inMap.getString("box_no");
		if (StrUtils.isNotNull(box_no)) {
			dynamicSql += " AND b.box_no LIKE ?";
			sqlMap.addParam("%" + box_no + "%");
		}
		String merchant_id = inMap.getString("merchant_id");
		if (StrUtils.isNotNull(merchant_id)) {
			dynamicSql += " AND c.merchant_id = ?";
			sqlMap.addParam(merchant_id);
		}
		String begin_date = inMap.getString("begin_date");
		if (StrUtils.isNotNull(begin_date)) {
			dynamicSql += " AND b.use_date >= ?";
			sqlMap.addParam(begin_date);
		}
		String end_date = inMap.getString("end_date");
		if (StrUtils.isNotNull(end_date)) {
			dynamicSql += " AND b.use_date <= ?";
			sqlMap.addParam(end_date);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		sqlMap.put("pageIndex", inMap.getInt("page_index", 1));
		sqlMap.put("pageSize", inMap.getInt("page_size", 10));
		ParaMap outMap = queryForPage(sqlMap);
		return outMap;
	}

	/**
	 * 车载盒子实时信息更新
	 * 
	 * @author 吴财宾
	 * @date 20170510
	 * @param inMap
	 * 				box_no-设备编号,network-联网状态（1-在线，0-离线），start-启用状态（1-启用中），
	 * 				longitude-经度，lasttime-纬度，status-设备状态，address-地址,use_date-投放日期，时间戳
	 * @return 
	 */
	public ParaMap updateBoxRealtime(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("box", "updateBoxRealtime");
		String dynamicSql = "";
		String network = inMap.getString("network");
		if (StrUtils.isNotNull(network)) {
			dynamicSql += ", network = ?";
			sqlMap.addParam(network);
		}
		String start = inMap.getString("start");
		if (StrUtils.isNotNull(start)) {
			dynamicSql += ", start = ?";
			sqlMap.addParam(start);
		}
		String longitude = inMap.getString("longitude");
		if (StrUtils.isNotNull(longitude)) {
			dynamicSql += ", longitude = ?";
			sqlMap.addParam(longitude);
		}
		String latitude = inMap.getString("latitude");
		if (StrUtils.isNotNull(latitude)) {
			dynamicSql += ", latitude = ?";
			sqlMap.addParam(latitude);
		}
		String status = inMap.getString("status");
		if (StrUtils.isNotNull(status)) {
			dynamicSql += ", status = ?";
			sqlMap.addParam(status);
		}
		String address = inMap.getString("address");
		if (StrUtils.isNotNull(address)) {
			dynamicSql += ", address = ?";
			sqlMap.addParam(address);
		}
		String dynamic_code = inMap.getString("dynamic_code");
		if (StrUtils.isNotNull(dynamic_code)) {
			dynamicSql += ", dynamic_code = ?";
			sqlMap.addParam(dynamic_code);
		}
		String use_date = inMap.getString("use_date");
		if (StrUtils.isNotNull(use_date)) {
			dynamicSql += ", use_date = ?";
			sqlMap.addParam(use_date);
		}
		dynamicSql += ", lasttime = ?";
		sqlMap.addParam(DateUtil.nowTime());
		sqlMap.addParam(inMap.getString("box_no"));
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql.substring(1));
		ParaMap outMap = update(sqlMap);
		return outMap;
	}

	/**
	 * 获取盒子信息通过盒子编码
	 * 
	 * @author 吴财宾
	 * @date 20170512
	 * @param inMap
	 * @return
	 */
	public ParaMap getBoxByNo(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("box", "getBoxByNo");
		sqlMap.addParam(inMap.getString("box_no"));
		String dynamicSql = "";
		String box_id = inMap.getString("box_id");
		if (StrUtils.isNotNull(box_id)) {
			dynamicSql += " AND b.id != ?";
			sqlMap.addParam(box_id);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}

	/**
	 * 查询盒子联网状态
	 * 
	 * @author 吴财宾
	 * @date 20170512
	 * @param inMap
	 * @return
	 */
	public ParaMap getBoxNetwork(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("box", "getBoxNetwork");
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
	 * 查询盒子设备状态
	 * 
	 * @author 吴财宾
	 * @date 20170512
	 * @param inMap
	 * @return
	 */
	public ParaMap getBoxStatus(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("box", "getBoxStatus");
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
	 * 获取盒子信息通过车辆ID
	 * 
	 * @author 吴财宾
	 * @date 20170513
	 * @param inMap
	 * @return
	 */
	public ParaMap getBoxByCarId(ParaMap inMap) throws Exception {
		SQLMap sqlMap = new SQLMap();
		sqlMap.setSQL("box", "getBoxByCarId");
		sqlMap.addParam(inMap.getString("car_id"));
		String dynamicSql = "";
		String box_id = inMap.getString("box_id");
		if (StrUtils.isNotNull(box_id)) {
			dynamicSql += " AND b.id != ?";
			sqlMap.addParam(box_id);
		}
		sqlMap.setPlaceHolder("$dynamicsql", dynamicSql);
		ParaMap outMap = query(sqlMap);
		return outMap;
	}
}
