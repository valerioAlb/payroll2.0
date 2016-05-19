package it.unipv.payroll.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "FlatSalaryEmployee")
@DiscriminatorValue("FLE")
public class FlatSalaryEmployee extends Employee{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double fixedSalary;
	private double commissionRate;
	
	

	public FlatSalaryEmployee(String name, String surname, double fixedSalary, double commissionRate) {
		super(name, surname);
		this.fixedSalary=fixedSalary;
		this.commissionRate=commissionRate;
	}
	
	public FlatSalaryEmployee() {
		// TODO Auto-generated constructor stub
	}



	@OneToMany(mappedBy="flat_employee")
	private List<SalesReceipt> receipts;



	public double getFixedSalary() {
		return fixedSalary;
	}

	public double getCommissionRate() {
		return commissionRate;
	}

	public List<SalesReceipt> getReceipts() {
		return receipts;
	}

	public void setFixedSalary(double fixedSalary) {
		this.fixedSalary = fixedSalary;
	}

	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}
	
	
	
	
}
