package it.unipv.payroll.test;

import it.unipv.payroll.dao.CleanerDao;
import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.PaymentDAO;
import it.unipv.payroll.dao.SalesRDao;
import it.unipv.payroll.dao.ServiceChargeDao;
import it.unipv.payroll.dao.UnionDao;
import it.unipv.payroll.logic.PayFlat;
import it.unipv.payroll.model.FlatSalaryEmployee;
import it.unipv.payroll.model.Payment;
import it.unipv.payroll.model.SalesReceipt;
import it.unipv.payroll.model.ServiceCharge;
import it.unipv.payroll.model.Union;

import java.sql.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class FlatPaymentTest extends ArquillianTest{
	
	@Inject
	CleanerDao cleanerDao;
	
	@Inject 
	PayFlat payFlat;
	
	@Inject
	EmployeeDao e_dao;
	
	@Inject
	ServiceChargeDao sc_dao;
	
	@Inject
	UnionDao u_dao;
	
	@Inject
	SalesRDao salesRDao;
	
	@Inject
	PaymentDAO paymentDAO;
	
	@Before
	public void cleanup(){
		cleanerDao.cleanAll();
	}
	
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testHourlyPayment(){
		
		Union union1 = new Union("CappellaSPA", 0.5);
		Union union2 = new Union("FifolaSPA", 0.2);
		
		u_dao.add(union1);
		u_dao.add(union2);
		
		FlatSalaryEmployee employee1 = new FlatSalaryEmployee("Mirto", "Musci", 1000, 0.3);
		FlatSalaryEmployee employee2 = new FlatSalaryEmployee("Bernarda", "Bresaola", 3000, 0.2);
		
		employee1.setUnion(union1);
		
		e_dao.add(employee1);
		e_dao.add(employee2);
		
		Date date1 = new Date(2016 -1900,05-1,23);
		Date date2 = new Date(2016 -1900,05-1,22);
		
		SalesReceipt receipt1 = new SalesReceipt(date1, 400);
		SalesReceipt receipt2 = new SalesReceipt(date1, 500);
		SalesReceipt receipt3 = new SalesReceipt(date2, 900);
		
		receipt1.setFlat_employee(employee1);
		receipt2.setFlat_employee(employee1);
		receipt3.setFlat_employee(employee1);
		
		salesRDao.add(receipt1);
		salesRDao.add(receipt2);
		salesRDao.add(receipt3);
		
		ServiceCharge charge1 = new ServiceCharge(employee1.getEmpId(), 30, new Date(2016-1900,05-1,24));
		ServiceCharge charge2 = new ServiceCharge(employee1.getEmpId(), 24, new Date(2016-1900,05-1,23));
		
		charge1.setUnion(union1);
		charge2.setUnion(union1);
		
		sc_dao.add(charge1);
		sc_dao.add(charge2);
		
		SalesReceipt receipt4 = new SalesReceipt(date1, 1000);
		SalesReceipt receipt5 = new SalesReceipt(date1, 700);
		SalesReceipt receipt6 = new SalesReceipt(date2, 800);
		
		receipt4.setFlat_employee(employee2);
		receipt5.setFlat_employee(employee2);
		receipt6.setFlat_employee(employee2);
		
		salesRDao.add(receipt4);
		salesRDao.add(receipt5);
		salesRDao.add(receipt6);
		
		float total1 = 446;
		float total2 = 3000;
		
		float commission1 = 540;
		float commission2 = 500;
		
		boolean test = true;
		
		payFlat.pay();
		
		List<Payment> payments = paymentDAO.findAll();
		
		for (Payment payment : payments) {
			
			if (payment.getPayType().equals("Salary") && (payment.getEmployee().getEmpId() == employee1.getEmpId())) {
				if (total1 != payment.getPayAmount()) {
					test = false;
				}
			}
			if (payment.getPayType().equals("Commission") && (payment.getEmployee().getEmpId() == employee1.getEmpId())) {
				
				if (commission1 != payment.getPayAmount()) {
					test = false;
				}
			}
			if (payment.getPayType().equals("Salary") && (payment.getEmployee().getEmpId() == employee2.getEmpId())) {
				
				if (total2 != payment.getPayAmount()) {
					test = false;
				}
			}
			if (payment.getPayType().equals("Commission") && (payment.getEmployee().getEmpId() == employee2.getEmpId())) {
				
				if (commission2 != payment.getPayAmount()) {
					test = false;
				}
			}
			
		}
		
		Assert.assertTrue("Il test non è stato eseguito correttamente", test);
		
	}

}
