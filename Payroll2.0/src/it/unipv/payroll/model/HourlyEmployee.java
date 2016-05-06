package it.unipv.payroll.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "HourlyEmployee")
@DiscriminatorValue("HRE")
public class HourlyEmployee extends Employee{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float hourlySalary;
	
	
	
	public HourlyEmployee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HourlyEmployee(String name, String surname, float hourlySalary) {
		super(name, surname);
		this.hourlySalary = hourlySalary;
	}

	@OneToMany(mappedBy="hourly_employee")
	private List<TimeCard> timeCards;

	public float getHourlySalary() {
		return hourlySalary;
	}

	public void setHourlySalary(float hourlySalary) {
		this.hourlySalary = hourlySalary;
	}
	
	
	
	
	

}
