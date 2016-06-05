package it.unipv.payroll.test;

import it.unipv.payroll.controller.AdminController;
import it.unipv.payroll.controller.FLEUserController;
import it.unipv.payroll.dao.CleanerDao;
import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.PaymentDAO;
import it.unipv.payroll.dao.ServiceChargeDao;
import it.unipv.payroll.dao.UnionDao;
import it.unipv.payroll.model.FlatSalaryEmployee;
import it.unipv.payroll.model.Payment;
import it.unipv.payroll.model.SalesReceipt;
import it.unipv.payroll.model.ServiceCharge;
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
public class FLEControllerTest extends ArquillianTest{
	
	@Inject
	CleanerDao cleanerDao;
	
	@Inject
	FLEUserController userController;
	
	@Inject
	AdminController adminController;
	
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
	
	@SuppressWarnings("deprecation")
	@Test
	public void timeCardsFunctionalityTest(){
		
		int id1 = adminController.add("flat", "user1", "Viola", "", "23456652454357", null, 0, 2000, 0.4, "user1");
		
		int id2 = adminController.add("flat", "user2", "Rosa", "", "132356768765342", null, 0, 1500, 0.2, "user2");
		
		FlatSalaryEmployee employee1 = userController.findEmployeeById(id1);
		
		
		userController.postReceipt(employee1, 300, new Date(2016, 5, 23));
		userController.postReceipt(employee1, 400, new Date(2016, 5, 24));
		userController.postReceipt(employee1, 700, new Date(2016, 5, 25));
		
		List<SalesReceipt> receipts1 = userController.findReceiptsByEmpID(id1);
		List<SalesReceipt> receipts2 = userController.findReceiptsByEmpID(id2);
		
		boolean test = true;
		
		double sum = 0;
		
		for (SalesReceipt salesReceipt : receipts1) {
			sum = sum + salesReceipt.getAmount();
		}		
		if (sum != 1400) {
			test = false;
		}
		
		sum = 0;
		for (SalesReceipt salesReceipt : receipts2) {
			sum = sum + salesReceipt.getAmount();
		}
		if (sum != 0) {
			test = false;
		}
		
		Assert.assertTrue("Sales receipts not added", test);
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void findPaymentsFunctionalityTest(){
		
		int id1 = adminController.add("flat", "user1", "Viola", "", "23456652454357", null, 0, 2000, 0.4, "user1");
		
		int id2 = adminController.add("flat", "user2", "Rosa", "", "132356768765342", null, 0, 1500, 0.2, "user2");
		
		FlatSalaryEmployee employee1 = userController.findEmployeeById(id1);
		
		FlatSalaryEmployee employee2 = userController.findEmployeeById(id2);
		
		Payment payment1 = new Payment(employee1, new Date(2300, 23, 23), "IBAN", 3000, "Salary");
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
		
		List<ServiceCharge> charges = userController.findChargesByEmpId(id1);
		
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
