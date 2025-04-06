package com.orangeHRM.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {

	private static final String DB_Url = "jdbc:mysql://localhost:3306/orangehrm";
	private static final String DB_Username = "root";
	private static final String DB_Password = "";

	public static Connection getDBConnection() {

		try {
			System.out.println("Starting DB connection");
			Connection conn = DriverManager.getConnection(DB_Url, DB_Username, DB_Password);
			System.out.println("DB connection successful");
			return conn;
		} catch (SQLException e) {
			System.out.println("Error while connection");
			e.printStackTrace();
			return null;
		}
	}

	public static Map<String, String> getEmployeeDetails(String Employee_id) {
		String query = "Select * from hs_hr_employee where employee_id =" + Employee_id;

		Map<String, String> employeeDetails = new HashMap<>();

		try (Connection conn = getDBConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			System.out.println("Executing query");
			if (rs.next()) {
				String firstname = rs.getString("emp_firstname");
				String middlename = rs.getString("emp_middlename");
				String lastname = rs.getString("emp_lastname");

				employeeDetails.put("firstname", firstname);
				employeeDetails.put("middlename", middlename);
				employeeDetails.put("lastname", lastname);

				System.out.println("data fetched");
			} else {
				System.out.println("Emp not found");
			}
		} catch (Exception e) {
			System.out.println("error in query");
			e.printStackTrace();
		}
		return employeeDetails;
	}

}
