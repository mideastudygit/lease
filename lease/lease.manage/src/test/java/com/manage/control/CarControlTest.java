package com.manage.control;

import junit.framework.TestCase;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;

public class CarControlTest extends TestCase {

	public void testGetCarList() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "car.getCarList");
		inMap.put("car_plate", "V1粤15452");
		inMap.put("merchant_id", "bonikai");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testAddCar() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "car.addCar");
		inMap.put("car_plate", "V1粤15452");
		inMap.put("brand", "知豆");
		inMap.put("merchant_name", "bonikai");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetCar() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "car.getCar");
		inMap.put("car_id", "20170518145739921441504516669327");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testUpdateCar() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "car.updateCar");
		inMap.put("car_id", "20170518145739921441504516669327");
		inMap.put("car_plate", "V1粤15452");
		inMap.put("brand", "知豆");
		inMap.put("mer_car_id", "145254112540");
		inMap.put("merchant_name", "bonikai41");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetCarPosition() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "car.getCarPosition");
		inMap.put("merchant_id", "20170518145739921441504516669327");
		inMap.put("status", 1);
		inMap.put("keyword", "知豆");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}

	public void testGetCarUseStatistics() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "car.getCarUseStatistics");
		inMap.put("merchant_id", "20170518145739921441504516669327");
		String response = HttpUtil.getData(ManageConsts.URL, inMap);
		System.out.println(response);
	}
}
