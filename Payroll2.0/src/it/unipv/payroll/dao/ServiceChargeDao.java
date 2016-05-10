package it.unipv.payroll.dao;

import it.unipv.payroll.model.ServiceCharge;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class ServiceChargeDao {
	
	@PersistenceContext
	EntityManager em;
	
	public void add(ServiceCharge charge) {
		em.persist(charge);
	}
	
	public List<ServiceCharge> findAll() {
		List<ServiceCharge> charges = em.createQuery("select p from ServiceCharge p", ServiceCharge.class).getResultList();
		return charges;
	}
	
	public List<ServiceCharge> findByDate(String initDate, String finDate) {
		List<ServiceCharge> charges = em.createQuery("select p from ServiceCharge WHERE date", ServiceCharge.class).getResultList();
		return charges;
	}
	
	
	public void cleanTable() {
		em.createQuery("DELETE FROM ServiceCharge").executeUpdate();
		
	}

}
