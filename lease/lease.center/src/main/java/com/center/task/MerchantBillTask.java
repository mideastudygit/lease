package com.center.task;

import java.util.Arrays;
import java.util.List;

import com.base.log.Logging;
import com.base.mq.MQSendUtils;
import com.base.task.MyTimerTask;
import com.base.utils.ParaMap;
import com.base.utils.StrUtils;
import com.center.consts.CenterConsts;
import com.center.consts.MqConsts;
import com.center.service.MerchantService;
import com.common.consts.SqlConsts;
import com.common.consts.RespConsts.RespKey;
import com.common.util.DateUtil;
import com.common.util.StringUtil;

/**
 * 商户对账服务
 * 
 * @author 唐宗鸿
 * @date 20170523
 * @version 1.1.0
 */
public class MerchantBillTask extends MyTimerTask {

	private static final Logging log = Logging.getLogging("task");
	private MerchantService merchantService = new MerchantService();

	public MerchantBillTask(String s) {
		super(s);
	}

	@Override
	public void execute() throws Exception {
		log.info("MerchantBillTask，开始：");
		ParaMap sendMap = new ParaMap();
		sendMap.put("bill_date", DateUtil.format(DateUtil.prevDay(1), DateUtil.TO_DAY));
		sendMap.put("action", "road.bill.addMerchantBill");
		ParaMap paraMap = new ParaMap();
		paraMap.put("is_page", SqlConsts.NOT_PAGE);

		// 查询所有的商户信息
		ParaMap resultMap = merchantService.getMerchantList(paraMap);
		List<ParaMap> merchantList = (List<ParaMap>) resultMap.get(RespKey.DATA.getValue());
		for (ParaMap merchantMap : merchantList) {
			Integer billType = merchantMap.getInteger("bill_type");
			String billCycle = merchantMap.getString("bill_cycle");
			String billStart = merchantMap.getString("bill_start");
			if (StrUtils.isNull(billCycle) || StrUtils.isNull(billStart)) {
				continue;
			}
			if (billType == CenterConsts.BILL_TYPE_DATE) {
				// 判断当前日期是否为对账周期内，因为定时对账针对的是前一天，因此，判断时，需要将当前月份日期往前推一天
				int dayOfMonth = DateUtil.get(DateUtil.DAY_OF_MONTH) - 1;
				String startDate = this.calculateStartDate(dayOfMonth, billCycle);
				if (StrUtils.isNull(startDate)) {
					continue;
				}
				sendMap.put("merchant_id", merchantMap.getString("merchant_id"));
				sendMap.put("start_date", startDate);
				sendMap.put("end_date", DateUtil.format(DateUtil.prevDay(1), DateUtil.TO_DAY));
				MQSendUtils.send(MqConsts.MERCHANT_BILL, sendMap);

			} else if (billType == CenterConsts.BILL_TYPE_CYCLE) {
				// 当日凌晨
				long today = DateUtil.getTime(DateUtil.now(DateUtil.TO_DAY), DateUtil.TO_DAY);
				long diff = today - Long.valueOf(billStart);
				// 当日与开始对账日期的时间间隔
				long period = Long.valueOf(billCycle) * DateUtil.DAY_TIME_MILLIS;// 对账周期
				if (diff % period == 0) {
					sendMap.put("merchant_id", merchantMap.getString("merchant_id"));
					// 对账从上一个周期开始
					sendMap.put("start_date", DateUtil.format(today - period, DateUtil.TO_DAY));
					sendMap.put("end_date", DateUtil.format(DateUtil.prevDay(1), DateUtil.TO_DAY));
					MQSendUtils.send(MqConsts.MERCHANT_BILL, sendMap);
				}
			}
		}
		log.info("MerchantBillTask，结束：");
	}

	/**
	 * 计算对账的开始日期
	 * 
	 * @param dayOfMonth
	 *            该月所在天数
	 * @param billCycle
	 *            商户的对账周期
	 * @return
	 */
	private String calculateStartDate(int dayOfMonth, String billCycle) {
		dayOfMonth = dayOfMonth == 0 ? 99 : dayOfMonth;// 用99标识来表示，每月最后一天
		String[] cycles = billCycle.split(",");
		Arrays.sort(cycles);
		String startDate = null;
		int index = StringUtil.indexOf(cycles, String.valueOf(dayOfMonth));
		if (index < 0) {
			return startDate;
		} else if (index == 0) {
			String temp = cycles[cycles.length - 1]; // 每月最后一个对账日
			if (temp.equals("99")) {
				if (cycles.length > 1) {
					startDate = DateUtil.now(DateUtil.TO_MONTH) + "01"; // 本月的第一天
				} else {
					startDate = DateUtil.prevMonth(DateUtil.TO_MONTH) + "01"; // 上月的第一天
				}
			} else {
				startDate = DateUtil.nextDay(DateUtil.prevMonth(DateUtil.TO_MONTH) + temp, DateUtil.TO_DAY); // 上月的最后一个对账日的下一天
			}
		} else if (index > 0) {
			String temp = cycles[index - 1]; // 前一个对账日
			startDate = DateUtil.nextDay(DateUtil.now(DateUtil.TO_MONTH) + temp, DateUtil.TO_DAY); // 本月的上一个对账日的下一天
		}
		return startDate;
	}
}
