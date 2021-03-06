package it.unipv.payroll.dao;

import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FlatSalaryEmployee;
import it.unipv.payroll.model.HourlyEmployee;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EmployeeDao {
	
	@PersistenceContext
	EntityManager em;
	
	public void add(Employee employee) {
		em.persist(employee);;
	}
	
	public int update(Employee employee) {
		employee = em.merge(employee);
		return employee.getEmpId();
	}
	
	public List<Employee> findAll() {
		List<Employee> employees = em.createQuery("select p from Employee p where name <> 'ADMIN'", Employee.class).getResultList();
		return employees;
	}
	
	public List<FlatSalaryEmployee> findAllFlat() {
		List<FlatSalaryEmployee> employees = em.createQuery("select p from FlatSalaryEmployee p", FlatSalaryEmployee.class).getResultList();
		return employees;
	}
	
	public List<HourlyEmployee> findAllHourly() {
		List<HourlyEmployee> employees = em.createQuery("select p from HourlyEmployee p", HourlyEmployee.class).getResultList();
		return employees;
	}
	
	public void setLastPayment(int payId,int EmpId) {
		em.createQuery("UPDATE Employee set lastPaymentId = "+payId+" WHERE EmpId = "+EmpId).executeUpdate();
	}
	
	public Employee findEmployeeById(int EmpId) {
		Employee employee = em.createQuery("select p from Employee p where EmpId = "+EmpId, Employee.class).getSingleResult();
		return employee;
	}
	
	public HourlyEmployee findHourlyEmployeeById(int EmpId) {
		HourlyEmployee employee = em.createQuery("select p from HourlyEmployee p where EmpId = "+EmpId, HourlyEmployee.class).getSingleResult();
		return employee;
	}
	
	public FlatSalaryEmployee findFlatEmployeeById(int EmpId) {
		FlatSalaryEmployee employee = em.createQuery("select p from FlatSalaryEmployee p where EmpId = "+EmpId, FlatSalaryEmployee.class).getSingleResult();
		return employee;
	}

	
	
}
