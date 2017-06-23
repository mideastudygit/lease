package com.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.base.utils.ParaMap;

public class ExcelUtil {

	/**
	 * 获取导出的模板文件
	 * 
	 * @author tzh
	 *
	 */
	public static Workbook getTemplateFile(String templateFileName) throws IOException {
		InputStream in = ExcelUtil.class.getResourceAsStream(templateFileName);
		Workbook workbook = new XSSFWorkbook(in);
		return workbook;
	}

	/**
	 * 填充表单模板数据
	 * 
	 * @author tzh
	 *
	 */
	public static void fillFormData(Sheet sheet, ParaMap dataMap) {
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			fillRowData(sheet, i, dataMap);
		}
	}

	public static void fillRowData(Row row, ParaMap dataMap, Row titleRow) {
		if (row == null) {
			return;
		}
		int firstCellNum = row.getFirstCellNum();
		int lastCellNum = row.getLastCellNum();
		for (int j = firstCellNum; j <= lastCellNum; j++) {
			Cell cell = row.getCell(j);
			if (cell == null) {
				continue;
			}
			fillCellData(cell, dataMap, titleRow.getCell(j));
		}
	}

	/**
	 * 填充行数据
	 * 
	 * @author tzh
	 *
	 */
	public static void fillRowData(Sheet sheet, int rowIndex, ParaMap dataMap) {
		Row row = sheet.getRow(rowIndex);
		if (row == null) {
			return;
		}
		int firstCellNum = row.getFirstCellNum();
		int lastCellNum = row.getLastCellNum();
		for (int j = firstCellNum; j <= lastCellNum; j++) {
			Cell cell = row.getCell(j);
			if (cell == null) {
				continue;
			}
			fillCellData(cell, dataMap);
		}
	}

	/**
	 * 默认的值填充行数据
	 * 
	 * @author tzh
	 *
	 */
	public static void fillRowData(Row row, String defaultValue) {
		if (row == null) {
			return;
		}
		int firstCellNum = row.getFirstCellNum();
		int lastCellNum = row.getLastCellNum();
		for (int j = firstCellNum; j <= lastCellNum; j++) {
			Cell cell = row.getCell(j);
			if (cell == null) {
				continue;
			}
			fillCellData(cell, defaultValue);
		}
	}

	public static void fillCellData(Cell cell, ParaMap dataMap, Cell oldCell) {
		String cellValue = oldCell.getStringCellValue();
		if (cellValue == null || "".equals(cellValue)) {
			return;
		}
		if (cellValue.startsWith("#")) {
			String key = cellValue.substring(1);
			fillCellByDataType(cell, key, null, dataMap);
		} else if (cellValue.contains("#")) {
			int index = cellValue.indexOf("#");
			String key = cellValue.substring(index + 1);
			fillCellByDataType(cell, key, cellValue.substring(0, index), dataMap);
		}
	}

	/**
	 * 填充单元格数据
	 * 
	 * @author tzh
	 *
	 */
	public static void fillCellData(Cell cell, ParaMap dataMap) {
		String cellValue = cell.getStringCellValue();
		if (cellValue == null || "".equals(cellValue)) {
			return;
		}
		if (cellValue.startsWith("#")) {
			String key = cellValue.substring(1);
			fillCellByDataType(cell, key, null, dataMap);
		} else if (cellValue.contains("#")) {
			int index = cellValue.indexOf("#");
			String key = cellValue.substring(index + 1);
			fillCellByDataType(cell, key, cellValue.substring(0, index), dataMap);
		}
	}

	/**
	 * 根据指定的行列，填充单元格数据
	 * 
	 * @author tzh
	 *
	 */
	public static void fillCellData(Sheet sheet, int rowIndex, int cellIndex, ParaMap dataMap) {
		Row row = sheet.getRow(rowIndex);
		Cell cell = row.getCell(cellIndex);
		if (cell == null) {
			return;
		}
		String cellValue = cell.getStringCellValue();
		if (cellValue == null || "".equals(cellValue)) {
			return;
		}
		if (cellValue.startsWith("#")) {
			String key = cellValue.substring(1);
			if (dataMap.containsKey(key)) {
				cell.setCellValue(dataMap.get(key).toString());
			} else {
				cell.setCellValue("");
			}
		}
	}

	/**
	 * 默认值填充单元格数据
	 * 
	 * @author tzh
	 *
	 */
	public static void fillCellData(Cell cell, String defaultValue) {
		String cellValue = cell.getStringCellValue();
		if (cellValue == null || "".equals(cellValue)) {
			return;
		}
		if (cellValue.startsWith("#")) {
			if (defaultValue == null || "".equals(defaultValue)) {
				cell.setCellValue("");
			} else {
				cell.setCellValue(defaultValue);
			}
		} else if (cellValue.contains("#")) {
			int index = cellValue.indexOf("#");
			String prefixValue = cellValue.substring(0, index);
			if (defaultValue == null || "".equals(defaultValue)) {
				cell.setCellValue(prefixValue);
			} else {
				cell.setCellValue(prefixValue + defaultValue);
			}
		}
	}

	/**
	 * 从指定的行号开始，到指定的列号，动态创建数据行
	 * 
	 * @author tzh
	 *
	 */
	public static void fillRowList(Sheet sheet, int rowIndex, int cellIndex, List<ParaMap> dataList) {
		Row row = sheet.getRow(rowIndex);
		if (dataList == null || dataList.size() < 1) {
			for (int i = 0; i < cellIndex; i++) {
				row.getCell(i).setCellValue("");
			}
		} else {
			for (int i = 1, len = dataList.size(); i < len; i++) {
				ParaMap tempMap = (ParaMap) dataList.get(i);
				rowIndex++;
				Row newRow = sheet.createRow(rowIndex);
				for (int j = 0; j < cellIndex; j++) {
					Cell cell = row.getCell(j);
					if (cell == null) {
						continue;
					}
					String cellValue = cell.getStringCellValue();
					if (cellValue == null || "".equals(cellValue)) {
						continue;
					}
					if (cellValue.startsWith("#")) {
						String key = cellValue.substring(1);
						if (tempMap.containsKey(key)) {
							Object value = tempMap.get(key);
							if (value == null) {
								newRow.createCell(j).setCellValue("");
							} else {
								newRow.createCell(j).setCellValue(value.toString());
							}
						} else {
							newRow.createCell(j).setCellValue("");
						}
					}
				}
			}
			ParaMap tempMap = (ParaMap) dataList.get(0);
			for (int i = 0; i < cellIndex; i++) {
				String cellValue = row.getCell(i).getStringCellValue();
				if (cellValue == null || "".equals(cellValue)) {
					continue;
				}
				if (cellValue.startsWith("#")) {
					String key = cellValue.substring(1);
					if (tempMap.containsKey(key)) {
						Object value = tempMap.get(key);
						if (value == null) {
							row.getCell(i).setCellValue("");
						} else {
							row.getCell(i).setCellValue(value.toString());
						}
					} else {
						row.getCell(i).setCellValue("");
					}
				}
			}
		}
	}

	/**
	 * 填充表格中的模块信息
	 * 
	 * @author tzh
	 *
	 */
	public static int fillModuleData(Sheet sheet, int rowIndex, int rowNum, ParaMap dataMap) {
		for (int i = 0; i < rowNum; i++) {
			fillRowData(sheet, rowIndex, dataMap);
			rowIndex++;
		}
		return rowIndex;
	}

	/**
	 * 填充表格中动态模块信息
	 * 
	 * @author tzh
	 *
	 */
	public static int fillModuleList(Sheet sheet, int rowIndex, ParaMap dataMap, String defaultValue) {
		fillRowData(sheet, rowIndex, dataMap);
		rowIndex = rowIndex + 2;
		List<ParaMap> dataList = (List<ParaMap>) dataMap.get("data");
		if (dataList == null || dataList.size() <= 0) {
			for (int i = 0; i < 2; i++) {
				if (defaultValue == null)
					defaultValue = "-";
				fillRowData(sheet.getRow(rowIndex), defaultValue);
				rowIndex++;
			}
		} else {
			int firstRowIndex = rowIndex;
			for (int i = 1, len = dataList.size(); i < len; i++) {
				rowIndex++;
				Row newRow = createRow(sheet, rowIndex, firstRowIndex);
				fillRowData(newRow, dataList.get(i), sheet.getRow(firstRowIndex));
			}
			fillRowData(sheet, firstRowIndex, dataList.get(0));
			rowIndex++;
			fillRowData(sheet, rowIndex, (ParaMap) dataMap.get("summary"));
			rowIndex = rowIndex + 1;
		}
		return rowIndex;
	}

	/**
	 * 指定的行号创建行，并整体后移
	 * 
	 * @author tzh
	 *
	 */
	public static Row createRow(Sheet sheet, int rowIndex, int oldRowIndex) {
		sheet.shiftRows(rowIndex, sheet.getLastRowNum(), 1, true, true);
		Row row = sheet.createRow(rowIndex);
		Row oldRow = sheet.getRow(oldRowIndex);
		for (int i = 0, len = oldRow.getLastCellNum(); i < len; i++) {
			Cell cell = row.createCell(i);
			Cell oldCell = oldRow.getCell(i);
			cell.setCellStyle(oldCell.getCellStyle());
		}
		return row;
	}

	public static void fillCellByDataType(Cell cell, String key, String prefix, ParaMap dataMap) {
		String[] dataKey = key.split("-");
		if (dataMap.containsKey(dataKey[0])) {
			if (dataKey.length < 2) {
				if (prefix == null)
					cell.setCellValue(dataMap.getString(dataKey[0]));
				else
					cell.setCellValue(prefix + dataMap.getString(dataKey[0]));
			} else {
				String type = dataKey[1];
				switch (type) {
				case "s":
					cell.setCellValue(dataMap.getString(dataKey[0]));
					break;
				case "n":
					String data = dataMap.getString(dataKey[0]);
					cell.setCellValue(Double.parseDouble(data));
					break;
				case "b":
					String bdata = dataMap.getString(dataKey[0]);
					cell.setCellValue(Boolean.parseBoolean(bdata));
					break;
				default:
					cell.setCellValue(dataMap.getString(dataKey[0]));
					break;
				}
			}
		} else {
			if (prefix == null)
				cell.setCellValue("");
			else
				cell.setCellValue(prefix);
		}
	}

	public static void createCellData(Sheet sheet, int rowIndex, int cellIndex, String value) {
		Row row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}
		Cell cell = row.getCell(cellIndex);
		if (cell == null) {
			cell = row.createCell(cellIndex);
			cell.setCellValue(value);
		}
	}
}
