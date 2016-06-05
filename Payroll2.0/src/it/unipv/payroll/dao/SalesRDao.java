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
	
	public void update(SalesReceipt receipt) {
		em.merge(receipt);
	}
	
	public List<SalesReceipt> findReceiptsByEmployee(int EmpId) {
		List<SalesReceipt> receipts = em.createQuery("select p from SalesReceipt p where EmpId = "+EmpId, SalesReceipt.class).getResultList();
		return receipts;
	}
	

	public List<SalesReceipt> findReceiptsByEmployeeAndByDate(int empId,String initDate, String finDate) {
		
		String query = "select p from SalesReceipt p where EmpId = "+empId+ " and ( date BETWEEN '"+initDate+"' AND '"+finDate+"')";
		List<SalesReceipt> receipts = em.createQuery(query, SalesReceipt.class).getResultList();
		
		return receipts;
	}

}
