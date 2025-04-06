package com.orangeHRM.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static Map<Long, WebDriver> driverMap = new HashMap<>();

	public static ExtentReports getReporter() {

		if (extent == null) {
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
			String reportPath = System.getProperty("user.dir") + "/src/test/resources/ExtentReports/ExtentReport-"
					+ timeStamp + ".html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			spark.config().setReportName("Automation Test Report");
			spark.config().setDocumentTitle("OrangeHRM Report");
			spark.config().setTheme(Theme.DARK);

			extent = new ExtentReports();
			extent.attachReporter(spark);
			extent.setSystemInfo("Operating System", System.getProperty("os.name"));
			extent.setSystemInfo("OS Version", System.getProperty("os.version"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("user name", System.getProperty("user.name"));
		}
		return extent;
	}

	public static ExtentTest startTest(String testName) {
		ExtentTest extest = getReporter().createTest(testName);
		test.set(extest);
		return extest;
	}

	public static void endTest() {
		getReporter().flush();
	}

	public static ExtentTest getTest() {
		return test.get();
	}

	public static String getTestName() {
		ExtentTest currentTest = getTest();
		if (currentTest != null) {
			return currentTest.getModel().getName();
		} else {
			return "No active test";
		}
	}

	public static void logStep(String logMessage) {
		getTest().info(logMessage);
	}

	public static void logPass(boolean condition, WebDriver driver, String logMessage) {
		if(condition==true) {
			getTest().pass(logMessage);
		} else {
			logFailure(driver, logMessage);
		}
		
	}

	public static void logFailure(WebDriver driver, String logMessage) {
		String screenshotPath = takeScreenshot(driver, getTestName());
		getTest().fail(logMessage, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

	}

	public static void logSkip(String logMessage) {
		getTest().skip(logMessage);
	}

	public static String takeScreenshot(WebDriver driver, String screenshotName) {
		// String base64Format = "";
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String destPath = System.getProperty("user.dir") + "/src/test/resources/screenshots/screenshot-" + timeStamp
				+ ".png";
		File finalPath = new File(destPath);
		try {
			FileUtils.copyFile(src, finalPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		try {
//			base64Format = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(src));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return destPath;

	}

//	public static void attachScreenshot(WebDriver driver, String message) {
//
//		String screenshotPath = takeScreenshot(driver, getTestName());
//		getTest().fail(message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
//
//	}

	public static void registerDriver(WebDriver driver) {
		driverMap.put(Thread.currentThread().getId(), driver);
	}

}
