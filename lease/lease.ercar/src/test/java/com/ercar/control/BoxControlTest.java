package com.ercar.control;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;
import com.common.util.DateUtil;

import junit.framework.TestCase;

public class BoxControlTest extends TestCase{

	BoxControl con = new BoxControl();
	
	public void testAddBox() throws Exception {
		ParaMap inMap = new ParaMap();
		int i = 6;
		inMap.put("box_no", "500" + i);
		inMap.put("car_plate", "粤B100" + i);
		inMap.put("use_date",DateUtil.nowTime());
		ParaMap resultMap = con.addBox(inMap);
		DataSourceManager.commit();
		System.out.println(resultMap.toString());
	}
	
	public void testUpdateBox() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("box_id", "20170516102020784434665680555892");
		inMap.put("box_no", "5001");
		inMap.put("car_plate", "粤B1001");
		inMap.put("use_date", "1491783000000");
		ParaMap resultMap = con.updateBox(inMap);
		DataSourceManager.commit();
		System.out.println(resultMap);
	}
}
