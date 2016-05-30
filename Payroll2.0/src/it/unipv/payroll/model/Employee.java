package it.unipv.payroll.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "empType", discriminatorType = DiscriminatorType.STRING)
public class Employee implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int EmpId;
	
	private String name;
	private String surname;
	private String postalAddress;
	private String IBAN;
	private int lastPaymentId;
	
	@Column(name="empType", insertable = false, updatable = false)
	private String empType;
	
	@ManyToOne
	@JoinColumn(name="unionId",referencedColumnName="unionId")
	private UnionTable union;
	
	@OneToMany(mappedBy="employee")
	private List<Payment> payments;
	
	@OneToOne(mappedBy="employee")
	private Credentials credential;
	
	public Employee() {
		// TODO Auto-generated constructor stub
	}
	
	public Employee(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
	}

	public String getEmpType() {
		return empType;
	}

	public String getPostalAddress() {
		return postalAddress;
	}
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}
	
	public int getLastPaymentId() {
		return lastPaymentId;
	}

	public void setLastPaymentId(int lastPaymentId) {
		this.lastPaymentId = lastPaymentId;
	}

	public UnionTable getUnion() {
		return union;
	}

	public void setUnion(UnionTable union) {
		this.union = union;
	}

	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}

	public int getEmpId() {
		return EmpId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
	
	


}
