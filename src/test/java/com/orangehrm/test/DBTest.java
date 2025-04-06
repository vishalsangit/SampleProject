package com.orangehrm.test;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.utilities.DBConnection;
import com.orangeHRM.utilities.DataProviders;
import com.orangeHRM.utilities.ExtentManager;

public class DBTest extends BaseClass {

	public static Logger logger = LogManager.getLogger(DummyClass.class.getName());

	@Test(dataProvider = "DataProvider", dataProviderClass = DataProviders.class)
	public void dbData(String username, String password) throws InterruptedException {

		Map<String, String> hmap = DBConnection.getEmployeeDetails("3");

		SoftAssert asser = new SoftAssert();
		for (String name : hmap.keySet()) {
			System.out.println(name);
			System.out.println(hmap.get(name));
			asser.assertTrue(hmap.get(name).equals("Sam"), "String not matches");
			ExtentManager.startTest("titleMatch");
			ExtentManager.logPass(hmap.get(name).equalsIgnoreCase("Sam"), driver, "Name not matches");
			asser.assertAll();
		}

	}

}
