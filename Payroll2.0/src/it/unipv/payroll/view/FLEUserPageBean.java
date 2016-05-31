package it.unipv.payroll.view;

import it.unipv.payroll.controller.FLEUserController;
import it.unipv.payroll.logic.CalendarService;
import it.unipv.payroll.model.FlatSalaryEmployee;
import it.unipv.payroll.model.Payment;
import it.unipv.payroll.model.SalesReceipt;
import it.unipv.payroll.model.ServiceCharge;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("FLEuserbean")
@Stateless
public class FLEUserPageBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	FLEUserController userController;
	
	@Inject
	CalendarService calendarService;
	
	private FlatSalaryEmployee flatSalaryEmployee;
	private List<Payment> payments;
	private List<SalesReceipt> receipts;
	private List<ServiceCharge> charges;
	
	
	private int empID;
	private double amount;
	private Date date;
	private String todayDate;

	
	 public void init(){
		
		FacesContext fc = FacesContext.getCurrentInstance();
		this.empID = getEmpID(fc);
		this.flatSalaryEmployee = userController.findEmployeeById(empID);
		this.payments=userController.findEmployeePayments(empID);
		this.charges=userController.findChargesByEmpId(empID);
		this.receipts=userController.findReceiptsByEmpID(empID);
		this.todayDate = calendarService.getFormattedToday();
		System.out.println("DATE: "+todayDate);
		System.out.println("USER NAME: "+flatSalaryEmployee.getName());
		
		 
	 }
	 
	public void updateReceipts(){

		this.receipts=userController.findReceiptsByEmpID(empID);
	} 
	
	public int getEmpID(FacesContext fc){
		
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		System.out.println(params.toString());
		return Integer.parseInt(params.get("form:EmpId"));
		
	}
	
	public void postSalesReceipt(){
		userController.postReceipt(flatSalaryEmployee,amount,date);
	}

	public FlatSalaryEmployee getFlatSalaryEmployee() {
		return flatSalaryEmployee;
	}

	public void setFlatSalaryEmployee(FlatSalaryEmployee flatSalaryEmployee) {
		this.flatSalaryEmployee = flatSalaryEmployee;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
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

	@SuppressWarnings("deprecation")
	public void setDate(Date date) {
		date.setDate(date.getDate()+1);
		this.date = date;
	}

	public String getTodayDate() {
		return todayDate;
	}

	public void setTodayDate(String todayDate) {
		this.todayDate = todayDate;
	}

	public List<SalesReceipt> getReceipts() {
		return receipts;
	}

	public void setReceipts(List<SalesReceipt> receipts) {
		this.receipts = receipts;
	}

	public List<ServiceCharge> getCharges() {
		return charges;
	}

	public void setCharges(List<ServiceCharge> charges) {
		this.charges = charges;
	}

	
	
	
	

}
