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
@Table(name = "ServiceCharge")
public class ServiceCharge {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ScId;
	
	@ManyToOne
	@JoinColumn(name="unionId",referencedColumnName="unionId")
	private UnionTable union;
	
	private int EmpId;
	private double amount;
	private Date date;
	
	
	
	public ServiceCharge(int EmpId, double amount, Date date) {
		super();
		this.EmpId = EmpId;
		this.amount = amount;
		this.date = date;
	}
	
	
	
	public void setUnion(UnionTable union) {
		this.union = union;
	}



	public ServiceCharge() {
		// TODO Auto-generated constructor stub
	}

	public int getEmpId() {
		return EmpId;
	}

	public double getAmount() {
		return amount;
	}

	public Date getDate() {
		return date;
	}

	public UnionTable getUnion() {
		return union;
	}
	
	
	
	
	
	
	
	

}
