package it.unipv.payroll.model;



import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Payment")
public class Payment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	
	
	public Payment(Employee employee, Date date, String payMethod,
			double payAmount, String payType) {
		super();
		this.employee = employee;
		this.date = date;
		this.payMethod = payMethod;
		this.payAmount = payAmount;
		this.payType = payType;
	}

	public Payment() {
		// TODO Auto-generated constructor stub
	}
	
	
	@ManyToOne
	@JoinColumn(name="EmpId",referencedColumnName="EmpId")
	private Employee employee;
	
	private Date date;
	
	private String payMethod;
	
	private String payType;
	
	private double payAmount;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	
	
	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	

}
