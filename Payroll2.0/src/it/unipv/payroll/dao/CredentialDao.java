package it.unipv.payroll.dao;

import it.unipv.payroll.model.Credentials;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CredentialDao {
	
	@PersistenceContext
	EntityManager em;
	
	public void add(Credentials credential) {
		em.persist(credential);
	}
	
	public void update(Credentials credential) {
		em.merge(credential);
	}
	
	public void updatePassword(int empID, String password){
		
		em.createQuery("UPDATE Credentials SET password = '"+password+"' where EmpId = "+empID).executeUpdate();

	}
	
	public Credentials findCredentialByEmployee(int EmpId) {
		Credentials credentials = null;
		try {
			credentials = em.createQuery("select p from Credentials p where EmpId = " + EmpId,Credentials.class).getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return credentials;
	}

	public String isValidUser(int empId, String password) {
		System.out.println("TESTING USER: "+empId+" With password: "+password);
		String result;
		String query = "select p from Credentials p where EmpId = "+empId+" and password = '"+password+"'";
		Credentials credential;
		try {
			credential = em.createQuery(query, Credentials.class).getSingleResult();
		} catch (Exception e) {
			//This means no hit into database.
			System.out.println("No user in the database");
			return "nouser";
		}
		if (credential.isAdmin()){
			result = "admin";
		} else {
			result = "user";
		}
		System.out.println("The result of query is: "+result);
		return result;
	}

}
