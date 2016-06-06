package it.unipv.payroll.view;

import it.unipv.payroll.controller.AdminController;
import it.unipv.payroll.dao.CredentialDao;
import it.unipv.payroll.logic.CalendarService;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FlatSalaryEmployee;
import it.unipv.payroll.model.HourlyEmployee;
import it.unipv.payroll.model.UnionTable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("employee")
@SessionScoped
//@ManagedBean(name="employee")
//@ViewScoped
public class AdministratorBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	AdminController e_controller;
	
	@Inject
	CredentialDao credentialDao;
	
	@Inject
	CalendarService calendarService;
	
	
	
	private int empID; //Used for delete operation
	private String name;
	private String surname;
	private String postalAddress;
	private String IBAN;
	private String unionName;
	private String type;
	private String password;
	private String todayDate;
	private String result;
	
	//Used only for the html interface
	private String tempType;
	private String dialogPath = "HourlyForm";
	
	//For hourly employee
	private float hourlySalary;
	
	//For Flat slary employee
	private double fixedSalary;
	private double commissionRate;
	private double amount;
	
	private Date date;
	
	private List<Employee> employees;
	
	private Employee selectedEmployee;
	private HourlyEmployee selectedHourlyEmployee;
	private FlatSalaryEmployee selectedFlatEmployee;
	
	public List<Employee> getEmployees() {
		return employees;
	}
	
	@PostConstruct
	 public void init(){
		 
		employees=e_controller.findAll();
		this.todayDate = calendarService.getFormattedToday();
		 
	 }
	
	private void resetBean(){
		this.name = "";
		this.surname = "";
		this.postalAddress = "";
		this.IBAN = "";
		this.unionName = "";
		this.hourlySalary = 0;
		this.fixedSalary = 0;
		this.commissionRate = 0;
		this.password="";
		this.amount=0;
		this.empID=0;
		this.date=null;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		
		this.selectedEmployee = selectedEmployee;
		
		try {
			this.password = credentialDao.findCredentialByEmployee(selectedEmployee.getEmpId()).getPassword();
		} catch (Exception e) {
			this.password = "";
		}
		
		if (selectedEmployee.getUnion() != null) {
			this.unionName = selectedEmployee.getUnion().getName();
		} else {
			this.unionName = "";
		}
		if (selectedEmployee.getEmpType().equals("HRE")) {
			selectedHourlyEmployee=e_controller.findHourly(selectedEmployee.getEmpId());
		}else {
			selectedFlatEmployee=e_controller.findFlat(selectedEmployee.getEmpId());
		}
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public void addEmployee(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		System.out.println(type);
		
		UnionTable union = null;
		if (!unionName.equals("")) {
			
			union = e_controller.findUnionByName(unionName);
			
			if(union==null){
				result = "Wrong Union Name Typed";
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"User not added ",  result) );
				resetBean();
				return;
			}
		}
		
		
		System.out.println(type);
		
		e_controller.add(type,name,surname,postalAddress,IBAN,union,hourlySalary,fixedSalary,commissionRate,password);
		
		result = "User "+name +" "+surname+" correctly added!";
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Result ",  result) );
		
		resetBean();
		
		
	}
	
	public void postServiceCharge(){
		
		UnionTable union = null;
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (!unionName.equals("")) {
			
			union = e_controller.findUnionByName(unionName);
			
			if (union == null) {
				
				result = "Wrong Union Name";
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Result ",  result) );
				
			} else if (!e_controller.validate(union,empID)){
				
				result = "Union Name doesn't match the Employee Union Name";
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Result ",  result) );
				
			} else {
				
				e_controller.postServiceCharge(empID,amount,date,union);
				result = "Union Service Charge Posted";
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Result ",  result) );
			
			}
		} else {
			
			result = "Union Name Required";
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Result ",  result) );
			
		}
		
		resetBean();
		
		
	}
	
	
	
	public void updatePath(String path){
		setDialogPath(path);
		System.out.println("Updated value "+this.dialogPath);
	}
	
	public void deleteEmployee(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		result = "User "+selectedEmployee.getName()+" "+selectedEmployee.getSurname()+" correctly deletd";
		
		e_controller.deleteEmployee(selectedEmployee.getEmpId());
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Done! ",  result) );
		
	}
	
	public String openModifyPage(){
		
		return selectedEmployee.getEmpType();
		
	}
	
	public void modifyEmployee(){
		
		System.out.println("MODIFING "+selectedEmployee.getEmpType());
		UnionTable union = null;
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (!unionName.equals("")) {
			
			union = e_controller.findUnionByName(unionName);
			
			if(union==null){
				result = "Wrong Union Name Typed";
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"User not modified ",  result) );
				System.out.println("Resetting bean");
				resetBean();
				return;
			}
		}
		
		if (selectedEmployee.getEmpType().equals("HRE")) {
				selectedHourlyEmployee.setUnion(union);
			e_controller.modifyEployee(selectedHourlyEmployee,password);
		} else {
				selectedFlatEmployee.setUnion(union);
			e_controller.modifyEployee(selectedFlatEmployee,password);
		}
		
		result = "User "+selectedEmployee.getName()+" "+selectedEmployee.getSurname()+" correctly modified";
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Completed ",  result) );
		resetBean();
	}
	
	public void runThePayrollForToday(){
		e_controller.runPayroll();
		result = "";
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Payroll correctly executed",  result) );
	}
	
	public void setHourly(){
		setType("hourly");
		System.out.println("set "+type);
	}
	public void setFlat(){
		setType("flat");
		System.out.println("set "+type);
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

	public String getDialogPath() {
		return dialogPath;
	}
	public void setDialogPath(String dialogPath){
		this.dialogPath= dialogPath;
	}

	public HourlyEmployee getSelectedHourlyEmployee() {
		return selectedHourlyEmployee;
	}

	public void setSelectedHourlyEmployee(HourlyEmployee selectedHourlyEmployee) {
		this.selectedHourlyEmployee = selectedHourlyEmployee;
	}

	public FlatSalaryEmployee getSelectedFlatEmployee() {
		return selectedFlatEmployee;
	}

	public void setSelectedFlatEmployee(FlatSalaryEmployee selectedFlatEmployee) {
		this.selectedFlatEmployee = selectedFlatEmployee;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTodayDate() {
		return todayDate;
	}

	public void setTodayDate(String todayDate) {
		this.todayDate = todayDate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	

}
