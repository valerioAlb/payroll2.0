package it.unipv.payroll.dao;

import it.unipv.payroll.model.TimeCard;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TimeCardDao {
	
	@PersistenceContext
	EntityManager em;
	
	public void add(TimeCard timeCard) {
		em.persist(timeCard);
	}
	
	public void merge(TimeCard timeCard) {
		em.merge(timeCard);
	}
	
	public List<TimeCard> findAll() {
		List<TimeCard> timeCards = em.createQuery("select p from TimeCard p", TimeCard.class).getResultList();
		return timeCards;
	}
	
	public List<TimeCard> findTimeCardsByEmployee(int EmpId) {
		List<TimeCard> timeCards = em.createQuery("select p from TimeCard p where EmpId = "+EmpId, TimeCard.class).getResultList();
		return timeCards;
	}
	
	

	public void cleanTable() {
		em.createQuery("DELETE FROM TimeCard").executeUpdate();
		
	}

}
