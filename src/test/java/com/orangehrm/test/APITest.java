package com.orangehrm.test;

import static org.hamcrest.CoreMatchers.equalTo;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.utilities.APIUtility;
import com.orangeHRM.utilities.ExtentManager;
import com.orangeHRM.utilities.RetryAnalyzer;

import org.hamcrest.MatcherAssert;

import io.restassured.response.Response;

public class APITest extends BaseClass {

	public static Logger logger = LogManager.getLogger(APITest.class.getName());

	//@Test(retryAnalyzer = RetryAnalyzer.class)
	@Test
	public void verifyAPITest(Method m) throws InterruptedException {

		
		SoftAssert sassert = new SoftAssert();
		ExtentManager.startTest(m.getName());
		String endPoint = "https://jsonplaceholder.typicode.com/users/1";
		Response resp = APIUtility.sendGetRquest(endPoint);
		System.out.println(resp.prettyPeek());
		System.out.println(resp.prettyPrint());
		System.out.println(resp.getStatusCode());
		System.out.println("response body "+resp.body().asString());
		System.out.println(resp.jsonPath().getString("username"));
		System.out.println(resp.jsonPath().getString("address.street"));
		sassert.assertEquals(resp.getStatusCode(), 200, "code matches");
		sassert.assertEquals(resp.jsonPath().getString("username"), "Bret", "username not matches");
		//MatcherAssert.assertThat(resp.jsonPath().getString("username"), equalTo("Bret"));
		ExtentManager.logPass(resp.getStatusCode()==200,driver, "API returned status code 200");
		ExtentManager.logPass(resp.jsonPath().getString("username").equals("Bret"),driver, "username not matches");
		sassert.assertAll();
		
	}
}
