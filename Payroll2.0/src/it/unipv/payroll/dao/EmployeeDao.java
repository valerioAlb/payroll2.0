package it.unipv.payroll.dao;

import it.unipv.payroll.model.Employee;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EmployeeDao {
	
	@PersistenceContext
	EntityManager em;
	
	public void add(Employee employee) {
		em.persist(employee);
	}
	
	public List<Employee> findAll() {
		List<Employee> employees = em.createQuery("select p from Employee p", Employee.class).getResultList();
		return employees;
	}

	public void cleanTable() {
		em.createQuery("DELETE FROM Employee").executeUpdate();
		
	}
	
	public Employee findEmployeeById(int EmpId) {
		Employee employee = em.createQuery("select p from Employee p where EmpId = "+EmpId, Employee.class).getSingleResult();
		return employee;
	}
	

}
