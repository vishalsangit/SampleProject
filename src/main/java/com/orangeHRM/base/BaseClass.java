package com.orangeHRM.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.orangeHRM.actiondriver.ActionDriver;
import com.orangeHRM.utilities.ExtentManager;

public class BaseClass {

	public static Properties prop;
	public static WebDriver driver;
	public static String browser;
	public static Logger logger = LogManager.getLogger(BaseClass.class.getName());

	@BeforeSuite
	public void beforeSuite() throws IOException {
		FileInputStream file = new FileInputStream("src/main/resources/config.properties");
		prop = new Properties();
		prop.load(file);
		logger.info("properties file loaded");
		logger.trace("properties file loaded");
		System.out.println("Before Suite runs...");
		ExtentManager.getReporter();
	}

	@BeforeMethod
	public void setup(Method m) throws InterruptedException {
		browser = prop.getProperty("browser");
		driver = ActionDriver.fn_launchBrowser(browser);
		driver.get(prop.getProperty("url"));
		logger.info("browser launched");
		System.out.println("Before method runs...");
		ActionDriver.waitForPageLoad(driver, 10);
		driver.manage().timeouts()
				.implicitlyWait(Duration.ofSeconds(Integer.parseInt(prop.getProperty("implicitwait"))));
		driver.manage().window().maximize();

	}

	@BeforeTest
	public void beforeTest() {
		System.out.println("Before Test runs...");
	}

	@AfterTest
	public void afterTest() {
		System.out.println("After Test runs...");
	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("Before Class runs...");
	}

	@AfterClass
	public void afterClass() {
		System.out.println("After Class runs...");
	}

	@AfterMethod
	public void tearDown(ITestResult result, Method m) {
		
		System.out.println(result.getStatus());

		if (driver != null) {
			driver.quit();
		}

		System.out.println("After method runs...");
		ExtentManager.endTest();
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("After Suite runs...");
	}

}
