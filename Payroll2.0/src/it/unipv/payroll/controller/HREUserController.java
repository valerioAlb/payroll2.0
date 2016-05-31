package it.unipv.payroll.controller;

import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.PaymentDAO;
import it.unipv.payroll.dao.ServiceChargeDao;
import it.unipv.payroll.dao.TimeCardDao;
import it.unipv.payroll.logic.CalendarService;
import it.unipv.payroll.model.HourlyEmployee;
import it.unipv.payroll.model.Payment;
import it.unipv.payroll.model.ServiceCharge;
import it.unipv.payroll.model.TimeCard;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HREUserController {
	
	@Inject 
	EmployeeDao e_dao;
	
	@Inject
	PaymentDAO paymentDAO;
	
	@Inject
	TimeCardDao timeCardDao;
	
	@Inject
	TimeCard timeCard;
	
	@Inject
	ServiceChargeDao sc_dao;
	
	@Inject
	CalendarService calendarService;
	
	public HourlyEmployee findEmployeeById(int EmpId){
		
	
		return e_dao.findHourlyEmployeeById(EmpId);

	}
	
	public List<Payment> findEmployeePayments(int EmpId){
		
		return paymentDAO.findPaymentsByEmployee(EmpId);
		
	}
	
	public void postTimeCard (HourlyEmployee hourly_employee,float hours){
		timeCard.setDate(calendarService.getToday());
		timeCard.setHours(hours);
		timeCard.setHourly_employee(hourly_employee);
		timeCardDao.merge(timeCard);
		System.out.println("Time card posted! -- "+hours);
		
	}

	public List<ServiceCharge> findChargesById(int empID) {
		return sc_dao.findChargesById(empID);
	}

	public List<TimeCard> findTimeCardsById(int empID) {
		return timeCardDao.findTimeCardsByEmployee(empID);
	}
	
	

}
