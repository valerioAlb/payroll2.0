package it.unipv.payroll.test;

import java.sql.Date;
import java.util.List;

import it.unipv.payroll.dao.CleanerDao;
import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.ServiceChargeDao;
import it.unipv.payroll.dao.UnionDao;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FlatSalaryEmployee;
import it.unipv.payroll.model.HourlyEmployee;
import it.unipv.payroll.model.ServiceCharge;
import it.unipv.payroll.model.UnionTable;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(Arquillian.class)
public class ServiceChargeTest extends ArquillianTest{
	
	@Inject
	CleanerDao cl_dao;
	
	@Inject
	EmployeeDao e_dao;
	
	@Inject
	ServiceChargeDao sc_dao;
	
	@Inject
	UnionDao u_dao;
	
	@Before
	public void cleanup() {
		cl_dao.cleanAll();
	}
	
	@Test
	public void testUnion(){
		
		UnionTable union1 = new UnionTable("CappellaSPA", 0.5);
		UnionTable union2 = new UnionTable("FifolaSPA", 0.2);
		
		u_dao.add(union1);
		u_dao.add(union2);
		
		Employee flatEmployee1 = new FlatSalaryEmployee("Rocco","Papaleo",12,0.2);
		Employee flatEmployee2 = new FlatSalaryEmployee("Dado","Papaleo",2,0.4);
		Employee flatEmployee3 = new FlatSalaryEmployee("Fat","Papaleo",16,0.1);
		Employee hourlyEmployee1 = new HourlyEmployee("Fede","Papaleo",16);
		
		flatEmployee1.setUnion(union1);
		flatEmployee2.setUnion(union2);
		flatEmployee3.setUnion(union1);
		hourlyEmployee1.setUnion(union1);
		
		e_dao.add(flatEmployee1);
		e_dao.add(flatEmployee2);
		e_dao.add(flatEmployee3);
		e_dao.add(hourlyEmployee1);
		
		boolean test = false;
		
		List<Employee> employees = e_dao.findAll();
		
		
		for (Employee employee : employees) {
			
			if (employee.getUnion().getUnionId() == union1.getUnionId() || employee.getUnion().getUnionId() == union2.getUnionId()) {
				test = true;
			}
			
		}
		
		Assert.assertTrue("L' associazione union-employee non è stata creata", test);
	
	}
	
	@Test
	public void testServiceCharge(){
		
		UnionTable union1 = new UnionTable("CappellaSPA", 0.5);
		UnionTable union2 = new UnionTable("FifolaSPA", 0.2);
		
		u_dao.add(union1);
		u_dao.add(union2);
		
		Employee flatEmployee1 = new FlatSalaryEmployee("Rocco","Papaleo",12,0.2);
		Employee flatEmployee2 = new FlatSalaryEmployee("Dado","Papaleo",2,0.4);
		Employee flatEmployee3 = new FlatSalaryEmployee("Fat","Papaleo",16,0.1);
		Employee hourlyEmployee1 = new HourlyEmployee("Fede","Papaleo",16);
		
		flatEmployee1.setUnion(union1);
		flatEmployee2.setUnion(union2);
		flatEmployee3.setUnion(union1);
		hourlyEmployee1.setUnion(union1);
		
		e_dao.add(flatEmployee1);
		e_dao.add(flatEmployee2);
		e_dao.add(flatEmployee3);
		e_dao.add(hourlyEmployee1);
		
		ServiceCharge charge1 = new ServiceCharge(flatEmployee1.getEmpId(), 2000, new Date(1312400));
		ServiceCharge charge2 = new ServiceCharge(flatEmployee2.getEmpId(), 500, new Date(1334600));
		ServiceCharge charge3 = new ServiceCharge(hourlyEmployee1.getEmpId(), 1000, new Date(1323240));
		
		charge1.setUnion(union1);
		charge2.setUnion(union1);
		charge3.setUnion(union2);
		
		sc_dao.add(charge1);
		sc_dao.add(charge2);
		sc_dao.add(charge3);
		
		boolean test = false;
		
		List<Employee> employees = e_dao.findAll();
		List<ServiceCharge> charges = sc_dao.findAll();
		
		
		for (ServiceCharge serviceCharge : charges) {
			//Check on the correspondence betweeen the unionId and Id in the serviceCharge
			if (serviceCharge.getUnion().getUnionId() == union1.getUnionId()) {
				//Cycle on each employee
				for (Employee employee : employees) {
					//Check if the specified employee in the serviceCharge actually exists
					if (serviceCharge.getEmpId() == employee.getEmpId()) {
						//Check if the retrieved employee is part of the considered union
						if (employee.getUnion().getUnionId() == union1.getUnionId()) {
							test = true;
						}
					}
				}
			}
			
		}
		
		Assert.assertTrue("Le associazioni di serviceCharge non sono state create correttamente.", test);
		
	}
	
	
	

}
