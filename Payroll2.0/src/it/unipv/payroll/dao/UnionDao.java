package it.unipv.payroll.dao;

import it.unipv.payroll.model.Union;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UnionDao {

	@PersistenceContext
	EntityManager em;
	
	public void add(Union union) {
		em.persist(union);
	}
	
	public List<Union> findAll() {
		List<Union> unions = em.createQuery("select p from Union p", Union.class).getResultList();
		return unions;
	}
	
	public void cleanTable() {
		em.createQuery("DELETE FROM Union").executeUpdate();
		
	}
	
	public Union findUnionByName(String name) {
		Union union;
		try {
			union = em.createQuery("select p from UnionTable p where name = "+name, Union.class).getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("FUCK");
			return null;
		}
		return union;
	}
}
