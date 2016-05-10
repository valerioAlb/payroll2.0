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
@Table(name = "ServiceCharge")
public class ServiceCharge {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ScId;
	
	@ManyToOne
	@JoinColumn(name="unionId",referencedColumnName="unionId")
	private Union union;
	
	private int empId;
	private double amount;
	private Date date;
	
	
	
	public ServiceCharge(int empId, double amount, Date date) {
		super();
		this.empId = empId;
		this.amount = amount;
		this.date = date;
	}
	
	
	
	public void setUnion(Union union) {
		this.union = union;
	}



	public ServiceCharge() {
		// TODO Auto-generated constructor stub
	}

	public int getEmpId() {
		return empId;
	}

	public double getAmount() {
		return amount;
	}

	public Date getDate() {
		return date;
	}

	public Union getUnion() {
		return union;
	}
	
	
	
	
	
	
	
	

}
