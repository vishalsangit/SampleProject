package com.orangeHRM.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(name = "username")
	public WebElement userNameField;

	@FindBy(css = "input[type='password']")
	public WebElement passwordField;

	@FindBy(xpath = "//button[text()=' Login ']")
	public WebElement loginButton;

}
