/*
 * Copyright 2020 Nirupam Ghosh (niupamghosh.blr@gmail.com)
 * SPDX-License-Identifier: MIT
 */


package com.runtest;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.base.BaseClass;
import com.utilitypackage.ExcelOperation;
import com.weather.CompareData;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;



public class APITest{
	private static Logger log = LogManager.getLogger(APITest.class.getName());
	ExcelOperation excel = new ExcelOperation();
	BaseClass base= new BaseClass();

	@Test(groups="api",description="API Test from openweathemap.com")
	public void openWeatherTemperatureTest() throws IOException {		
		String key = base.getPropertyValue("key");
		String valueUnit = base.getPropertyValue("valueUnit");
		String cityName = base.getPropertyValue("cityName");
		String getDataOf = base.getPropertyValue("getDataOf");
		String uril= base.getPropertyValue("uril");
		String tempValue = base.getPropertyValue("tempValue");
	
		RestAssured.baseURI =uril ;

		String response = 
				given().
					log().all().queryParam("q", cityName)
					.queryParam("appid", key).queryParam("units", valueUnit)
				.when()
					.get(getDataOf)
				.then()
					.assertThat().log().all().statusCode(200).extract().response().asString();

		log.info(response);
		JsonPath js = new JsonPath(response);
		String tempV = js.getString(tempValue);
		String city = base.getCityName();
		String newName = "API_"+city;
		excel.writeExcel(newName, tempV,"WeatherAPI");
	}

	
	
	
}
