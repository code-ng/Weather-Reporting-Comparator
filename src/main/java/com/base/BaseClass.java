/*
 * Copyright 2020 Nirupam Ghosh (niupamghosh.blr@gmail.com)
 * SPDX-License-Identifier: MIT
 */


package com.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import com.utilitypackage.ExcelOperation;

/**
 * This a Base class which holds all the common functionality for performing the
 * test
 * 
 * @author Nirupam
 * 
 */

public class BaseClass {

	private static Logger log = LogManager.getLogger(BaseClass.class.getName());
	public static WebDriver driver;
	ExcelOperation excel = new ExcelOperation();
	public static Properties prop;
	public static Properties proplocator;
	String directoryPath = System.getProperty("user.dir");

	/**
	 * Create constructor to initialize the properties file
	 */
	public BaseClass() {

		try {
			// Get the system path
			String filePath = System.getProperty("user.dir");
			log.info("");
			log.info("Project Directory path :: " + filePath);
			// Path to config properties file
			String configFilePath = filePath + "\\config.properties";
			File file = new File(configFilePath);
			log.info("Going to load config properties file");
			prop = new Properties();
			FileInputStream fileInput = new FileInputStream(file);
			// load config properties file
			prop.load(fileInput);
			// Path to locator properties file
			String locatorFilePath = filePath + "\\testData\\locator.properties";
			File locatorFile = new File(locatorFilePath);
			log.info("Going to load locator properties file");
			proplocator = new Properties();
			FileInputStream fileInputLocator = new FileInputStream(locatorFile);
			// load locator properties file
			proplocator.load(fileInputLocator);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * generateFirefoxPath() - to create firefox Driver Path
	 * 
	 * @return
	 */
	private String generateFirefoxPath() {
		log.info("Inside generateFirefoxPath() method =============== ");
		String firefoxPath = getPropertyValue("firefoxDriver");
		String path = directoryPath + firefoxPath;
		log.info("Exit from generateFirefoxPath() method =============== ");
		return path;
	}

	public String getFirefoxPath() {
		return generateFirefoxPath();
	}

	/**
	 * generateChromePath() - to create chrome Driver Path
	 * 
	 * @return
	 */
	private String generateChromePath() {
		log.info("Inside generateChromePath() method =============== ");
		String chromePath = getPropertyValue("chromeDriver");
		String path = directoryPath + chromePath;
		log.info("Exit from generateChromePath() method =============== ");
		return path;
	}

	public String getChromePath() {
		return generateChromePath();
	}

	public static Map mapping() {
		Map<String, String> hmap = new HashMap<>();
		hmap.put("firefoxDriver", "webdriver.gecko.driver");
		hmap.put("chromeDriver", "webdriver.chrome.driver");

		return hmap;
	}

	/**
	 * Initialize the browser
	 * 
	 * @throws InterruptedException
	 */
	public void initiliaze() throws InterruptedException {
		log.info("Inside initiliaze() method =============== ");
		String browserName = getBrowser();
		log.info("Browser selected :: " + browserName);

		if (browserName.equals("firefox")) {
			String firefoxDriver = mapping().get("firefoxDriver").toString();
			System.setProperty(firefoxDriver, getFirefoxPath());
			driver = new FirefoxDriver();
		}
		else if (browserName.equals("chrome")) {
			String chromeDriver = mapping().get("chromeDriver").toString();
			System.setProperty(chromeDriver, generateChromePath());
			driver = new ChromeDriver();
		} else {
			// log.error("Driver not present, please look into");
		}
		String url = getPropertyValue("urlName");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
		log.info("Exit from initiliaze() method =============== ");
	}

	/**
	 * getWeatherPageTitle() - return the weather page title
	 * 
	 * @return
	 */
	public String getWeatherPageTitle() {
		log.info("Inside getWeatherPageTitle() method =============== ");
		String title = prop.getProperty("weatherPageTitle");
		if (title != null) {
			return title;
		} else {
			throw new RuntimeException("Weather Page title not available");
		}
	}

	/**
	 * getBrowser() - return browser name
	 * 
	 * @return
	 */
	public String getBrowser() {
		log.info("Inside getBrowser() method =============== ");
		String browserName = prop.getProperty("browser");
		System.out.println(browserName);
		if (browserName != null) {
			return browserName;
		} else {
			throw new RuntimeException(" BrowserName not available");
		}
	}

	/**
	 * getPropertyValue() - Generic function to return the value from config
	 * property file
	 * 
	 * @param name
	 * @return
	 */
	public String getPropertyValue(String name) {
		log.info("Inside getPropertyValue() method =============== ");
		String value = prop.getProperty(name);
		System.out.println(value);
		if (value != null) {
			return value;
		} else {
			throw new RuntimeException(" Value is not available -> " + name);
		}
	}

	/**
	 * getPageTitle() - return current page title
	 * 
	 * @return
	 */
	public String getPageTitle() {
		return driver.getTitle().trim();
	}

	/**
	 * takeScreesnShot() - Generic function for taking screen shot
	 * 
	 * @param driver
	 * @throws Exception
	 */
	public static void takeScreesnShot(WebDriver driver) throws Exception {
		log.info("Inside takeScreesnShot() method =============== ");
		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) driver);

		// Call getScreenshotAs method to create image file
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		String filePath = System.getProperty("user.dir");
		String path = prop.getProperty("screenShotCapture");

		// Open the current date and time
		String fileName = "cityTemperature";
		String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());

