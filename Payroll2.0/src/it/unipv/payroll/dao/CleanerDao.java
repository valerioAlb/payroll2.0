package it.unipv.payroll.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CleanerDao {

	@PersistenceContext
	EntityManager em;
	
	
	public void cleanAll() {
		
		try {
			em.createQuery("DELETE FROM SalesReceipt").executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			em.createQuery("DELETE FROM Payment").executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			em.createQuery("DELETE FROM TimeCard").executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			em.createQuery("DELETE FROM ServiceCharge").executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			em.createQuery("DELETE FROM Credentials").executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			em.createQuery("DELETE FROM Employee").executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			em.createQuery("DELETE FROM UnionTable").executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	public void removeEmployee(int EmpId) {
		
		em.createQuery("DELETE FROM SalesReceipt WHERE EmpId="+EmpId).executeUpdate();
		em.createQuery("DELETE FROM TimeCard WHERE EmpId="+EmpId).executeUpdate();
		em.createQuery("DELETE FROM ServiceCharge WHERE EmpId="+EmpId).executeUpdate();
		em.createQuery("DELETE FROM Payment WHERE EmpId="+EmpId).executeUpdate();
		em.createQuery("DELETE FROM Credentials WHERE EmpId="+EmpId).executeUpdate();
		em.createQuery("DELETE FROM Employee WHERE EmpId="+EmpId).executeUpdate();
		
		
	}
	
}
