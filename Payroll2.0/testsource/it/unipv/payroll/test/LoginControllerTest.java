package it.unipv.payroll.test;

import it.unipv.payroll.controller.AdminController;
import it.unipv.payroll.controller.LoginController;
import it.unipv.payroll.dao.CleanerDao;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class LoginControllerTest extends ArquillianTest{
	
	@Inject
	CleanerDao cleanerDao;
	
	@Inject
	LoginController loginController;
	
	@Inject
	AdminController adminController;
	
	
	@Before
	public void cleanup() {
		
		cleanerDao.cleanAll();
	
	}
	
	@Test
	public void validateFunctionalityTest(){
		
		int id1 = adminController.add("hourly", "user1", "Viola", "Via Via", "", null, 12, 0, 0, "user1");
		int id2 = adminController.add("flat", "user2", "Rosa", "", "132356768765342", null, 0, 1500, 0.2, "user2");
		
		boolean test = true;
		
		if (!loginController.validate(id1, "user1").equals("HREuser")) {
			test = false;
		}
		if (!loginController.validate(id2, "user2").equals("FLEuser")) {
			test = false;
		}
		Assert.assertTrue("User not matching the password", test);
		
	}

}
