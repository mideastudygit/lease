package com.open.control;

import com.base.utils.DateUtils;
import com.base.utils.ParaMap;
import com.common.util.HttpUtil;
import com.common.util.MD5SignUtil;

import junit.framework.TestCase;

public class BoxControlTest extends TestCase {

	public void testUpdateBox() throws Exception {
		ParaMap inMap = new ParaMap();
		inMap.put("action", "box.updateBox");
		inMap.put("app_id", "bonicai");
		inMap.put("timestamp", DateUtils.nowTime());
		inMap.put("sign_type", "MD5");
		inMap.put("box_no", "5004");
		String sign = MD5SignUtil.sign(inMap, "#451554$*qsdgrdfc");
		inMap.put("sign", sign);
		String response = HttpUtil.getData(OpenConsts.URL, inMap);
		System.out.println(response);
	}
}
