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

public class NDTVTest extends BaseClass {

	private static Logger log = LogManager.getLogger(NDTVTest.class.getName());
	ExcelOperation excel = new ExcelOperation();

	@BeforeTest
	public void setUp() throws InterruptedException {
		initiliaze();
	}

	@Test(groups = "nd")
	public void ndtvTest() throws Exception {

		clickElement("menuLink");

		clickElement("weather");

		sendTextValue("cityName", "searchElement");

		selectElement("city");

		presenceOfCity("cityName", "cityList");

		captureData("cityName", "tempvalue");

		clickElement("clickCity");

		takeScreesnShot(driver);

	}
}
