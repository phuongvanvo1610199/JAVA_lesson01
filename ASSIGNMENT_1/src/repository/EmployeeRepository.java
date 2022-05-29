package repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.mysql.cj.xdevapi.PreparableStatement;

import entity.EmployeeEntity;
import entity.OfficialEmpEntity;
import entity.TrialEmpEntity;
import enums.ContractType;
import enums.Level;
import utils.ConnectionUtils;

public class EmployeeRepository {

	public static void insertDataFromTxtFile() {
		try {
			File file = new File("C:\\Users\\Admin\\Desktop\\data.txt");

			BufferedReader br = new BufferedReader(new FileReader(file));

			String st;
			String temp;

			var dataTrial = new ArrayList<TrialEmpEntity>();
			var dataOfficial = new ArrayList<OfficialEmpEntity>();

			// Read data from txt File
			while ((st = br.readLine()) != null) {
				temp = st.split(",")[6];
				// Official Employee
				if (temp.trim().equals("0")) {
					dataOfficial.add(convertStringToOfficialEmp(st));
				} else {
					// Trial Employee
					dataTrial.add(convertStringToTrialEmp(st));
				}
			}

			// Validation Data
			// validate trial obj
			dataTrial.forEach(item -> {
				try {
					validateObject(item.getEmpNo(), item.getBirthDay(), item.getDayRegist(), item.getContractType());
				} catch (Exception e) {
					System.out.println(item.getEmpNo() + e.getMessage());
					System.exit(0);
				}
			});

			// validate official obj
			dataOfficial.forEach(item -> {
				try {
					validateObject(item.getEmpNo(), item.getBirthDay(), item.getDayRegist(), item.getContractType());
				} catch (Exception e) {
					System.out.println(item.getEmpNo() + e.getMessage());
					System.exit(0);
				}
			});

			br.close();

			try {
				dataTrial.forEach(item -> {
					insertEmployee(item.getEmpNo(), item.getName(), item.getBirthDay(), item.getDayRegist(),
							item.getPhone(), item.getAddress(), item.getContractType(), item.getContractTerm(),
							item.getSalaryGross(), item.getPit(), null, null, item.getSalaryPerDay(), item.getManDay());
				});

				dataOfficial.forEach(item -> {
					insertEmployee(item.getEmpNo(), item.getName(), item.getBirthDay(), item.getDayRegist(),
							item.getPhone(), item.getAddress(), item.getContractType(), item.getContractTerm(),
							item.getSalaryGross(), item.getPit(), item.getLevel().getValue(), item.getSalary(), null,
							null);
				});
			} catch (Exception e) {
				e.getMessage();
			}

			System.out.println("No problem!!!");
		} catch (Exception e) {
			System.out.println("Program have unexpected error occurred");
		}
	}

	public static void validateObject(String empNo, Date birthDayEmp, Date DayRegist, ContractType type)
			throws Exception {
		// Validate for EmpNo
		if (!(empNo.startsWith("TR") || empNo.startsWith("NV"))) {
			throwCustomException(getMsgException("EmpNoInvalidException", false));
		}
		try {
			int checkNumber = Integer.parseInt(empNo.substring(2));
		} catch (Exception e) {
			throwCustomException(getMsgException("EmpNoInvalidException", false));
		}

		// Validate Date
		Date currentDate = new Date();

		if (birthDayEmp.after(currentDate) || DayRegist.after(currentDate)) {
			throwCustomException(getMsgException("DateInvalidException", true));
		}

		long diffDate = getDifferenceDays(birthDayEmp, DayRegist);
		if (diffDate / 365 < 18) {
			throwCustomException(getMsgException("DateInvalidException", false));
		}

		// Validate Month
		long totalDate = getDifferenceDays(DayRegist, currentDate);
		if (type.equals(ContractType.OFFICIAL_EMP)) {
			if (totalDate / 30 < 14) {
				throwCustomException(getMsgException("MonthInvalidException", false));
			}
		} else {
			if (totalDate / 30 > 6) {
				throwCustomException(getMsgException("MonthInvalidException", false));
			}
		}
	}

