package main;

import java.io.IOException;

import service.EmployeeService;

public class Main {
	private static EmployeeService employeeService = new EmployeeService();

	public static void main(String[] args) throws Exception {
		employeeService.insertDataFromTxtFile();
		System.out.println("Insert thanh cong");
		employeeService.updateDataToDB();
		System.out.println("Update db thanh cong");

		try {
			employeeService.updateDataToDB();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		employeeService.showLast();
		System.out.println("Update saralry and show infor employee");
	}

}
