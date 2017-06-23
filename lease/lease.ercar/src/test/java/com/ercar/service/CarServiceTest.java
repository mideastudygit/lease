package com.ercar.service;

import junit.framework.TestCase;

import com.base.ds.DataSourceManager;
import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;

public class CarServiceTest extends TestCase{

	CarService service = new CarService();
	
	public void testAddCar() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("mer_car_id", IDGenerator.newGUID());
		int i = 4;
		inMap.put("car_plate", "粤B100"+i);
		inMap.put("brand", "富士康"+i);
		inMap.put("merchant_id", "20170513181530248919062988563556");
		inMap.put("use_date", "1494900601000");
		inMap.put("branch", "财富港");
		ParaMap paraMap = service.addCar(inMap);
		DataSourceManager.commit();
		System.out.println(paraMap.toString());
	}

	public void testGetCar() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("car_id", "20170513181530592696230258284001");
//		inMap.put("car_plate", "粤B1001");
		ParaMap map = service.getCar(inMap);
		System.out.println(map.toString());
	}
	
	public void testUpdateCar() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("mer_car_id", IDGenerator.newGUID());
		inMap.put("car_plate", "粤B1004");
		inMap.put("brand", "富士康4");
		inMap.put("merchant_id", "20170513181530248919062988563556");
		inMap.put("car_id", "20170516111630169775951876008699");
		inMap.put("use_date", "1494905245063");
		inMap.put("branch", "财富港4");
		ParaMap paraMap = service.updateCar(inMap);
		DataSourceManager.commit();
		System.out.println(paraMap.toString());
	}
	
	public void testGetCarList() throws Exception{
		ParaMap inMap = new ParaMap();
//		inMap.put("car_plate", "粤B1003");
//		inMap.put("merchant_id", "20170513181530248919062988563556");
//		inMap.put("begin_time", "1489507200000");
//		inMap.put("end_time", "1496246400000");
//		inMap.put("page_index", "1");
//		inMap.put("page_size", "20");
		ParaMap paraMap = service.getCarList(inMap);
		System.out.println(paraMap.toString());
	}
	
	public void testUpdateCarRealtime() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("car_plate","粤B1004");
		inMap.put("status","3");   
		inMap.put("capacity","41"); 
		inMap.put("soc","41");      
		ParaMap paraMap = service.updateCarRealtime(inMap);
		DataSourceManager.commit();
		System.out.println(paraMap.toString());
	}
	
	public void testGetCarRealtime() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_id", "20170513181530248919062988563556");
		ParaMap map = service.getCarRealtime(inMap);
		System.out.println(map.toString());
	}
	
	public void testGetCarPosition() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_id", "20170513181530248919062988563556");
		inMap.put("keyword", "比亚迪");
		ParaMap map = service.getCarPosition(inMap);
		System.out.println(map.toString());
	}
	
}
