package com.orangehrm.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.utilities.DataProviders;
import com.orangeHRM.utilities.ExtentManager;
import com.orangeHRM.utilities.RetryAnalyzer;

public class DummyClass extends BaseClass {

	public static Logger logger = LogManager.getLogger(DummyClass.class.getName());

	@Test(dataProvider = "DataProvider", dataProviderClass = DataProviders.class)
	public void titleMatch(String username, String password) throws InterruptedException {

		System.out.println(username);
		System.out.println(password);
		SoftAssert asser = new SoftAssert();
		ExtentManager.startTest("titleMatch");
		Thread.sleep(5000);
		asser.assertTrue(driver.getTitle().equals("OrangeHRM"), "title matches");
		logger.info("Title matches");
		ExtentManager.logPass(driver.getTitle().equals("OrangeHRM"), driver, "Title matches");
		asser.assertAll();

	}
}
