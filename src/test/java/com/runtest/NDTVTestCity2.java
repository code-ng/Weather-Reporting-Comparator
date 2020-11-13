/*
 * Copyright 2020 Nirupam Ghosh (niupamghosh.blr@gmail.com)
 * SPDX-License-Identifier: MIT
 */


package com.runtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.base.BaseClass;
import com.utilitypackage.ExcelOperation;

public class NDTVTestCity2 extends BaseClass {

	private static Logger log = LogManager.getLogger(NDTVTestCity2.class.getName());
	ExcelOperation excel = new ExcelOperation();

	@BeforeTest
	public void setUp() throws InterruptedException {
		initiliaze();
	}

	@Test(priority = 5,description="API Test from city Ajmer ndtv.com")
	public void ndtvCity2TemperatureTest() throws Exception {

		clickElement("menuLink");

		clickElement("weather");

		sendTextValue("cityName2", "searchElement");

		selectElement("city2");

		presenceOfCity("cityName2", "cityList");

		clickElement("clickCityAjmer");

		takeScreesnShot(driver);

	}
}
