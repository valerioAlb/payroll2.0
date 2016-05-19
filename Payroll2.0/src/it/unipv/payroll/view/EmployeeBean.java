package it.unipv.payroll.view;

import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.Union;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("employee")
@SessionScoped
//@ManagedBean(name="employee")
//@ViewScoped
public class EmployeeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	EmployeeController e_controller;
	
	
	private int empID; //Used for delete operation
	private String name;
	private String surname;
	private String postalAddress;
	private String IBAN;
	private String unionName;
	private String type;
	
	//Used only for the html interface
	private String tempType;
	
	//For hourly employee
	private float hourlySalary;
	
	//For Flat slary employee
	private double fixedSalary;
	private double commissionRate;
	
	private List<Employee> employees;
	
	private Employee selectedEmployee;
	
	public List<Employee> getEmployees() {
		return employees;
	}
	
	@PostConstruct
	 public void init(){
		 
		employees=e_controller.findAll();
		 
	 }

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public void addEmployee(){
		
		
		System.out.println(type);
		
		Union union = e_controller.findUnionByName(unionName);
		
		System.out.println(type);
		
		e_controller.add(type,name,surname,postalAddress,IBAN,union,hourlySalary,fixedSalary,commissionRate);
		
	}
	
	public void deleteEmployee(){
		e_controller.deleteEmployee(selectedEmployee.getEmpId());
		//resetBean();
		
	}
	
	public void modifyEmployee(){
		
		Union union = e_controller.findUnionByName(unionName);
		e_controller.modifyEployee(empID,postalAddress,IBAN,union,hourlySalary,fixedSalary,commissionRate);
		
	}
	
	public void setHourly(){
		setType("hourly");
		System.out.println("set "+type);
	}
	public void setFlat(){
		setType("flat");
		System.out.println("set "+type);
	}
	
	
	private void resetBean(){
		
		this.empID=0; //Used for delete operation
		this.name=null;
		this.surname=null;
		this.postalAddress=null;
		this.IBAN=null;
		this.unionName=null;
		this.type=null;
		
		this.hourlySalary=0;
		
		this.fixedSalary=0;
		this.commissionRate=0;
	}

	public int getEmpID() {
		return empID;
	}

	public void setEmpID(int empID) {
		this.empID = empID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public String getUnionName() {
		return unionName;
	}

	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getHourlySalary() {
		return hourlySalary;
	}

	public void setHourlySalary(float hourlySalary) {
		this.hourlySalary = hourlySalary;
	}

	public double getFixedSalary() {
		return fixedSalary;
	}

	public void setFixedSalary(double fixedSalary) {
		this.fixedSalary = fixedSalary;
	}

	public double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getTempType() {
		return tempType;
	}

	public void setTempType(String tempType) {
		this.tempType = tempType;
		System.out.println(tempType);
	}
	
	public void Test(){
		System.out.println("ALLAAAAHHHHH");
	}
	
	
	

}
