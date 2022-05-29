package main;

import java.io.IOException;

import service.EmployeeService;

public class Main {
	private static EmployeeService employeeService = new EmployeeService();

	public static void main(String[] args) throws Exception {
		employeeService.insertDataFromTxtFile();
		employeeService.updateDataToDB();

		try {
			employeeService.updateDataToDB();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		employeeService.showLast();
	}

}
