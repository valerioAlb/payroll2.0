package it.unipv.payroll.test;

import it.unipv.payroll.dao.CleanerDao;
import it.unipv.payroll.dao.CredentialDao;
import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.model.Credentials;
import it.unipv.payroll.model.HourlyEmployee;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CredentialTest extends ArquillianTest{
	
	@Inject
	CleanerDao cleanerDao;
	
	@Inject
	CredentialDao credentialDao;
	
	@Inject
	EmployeeDao e_dao;
	
	@Before
	public void cleanup() {
		
		cleanerDao.cleanAll();
	
	}
	
	@Test
	public void credentialTest(){
		
		HourlyEmployee employee1 = new HourlyEmployee("Marco", "Gionni", 234);
		e_dao.add(employee1);
		
		HourlyEmployee employee2 = new HourlyEmployee("Valerio", "Joe", 234);
		e_dao.add(employee2);
		
		Credentials credential1 = new Credentials(employee1, "password", false);
		credentialDao.add(credential1);
		
		Credentials credential2 = new Credentials(employee2, "qwerty", true);
		credentialDao.add(credential2);
		
		boolean test = false;
		
		String result = credentialDao.isValidUser(employee1.getEmpId(),"password");
		
		if (result.equals("user")) {
			test = true;
		}
		
		result = credentialDao.isValidUser(employee2.getEmpId(),"qwerty");
		
		if (result.equals("admin")) {
			test = true;
		}
		
		result = credentialDao.isValidUser(employee2.getEmpId(),"qwertzzz");
		
		if (result.equals("nouser")) {
			test = true;
		}
		
		Assert.assertTrue("Credenziali non confermate", test);
		
		
	}

}
