package it.unipv.payroll.test;

import it.unipv.payroll.dao.CleanerDao;
import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.SalesRDao;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FlatSalaryEmployee;
import it.unipv.payroll.model.SalesReceipt;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class FlatSalaryTest extends ArquillianTest{
	
	@Inject
	EmployeeDao e_dao;
	
	@Inject
	CleanerDao cl_dao;
	
	@Inject
	SalesRDao sr_dao;
	
	@Before
	public void cleanup() {
		
		cl_dao.cleanAll();
	
	}
	
	@Test
	public void testFlatEmployee(){
		
		Employee flatEmployee1 = new FlatSalaryEmployee("Rocco","Papaleo",12,0.2);
		
		Employee flatEmployee2 = new FlatSalaryEmployee("Dado","Papaleo",2,0.4);
		
		Employee flatEmployee3 = new FlatSalaryEmployee("Fat","Papaleo",16,0.1);
		
		e_dao.add(flatEmployee1);
		e_dao.add(flatEmployee2);
		e_dao.add(flatEmployee3);
		
		List<Employee> employees = e_dao.findAll();
		
		boolean test = false;
		
		for (Employee employee2 : employees) {
			if (employee2.getName().equals("Rocco") && employee2.getSurname().equals("Papaleo")) {
				test=true;
			}
		}
		
		Assert.assertTrue("L' oggetto non è stato aggiunto", test);
		
	}
	
	@Test
	public void testSalesReceipts(){
		
		FlatSalaryEmployee flatEmployee1 = new FlatSalaryEmployee("Marco","Tranello",12,0.2);
		flatEmployee1.setIBAN("MAMMAPUTTANA");
		FlatSalaryEmployee flatEmployee2 = new FlatSalaryEmployee("Trivella","Perforante",2,0.4);
		
		FlatSalaryEmployee flatEmployee3 = new FlatSalaryEmployee("Mazinga","Z",16,0.1);
		
		e_dao.add(flatEmployee1);
		e_dao.add(flatEmployee2);
		e_dao.add(flatEmployee3);
		
		SalesReceipt salesReceipt1 = new SalesReceipt(new Date(1000000000), 23);
		SalesReceipt salesReceipt2 = new SalesReceipt(new Date(100001000), 4000);
		SalesReceipt salesReceipt3 = new SalesReceipt(new Date(1021000000), 234);
		
		salesReceipt1.setFlat_employee(flatEmployee1);
		salesReceipt2.setFlat_employee(flatEmployee1);
		salesReceipt3.setFlat_employee(flatEmployee2);
		
		sr_dao.add(salesReceipt1);
		sr_dao.add(salesReceipt2);
		sr_dao.add(salesReceipt3);
		
		
		
		List<SalesReceipt> receipts = sr_dao.findReceiptsByEmployee(flatEmployee1.getEmpId());
		
		Employee testEmployee = e_dao.findEmployeeById(receipts.get(0).getFlat_employee().getEmpId());
		
		boolean test = false;
		
		if (testEmployee.getName().equals("Marco") && testEmployee.getSurname().equals("Tranello")) {
			test = true;
		}
		
		Assert.assertTrue("L' oggetto non è presente", test);
		
	}

}
