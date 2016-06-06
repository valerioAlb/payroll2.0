package it.unipv.payroll.logic;

import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.PaymentDAO;
import it.unipv.payroll.dao.ServiceChargeDao;
import it.unipv.payroll.dao.TimeCardDao;
import it.unipv.payroll.model.HourlyEmployee;
import it.unipv.payroll.model.Payment;
import it.unipv.payroll.model.ServiceCharge;
import it.unipv.payroll.model.TimeCard;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;



@Stateless
public class PayHourly{
	
	@Inject
	EmployeeDao e_dao;
	
	@Inject
	TimeCardDao t_dao;
	
	@Inject
	PaymentDAO p_dao;
	
	@Inject
	ServiceChargeDao s_dao;
	
	@Inject
	CalendarService calendarService;
	
	
	@Inject
	Payment payment;
	
	private List<HourlyEmployee> employees;
	private List<TimeCard> timeCards;
	private List<ServiceCharge> charges;
	private double total;
	private String paymethod;
	private int paymentID;
	
	
	@SuppressWarnings("deprecation")
	public void pay() {
		
		if (calendarService.isFriday()) {
			

		Date dateToday = calendarService.getToday();
		List<Date> working_days = calendarService.lastWeekList();
		employees = e_dao.findAllHourly();
		
		for (HourlyEmployee employee : employees) {
			System.out.println(employee.getName());
		
			int numLast;
			Date lastPaymentDate;
			if (employee.getLastPaymentId() != 0) {
				Payment lastPayment = p_dao.findPaymentById(employee.getLastPaymentId());
				lastPaymentDate = lastPayment.getDate();
				numLast =  lastPaymentDate.getYear()*10000+lastPaymentDate.getMonth()*100+lastPaymentDate.getDay();
			} else {
				numLast=0;
			}
			
			int numToday = dateToday.getYear()*10000+dateToday.getMonth()*100+dateToday.getDay();
			
		
			if (numToday > numLast) {
				
				total = 0; 
				timeCards = t_dao.findTimeCardsByEmployee(employee.getEmpId());
				
				for (TimeCard card : timeCards) {
					for (Date date : working_days) {
						
						if(card.getDate().toString().equals(date.toString())){
							if (card.getHours() > 8) {
								
								total+= (card.getHours()-8)* 1.5*employee.getHourlySalary();
								total+= 8*employee.getHourlySalary();
							} else {
								total+= card.getHours()*employee.getHourlySalary();
							}
							
							
						}
						
					}
					
				}
				
				if (employee.getUnion() != null) {
					double dueRate = employee.getUnion().getDuesRate();
					total-= dueRate*total;
					
					String initDate = working_days.get(6).toString();
					String finDate = dateToday.toString();
					
					charges = s_dao.findByDate(initDate, finDate);
					
					for (ServiceCharge charge : charges) {
						if (charge.getEmpId() == employee.getEmpId()) {
							total-=charge.getAmount();
						}
					}
					
					
				}
				
				if(total==0){
					return;  //We don't want to load a DB record with null amount of payment
				}
				
				payment.setDate(calendarService.getToday());
				payment.setEmployee(employee);
				payment.setPayAmount(total);
				payment.setPayType("Salary");
				
				if (employee.getIBAN() != null) {
					paymethod = "IBAN";
				} else if (employee.getPostalAddress() != null){
					paymethod = "postalAddress";
				} else {
					paymethod = "pickup";
				}
				payment.setPayMethod(paymethod);
				
				paymentID = p_dao.update(payment);
				
				e_dao.setLastPayment(paymentID, employee.getEmpId());
			}
			
			
			
		}
	
			
		}
		
			
		
	}


}