	/**
	 * @param d1 Date before
	 * @param d2 Date after
	 * @return Distance Date
	 */
	public static long getDifferenceDays(Date d1, Date d2) {
		long diff = d2.getTime() - d1.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	/**
	 * @param msg Exception Message
	 * @throws Exception
	 */
	public static void throwCustomException(String msg) throws Exception {
		throw new Exception(msg);
	}

	/**
	 * Convert String To Official Employee
	 *
	 * @param txtData String data
	 * @return OfficialEmpEntity
	 */
	public static OfficialEmpEntity convertStringToOfficialEmp(String txtData) throws Exception {
		var dataColumns = txtData.split(",");
		var entity = new OfficialEmpEntity();
		entity.setEmpNo(dataColumns[0].trim());
		entity.setName(dataColumns[1].trim());
		try {
			entity.setBirthDay(new SimpleDateFormat("dd-MM-yyyy").parse(dataColumns[2].trim()));
			entity.setDayRegist(new SimpleDateFormat("dd-MM-yyyy").parse(dataColumns[3].trim()));
		} catch (Exception e) {
			throwCustomException("Dinh dang ngay thang nam khong chinh xac");
		}
		entity.setPhone(dataColumns[4].trim());
		entity.setContractType(ContractType.OFFICIAL_EMP);
		entity.setAddress(dataColumns[5].trim());
		entity.setContractTerm(Integer.parseInt(dataColumns[6].trim()));
		entity.setSalaryGross(0.0);
		entity.setPit(0.0);
		entity.setLevel(Level.convertStringToLevel(dataColumns[7].trim()));
		entity.setSalary(Double.parseDouble(dataColumns[8].trim()));
		return entity;
	}

	/**
	 * Convert String To Official Employee
	 *
	 * @param txtData String data
	 * @return TrialEmpEntity
	 */
	public static TrialEmpEntity convertStringToTrialEmp(String txtData) throws ParseException {
		var dataColumns = txtData.split(",");
		var entity = new TrialEmpEntity();
		entity.setEmpNo(dataColumns[0].trim());
		entity.setName(dataColumns[1].trim());
		entity.setBirthDay(new SimpleDateFormat("dd-MM-yyyy").parse(dataColumns[2].trim()));
		entity.setDayRegist(new SimpleDateFormat("dd-MM-yyyy").parse(dataColumns[3].trim()));
		entity.setPhone(dataColumns[4].trim());
		entity.setContractType(ContractType.TRIAL_EMP);
		entity.setAddress(dataColumns[5].trim());
		entity.setContractTerm(Integer.parseInt(dataColumns[6].trim()));
		entity.setSalaryGross(0.0);
		entity.setPit(0.0);
		entity.setSalaryPerDay(Double.parseDouble(dataColumns[7].trim()));
		entity.setManDay(Integer.parseInt(dataColumns[8].trim()));
		return entity;
	}

	/**
	 * @param err   Type Error
	 * @param check Flag DateInvalidException
	 * @return Exception Message
	 */
	private static String getMsgException(String err, boolean check) {
		String returnStr = "";
		switch (err) {
		case "EmpNoInvalidException":
			returnStr = "Input sai thong tin ma nhan vien";
			break;
		case "DateInvalidException":
			if (check)
				returnStr = "Ngay sinh va ngay bat dau lam viec phai nho hon ngay hien tai";
			else
				returnStr = "Ngay bat dau lam viec khong chinh xac";
			break;
		case "MonthInvalidException":
			returnStr = "So thang lam viec khong chinh xac";
			break;
		default:
			break;
		}
		return returnStr;
	}

	/**
	 * Insert Employee
	 * 
	 * @param empNo
	 * @param name
	 * @param birthDay
	 * @param DayRegist
	 * @param phone
	 * @param address
	 * @param contractType
	 * @param contractTerm
	 * @param salaryGross
	 * @param pit
	 * @param level
	 * @param salary
	 * @param salaryPerDay
	 * @param manDay
	 * @return
	 */
	public static boolean insertEmployee(String empNo, String name, Date birthDay, Date DayRegist, String phone,
			String address, ContractType contractType, Integer contractTerm, Double salaryGross, Double pit,
			Integer level, Double salary, Double salaryPerDay, Integer manDay) {
		boolean rowEffected = false;
		try {
			String sql = "";
			if (contractType.equals(ContractType.OFFICIAL_EMP)) {
				sql = "INSERT employee(emp_no,name_emp,birth_day,date_regist,phone,address,contract_type,"
						+ "contract_term,salary_gross,pit,level_emp,salary)"
						+ "VALUES (?, ?, ?, ? , ? , ? , ? , ? , ? , ? , ?, ?)";
			} else {
				sql = "INSERT employee(emp_no,name_emp,birth_day,date_regist,phone,address,contract_type,"
						+ "contract_term,salary_gross,pit,salary_per_day,man_day)"
						+ "VALUES (?, ?, ?, ? , ? , ? , ? , ? , ? , ? , ?, ?)";
			}

			PreparedStatement preparedStatement;

			Connection connection = ConnectionUtils.getMySQLConnection();

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, empNo);
			preparedStatement.setString(2, name);
			preparedStatement.setDate(3, new java.sql.Date(birthDay.getTime()));
			preparedStatement.setDate(4, new java.sql.Date(DayRegist.getTime()));
			preparedStatement.setString(5, phone);
			preparedStatement.setString(6, address);
			preparedStatement.setInt(7, contractType.getValue());
			preparedStatement.setInt(8, contractTerm);
			preparedStatement.setDouble(9, salaryGross);
			preparedStatement.setDouble(10, pit);
			if (contractType.equals(ContractType.OFFICIAL_EMP)) {
				preparedStatement.setInt(11, level);
				preparedStatement.setDouble(12, salary);
			} else {
				preparedStatement.setDouble(11, salaryPerDay);
				preparedStatement.setInt(12, manDay);
			}

			rowEffected = preparedStatement.execute();

			connection.close();
		} catch (Exception e) {
			System.out.println(empNo + e.getMessage());
			System.exit(0);
		}
		return rowEffected;
	}

