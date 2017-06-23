package com.center.base;

import java.util.Iterator;
import java.util.Set;

import com.base.log.Logging;
import com.base.mq.MQReceUtils;
import com.base.task.TaskManager;
import com.base.web.AppConfig;
import com.base.web.AppInit;
import com.base.web.filter.DirectFilter;
import com.center.mq.MerchantBillRece;
import com.center.mq.RoadOrderPushRece;
import com.center.mq.SystemBillRece;
import com.center.task.MerchantBillTask;
import com.center.task.SystemBillTask;
import com.common.conf.DirectConfig;

/**
 * 服务启动处理类
 */
public class ApplicationMain extends AppInit {

	private Logging log = Logging.getLogging("application");

	public void saveMQException(long paramLong, String paramString1, String paramString2, String paramString3)
			throws Exception {
	}

	public void mqInit() {
		MQReceUtils.addRece(new RoadOrderPushRece()); // 路外订单结算推送
		MQReceUtils.addRece(new SystemBillRece()); // 系统对账
		MQReceUtils.addRece(new MerchantBillRece()); // 商户对账
	}

	public void directInit() {
		Set<Object> keySet = DirectConfig.getAllKeys();
		Iterator<Object> it = keySet.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String redirect = DirectConfig.get(key);
			DirectFilter.msmList.add(redirect);
			log.debug("Direct: " + redirect + " init.");
		}
	}

	public void init() {
		try {
			directInit();

			if (AppConfig.getBooleanPro("timer.task.enable")) {
				taskInit();
			}

			if (AppConfig.getBooleanPro("mqReceEnable")) {
				mqInit();
				MQReceUtils.startRece();
			}
			log.debug("********** 服务初始化完成. **********");
		} catch (Exception e) {
			log.error("********** 服务初始化出错：" + e + " **********");
		}
	}

	private void taskInit() {
		try {
	//		TaskManager.add(new SystemBillTask("SystemBill"), 5000, 3600000);
	//		TaskManager.add(new MerchantBillTask("MerchantBill"), 5000, 3600000);
			log.debug("**********定时器初始化完成**********");
		} catch (Exception e) {
			log.error("定时任务初始化出错：" + e);
		}
	}
}
