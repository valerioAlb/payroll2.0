package it.unipv.payroll.test;

import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.HourlyEmployee;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class HourlyEmployeeTest extends ArquillianTest{
	
	
	
	@Inject
	EmployeeDao e_dao;
	
	@Before
	public void cleanup() {
		e_dao.cleanTable();
	}
	
	@Test
	public void testHourlyEmployee(){
		
		Employee hourlyEmployee1 = new HourlyEmployee("Rocco","Papaleo",12);
		
		Employee hourlyEmployee2 = new HourlyEmployee("Dado","Papaleo",2);
		
		Employee hourlyEmployee3 = new HourlyEmployee("Fede","Papaleo",16);
		
		e_dao.add(hourlyEmployee1);
		e_dao.add(hourlyEmployee2);
		e_dao.add(hourlyEmployee3);
		
		List<Employee> employees = e_dao.findAll();
		
		boolean test = false;
		
		for (Employee employee2 : employees) {
			if (employee2.getName().equals("Rocco") && employee2.getSurname().equals("Papaleo")) {
				test=true;
			}
		}
		
		Assert.assertTrue("L' oggetto non è stato aggiunto", test);
		
	}

}
