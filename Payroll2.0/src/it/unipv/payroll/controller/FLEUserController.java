package it.unipv.payroll.controller;

import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.PaymentDAO;
import it.unipv.payroll.dao.SalesRDao;
import it.unipv.payroll.logic.CalendarService;
import it.unipv.payroll.model.FlatSalaryEmployee;
import it.unipv.payroll.model.Payment;
import it.unipv.payroll.model.SalesReceipt;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class FLEUserController {
	
	@Inject 
	EmployeeDao e_dao;
	
	@Inject
	PaymentDAO paymentDAO;
	
	@Inject
	SalesRDao salesRDao;
	
	@Inject
	SalesReceipt salesReceipt;

	@Inject
	CalendarService calendarService;
	
	public FlatSalaryEmployee findEmployeeById(int EmpId){
		
		return e_dao.findFlatEmployeeById(EmpId);

	}
	
	public List<Payment> findEmployeePayments(int EmpId){
		
		return paymentDAO.findPaymentsByEmployee(EmpId);
		
	}

	public void postReceipt(FlatSalaryEmployee flatSalaryEmployee, double amount,Date date) {
		
		salesReceipt.setFlat_employee(flatSalaryEmployee);
		salesReceipt.setAmount(amount);
		salesReceipt.setDate(calendarService.getSqlDate(date));
		
		salesRDao.update(salesReceipt);
		System.out.println("Receipt Posted! -- "+ amount);
		
	}

}
