package it.unipv.payroll.dao;

import it.unipv.payroll.model.UnionTable;

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
	
	public UnionTable findUnionByName(String name) {
		UnionTable union = null;
		String query = "select p from UnionTable p where name = '"+name+"'";
		System.out.println("The query is: "+query);
		try {
			union = em.createQuery(query, UnionTable.class).getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Union not found");
		}
		
		return union;
	}
}
