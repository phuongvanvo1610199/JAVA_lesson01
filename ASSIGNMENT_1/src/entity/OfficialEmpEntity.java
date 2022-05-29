package entity;

import java.util.Date;

import enums.ContractType;
import enums.Level;

public class OfficialEmpEntity extends EmployeeEntity {
	private Level level;
	private Double salary;

	public OfficialEmpEntity() {
		super();
	}

	/**
	 * Calculating bonus of employee
	 * 
	 * @return employeeOfficial bonus
	 * 
	 */
	public Double getBonus() {
		Double calcBonus = 0.0;
		switch (level) {
		case LEVEL_1: {
			calcBonus = (5 / 100) * salary;
			break;
		}
		case LEVEL_2: {
			calcBonus = (10 / 100) * salary;
			break;
		}
		case LEVEL_3: {
			calcBonus = (20 / 100) * salary;
			break;
		}
		case LEVEL_4: {
			calcBonus = (35 / 100) * salary;
			break;
		}
		default:
		}
		return calcBonus;
	}

	@Override
	public Double calcGross() {
		return salary + getBonus();
	}

	@Override
	public Double calcPit() {
		// TODO Auto-generated method stub
		Double calPit = 0.0;
		Double TNTT = calcGross() - 11000000;
		if (TNTT < 5000000) {
			calPit = TNTT * 5 / 100;
		} else if (TNTT > 5000000 && TNTT < 10000000) {
			calPit = TNTT * (10 / 100) - 250000;
		} else if (TNTT > 10000000 && TNTT < 18000000) {
			calPit = TNTT * 20 / 100 - 750000;
		} else if (TNTT > 18000000 && TNTT < 32000000) {
			calPit = TNTT * 35 / 100 - 1950000;
		}
		return calPit;
	}

	public OfficialEmpEntity(String empNo, String name, Date birthDay, Date dayRegist, String phone, String address,
			ContractType contractType, Integer contractTerm, Double salaryGross, Double pit) {
		super(empNo, name, birthDay, dayRegist, phone, address, contractType, contractTerm, salaryGross, pit);
		// TODO Auto-generated constructor stub
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
	

	@Override
	public String toString() {
		return super.toString() + "OfficialEmpEntity [level=" + level + ", salary=" + salary + "]";
	}

}
