package it.unipv.payroll.controller;

import java.util.List;

import it.unipv.payroll.dao.CleanerDao;
import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.UnionDao;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FlatSalaryEmployee;
import it.unipv.payroll.model.HourlyEmployee;
import it.unipv.payroll.model.UnionTable;

import javax.ejb.Stateless;
import javax.inject.Inject;


@Stateless
public class EmployeeController {
	
	@Inject 
	EmployeeDao e_dao;
	
	@Inject 
	UnionDao u_dao;
	
	@Inject 
	CleanerDao cl_dao;
	
	@Inject
	HourlyEmployee hourly;
	
	@Inject
	FlatSalaryEmployee flat;

	public void add(String type, String name, String surname, String postalAddress,
			String iBAN, UnionTable union, float hourlySalary,
			double fixedSalary, double commissionRate) {
		if (type.equals("hourly")) {
			
			hourly.setName(name);
			hourly.setSurname(surname);
			hourly.setHourlySalary(hourlySalary);
			hourly.setPostalAddress(postalAddress);
			hourly.setIBAN(iBAN);
			hourly.setUnion(union);
			
			e_dao.update(hourly);
			
		} else if (type.equals("flat")) {
			
			flat.setName(name);
			flat.setSurname(surname);
			flat.setPostalAddress(postalAddress);
			flat.setIBAN(iBAN);
			flat.setUnion(union);
			flat.setFixedSalary(fixedSalary);
			flat.setCommissionRate(commissionRate);
			
			e_dao.update(flat);
			
		}
		
	}

	public void deleteEmployee(int empID) {
		
		cl_dao.removeEmployee(empID);
		
	}

	public void modifyEployee(Employee employee) {
		System.out.println("ADDRESS "+employee.getPostalAddress());
		e_dao.update(employee);
		
	}
	
	public List<Employee> findAll(){
		return e_dao.findAll();
	}
	
	public UnionTable findUnionByName(String name) {
		
		return u_dao.findUnionByName(name);
	}

	public HourlyEmployee findHourly(int empId) {
		HourlyEmployee hourlyEmployee = e_dao.findHourlyEmployeeById(empId);
		return hourlyEmployee;
		
	}
	
	public FlatSalaryEmployee findFlat(int empId) {
		FlatSalaryEmployee flaEmployee = e_dao.findFlatEmployeeById(empId);
		return flaEmployee;
		
	}
	
	

}
