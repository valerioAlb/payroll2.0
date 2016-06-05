package it.unipv.payroll.test;

import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.model.Employee;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class HelloTest extends ArquillianTest {
	
	@Inject
	EmployeeDao e_dao;
	
	@Test
	public void testAdddingEmployee(){
		
		Employee employee = new Employee("Valerio","Albini");
		
		e_dao.add(employee);
		
		List<Employee> employees = e_dao.findAll();
		
		boolean test = false;
		
		for (Employee employee2 : employees) {
			if (employee2.getName().equals("Valerio") && employee2.getSurname().equals("Albini")) {
				test=true;
			}
		}
		
		Assert.assertTrue("L' oggetto non è stato aggiunto", test);
		
	}

	

}
