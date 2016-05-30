package it.unipv.payroll.test;

import it.unipv.payroll.dao.CleanerDao;
import it.unipv.payroll.dao.CredentialDao;
import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.PaymentDAO;
import it.unipv.payroll.dao.ServiceChargeDao;
import it.unipv.payroll.dao.TimeCardDao;
import it.unipv.payroll.dao.UnionDao;
import it.unipv.payroll.logic.PayHourly;
import it.unipv.payroll.model.Credentials;
import it.unipv.payroll.model.FlatSalaryEmployee;
import it.unipv.payroll.model.HourlyEmployee;
import it.unipv.payroll.model.ServiceCharge;
import it.unipv.payroll.model.TimeCard;
import it.unipv.payroll.model.UnionTable;

import java.sql.Date;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class EmployeeGeneratorTest extends ArquillianTest{
	
	@Inject
	CleanerDao cleanerDao;
	
	@Inject
	CredentialDao credentialDao;
	
	@Inject
	EmployeeDao e_dao;
	
	@Inject
	PaymentDAO paymentDAO;
	
	@Inject
	UnionDao u_dao;
	
	@Inject
	TimeCardDao time_dao;
	
	@Inject
	ServiceChargeDao sc_dao;
	
	@Inject 
	PayHourly payHourly;
	
	@Before
	public void cleanup() {
		
		cleanerDao.cleanAll();
	
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void fullfillTable(){
		
		
		UnionTable union1 = new UnionTable("CappellaSPA", 0.5);
		UnionTable union2 = new UnionTable("FifolaSPA", 0.2);
		
		u_dao.add(union1);
		u_dao.add(union2);
		
		HourlyEmployee employee1 = new HourlyEmployee("Marco", "Gionni", 234);
		employee1.setUnion(union1);
		e_dao.add(employee1);
				
		HourlyEmployee employee2 = new HourlyEmployee("Annalisa", "Joe", 234);
		employee2.setUnion(union2);
		e_dao.add(employee2);
		
		FlatSalaryEmployee flatSalaryEmployee1 = new FlatSalaryEmployee("Termosifone", "Cane" , 1000, 0.2);
		flatSalaryEmployee1.setUnion(union2);
		e_dao.add(flatSalaryEmployee1);
		
		FlatSalaryEmployee flatSalaryEmployee2 = new FlatSalaryEmployee("Calogero", "Crosta" , 2000, 0.3);
		flatSalaryEmployee1.setUnion(union2);
		e_dao.add(flatSalaryEmployee2);
		
		FlatSalaryEmployee admin = new FlatSalaryEmployee("ADMIN", "ADMIN" , 2000, 0.3);
		e_dao.add(admin);
		
		Credentials credential1 = new Credentials(employee1, "password", false);
		credentialDao.add(credential1);
		
		Credentials credential2 = new Credentials(employee2, "qwerty", false);
		credentialDao.add(credential2);
		
		Credentials credential3 = new Credentials(flatSalaryEmployee1, "password", false);
		credentialDao.add(credential3);
		
		Credentials credential4 = new Credentials(flatSalaryEmployee2, "qwerty", false);
		credentialDao.add(credential4);
		
		Credentials credential5 = new Credentials(admin, "admin", true);
		credentialDao.add(credential5);
		
		TimeCard timeCard1 = new TimeCard(new Date(2016-1900,05-1,23), 8);
		TimeCard timeCard2 = new TimeCard(new Date(2016-1900,05-1,24), 5);
		TimeCard timeCard3 = new TimeCard(new Date(2016-1900,05-1,23), 10);
		
		timeCard1.setHourly_employee(employee1);
		timeCard2.setHourly_employee(employee1);
		timeCard3.setHourly_employee(employee1);
		
		time_dao.add(timeCard1);
		time_dao.add(timeCard2);
		time_dao.add(timeCard3);
		
		ServiceCharge charge1 = new ServiceCharge(employee1.getEmpId(), 30, new Date(2016-1900,05-1,24));
		ServiceCharge charge2 = new ServiceCharge(employee1.getEmpId(), 24, new Date(2016-1900,05-1,23));
		
		charge1.setUnion(union1);
		charge2.setUnion(union1);
		
		sc_dao.add(charge1);
		sc_dao.add(charge2);
		
		payHourly.pay();
		
		Assert.assertTrue("Credenziali non confermate", true);
		
		
	}

}
