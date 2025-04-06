package com.orangehrm.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangeHRM.actiondriver.ActionDriver;
import com.orangeHRM.base.BaseClass;
import com.orangeHRM.pages.HomePage;
import com.orangeHRM.pages.LoginPage;
import com.orangeHRM.utilities.DataProviders;
import com.orangeHRM.utilities.ExtentManager;

public class LoginPageTest extends BaseClass {

	LoginPage loginPage;
	HomePage homePage;
	//Method m;
	public static Logger logger = LogManager.getLogger(LoginPageTest.class.getName());

	@Test(dataProvider="DataProvider", dataProviderClass = DataProviders.class)
	public void verifyLoginTest(String username, String password) throws InterruptedException {

		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		SoftAssert softAssert = new SoftAssert();
		ExtentManager.startTest("verifyLoginTest");
		softAssert.assertTrue(ActionDriver.waitForElementToBeClickable(driver, loginPage.userNameField), "login Page loaded and username tab is visible");
		ActionDriver.enterText(driver, loginPage.userNameField, username);
		Thread.sleep(2000);
		ActionDriver.enterText(driver, loginPage.passwordField, password);
		Thread.sleep(2000);
		ActionDriver.Click(driver, loginPage.loginButton);
		ActionDriver.waitForPageLoad(driver, 10);
		logger.info("User logged in");
		Thread.sleep(2000);
		softAssert.assertTrue(ActionDriver.waitForElementToBeClickable(driver, homePage.userIDButton), "Home Page loaded and Admin tab is visible");
		ExtentManager.logPass(ActionDriver.waitForElementToBeClickable(driver, homePage.userIDButton), driver, "User logged in");
		logger.info("Home Page loaded and Admin tab is visible");
		ExtentManager.logPass(ActionDriver.waitForElementToBeClickable(driver, homePage.userIDButton), driver, "Home Page loaded and Admin tab is visible");
		ActionDriver.Click(driver, homePage.userIDButton);
		Thread.sleep(5000);
		ActionDriver.Click(driver, homePage.logoutButton);
		ExtentManager.logPass(ActionDriver.waitForElementToBeClickable(driver, loginPage.userNameField), driver, "User log out");
		logger.info("user logout");
		softAssert.assertAll();
	}

}
