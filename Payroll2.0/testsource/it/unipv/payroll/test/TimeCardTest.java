package it.unipv.payroll.test;

import it.unipv.payroll.dao.CleanerDao;
import it.unipv.payroll.dao.EmployeeDao;
import it.unipv.payroll.dao.TimeCardDao;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.HourlyEmployee;
import it.unipv.payroll.model.TimeCard;

import java.sql.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TimeCardTest extends ArquillianTest {
	
	@Inject
	EmployeeDao e_dao;
	
	@Inject
	TimeCardDao time_dao;
	
	@Inject
	CleanerDao cl_dao;
	
	@Before
	public void cleanup() {
		
		//cl_dao.cleanAll();
		
	}
	
	@Test
	public void testHourlyTimeCard(){
		
		
		HourlyEmployee hourlyEmployee1 = new HourlyEmployee("Dao","Ambiguous",12);
		HourlyEmployee hourlyEmployee2 = new HourlyEmployee("Gianni","Barbabietola",32);
		
		e_dao.add(hourlyEmployee1);
		e_dao.add(hourlyEmployee2);
		
		TimeCard timeCard1 = new TimeCard(new Date(2000), 8);
		TimeCard timeCard2 = new TimeCard(new Date(2000), 5);
		TimeCard timeCard3 = new TimeCard(new Date(2000), 10);
		
		timeCard1.setHourly_employee(hourlyEmployee1);
		timeCard2.setHourly_employee(hourlyEmployee1);
		timeCard3.setHourly_employee(hourlyEmployee2);
		
		time_dao.add(timeCard1);
		time_dao.add(timeCard2);
		time_dao.add(timeCard3);
		
		List<TimeCard> timeCards = time_dao.findTimeCardsByEmployee(hourlyEmployee1.getEmpId());
		
		Employee testEmployee = e_dao.findEmployeeById(timeCards.get(0).getHourly_employee().getEmpId());
		
		boolean test = false;
		
		if (testEmployee.getName().equals("Dao") && testEmployee.getSurname().equals("Ambiguous")) {
			test = true;
		}
		
		Assert.assertTrue("L' oggetto non è presente", test);
		
		

	}
	
	

}
