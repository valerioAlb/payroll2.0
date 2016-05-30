package it.unipv.payroll.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Credentials")
public class Credentials {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String password;
	private boolean admin;
	
	public Credentials(Employee employee, String password, boolean admin) {
		super();
		this.employee = employee;
		this.password = password;
		this.admin = admin;
	}

	@OneToOne
	@JoinColumn(name="EmpId",referencedColumnName="EmpId",nullable = true)
	private Employee employee;

	public Credentials() {
		// TODO Auto-generated constructor stub
	}

	
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	

}