	/**
	 * Update SalaryGross and Pit into DB
	 * 
	 * @throws Exception
	 */
	public static void updateSalaryGrossAndPit() throws Exception {
		String sql1 = "SELECT * FROM employee" + " WHERE employee.contract_type = 0";
		String sql2 = "SELECT * FROM employee" + " WHERE employee.contract_type = 1";

		var officialEmp = new ArrayList<OfficialEmpEntity>();
		var trialEmp = new ArrayList<TrialEmpEntity>();
		Connection connection = ConnectionUtils.getMySQLConnection();
		Statement statement = connection.createStatement();
		var data = statement.executeQuery(sql1);
		while (data.next()) {
			officialEmp.add(convertDataDBToOfficialEntity(data));
		}
		data = statement.executeQuery(sql2);
		while (data.next()) {
			trialEmp.add(convertDataDBToTrialEntity(data));
		}

		// UPDATE SALARY GROSS & PIT
		officialEmp.forEach(item -> {
			item.setSalaryGross(item.calcGross());
			item.setPit(item.calcPit());
			System.out.println(item);
		});
		trialEmp.forEach(item -> {
			item.setSalaryGross(item.calcGross());
			item.setPit(item.calcPit());
			System.out.println(item);
		});

		// NHAC VIEC . First
		// TODO UPDATE
		// tiepe tuc cau lenh prestatement de cap nhat lai du lieu sau do.
		String sqlUpdate = "UPDATE employee " + "SET salary_gross = ?, pit = ? " + "WHERE emp_no = ?";
		PreparedStatement pstmt = connection.prepareStatement(sqlUpdate);
		for (var ele : officialEmp) {
			pstmt.setDouble(1, ele.getSalaryGross());
			pstmt.setDouble(2, ele.getPit());
			pstmt.setString(3, ele.getEmpNo());
			int rowAffected = pstmt.executeUpdate();
		}
		for (var ele : trialEmp) {
			pstmt.setDouble(1, ele.getSalaryGross());
			pstmt.setDouble(2, ele.getPit());
			pstmt.setString(3, ele.getEmpNo());
			int rowAffected = pstmt.executeUpdate();
		}

		connection.close();

	}

