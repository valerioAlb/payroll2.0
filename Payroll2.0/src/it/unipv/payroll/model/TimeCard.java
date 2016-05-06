package it.unipv.payroll.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TimeCard")
public class TimeCard {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private Date date;
	private float hours;
	
	public TimeCard() {
		// TODO Auto-generated constructor stub
	}
	
	public TimeCard(Date date, float hours) {
		super();
		this.date = date;
		this.hours = hours;
	}
	
	@ManyToOne
	@JoinColumn(name="EmpId",referencedColumnName="EmpId")
	private HourlyEmployee hourly_employee;

	public Date getDate() {
		return date;
	}

	public float getHours() {
		return hours;
	}

	public HourlyEmployee getHourly_employee() {
		return hourly_employee;
	}

	public void setHourly_employee(HourlyEmployee hourly_employee) {
		this.hourly_employee = hourly_employee;
	}
	
	
	
	
	
	

}
