package it.unipv.payroll.test;

import it.unipv.payroll.controller.AdminController;
import it.unipv.payroll.dao.CleanerDao;
import it.unipv.payroll.dao.PaymentDAO;
import it.unipv.payroll.dao.SalesRDao;
import it.unipv.payroll.dao.ServiceChargeDao;
import it.unipv.payroll.dao.TimeCardDao;
import it.unipv.payroll.dao.UnionDao;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FlatSalaryEmployee;
import it.unipv.payroll.model.HourlyEmployee;
import it.unipv.payroll.model.Payment;
import it.unipv.payroll.model.SalesReceipt;
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
public class AdminControllerTest extends ArquillianTest{
	
	@Inject
	AdminController adminController;
	
	@Inject
	CleanerDao cleanerDao;
	
	@Inject 
	UnionDao unionDao;
	
	@Inject 
	ServiceChargeDao chargeDao;
	
	@Inject
	TimeCardDao timeCardDao;
	
	@Inject
	PaymentDAO paymentDAO;
	
	@Inject
	SalesRDao salesRDao;
	
	
	@Before
	public void cleanup() {
		
		cleanerDao.cleanAll();
	
	}
	
	@Test
	public void addFunctionalityTest(){
		
		int id1 = adminController.add("hourly", "user1", "Viola", "Via Via", "", null, 12, 0, 0, "user1");
		
		int id2 = adminController.add("flat", "user2", "Rosa", "", "132356768765342", null, 0, 1500, 0.2, "user2");
		
		HourlyEmployee employee1 = adminController.findHourly(id1);
		
		FlatSalaryEmployee employee2 = adminController.findFlat(id2);
		
		boolean test = false;
		
		if (employee1.getName().equals("user1") && employee2.getName().equals("user2")) {
			
			test = true;
			
		}
		
		Assert.assertTrue("User not added", test);
		
	}
	
	@Test
	public void modifyFunctionalityTest(){
		
		int id1 = adminController.add("hourly", "user1", "Viola", "Via Via", "", null, 12, 0, 0, "user1");
		
		int id2 = adminController.add("flat", "user2", "Rosa", "", "132356768765342", null, 0, 1500, 0.2, "user2");
		
		HourlyEmployee employee1 = adminController.findHourly(id1);
		
		FlatSalaryEmployee employee2 = adminController.findFlat(id2);
		
		employee1.setName("Marco");
		employee1.setSurname("Rossi");
		
		employee2.setName("Luca");
		employee2.setSurname("Verdi");
		
		adminController.modifyEployee(employee1, "user1");
		
		adminController.modifyEployee(employee2, "user2");
		
		employee1 = adminController.findHourly(id1);
		employee2 = adminController.findFlat(id2);
		
		boolean test = false;
		
		if (employee1.getName().equals("Marco") && employee1.getSurname().equals("Rossi") && employee2.getName().equals("Luca") && employee2.getSurname().equals("Verdi")) {
			
			test = true;
			
		}
		
		Assert.assertTrue("User not Modified", test);
		
	}
	
	@Test
	public void deleteFunctionalityTest(){
		
		int id1 = adminController.add("hourly", "user1", "Viola", "Via Via", "", null, 12, 0, 0, "user1");
		
		int id2 = adminController.add("flat", "user2", "Rosa", "", "132356768765342", null, 0, 1500, 0.2, "user2");
		
		adminController.deleteEmployee(id1);
		
		adminController.deleteEmployee(id2);
		
		List<Employee> employees = adminController.findAll();
		
		boolean test = true;
		
		for (Employee employee : employees) {
			
			if (employee.getName().equals("user1") || employee.getName().equals("user2")) {
				
				test = false;
				
			}
			
		}
		
		Assert.assertTrue("User not deleted", test);
		
	}
	

	
	@SuppressWarnings("deprecation")
	@Test
	public void postServiceChargeTest(){
		
		UnionTable union1 = new UnionTable("Union1", 0.2);
		
		unionDao.add(union1);
		
		int id1 = adminController.add("hourly", "user1", "Viola", "Via Via", "", union1, 12, 0, 0, "user1");
		
		adminController.postServiceCharge(id1, 50, new java.util.Date(2016, 5, 29), union1);
		
		adminController.postServiceCharge(id1, 75, new java.util.Date(2016, 5, 30), union1);
		
		boolean test = false;
		
		List<ServiceCharge> charges = chargeDao.findAll();
		
		HourlyEmployee employee1 = adminController.findHourly(id1);
		
		for (ServiceCharge serviceCharge : charges) {
			
			if ((serviceCharge.getEmpId() == id1) && serviceCharge.getUnion().getName().equals(employee1.getUnion().getName())) {
				
				test = true;
				
			} else {
				
				test = false;
				break;
			}
			
		}
		
		Assert.assertTrue("Service Charge not posted", test);
		
	}
	
