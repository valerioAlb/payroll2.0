package it.unipv.payroll.test;

import it.unipv.payroll.dao.CleanerDao;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UnionTest extends ArquillianTest{
	
	@Inject
	CleanerDao cleanerDao;
	
	
	@Before
	public void cleanup() {
		
		cleanerDao.cleanAll();
	
	}
	
	@Test
	public void unionTest(){
		
	}

}
