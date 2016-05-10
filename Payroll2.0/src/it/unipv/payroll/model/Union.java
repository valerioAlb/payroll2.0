package it.unipv.payroll.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "UnionTable")
public class Union{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int unionId;
	
	
	
	public Union(String name, double duesRate) {
		super();
		this.name = name;
		this.duesRate = duesRate;
	}
	
	public Union() {
		// TODO Auto-generated constructor stub
	}

	private String name;
	private double duesRate;
	
	@OneToMany(mappedBy="union",targetEntity=Employee.class)
	private List<Employee> employees;
	
	@OneToMany(mappedBy="union",targetEntity=ServiceCharge.class)
	private List<ServiceCharge> charges;

	public int getUnionId() {
		return unionId;
	}

	public String getName() {
		return name;
	}

	public double getDuesRate() {
		return duesRate;
	}

	
	public void setDuesRate(double duesRate) {
		this.duesRate = duesRate;
	}

	public List<Employee> getEmployees() {
		return employees;
	}
	
	
	
	
	
}
