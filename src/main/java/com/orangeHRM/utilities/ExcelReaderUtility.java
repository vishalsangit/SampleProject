package com.orangeHRM.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtility {

	public static Object[][] getData_XLS(String path, String SheetName) throws IOException {
		System.out.println("WorkbookPath=============================" + path);
		FileInputStream fisObj = new FileInputStream(path);
		// System.out.println("fisObj=========================================="+fisObj);
		XSSFWorkbook WBookObj = new XSSFWorkbook(fisObj);
		XSSFSheet WSheetObj = WBookObj.getSheet(SheetName);
		int RowCount = WSheetObj.getLastRowNum();
		System.out.println("rowcount==============================================" + RowCount);
		int ColmCount = WSheetObj.getRow(1).getLastCellNum();
		System.out.println("ColmCount=====================================" + ColmCount);
		Object CellVal = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
		String[][] ArrObj = new String[RowCount][ColmCount];

		for (int i = 0; i <= RowCount - 1; i++) {
			XSSFRow RowObject = WSheetObj.getRow(i + 1);
			int CellCount = RowObject.getLastCellNum();
			for (int j = 0; j <= CellCount - 1; j++) {

				int CellType = RowObject.getCell(j).getCellType();

				if (CellType == Cell.CELL_TYPE_STRING) {
					CellVal = RowObject.getCell(j, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
				} else if (CellType == Cell.CELL_TYPE_NUMERIC) {
					if (DateUtil.isCellDateFormatted(RowObject.getCell(j))) {
						CellVal = sdf.format(RowObject.getCell(j, Row.CREATE_NULL_AS_BLANK).getDateCellValue());
					} else {
						Double RowCellvalue = RowObject.getCell(j, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
						Integer CellVal1 = BigDecimal.valueOf(RowCellvalue).intValue();
						CellVal = CellVal1.toString();
					}
					// CellVal = BigDecimal.valueOf(RowObject.getCell(j,
					// Row.CREATE_NULL_AS_BLANK).getNumericCellValue()).toPlainString();

				} else if (CellType == Cell.CELL_TYPE_BLANK) {
					CellVal = "".toString();
				}
				System.out.println("CellVal=================================" + CellVal);
				ArrObj[i][j] = (String) CellVal;
			}

		}
		return ArrObj;
	}
}
