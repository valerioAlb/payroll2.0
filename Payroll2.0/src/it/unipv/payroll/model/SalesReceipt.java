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
@Table(name="SalesReceipt")
public class SalesReceipt {
	
	private Date date;
	private double amount;
	
	public SalesReceipt(Date date, double amount) {
		super();
		this.date = date;
		this.amount = amount;
	}
	
	public SalesReceipt() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="EmpId",referencedColumnName="EmpId")
	private FlatSalaryEmployee flat_employee;
	
	
	
	public FlatSalaryEmployee getFlat_employee() {
		return flat_employee;
	}
	public void setFlat_employee(FlatSalaryEmployee flat_employee) {
		this.flat_employee = flat_employee;
	}
	
	public Date getDate() {
		return date;
	}
	public double getAmount() {
		return amount;
	}
	
	

}
