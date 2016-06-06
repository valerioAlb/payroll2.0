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
import it.unipv.payroll.model.UnionTable;

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
	
	@Test
	public void fullfillTable(){
		
		
		UnionTable union1 = new UnionTable("DetroitSPA", 0.5);
		UnionTable union2 = new UnionTable("FifolaSPA", 0.2);
		
		u_dao.add(union1);
		u_dao.add(union2);
		
		HourlyEmployee employee1 = new HourlyEmployee("Marco", "Scorpion", 23);
		employee1.setIBAN("IT32543657367965");
		employee1.setUnion(union1);
		e_dao.add(employee1);
				
		HourlyEmployee employee2 = new HourlyEmployee("Annalisa", "Joe", 30);
		employee2.setPostalAddress("Via Dapavia");
		employee2.setUnion(union2);
		e_dao.add(employee2);
		
		HourlyEmployee employee3 = new HourlyEmployee("Putin", "Pippolotto", 27);
		employee3.setPostalAddress("Via Vai");
		employee3.setUnion(union1);
		e_dao.add(employee3);
		
		FlatSalaryEmployee flatSalaryEmployee1 = new FlatSalaryEmployee("Feather", "Snowdancer" , 1000, 0.2);
		flatSalaryEmployee1.setIBAN("IT2643568756902475");
		flatSalaryEmployee1.setUnion(union2);
		e_dao.add(flatSalaryEmployee1);
		
		FlatSalaryEmployee flatSalaryEmployee2 = new FlatSalaryEmployee("Calogero", "Scevola" , 2000, 0.3);
		flatSalaryEmployee1.setUnion(union2);
		e_dao.add(flatSalaryEmployee2);
		
		FlatSalaryEmployee flatSalaryEmployee3 = new FlatSalaryEmployee("Cino", "Furgon" , 1500, 0.25);
		flatSalaryEmployee3.setPostalAddress("Via R");
		flatSalaryEmployee1.setUnion(union1);
		e_dao.add(flatSalaryEmployee3);
		
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
		
		Credentials credential6 = new Credentials(employee3, "123456", false);
		credentialDao.add(credential6);
		
		Credentials credential7 = new Credentials(flatSalaryEmployee3, "gullo", false);
		credentialDao.add(credential7);
		
		
		Assert.assertTrue("", true);
		
		
	}

}
