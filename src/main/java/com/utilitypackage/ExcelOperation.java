/*
 * Copyright 2020 Nirupam Ghosh (niupamghosh.blr@gmail.com)
 * SPDX-License-Identifier: MIT
 */


package com.utilitypackage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.base.BaseClass;

/**
 * Generic Excel class to write fetch values
 * 
 * @author Nirupam
 *
 */
public class ExcelOperation {

	private static Logger log = LogManager.getLogger(ExcelOperation.class.getName());
	String systemPath = System.getProperty("user.dir");
	String filePath = systemPath + "\\testData\\data.xlsx";

	/**
	 * writeExcel() - to write in excel sheet the temperature values
	 * 
	 * @param cityname
	 * @param tempmin
	 * @param sheetName
	 * @throws IOException
	 */
	public void writeExcel(String cityname, String tempmin, String sheetName) throws IOException {
		log.info("Inside writeExcel() method =============== ");
		FileInputStream fis = null;
		fis = new FileInputStream(filePath);
		Workbook wb;
		wb = new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		Row row;
		int rownum;
		// create a new row to enter value
		row = sheet.createRow(rowCount + 1);
		// enter cityname in excel
		row.createCell(0).setCellValue(cityname);
		// enter temperature in excel
		row.createCell(1).setCellValue(tempmin);
		fis.close();
		FileOutputStream fos = new FileOutputStream(filePath);
		wb.write(fos);
		fos.close();
		log.info("Exit from writeExcel() method =============== ");
	}

	/**
	 * getCellValue() - take the sheet name and return value
	 * 
	 * @param sheetName
	 * @return
	 * @throws IOException
	 */
	public String getCellValue(String sheetName) throws IOException {
		log.info("Inside getCellValue() method =============== ");
		FileInputStream fis = new FileInputStream(filePath);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheet(sheetName);
		int lastrow = sheet.getLastRowNum();
		Row row;
		row = sheet.getRow(lastrow);
		String tempCellValue = row.getCell(1).getStringCellValue();
		log.info(" Retrive cell value :: " + tempCellValue);

		return tempCellValue;

	}
}
