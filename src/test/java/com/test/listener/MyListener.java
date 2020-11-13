package com.test.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.base.BaseClass;

public class MyListener implements ITestListener {

	WebDriver driver;
	BaseClass base = new BaseClass();

	private static Logger log = LogManager.getLogger(MyListener.class.getName());

	public void onTestStart(ITestResult result) {

		System.out.println("on test start");

	}

	public void onTestSuccess(ITestResult result) {

		System.out.println("on test success");

		log.info(result.getMethod().getMethodName() + "Test is Passed");
	}

	public void onTestFailure(ITestResult result) {

		System.out.println("on test failure");
		log.info(result.getMethod().getMethodName() + "Test is Failed");
		try {
			base.takeScreesnShot(driver);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void onTestSkipped(ITestResult result) {

		System.out.println("on test skipped");
		log.info(result.getMethod().getMethodName() + "Test is Skipped");

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

		System.out.println("on test sucess within percentage");

	}

	public void onStart(ITestContext context) {

		System.out.println("on start");

	}

	public void onFinish(ITestContext context) {

		System.out.println("on finish");

		// driver.close();

	}
}
