package it.unipv.payroll.controller;

import it.unipv.payroll.dao.CredentialDao;
import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.model.Employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class LoginController {
	
	@Inject
	CredentialDao credentialDao;
	
	@Inject
	EmployeeDao employeeDao;
	
	
	public String validate(int empId, String password){
		
		String result = credentialDao.isValidUser(empId, password);
		if (result.equals("user")) {
			Employee employee = employeeDao.findEmployeeById(empId);
			if (employee.getEmpType().equals("HRE")) {
				result = "HREuser";
			} else {
				result = "FLEuser";
			}
		}
		System.out.println("The selected employee is of type: "+result);
		return result;
		
	}

}
