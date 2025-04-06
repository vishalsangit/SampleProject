package com.orangeHRM.utilities;

import java.net.MalformedURLException;
import java.net.URL;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIUtility {

	public static Response sendGetRquest(String endPoint) {

		try {
			URL url = new URL(endPoint);
			RequestSpecBuilder reqBuild = new RequestSpecBuilder();
			RequestSpecification spec = reqBuild.build();
			Response resp = given(spec).get(url);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static Response sendPostRquest(String endPoint, String body) {

		try {
			URL url = new URL(endPoint);
			RequestSpecBuilder reqBuild = new RequestSpecBuilder();
			reqBuild.setBody(body);
			RequestSpecification spec = reqBuild.build();
			Response resp = given(spec).post(url);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Boolean validateStatusCode(Response resp, int statusCode) {
		return resp.getStatusCode() == statusCode;
	}
	
	public static String getValueFromResponse(Response resp, String key) {
		
		String value = resp.jsonPath().get(key);
		return value;
	}
	
	
	

}
