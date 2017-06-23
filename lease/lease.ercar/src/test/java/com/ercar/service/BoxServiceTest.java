package com.ercar.service;

import junit.framework.TestCase;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;
import com.common.util.DateUtil;
import com.ercar.consts.DataDict.BoxStatus;

public class BoxServiceTest extends TestCase {

	BoxService service = new BoxService();
	
	public void testAddBox() throws Exception {
		ParaMap inMap = new ParaMap();
		int i = 4;
		inMap.put("box_no", "500" + i);
		inMap.put("car_plate", "粤B100" + i);
		inMap.put("use_date",DateUtil.nowTime());
		ParaMap resultMap = service.addBox(inMap);
		DataSourceManager.commit();
		System.out.println(resultMap.toString());
	}
	
	public void testGetBox() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("box_id", "20170516102020784434665680555892");
//		inMap.put("box_no", "5001");
		ParaMap resultMap = service.getBox(inMap);
		System.out.println(resultMap.toString());
	}
	
	public void testUpdateBox() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("box_id", "20170516102020784434665680555892");
		inMap.put("box_no", "5001");
		inMap.put("car_plate", "粤B1001");
		inMap.put("use_date", "1491783000000");
		ParaMap resultMap = service.updateBox(inMap);
		DataSourceManager.commit();
		System.out.println(resultMap);
	}
	
	public void testGetBoxList() throws Exception {
		ParaMap inMap = new ParaMap();
//		inMap.put("box_no", "5001");
		inMap.put("merchant_id", "20170513181530248919062988563556");
		inMap.put("begin_date","1490976000000");     
		inMap.put("end_date","1496246400000");
		inMap.put("page_index","1");  
		inMap.put("page_size","20");    
		ParaMap resultMap = service.getBoxList(inMap);
		System.out.println(resultMap.toString());
	}
	
	public void testUpdateBoxRealtime() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("box_no", "5006");
		inMap.put("network","1");  
		inMap.put("start","1");     
		inMap.put("longitude","22.5677317828");
		inMap.put("latitude","113.8663387299");  
		inMap.put("status","1");    
		inMap.put("address","宝安区宝源路");   
		inMap.put("dynamic_code","1116");   
		ParaMap resultMap = service.updateBoxRealtime(inMap);
		DataSourceManager.commit();
		System.out.println(resultMap);
	}
	public void test12() throws Exception {
		System.out.println(DateUtil.nowTime());
	}
	
	public void testGetBoxRealtime() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("merchant_id", "20170513181530248919062988563551");
		ParaMap resultMap = service.getBoxRealtime(inMap);
		System.out.println(resultMap);
	}
	
	public void testE() throws Exception {
		Integer string = BoxStatus.FAIL.getValue();
//		BoxStatus.NOMAL.getValue()
		System.out.println(string);
	}
}
