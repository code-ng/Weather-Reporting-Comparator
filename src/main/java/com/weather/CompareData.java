package com.weather;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import com.utilitypackage.ExcelOperation;


/**
 * Class to perform temperature value comparison
 * 
 * @author Nirupam
 *
 */
public class CompareData {

	private static Logger log = LogManager.getLogger(CompareData.class.getName());

	ExcelOperation excel = new ExcelOperation();
	/**
	 * weatherDataCompare()- perform the temperature comparison
	 * @throws Exception
	 */
	public void weatherDataCompare() throws Exception {
			
		log.info("Inside weatherDataCompare() method =============== ");

		String ndtvTempValue = excel.getCellValue("NDTV");
		log.info("ndtvTempValue :: "+ndtvTempValue);
		float f = Float.parseFloat(ndtvTempValue); 
		
		String apiTempValue = excel.getCellValue("WeatherAPI");
		float f1 = Float.parseFloat(apiTempValue);
		
		Compareresult compareV = new Compareresult();

		int v1 = compareV.compare(new Weather(f), new Weather(f1));
		log.info(v1);

		boolean flag = false;
		
		if (v1 == 0 || v1 < 3 || v1 == 3) {
			flag = true;
			Assert.assertTrue(flag);
		} else {
			flag = false;		
			Assert.assertTrue(flag);
			throw new Exception("Difference in value is more than 2");

		}

	}

	
	
	
	
}