	/**
	 * get data from db. convert data (String) to OfficialEntity
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static OfficialEmpEntity convertDataDBToOfficialEntity(ResultSet data) throws Exception {
		var obj = new OfficialEmpEntity();
		obj.setEmpNo(data.getString(1));
		obj.setName(data.getString(2));
		obj.setBirthDay(data.getDate(3));
		obj.setDayRegist(data.getDate(4));
		obj.setPhone(data.getString(5));
		obj.setAddress(data.getString(6));
		obj.setContractType(ContractType.OFFICIAL_EMP);
		obj.setContractTerm(data.getInt(8));
		obj.setSalaryGross(data.getDouble(9));
		obj.setPit(data.getDouble(10));
		obj.setLevel(Level.convertStringToLevel(data.getInt(11) + ""));
		obj.setSalary(data.getDouble(12));
		return obj;
	}

	/**
	 * get data from db. convert data (String) to TrialEntity
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static TrialEmpEntity convertDataDBToTrialEntity(ResultSet data) throws Exception {
		var obj = new TrialEmpEntity();
		obj.setEmpNo(data.getString(1));
		obj.setName(data.getString(2));
		obj.setBirthDay(data.getDate(3));
		obj.setDayRegist(data.getDate(4));
		obj.setPhone(data.getString(5));
		obj.setAddress(data.getString(6));
		obj.setContractType(ContractType.OFFICIAL_EMP);
		obj.setContractTerm(data.getInt(8));
		obj.setSalaryGross(data.getDouble(9));
		obj.setPit(data.getDouble(10));
		obj.setSalaryPerDay(data.getDouble(13));
		obj.setManDay(data.getInt(14));
		return obj;
	}

	public static void showInfomationEmp() throws Exception {

		String sql1 = "SELECT * FROM employee" + " WHERE employee.contract_type = 0";
		String sql2 = "SELECT * FROM employee" + " WHERE employee.contract_type = 1";

		var officialEmp = new ArrayList<OfficialEmpEntity>();
		var trialEmp = new ArrayList<TrialEmpEntity>();
		Connection connection = ConnectionUtils.getMySQLConnection();
		Statement statement = connection.createStatement();
		var data = statement.executeQuery(sql1);
		while (data.next()) {
			officialEmp.add(convertDataDBToOfficialEntity(data));
		}
		data = statement.executeQuery(sql2);
		while (data.next()) {
			trialEmp.add(convertDataDBToTrialEntity(data));
		}

		List<EmployeeEntity> list = new ArrayList<>();
		list.addAll(officialEmp);
		list.addAll(trialEmp);

		Collections.sort(list);

		System.out.printf("%-12s%-30s%-20s%-20s%-20s%s\n", "EmpNo", "Name", "Phone", "ContractType", "SalaryGross",
				"SalaryNe");
		int count = 0;
		for (var ele : list) {

			if (ele.getSalaryGross() == 0) {
				count++;
				continue;
			}
			System.out.printf("%-12s%-30s%-20s%-20s%-20s%s\n", ele.getEmpNo(), ele.getName(), ele.getPhone(),
					ele.getContractType(), ele.getSalaryGross(), ele.calSalaryNet());

		}
		if (count == list.size()) {
			System.out.println("Hien tai chua nhap danh sach nhan vien vao DB hoac chua tinh luong");
		}
	}

}