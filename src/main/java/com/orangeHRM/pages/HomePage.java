package com.orangeHRM.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	
	@FindBy(className = "oxd-userdropdown-name")
	public WebElement userIDButton;

	@FindBy(xpath = "//span[text()='Admin']")
	public WebElement adminTab;

	@FindBy(xpath = "//a[text()='Logout']")
	public WebElement logoutButton;

}
