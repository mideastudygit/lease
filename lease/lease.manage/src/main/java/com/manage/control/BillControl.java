package com.manage.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.RespConsts.RespState;
import com.common.route.RouteManager;
import com.common.util.DateUtil;
import com.common.util.RespUtil;
import com.manage.service.ExcelService;
import com.manage.service.HttpService;

public class BillControl extends HttpService {

	public byte[] exportBill(ParaMap inMap) throws Exception {
		ParaMap billMap = (ParaMap) RouteManager.route("bill.getBill", inMap).get(RespKey.DATA.getValue());
		billMap.put(
				"period",
				DateUtil.format(billMap.getLong("start_time"), DateUtil.TO_DAY_LINE) + "至"
						+ DateUtil.format(billMap.getLong("end_time"), DateUtil.TO_DAY_LINE));
		String startTime = DateUtil.format(DateUtil.getStartTimeOfDay(billMap.getLong("start_time")),
				DateUtil.TO_SECOND_LINE);
		String endTime = DateUtil
				.format(DateUtil.getEndTimeOfDay(billMap.getLong("end_time")), DateUtil.TO_SECOND_LINE);
		billMap.put("period_detail", startTime + "至" + endTime);

		List<ParaMap> billDataList = (List<ParaMap>) RouteManager.route("bill.getBillDataList", inMap).get(
				RespKey.DATA.getValue());
		if (billDataList == null) {
			billDataList = new ArrayList<ParaMap>();
		}
		ParaMap billDataMap = new ParaMap();
		billDataMap.put("bill_list", billDataList);
		billDataMap.put("export_date", DateUtil.format(new Date(), DateUtil.TO_SECOND_LINE));
		billDataMap.put("start_time", startTime);
		billDataMap.put("end_time", endTime);
		billDataMap.put("total", billDataList.size());

		ExcelService service = new ExcelService();
		byte[] response = service.exportBill(billMap, billDataMap, getRequest(), getResponse());
		return response;
	}

	public ParaMap repeatBill(ParaMap inMap) throws Exception {
		if (StrUtils.isNull(inMap.getString("bill_id"))) {
			return RespUtil.setResp(RespState.FAIL, "param.missing");
		}
		ParaMap resultMap = RouteManager.route(inMap.getString("action"), inMap);
		return resultMap;
	}
}
