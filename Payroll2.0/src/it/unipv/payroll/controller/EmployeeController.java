package it.unipv.payroll.controller;

import it.unipv.payroll.dao.CleanerDao;
import it.unipv.payroll.dao.CredentialDao;
import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.ServiceChargeDao;
import it.unipv.payroll.dao.UnionDao;
import it.unipv.payroll.logic.CalendarService;
import it.unipv.payroll.logic.PayFlat;
import it.unipv.payroll.logic.PayHourly;
import it.unipv.payroll.model.Credentials;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FlatSalaryEmployee;
import it.unipv.payroll.model.HourlyEmployee;
import it.unipv.payroll.model.ServiceCharge;
import it.unipv.payroll.model.UnionTable;

import java.util.Date;
import java.util.List;

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
	CredentialDao credentialDao;
	
	@Inject
	Credentials credentials;
	
	@Inject
	FlatSalaryEmployee flat;
	
	@Inject
	ServiceChargeDao chargeDao;
	
	@Inject
	ServiceCharge serviceCharge;
	
	@Inject
	CalendarService calendarService;
	
	@Inject
	PayHourly payHourly;
	
	@Inject
	PayFlat payFlat;

	public void add(String type, String name, String surname, String postalAddress,
			String iBAN, UnionTable union, float hourlySalary,
			double fixedSalary, double commissionRate,String password) {
		
		int id;
		credentials.setAdmin(false);
		credentials.setPassword(password);
		
		if (type.equals("hourly")) {
			
			hourly.setName(name);
			hourly.setSurname(surname);
			hourly.setHourlySalary(hourlySalary);
			hourly.setPostalAddress(postalAddress);
			hourly.setIBAN(iBAN);
			hourly.setUnion(union);
			
			id = e_dao.update(hourly);
			
			
			
		} else {
			
			flat.setName(name);
			flat.setSurname(surname);
			flat.setPostalAddress(postalAddress);
			flat.setIBAN(iBAN);
			flat.setUnion(union);
			flat.setFixedSalary(fixedSalary);
			flat.setCommissionRate(commissionRate);
			
			id = e_dao.update(flat);
			
			
		}
		System.out.println("The id of the last employee inserted is: "+id);
		credentials.setEmployee(e_dao.findEmployeeById(id));
		credentialDao.update(credentials);
		
	}

	public void deleteEmployee(int empID) {
		
		cl_dao.removeEmployee(empID);
		
	}

	public void modifyEployee(Employee employee,String password) {
		System.out.println("ADDRESS "+employee.getPostalAddress());
		
		e_dao.update(employee);
		credentialDao.updatePassword(employee.getEmpId(), password);
		
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

	public void postServiceCharge(int empID, double amount, Date date,UnionTable union) {
		
		serviceCharge.setUnion(union);
		serviceCharge.setAmount(amount);
		serviceCharge.setEmpId(empID);
		serviceCharge.setDate(calendarService.getSqlDate(date));
		
		chargeDao.update(serviceCharge);
		System.out.println("Union charge posted: "+amount+" "+union.getName());
		
		
	}

	public boolean validate(UnionTable union, int empID) {
		
			
		Employee employee = e_dao.findEmployeeById(empID);
		if (employee.getUnion().getName().equals(union.getName())) {
			return true;
		}else {
			return false;
		}
		
		
	}

	public void runPayroll() {
		payHourly.pay();
		payFlat.pay();
		
	}
	
	

}
