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
	
	public void update(ServiceCharge charge) {
		em.merge(charge);
	}
	
	public List<ServiceCharge> findAll() {
		List<ServiceCharge> charges = em.createQuery("select p from ServiceCharge p", ServiceCharge.class).getResultList();
		return charges;
	}
	
	public List<ServiceCharge> findByDate(String initDate, String finDate) {
		String query ="select p from ServiceCharge p WHERE date BETWEEN '"+initDate+"' AND '"+finDate+"'";
		List<ServiceCharge> charges = em.createQuery(query, ServiceCharge.class).getResultList();
		return charges;
	}

	public List<ServiceCharge> findChargesById(int empID) {
		String query ="select p from ServiceCharge p WHERE EmpId = "+empID;
		List<ServiceCharge> charges = em.createQuery(query, ServiceCharge.class).getResultList();
		return charges;
	}

}