		String screenShotpath = filePath + path + fileName + timestamp + ".png";
		log.info("Screenshot stored at " + screenShotpath);

		// Move image file to new destination
		File destFile = new File(screenShotpath);

		// Copy file at destination
		FileUtils.copyFile(SrcFile, destFile);
		log.info("Exit from takeScreesnShot() method =============== ");
	}

	/**
	 * clickElement() - Generic method to click on any element
	 * 
	 * @param name
	 * @throws InterruptedException
	 */
	public void clickElement(String name) throws InterruptedException {
		log.info("Inside clickElement() method =============== ");
		boolean value = elementPresent(name);
		log.info("Element is presnet :: " + value);
		if (value == false) {
			wait(20, name);
		}
		getElement(name).click();
		log.info("Exit from clickElement() method =============== ");
	}

	/**
	 * sendTextValue() - to send values to text boxes
	 * 
	 * @param cityname
	 * @param locatorname
	 */
	public void sendTextValue(String cityname, String locatorname) {
		log.info("Inside sendTextValue() method =============== ");
		String cityName = prop.getProperty(cityname);
		log.info("Given city Name :: " + cityName);
		getElement(locatorname).sendKeys(cityName);
		log.info("Exit from sendTextValue() method =============== ");
	}

	/**
	 * selectElement()- to select checkboxes
	 * 
	 * @param name
	 */
	public void selectElement(String name) {
		log.info("Inside selectElement() method =============== ");
		boolean value = isCheckBoxSelected(name);
		if (value == false) {
			getElement(name).click();
		}
		log.info("Exit from selectElement() method =============== ");
	}

	/**
	 * presenceOfCity()- validating city name present or not in list
	 * 
	 * @param cityname
	 * @param cityList
	 */
	public void presenceOfCity(String cityname, String cityList) {
		log.info("Inside presenceOfCity() method =============== ");

		List<WebElement> list = getElements(cityList);
		String cityName = prop.getProperty(cityname);
		boolean flag = false;
		for (int i = 0; i < list.size(); i++) {
			String getName = list.get(i).getAttribute("title");

			if (getName.equals(cityName)) {
				log.info("City name present :: " + cityName);
				flag = true;
				break;
			}
		}

		if (flag) {
			log.info("City name present :: " + cityName);
		} else {
			log.error("City name  not present :: " + cityName);
		}

		log.info("Exit from presenceOfCity() method =============== ");
	}

	/**
	 * captureData()- get the values from web and update in excel sheet
	 * 
	 * @param cityName
	 * @param name
	 * @throws IOException
	 */
	public void captureData(String cityName, String name) throws IOException {
		log.info("Inside captureData() method =============== ");
		String city = prop.getProperty(cityName);
		String tempValue = getElement(name).getText();
		String tempreature = "";
		String newName = "NDTV_" + city;
		tempreature = tempValue.substring(0, tempValue.length() - 1);
		excel.writeExcel(newName, tempreature, "NDTV");
		log.info("Exit from captureData() method =============== ");
	}

	/**
	 * getElement() - generic method to return the element from locator file and
	 * perform operation
	 * 
	 * @param name
	 * @return
	 */
	public WebElement getElement(String name) {
		log.info("Inside getElement() method =============== ");
		WebElement element = null;
		String locator = proplocator.getProperty(name);
		String locatorType = locator.split(":")[0].trim();
		String locatorValue = locator.split(":")[1].trim();
		log.info(" locatorType :: " + locatorType);
		log.info(" locatorValue :: " + locatorValue);
		try {
			if (locatorType.equalsIgnoreCase("id")) {
				element = driver.findElement(By.id(locatorValue));
				return element;
			} else if (locatorType.equalsIgnoreCase("xpath")) {
				element = driver.findElement(By.xpath(locatorValue));
				return element;
			}
			else if (locatorType.equalsIgnoreCase("className")) {
				element = driver.findElement(By.className(locatorValue));
				return element;
			} else if (locatorType.equalsIgnoreCase("css")) {
				element = driver.findElement(By.cssSelector(locatorValue));
				return element;
			} else if (locatorType.equalsIgnoreCase("linkText")) {
				element = driver.findElement(By.linkText(locatorValue));
				return element;
			} else if (locatorType.equalsIgnoreCase("partialLinkText")) {
				element = driver.findElement(By.partialLinkText(locatorValue));
				return element;
			} else if (locatorType.equalsIgnoreCase("name")) {
				element = driver.findElement(By.name(locatorValue));
				return element;
			} else {
				log.error("Invalid name :: " + name);
				throw new Error("Invalid name :: " + name);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		return element;
	}

	/**
	 * getElement() - generic method to return the element list from locator file
	 * and perform operation
	 * 
	 * @param name
	 * @return
	 */
	public List<WebElement> getElements(String name) {
		log.info("Inside getElements() method =============== ");
		List<WebElement> element = null;
		String locator = proplocator.getProperty(name);
		String locatorType = locator.split(":")[0].trim();
		String locatorValue = locator.split(":")[1].trim();
		log.info(" locatorType :: " + locatorType);
		log.info(" locatorValue :: " + locatorValue);
		try {
			if (locatorType.equalsIgnoreCase("id")) {
				element = driver.findElements(By.id(locatorValue));
				return element;
			} else if (locatorType.equalsIgnoreCase("xpath")) {
				element = driver.findElements(By.xpath(locatorValue));
				return element;
			} else if (locatorType.equalsIgnoreCase("className")) {
				element = driver.findElements(By.className(locatorValue));
				return element;
			} else if (locatorType.equalsIgnoreCase("css")) {
				element = driver.findElements(By.cssSelector(locatorValue));
				return element;
			} else if (locatorType.equalsIgnoreCase("linkText")) {
				element = driver.findElements(By.linkText(locatorValue));
				return element;
			} else if (locatorType.equalsIgnoreCase("partialLinkText")) {
				element = driver.findElements(By.partialLinkText(locatorValue));
				return element;
			} else if (locatorType.equalsIgnoreCase("name")) {
				element = driver.findElements(By.name(locatorValue));
				return element;
			} else {
				log.error("Invalid name :: " + name);
				throw new Error("Invalid name :: " + name);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		return element;
	}

	/**
	 * elementPresent()- return boolean value for on element present or not
	 * 
	 * @param name
	 * @return
	 */
	public boolean elementPresent(String name) {
		log.info("Inside elementPresent() method =============== ");
		boolean value = getElement(name).isDisplayed();

		return value;
	}

	/**
	 * isCheckBoxSelected() - return boolean value for checkbox selected or not
	 * 
	 * @param name
	 * @return
	 */
	public boolean isCheckBoxSelected(String name) {
		log.info("Inside isCheckBoxSelected() method =============== ");
		boolean value = getElement(name).isSelected();
		log.info("Checkbox selected :: " + value);
		
		return value;
	}

	/**
	 * wait()- generic method to wait for a element to get displayed
	 * 
	 * @param timeToWait
	 * @param name
	 * @throws InterruptedException
	 */
	public void wait(int timeToWait, String name) throws InterruptedException {
		log.info("Inside wait() method =============== ");
		for (int i = 0; i < timeToWait; i++) {
			Thread.sleep(1000);
			if (getElement(name).isDisplayed()) {
				break;
			}
		}
	}

	/**
	 * getCityName() - return name of the city
	 * 
	 * @return
	 */
	public String getCityName() {
		log.info("Inside getCityName() method =============== ");
		String city = prop.getProperty("cityName");
		return city;
	}

	@AfterTest
	public void exit() {
		driver.quit();
	}

}
