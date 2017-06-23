package com.center.task;

import java.util.List;

import com.base.log.Logging;
import com.base.mq.MQSendUtils;
import com.base.task.MyTimerTask;
import com.base.utils.ParaMap;
import com.center.consts.MqConsts;
import com.center.service.MerchantService;
import com.common.consts.RespConsts.RespKey;
import com.common.consts.SqlConsts;
import com.common.util.DateUtil;

/**
 * 系统对账服务
 * 
 * @author 唐宗鸿
 * @date 20170523
 * @version 1.1.0
 */
public class SystemBillTask extends MyTimerTask {

	private static final Logging log = Logging.getLogging("task");
	private MerchantService merchantService = new MerchantService();

	public SystemBillTask(String s) {
		super(s);
	}

	@Override
	public void execute() throws Exception {
		log.info("SystemBillTask，开始：");
		ParaMap sendMap = new ParaMap();
		sendMap.put("bill_date", DateUtil.format(DateUtil.prevDay(1), DateUtil.TO_DAY));
		sendMap.put("action", "road.bill.addSystemBill");
		ParaMap paraMap = new ParaMap();
		paraMap.put("is_page", SqlConsts.NOT_PAGE);
		// 查询所有的商户信息
		ParaMap resultMap = merchantService.getMerchantList(paraMap);
		List<ParaMap> merchantList = (List<ParaMap>) resultMap.get(RespKey.DATA.getValue());
		for (ParaMap merchantMap : merchantList) {
			sendMap.put("merchant_id", merchantMap.getString("merchant_id"));
			MQSendUtils.send(MqConsts.SYSTEM_BILL, sendMap);
		}
		log.info("SystemBillTask，结束：");
	}
}
