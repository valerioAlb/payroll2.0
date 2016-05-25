package it.unipv.payroll.logic;

import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.PaymentDAO;
import it.unipv.payroll.dao.SalesRDao;
import it.unipv.payroll.dao.ServiceChargeDao;
import it.unipv.payroll.dao.TimeCardDao;
import it.unipv.payroll.model.FlatSalaryEmployee;
import it.unipv.payroll.model.Payment;
import it.unipv.payroll.model.SalesReceipt;
import it.unipv.payroll.model.ServiceCharge;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PayFlat{
	
	private List<FlatSalaryEmployee> employees;
	private List<ServiceCharge> charges;
	private List<SalesReceipt> receipts;
	private double total;
	private String paymethod;
	
	@Inject
	EmployeeDao e_dao;
	
	@Inject
	TimeCardDao t_dao;
	
	@Inject
	PaymentDAO p_dao;
	
	@Inject
	SalesRDao salesRDao;
	
	@Inject
	ServiceChargeDao s_dao;
	
	@Inject
	CalendarService calendarService;
	
	
	@Inject
	Payment payment;

	
	public void pay() {
		
		Date dateToday = calendarService.getToday();
		employees = e_dao.findAllFlat();
		List<Date> working_days = calendarService.lastMonthList();
		List<Date> lastTwoWeeks = calendarService.last2WeeksList();
		
		//if (calendarService.d1ChangeMounth() || (calendarService.isFriday() && calendarService.d3ChangeMounth())) {
		if (true) {
			
			
			for (FlatSalaryEmployee employee : employees) {
				total = 0;
				
				total += employee.getFixedSalary();
				
				if (employee.getUnion() != null) {
					double dueRate = employee.getUnion().getDuesRate();
					total-= dueRate*total;
					
					String initDate = working_days.get(working_days.size()-1).toString();
					String finDate = dateToday.toString();
					System.out.println("LE due date: "+ initDate + "    "+ finDate);
					
					charges = s_dao.findByDate(initDate, finDate);
					
					for (ServiceCharge charge : charges) {
						System.out.println("charge amount "+charge.getAmount());
						System.out.println("id cazzo "+charge.getEmpId());
						if (charge.getEmpId() == employee.getEmpId()) {
							total-=charge.getAmount();
						}
					}
					
					
				}
				
				loadPayment(employee);
				
			}
			
		}
		
		if (calendarService.isFriday() && calendarService.isWeekNumberPair()) {
			
			for (FlatSalaryEmployee employee : employees) {
				
				total = 0;
				String initDate = lastTwoWeeks.get(lastTwoWeeks.size()-1).toString();
				String finDate = dateToday.toString();
				
				receipts = salesRDao.findReceiptsByEmployeeAndByDate(employee.getEmpId(),initDate,finDate);
				
				for (SalesReceipt receipt : receipts) {
					total += receipt.getAmount() * employee.getCommissionRate();
				}
				
				loadPayment(employee);
				
				
			}
			
		}
		
		
	}


	private void loadPayment(FlatSalaryEmployee employee) {
		payment.setDate(calendarService.getToday());
		payment.setEmployee(employee);
		payment.setPayAmount(total);
		
		if (employee.getIBAN() != null) {
			paymethod = "IBAN";
		} else if (employee.getPostalAddress() != null){
			paymethod = "postalAddress";
		} else {
			paymethod = "pickup";
		}
		payment.setPayMethod(paymethod);
		System.out.println("TOTALEEEEE "+total);
		
		p_dao.add(payment);
		
		e_dao.setLastPayment(payment.getId(), employee.getEmpId());
	}

}
