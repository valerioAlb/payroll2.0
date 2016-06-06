package it.unipv.payroll.test;

import it.unipv.payroll.dao.CleanerDao;
import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.PaymentDAO;
import it.unipv.payroll.dao.ServiceChargeDao;
import it.unipv.payroll.dao.TimeCardDao;
import it.unipv.payroll.dao.UnionDao;
import it.unipv.payroll.logic.PayHourly;
import it.unipv.payroll.model.HourlyEmployee;
import it.unipv.payroll.model.Payment;
import it.unipv.payroll.model.ServiceCharge;
import it.unipv.payroll.model.TimeCard;
import it.unipv.payroll.model.UnionTable;

import java.sql.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class HourlyPaymentTest extends ArquillianTest{
	
	@Inject
	CleanerDao cleanerDao;
	
	@Inject 
	PayHourly payHourly;
	
	@Inject
	EmployeeDao e_dao;
	
	@Inject
	ServiceChargeDao sc_dao;
	
	@Inject
	UnionDao u_dao;
	
	@Inject
	TimeCardDao time_dao;
	
	@Inject
	PaymentDAO paymentDAO;
	
	@Before
	public void cleanup(){
		cleanerDao.cleanAll();
	}
	
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testHourlyPayment(){
		
		UnionTable union1 = new UnionTable("DetroitSPA", 0.5);
		UnionTable union2 = new UnionTable("FifolaSPA", 0.2);
		
		u_dao.add(union1);
		u_dao.add(union2);
		
		HourlyEmployee hourlyEmployee1 = new HourlyEmployee("Rocco","Papaleo",20);
		HourlyEmployee hourlyEmployee2 = new HourlyEmployee("Dado","Ciccio",20);
		
		hourlyEmployee1.setUnion(union1);
		
		e_dao.add(hourlyEmployee1);
		e_dao.add(hourlyEmployee2);
		
		TimeCard timeCard1 = new TimeCard(new Date(2016-1900,05-1,23), 8);
		TimeCard timeCard2 = new TimeCard(new Date(2016-1900,05-1,24), 5);
		TimeCard timeCard3 = new TimeCard(new Date(2016-1900,05-1,23), 10);
		
		timeCard1.setHourly_employee(hourlyEmployee1);
		timeCard2.setHourly_employee(hourlyEmployee1);
		timeCard3.setHourly_employee(hourlyEmployee1);
		
		time_dao.add(timeCard1);
		time_dao.add(timeCard2);
		time_dao.add(timeCard3);
		
		TimeCard timeCard4 = new TimeCard(new Date(2016 -1900,05-1,23), 8);
		TimeCard timeCard5 = new TimeCard(new Date(2016 -1900,05-1,24), 5);
		TimeCard timeCard6 = new TimeCard(new Date(2016 -1900,05-1,24), 10);
		
		timeCard4.setHourly_employee(hourlyEmployee2);
		timeCard5.setHourly_employee(hourlyEmployee2);
		timeCard6.setHourly_employee(hourlyEmployee2);
		
		time_dao.add(timeCard4);
		time_dao.add(timeCard5);
		time_dao.add(timeCard6);
		
		ServiceCharge charge1 = new ServiceCharge(hourlyEmployee1.getEmpId(), 30, new Date(2016-1900,05-1,24));
		ServiceCharge charge2 = new ServiceCharge(hourlyEmployee1.getEmpId(), 24, new Date(2016-1900,05-1,23));
		
		charge1.setUnion(union1);
		charge2.setUnion(union1);
		
		sc_dao.add(charge1);
		sc_dao.add(charge2);
		
		float total1 = 186;
		float total2 = 480;
		
		boolean test = true;
		
		payHourly.pay();
		
		List<Payment> payments = paymentDAO.findAll();
		
		for (Payment payment : payments) {
			
			if (payment.getPayType().equals("Salary") && (payment.getEmployee().getEmpId() == hourlyEmployee1.getEmpId())) {
				if (total1 != payment.getPayAmount()) {
					test = false;
				}
			}
			
			if (payment.getPayType().equals("Salary") && (payment.getEmployee().getEmpId() == hourlyEmployee2.getEmpId())) {
				
				if (total2 != payment.getPayAmount()) {
					test = false;
				}
			}
			
			
		}
		
		Assert.assertTrue("Il test non è stato eseguito correttamente", test);
		
	}

}
