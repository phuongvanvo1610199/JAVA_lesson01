package entity;

import java.util.Date;

import enums.ContractType;

public class TrialEmpEntity extends EmployeeEntity {
	private Double salaryPerDay;

	private Integer manDay;

	public TrialEmpEntity(String empNo, String name, Date birthDay, Date dateRegist, String phone, String address,
			ContractType contractType, Integer contractTerm, Double salaryGross, Double pit, Double salaryPerDay,
			Integer manDay) {
		super(empNo, name, birthDay, dateRegist, phone, address, contractType, contractTerm, salaryGross, pit);
		this.salaryPerDay = salaryPerDay;
		this.manDay = manDay;
	}

	public TrialEmpEntity() {

	}

	public Double getSalaryPerDay() {
		return salaryPerDay;
	}

	public void setSalaryPerDay(Double salaryPerDay) {
		this.salaryPerDay = salaryPerDay;
	}

	public Integer getManDay() {
		return manDay;
	}

	public void setManDay(Integer manDay) {
		this.manDay = manDay;
	}

	/*
	 * Calculating total salary of employees before tax
	 */
	@Override
	public Double calcGross() {
		return salaryPerDay * manDay;
	}

	/*
	 * Calculation of employee's personal income tax
	 */
	@Override
	public Double calcPit() {
		return calcGross() * 15 / 100;
	}

	@Override
	public String toString() {
		return super.toString() + "TrialEmpEntity{" + "salaryPerDay=" + salaryPerDay + ", manDay=" + manDay + '}';
	}
}