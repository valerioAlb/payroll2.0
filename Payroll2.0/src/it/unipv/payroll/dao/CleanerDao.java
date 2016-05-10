package it.unipv.payroll.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CleanerDao {

	@PersistenceContext
	EntityManager em;
	
	
	public void cleanAll() {
		
		em.createQuery("DELETE FROM SalesReceipt").executeUpdate();
		em.createQuery("DELETE FROM TimeCard").executeUpdate();
		em.createQuery("DELETE FROM Union").executeUpdate();
		em.createQuery("DELETE FROM Employee").executeUpdate();
		
	}
	
	public void removeEmployee(int EmpId) {
		
		em.createQuery("DELETE FROM SalesReceipt WHERE EmpId="+EmpId).executeUpdate();
		em.createQuery("DELETE FROM TimeCard WHERE EmpId="+EmpId).executeUpdate();
		em.createQuery("DELETE FROM Union WHERE EmpId="+EmpId).executeUpdate();
		em.createQuery("DELETE FROM Employee WHERE EmpId="+EmpId).executeUpdate();
		
	}
	
}
