package com.manage.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.base.utils.ParaMap;
import com.common.util.ExcelUtil;
import com.common.util.HttpUtil;
import com.manage.consts.ManageConsts;

public class ExcelService {

	/**
	 * 导出对账单
	 */
	public byte[] exportBill(ParaMap billMap, ParaMap billDataMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Workbook workbook = ExcelUtil.getTemplateFile(ManageConsts.BILL_TEMPLATE);
		Sheet sheet = workbook.getSheetAt(0);
		int rowNum = sheet.getFirstRowNum();

		rowNum = ExcelUtil.fillModuleData(sheet, rowNum, 2, billMap);
		rowNum = ExcelUtil.fillModuleData(sheet, rowNum, 1, billMap);

		rowNum = ExcelUtil.fillModuleData(sheet, rowNum + 3, 1, billMap);
		rowNum = ExcelUtil.fillModuleData(sheet, rowNum + 3, 1, billMap);
		rowNum = ExcelUtil.fillModuleData(sheet, rowNum + 3, 1, billMap);
		rowNum = ExcelUtil.fillModuleData(sheet, rowNum + 2, 1, billMap);
		
		Sheet orderSheet = workbook.getSheetAt(1);
		ExcelUtil.fillCellData(orderSheet, 0, 1, billDataMap);
		ExcelUtil.fillCellData(orderSheet, 1, 1, billDataMap);
		ExcelUtil.fillCellData(orderSheet, 2, 1, billDataMap);
		ExcelUtil.fillCellData(orderSheet, 3, 1, billDataMap);
		ExcelUtil.fillRowList(orderSheet, 6, 16, (List<ParaMap>) billDataMap.get("bill_list"));

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		byte[] buf = out.toByteArray();
		out.close();
		workbook.close();
		String fileName = billMap.getString("merchant_name") + "停车消费对账单" + billMap.getString("period") + ".xlsx";
		HttpUtil.setFileResponse(fileName, null, request, response);
		return buf;
	}
}
