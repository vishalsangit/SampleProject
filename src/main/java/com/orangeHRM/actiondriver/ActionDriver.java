package com.orangeHRM.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.utilities.ExtentManager;

public class ActionDriver {

	public static Logger logger = LogManager.getLogger(ActionDriver.class.getName());

	public static WebDriver fn_launchBrowser(String browser) {
		WebDriver driver_Object = null;
		if (browser.equalsIgnoreCase("chrome")) {
			ExtentManager.registerDriver(driver_Object);
			logger.info("chrome launched");
			driver_Object = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			logger.info("firefox launched");
			driver_Object = new FirefoxDriver();
		} else {
			throw new IllegalArgumentException("browser not supported: " + browser);
		}

		return driver_Object;
	}

	public static boolean waitForElementToBeClickable(WebDriver driver, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,
					Duration.ofSeconds(Integer.parseInt(BaseClass.prop.getProperty("timeInSeconds"))));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			ExtentManager.logPass(true, driver, "Element is clickable:");
			return true;
		} catch (Exception e) {
			ExtentManager.logFailure(driver, "Element not clickable:");
			logger.info("Element not clickable: " + e.getMessage());
			return false;
		}
	}

	public static boolean waitForElementToBeVisible(WebDriver driver, By by) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,
					Duration.ofSeconds(Integer.parseInt(BaseClass.prop.getProperty("timeInSeconds"))));
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			ExtentManager.logPass(true, driver, "Element found:");
			return true;
		} catch (Exception e) {
			ExtentManager.logFailure(driver, "Element not found:");
			logger.info("Element not clickable: " + e.getMessage());
			return false;
		}
	}

	public static void Click(WebDriver driver, WebElement element) {
		try {
			if (waitForElementToBeClickable(driver, element)) {
				ExtentManager.logPass(waitForElementToBeClickable(driver, element), driver, "Clicked on an element:");
				element.click();
				logger.info("element clicked");
			}
		} catch (Exception e) {
			System.out.println("Element not clickable: " + e.getMessage());
			ExtentManager.logFailure(driver, "Unable to click on an element:");
		}
	}

	public static void enterText(WebDriver driver, WebElement element, String sValue) {
		try {
			if (waitForElementToBeClickable(driver, element)) {
				element.clear();
				element.sendKeys(sValue);
				ExtentManager.logPass(waitForElementToBeClickable(driver, element), driver,
						"Entered value on an element");
			}
		} catch (Exception e) {
			ExtentManager.logFailure(driver, "Unable to enter value on an element:");
			System.out.println("Element not clickable: " + e.getMessage());
		}
	}

	public static boolean isElementDisplayed(WebDriver driver, By by) {
		try {
			waitForElementToBeVisible(driver, by);
			if (driver.findElement(by).isDisplayed()) {
				logger.info("element is displayed");
				ExtentManager.logPass(waitForElementToBeVisible(driver, by), driver, "Element displayed");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static void scrollToElement(WebDriver driver, By by) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(by));
	}

	public static boolean waitForPageLoad(WebDriver driver, int iTimeOut) throws InterruptedException {
		boolean isLoaded = false;

		try {
			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
				}
			};
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(iTimeOut));
			wait.until(pageLoadCondition);
			isLoaded = true;
		} catch (Exception e) {
			System.out.println("Error Occured waiting for Page Load " + driver.getCurrentUrl());
		}
		return isLoaded;
	}

	public static void clickByJS(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		System.out.println("Clicked on element by JS" + element);
	}

	public static void sendKeysByJS(WebDriver driver, WebElement element, String sData) throws InterruptedException {
		Thread.sleep(750);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].value=arguments[1];", element, sData);
		System.out.println("Input the text using JavaScript");
	}

}
