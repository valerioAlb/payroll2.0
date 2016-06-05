package it.unipv.payroll.test;

import it.unipv.payroll.dao.CleanerDao;
import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FlatSalaryEmployee;
import it.unipv.payroll.model.HourlyEmployee;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DeleteEmployeeTest extends ArquillianTest {
	
	@Inject
	CleanerDao cl_dao;
	
	@Inject
	EmployeeDao e_dao;
	
	@Before
	public void cleanup() {
		cl_dao.cleanAll();
	}
	
	@Test
	public void testDeleteEmployee(){
		
		Employee hourlyEmployee1 = new HourlyEmployee("Rocco","Papaleo",12);
		
		Employee hourlyEmployee2 = new HourlyEmployee("Magikarp","Pokè",2);
		
		Employee hourlyEmployee3 = new HourlyEmployee("Tavernello","Cartoncino",16);
		
		Employee flatEmployee1 = new FlatSalaryEmployee("Fra","Cappella",12,0.2);
		
		Employee flatEmployee2 = new FlatSalaryEmployee("Dado","Papaleo",2,0.4);
		
		e_dao.add(hourlyEmployee1);
		e_dao.add(hourlyEmployee2);
		e_dao.add(hourlyEmployee3);
		e_dao.add(flatEmployee1);
		e_dao.add(flatEmployee2);
		
		cl_dao.removeEmployee(hourlyEmployee3.getEmpId());
		
		
		List<Employee> employees = e_dao.findAll();
		
		boolean test = true;
		
		for (Employee employee2 : employees) {
			if (employee2.getEmpId() == hourlyEmployee3.getEmpId()) {
				test=false;
			}
		}
		
		Assert.assertTrue("L' oggetto non è stato rimosso", test);
		
	}
	
	

}
