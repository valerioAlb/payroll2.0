package it.unipv.payroll.dao;

import it.unipv.payroll.model.UnionTable;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UnionDao {

	@PersistenceContext
	EntityManager em;
	
	public void add(UnionTable union) {
		em.persist(union);
	}
	
	public List<UnionTable> findAll() {
		List<UnionTable> unions = em.createQuery("select p from UnionTable p", UnionTable.class).getResultList();
		return unions;
	}
	
	public void cleanTable() {
		em.createQuery("DELETE FROM UnionTable").executeUpdate();
		
	}
	
	public UnionTable findUnionByName(String name) {
		UnionTable union;
		String query = "select p from UnionTable p where name = '"+name+"'";
		System.out.println("The query is: "+query);
		union = em.createQuery(query, UnionTable.class).getSingleResult();
		
		return union;
	}
}
