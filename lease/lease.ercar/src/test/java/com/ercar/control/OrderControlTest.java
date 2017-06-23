package com.ercar.control;

import com.base.ds.DataSourceManager;
import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;

import junit.framework.TestCase;

public class OrderControlTest extends TestCase{
	
	ErcarOrderControl con = new ErcarOrderControl();
	
	public void testAddOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("mer_order_id",IDGenerator.newGUID());    
		inMap.put("car_plate","粤B1002");   
		inMap.put("duration","4");    
		inMap.put("amount","62");      
		inMap.put("merchant_id","20170513181530248919062988563556"); 
		inMap.put("status","2");      
		inMap.put("take_branch","宝安区"); 
		inMap.put("park_branch","福田区"); 
		inMap.put("take_berth","123005");  
		inMap.put("park_berth","123006");  
		inMap.put("begin_time","1492654200000");  
		inMap.put("end_time","1492654200000");    
		inMap.put("longitude","22.5639273303");    
		inMap.put("latitude","113.8686561584");    
		ParaMap order = con.addOrder(inMap);
		DataSourceManager.commit();
		System.out.println(order);
	}
	
	public void testGetCarUseRate() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_id","201705131815302489190629885635561"); 
		ParaMap order = con.getCarUseRate(inMap);
		System.out.println(order);
	}
	
	public void testGetOrderList() throws Exception {
		ParaMap inMap = new ParaMap();
//		inMap.put("status", 2);
//		inMap.put("car_keyword", "002");
		ParaMap order = con.getOrderList(inMap);
		System.out.println(order);
	}
	

}
