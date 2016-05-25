package it.unipv.payroll.test;

import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.PaymentDAO;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.HourlyEmployee;
import it.unipv.payroll.model.Payment;

import java.sql.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class PaymentTest extends ArquillianTest {
	
	@Inject
	PaymentDAO paymentDAO;
	
	@Inject
	EmployeeDao e_dao;
	
	@SuppressWarnings("deprecation")
	@Test
	public void paymentTest(){
		
		HourlyEmployee employee1 = new HourlyEmployee("Marco", "Gionni", 234);
		e_dao.add(employee1);
		Payment payment1 = new Payment(employee1, new Date(2009-1900, 6-1, 4), "IBAN", 3000, "Salary");
		paymentDAO.add(payment1);
		e_dao.setLastPayment(payment1.getId(), employee1.getEmpId());
		
		boolean test = false;
		
		List<Payment> payments = paymentDAO.findAll();
		List<Employee> employees = e_dao.findAll();
		
		
		for (Payment payment : payments) {
			
			for (Employee employee : employees) {
				
				if (payment.getEmployee().getName().equals("Marco") && payment.getId() == employee.getLastPaymentId()) {
					test = true;
				}
				
			}
			
			
			
		}
		
		Assert.assertTrue("L' associazione payment-employee non è stata creata", test);
		

		
	}

}
