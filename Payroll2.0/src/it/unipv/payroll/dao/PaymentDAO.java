package it.unipv.payroll.dao;

import it.unipv.payroll.model.Payment;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PaymentDAO {
	
	@PersistenceContext
	EntityManager em;
	
	public void add(Payment payment) {
		em.merge(payment);
	}
	
	public List<Payment> findAll() {
		List<Payment> payments = em.createQuery("select p from Payment p", Payment.class).getResultList();
		return payments;
	}
	
	public List<Payment> findPaymentsByEmployee(int EmpId) {
		List<Payment> payments = em.createQuery("select p from Payment p where employee_EmpId = "+EmpId, Payment.class).getResultList();
		return payments;
	}
	
	public Payment findPaymentById(int id){
		
		Payment payment = em.createQuery("select p from Payment p where id = "+id, Payment.class).getSingleResult();
		return payment;
		
	}
	
	public void cleanTable() {
		em.createQuery("DELETE FROM Payment").executeUpdate();
		
	}

}
