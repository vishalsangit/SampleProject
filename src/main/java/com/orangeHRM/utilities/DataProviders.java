package com.orangeHRM.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	public static final String path = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\testdata\\TestData.xlsx";

	@DataProvider(name = "DataProvider")
	public static Object[][] validLoginData() throws IOException {
		return ExcelReaderUtility.getData_XLS(path, "validLoginData");
		// return new Object[][] {{"admin", "admin123"}};

	}

}
