package entity;

import java.util.Date;

import enums.ContractType;

public abstract class EmployeeEntity implements Comparable<EmployeeEntity> {
	private String empNo;
	private String name;
	private Date birthDay;
	private Date dayRegist;
	private String phone;
	private String address;
	private ContractType contractType;
	private Integer contractTerm;
	private Double salaryGross;
	private Double pit;

	public EmployeeEntity() {
		super();
	}

	public EmployeeEntity(String empNo, String name, Date birthDay, Date dayRegist, String phone, String address,
			ContractType contractType, Integer contractTerm, Double salaryGross, Double pit) {
		super();
		this.empNo = empNo;
		this.name = name;
		this.birthDay = birthDay;
		this.dayRegist = dayRegist;
		this.phone = phone;
		this.address = address;
		this.contractType = contractType;
		this.contractTerm = contractTerm;
		this.salaryGross = salaryGross;
		this.pit = pit;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Date getDayRegist() {
		return dayRegist;
	}

	public void setDayRegist(Date dayRegist) {
		this.dayRegist = dayRegist;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ContractType getContractType() {
		return contractType;
	}

	public void setContractType(ContractType contractType) {
		this.contractType = contractType;
	}

	public Integer getContractTerm() {
		return contractTerm;
	}

	public void setContractTerm(Integer contractTerm) {
		this.contractTerm = contractTerm;
	}

	public Double getSalaryGross() {
		return salaryGross;
	}

	public void setSalaryGross(Double salaryGross) {
		this.salaryGross = salaryGross;
	}

	public Double getPit() {
		return pit;
	}

	public void setPit(Double pit) {
		this.pit = pit;
	}

	/**
	 * Calculation Gross
	 * 
	 * @return Gross
	 */
	public abstract Double calcGross();

	/**
	 * Calculation Pit
	 * 
	 * @return Pit
	 */
	public abstract Double calcPit();

	/**
	 * Calculation SalaryNet
	 * 
	 * @return SalaryNet
	 */
	public Double calSalaryNet() {
		return salaryGross - pit;
	};

	@Override
	public String toString() {
		return "EmployeeEntity [empNo=" + empNo + ", name=" + name + ", birthDay=" + birthDay + ", dayRegist="
				+ dayRegist + ", phone=" + phone + ", address=" + address + ", contractType=" + contractType
				+ ", contractTerm=" + contractTerm + ", salaryGross=" + salaryGross + ", pit=" + pit;
	}

	@Override
	public int compareTo(EmployeeEntity o) {
		if (this.calSalaryNet().compareTo(o.calSalaryNet()) == 0) {
			int x = Integer.parseInt(this.getBirthDay().toString().split("-")[2]);
			int y = Integer.parseInt(o.getBirthDay().toString().split("-")[2]);
			if (x > y)
				return -1;
		}
		return this.calSalaryNet().compareTo(o.calSalaryNet());
	}
}
