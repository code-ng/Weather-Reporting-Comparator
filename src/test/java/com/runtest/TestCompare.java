/*
 * Copyright 2020 Nirupam Ghosh (niupamghosh.blr@gmail.com)
 * SPDX-License-Identifier: MIT
 */


package com.runtest;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.base.BaseClass;
import com.weather.CompareData;

public class TestCompare {
	private static Logger log = LogManager.getLogger(TestCompare.class.getName());
	CompareData compare = new CompareData();
	@Test(dependsOnGroups= {"api","nd"},description="Compare Data in between NDTV and OpenWeather")
	public void compareData() throws Exception {
		log.info("Inside compareOne() method =============== ");
	
		try {
			compare.weatherDataCompare();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