	@Test
	public void findUnionFunctionalityTest(){
		
		UnionTable union1 = new UnionTable("Union1", 0.2);
		UnionTable union2 = new UnionTable("Union2", 0.3);
		
		unionDao.add(union1);
		unionDao.add(union2);
		
		UnionTable unionRetrieved1 = adminController.findUnionByName("Union1");
		UnionTable unionRetrieved2 = adminController.findUnionByName("Union2");
		
		boolean test = false;
		
		if (union1.getName().equals(unionRetrieved1.getName()) && union2.getName().equals(unionRetrieved2.getName())) {
			
			test = true;
			
		}
		
		Assert.assertTrue("Union not found.", test);
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void payFunctionalityTest(){
		
		UnionTable union1 = new UnionTable("CappellaSPA", 0.5);
		UnionTable union2 = new UnionTable("FifolaSPA", 0.2);
		
		unionDao.add(union1);
		unionDao.add(union2);
		
		
		int id1 = adminController.add("hourly", "user1", "Viola", "Via Via", "", union1, 12, 0, 0, "user1");
		int id2 = adminController.add("hourly", "user2", "Rosa", "Via Via", "", union1, 14, 0, 0, "user2");
		
		int id3 = adminController.add("flat", "user3", "Rosa", "", "132356768765342", union2, 0, 1500, 0.2, "user3");
			
		TimeCard timeCard1 = new TimeCard(new Date(2016-1900,05-1,27), 8);
		TimeCard timeCard2 = new TimeCard(new Date(2016-1900,05-1,30), 5);
		TimeCard timeCard3 = new TimeCard(new Date(2016-1900,05-1,31), 10);
		
		HourlyEmployee employee1 = adminController.findHourly(id1);
		HourlyEmployee employee2 = adminController.findHourly(id2);

		timeCard1.setHourly_employee(employee1);
		timeCard2.setHourly_employee(employee1);
		timeCard3.setHourly_employee(employee1);
		
		timeCardDao.add(timeCard1);
		timeCardDao.add(timeCard2);
		timeCardDao.add(timeCard3);
		
		TimeCard timeCard4 = new TimeCard(new Date(2016 -1900,05-1,27), 8);
		TimeCard timeCard5 = new TimeCard(new Date(2016 -1900,05-1,30), 5);
		TimeCard timeCard6 = new TimeCard(new Date(2016 -1900,05-1,31), 10);
		
		timeCard4.setHourly_employee(employee2);
		timeCard5.setHourly_employee(employee2);
		timeCard6.setHourly_employee(employee2);
		
		timeCardDao.add(timeCard4);
		timeCardDao.add(timeCard5);
		timeCardDao.add(timeCard6);
		
		Date date1 = new Date(2016 -1900,05-1,30);
		Date date2 = new Date(2016 -1900,05-1,31);
		
		FlatSalaryEmployee employee3 = adminController.findFlat(id3);
		
		SalesReceipt receipt1 = new SalesReceipt(date1, 400);
		SalesReceipt receipt2 = new SalesReceipt(date1, 500);
		SalesReceipt receipt3 = new SalesReceipt(date2, 900);
		
		receipt1.setFlat_employee(employee3);
		receipt2.setFlat_employee(employee3);
		receipt3.setFlat_employee(employee3);
		
		salesRDao.add(receipt1);
		salesRDao.add(receipt2);
		salesRDao.add(receipt3);
		
		
		adminController.postServiceCharge(id1, 30, new java.util.Date(2016, 5, 27), union1);
		adminController.postServiceCharge(id1, 24, new java.util.Date(2016, 5, 30), union1);
		
		adminController.postServiceCharge(id3, 40, new java.util.Date(2016, 5, 31), union2);
		
		adminController.runPayroll();
		
		float total1 = 186;
		float total2 = 480;
		
		boolean test = true;
		
		List<Payment> payments = paymentDAO.findAll();
		
		for (Payment payment : payments) {
			
			if (payment.getPayType().equals("Salary") && (payment.getEmployee().getEmpId() == employee1.getEmpId())) {
				if (total1 != payment.getPayAmount()) {
					test = false;
				}
			}
			
			if (payment.getPayType().equals("Salary") && (payment.getEmployee().getEmpId() == employee2.getEmpId())) {
				
				if (total2 != payment.getPayAmount()) {
					test = false;
				}
			}
			
			
		}
		
		Assert.assertTrue("Il test non è stato eseguito correttamente", test);
		
	}
	
	@Test
	public void validateFunctionalityTest(){
		
		UnionTable union1 = new UnionTable("Union1", 0.2);
		UnionTable union2 = new UnionTable("Union2", 0.2);
		
		unionDao.add(union1);
		unionDao.add(union2);
		
		int id1 = adminController.add("hourly", "user1", "Viola", "Via Via", "", union1, 12, 0, 0, "user1");
		
		UnionTable unionRetrieved1 = adminController.findUnionByName("Union1");
		UnionTable unionRetrieved2 = adminController.findUnionByName("Union2");
		
		boolean test = false;
		
		if (adminController.validate(unionRetrieved1, id1)) {
			
			test = true;
			
		}
		
		if (!adminController.validate(unionRetrieved2, id1)) {
			
			test = true;
			
		}
		
		Assert.assertTrue("Union not matching.", test);
		
	}

}
