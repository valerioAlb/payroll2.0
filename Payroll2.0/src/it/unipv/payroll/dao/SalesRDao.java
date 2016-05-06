package it.unipv.payroll.dao;

import it.unipv.payroll.model.SalesReceipt;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SalesRDao {
	
	@PersistenceContext
	EntityManager em;
	
	public void add(SalesReceipt receipt) {
		em.persist(receipt);
	}
	
	public List<SalesReceipt> findAll() {
		List<SalesReceipt> receipts = em.createQuery("select p from SalesReceipt p", SalesReceipt.class).getResultList();
		return receipts;
	}
	
	public List<SalesReceipt> findReceiptsByEmployee(int EmpId) {
		List<SalesReceipt> receipts = em.createQuery("select p from SalesReceipt p where EmpId = "+EmpId, SalesReceipt.class).getResultList();
		return receipts;
	}
	
	

	public void cleanTable() {
		
		
		em.createQuery("DELETE FROM SalesReceipt").executeUpdate();
		
		
	}

}
