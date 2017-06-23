package com.ercar.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import com.base.ds.DataSourceManager;
import com.base.utils.IDGenerator;
import com.base.utils.ParaMap;
import com.ercar.util.DateTimeUtil;

public class OrderServiceTest extends TestCase{

	OrderService service = new OrderService();
	
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
		ParaMap order = service.addOrder(inMap);
		DataSourceManager.commit();
		System.out.println(order);
	}
	
	public void testGetTodayCount() throws Exception {
		ParaMap inMap = new ParaMap();
//		inMap.put("merchant_id","2017102"); 
		ParaMap order = service.getTodayCount(inMap);
		System.out.println(order);
	}
	
	public void testUpdateOrderRealtime() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("mer_order_id","20170516155624936582712051783244");    
		inMap.put("duration","1.51");    
		inMap.put("amount","18.11");      
		inMap.put("status","1");      
		ParaMap order = service.updateOrderRealtime(inMap);
		DataSourceManager.commit();
		System.out.println(order);
	}
	
	public void testUpdateOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("mer_order_id","201705161556249365827120517832440");    
		inMap.put("duration","1.5");    
		inMap.put("amount","18.1");      
		inMap.put("status","2");      
		inMap.put("park_branch",""); 
		inMap.put("park_berth","");  
		inMap.put("end_time","");  
		ParaMap order = service.updateOrder(inMap);
		DataSourceManager.commit();
		System.out.println(order);
	}
	
	public void testGetOrder() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("order_id","20170510165537871433876721836977");    
		ParaMap order = service.getOrder(inMap);
		DataSourceManager.commit();
		System.out.println(order);
	}
	
	public void testGetOrderList() throws Exception {
		ParaMap inMap = new ParaMap();
//		inMap.put("status", 2);
		inMap.put("car_plate", "粤B1001");
		ParaMap order = service.getOrderList(inMap);
		System.out.println(order);
	}
	
	public void test1() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(new Date(DateTimeUtil.getFirstday())));
        System.out.println(format.format(new Date(DateTimeUtil.getLastday())));
        System.out.println(format.format(new Date(DateTimeUtil.getTimesmorning())));
        System.out.println(format.format(new Date(DateTimeUtil.getTimesnight())));
        
	}
	
	public void testGetMonthCount() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_id","2017101"); 
		ParaMap order = service.getMonthCount(inMap);
		System.out.println(order);
	}
	
	public void testGetCarUseRate() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_id","201705131815302489190629885635560"); 
		ParaMap order = service.getCarUseRate(inMap);
		System.out.println(order);
	}
	
	public void testGetOrderAnalysis() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_id","20170513181530248919062988563556"); 
		inMap.put("month","1492647000000"); 
		ParaMap order = service.getOrderAnalysis(inMap); 
		System.out.println(order); 
		
	}
	
	public void testGetRoad() throws Exception {
		ParaMap inMap = new ParaMap();
//		inMap.put("merchant_id","2017101"); 
		inMap.put("month","1494491782000"); 
		ParaMap order = service.getRoad(inMap); 
		System.out.println(order); 
		
	}
	
	
}
