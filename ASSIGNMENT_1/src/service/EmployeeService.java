package service;

import java.io.IOException;
import java.sql.SQLException;

import repository.EmployeeRepository;

public class EmployeeService {
	private EmployeeRepository employeeRepository;

	public EmployeeService() {
		employeeRepository = new EmployeeRepository();
	}

	public void insertDataFromTxtFile() throws IOException {
		employeeRepository.insertDataFromTxtFile();
	}

	public void updateDataToDB() throws Exception {
		EmployeeRepository.updateSalaryGrossAndPit();
	}

	public void showLast() throws Exception {
		EmployeeRepository.showInfomationEmp();
	}

}
