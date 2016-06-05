package it.unipv.payroll.test;

import it.unipv.payroll.controller.AdminController;
import it.unipv.payroll.controller.HREUserController;
import it.unipv.payroll.dao.CleanerDao;
import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.PaymentDAO;
import it.unipv.payroll.dao.ServiceChargeDao;
import it.unipv.payroll.dao.UnionDao;
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
public class HREControllerTest extends ArquillianTest{
	
	@Inject
	CleanerDao cleanerDao;
	
	@Inject
	AdminController adminController;
	
	@Inject
	HREUserController userController;
	
	@Inject
	PaymentDAO paymentDAO;
	
	@Inject
	EmployeeDao employeeDao;
	
	@Inject
	UnionDao unionDao;
	
	@Inject
	ServiceChargeDao chargeDao;
	
	@Before
	public void cleanup() {
		
		cleanerDao.cleanAll();
	
	}
	
	@Test
	public void timeCardsFunctionalityTest(){
		
		int id1 = adminController.add("hourly", "user1", "Viola", "Via Via", "", null, 12, 0, 0, "user1");
		
		int id2 = adminController.add("hourly", "user2", "Verdi", "Via Vie", "", null, 14, 0, 0, "user2");
		
		HourlyEmployee employee1 = userController.findEmployeeById(id1);
		
		HourlyEmployee employee2 = userController.findEmployeeById(id2);
		
		userController.postTimeCard(employee1, 5);
		userController.postTimeCard(employee2, 8);
		
		List<TimeCard> timeCards1 = userController.findTimeCardsById(id1);
		List<TimeCard> timeCards2 = userController.findTimeCardsById(id2);
		
		boolean test = true;
		
		for (TimeCard timeCard : timeCards2) {
			if (!timeCard.getHourly_employee().getName().equals("user2")) {
				test = false;
			}
		}
		
		for (TimeCard timeCard : timeCards1) {
			if (!timeCard.getHourly_employee().getName().equals("user1")) {
				test = false;
			}
		}
		
		Assert.assertTrue("Time Card not added", test);
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void findPaymentsFunctionalityTest(){
		
		int id1 = adminController.add("hourly", "user1", "Viola", "Via Via", "", null, 12, 0, 0, "user1");
		
		int id2 = adminController.add("hourly", "user2", "Verdi", "Via Vie", "", null, 14, 0, 0, "user2");
		
		HourlyEmployee employee1 = userController.findEmployeeById(id1);
		
		HourlyEmployee employee2 = userController.findEmployeeById(id2);
		
		Payment payment1 = new Payment(employee1, new Date(2009-1900, 6-1, 4), "IBAN", 3000, "Salary");
		paymentDAO.add(payment1);
		employeeDao.setLastPayment(payment1.getId(), id1);
		
		Payment payment2 = new Payment(employee2, new Date(2009-1900, 8-1, 4), "IBAN", 2000, "Salary");
		paymentDAO.add(payment2);
		employeeDao.setLastPayment(payment2.getId(), id2);
		
		List<Payment> payments1 = userController.findEmployeePayments(id1);
		
		List<Payment> payments2 = userController.findEmployeePayments(id2);
		
		boolean test = true;
		
		if (!(payments1.get(0).getPayAmount() == 3000 && payments1.get(0).getEmployee().getEmpId() == id1)) {
			test = false;
		}
		if (!(payments2.get(0).getPayAmount() == 2000 && payments2.get(0).getEmployee().getEmpId() == id2)) {
			test = false;
		}
		
		Assert.assertTrue("Payments not found", test);
		
	}
	
	@Test
	public void findChargesFunctionalityTest(){
		
		UnionTable union1 = new UnionTable("Union1", 0.5);
		
		unionDao.add(union1);
		
		int id1 = adminController.add("hourly", "user1", "Viola", "Via Via", "", union1, 12, 0, 0, "user1");
		
		ServiceCharge charge1 = new ServiceCharge(id1, 2000, new Date(1312400));
		ServiceCharge charge2 = new ServiceCharge(id1, 500, new Date(1334600));
		ServiceCharge charge3 = new ServiceCharge(id1, 1000, new Date(1323240));
		
		charge1.setUnion(union1);
		charge2.setUnion(union1);
		charge3.setUnion(union1);
		
		chargeDao.add(charge1);
		chargeDao.add(charge2);
		chargeDao.add(charge3);
		
		List<ServiceCharge> charges = userController.findChargesById(id1);
		
		boolean test = false;
		
		double sum = 0;
		
		for (ServiceCharge serviceCharge : charges) {
			sum = sum + serviceCharge.getAmount();
		}
		if (sum == 3500) {
			test = true;
		}
		
		Assert.assertTrue("Error with the processing of service charges", test);
		
	}

}
