package it.unipv.payroll.view;

import it.unipv.payroll.controller.HREUserController;
import it.unipv.payroll.model.HourlyEmployee;
import it.unipv.payroll.model.Payment;
import it.unipv.payroll.model.ServiceCharge;
import it.unipv.payroll.model.TimeCard;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("HREuserbean")
@Stateless
public class HREUserPageBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	HREUserController userController;
	
	private HourlyEmployee hourlyEmployee;
	private List<Payment> payments;
	private List<TimeCard> timeCards;
	private List<ServiceCharge> serviceCharges;
	
	private int empID;
	private float numHours;

	
	
	public void init(){

		
		FacesContext fc = FacesContext.getCurrentInstance();
		this.empID = getEmpID(fc);
		System.out.println("Init! empID: "+empID);
		this.hourlyEmployee = userController.findEmployeeById(empID);
		this.payments=userController.findEmployeePayments(empID);
		this.serviceCharges = userController.findChargesById(empID);
		this.timeCards = userController.findTimeCardsById(empID);
		System.out.println("USER NAME: "+hourlyEmployee.getName());
		 
	 }
	
	public void postTimeCard(){
		userController.postTimeCard(hourlyEmployee,numHours);
		FacesContext context = FacesContext.getCurrentInstance();
		String result = "Time Card posted";
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Done ",  result) );
		numHours = 0;
	}
	
	public void updateTimeCards(){
		this.timeCards = userController.findTimeCardsById(empID);
	}
	
	public int getEmpID(FacesContext fc){
		
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		System.out.println(params.toString());
		return Integer.parseInt(params.get("form:EmpId"));
		
	}

	public HourlyEmployee getHourlyEmployee() {
		return hourlyEmployee;
	}

	public void setHourlyEmployee(HourlyEmployee hourlyEmployee) {
		this.hourlyEmployee = hourlyEmployee;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public float getNumHours() {
		return numHours;
	}

	public void setNumHours(float numHours) {
		this.numHours = numHours;
	}

	public List<TimeCard> getTimeCards() {
		return timeCards;
	}

	public void setTimeCards(List<TimeCard> timeCards) {
		this.timeCards = timeCards;
	}

	public List<ServiceCharge> getServiceCharges() {
		return serviceCharges;
	}

	public void setServiceCharges(List<ServiceCharge> serviceCharges) {
		this.serviceCharges = serviceCharges;
	}
	
	
	
	

}
