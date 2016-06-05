package it.unipv.payroll.logic;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarService implements Serializable{
	
	
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Calendar calendar;
	
	private Date wrapDate(Calendar c){		
		@SuppressWarnings("deprecation")
		Date date = new Date(c.get(Calendar.YEAR)-1900,c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
		return date;
	}	
	
	
	public Date getSqlDate(java.util.Date date){
		
		Date sqlDate = new Date(date.getTime());
		return sqlDate;
		
	}
	
	public Date getToday(){
		calendar = new GregorianCalendar();
		Date date = new Date(0);
		date = wrapDate(calendar);
		return date;
	}
	
	public String getFormattedToday(){
		calendar = new GregorianCalendar();
		String formatted = calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+(calendar.get(Calendar.YEAR)-2000);
		return formatted;
	}

	public int getCurrentNumberDay() {
		calendar = new GregorianCalendar();
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	public boolean isFriday() {
		calendar = new GregorianCalendar();
		// check if the day is friday
		if (calendar.get(Calendar.DAY_OF_WEEK) == 6)
			return true;
		return false;
	}
	
	
	public boolean d1ChangeMounth() {
		calendar = new GregorianCalendar();
		int d_month = calendar.get(Calendar.MONTH);
		calendar.add(Calendar.DATE, 1); // set the next day
		int d1_month = calendar.get(Calendar.MONTH);

		if (d_month != d1_month)
			return true;
		return false;
	}

	public boolean d3ChangeMounth() {
		calendar = new GregorianCalendar();
		int d_month = calendar.get(Calendar.MONTH);
		calendar.add(Calendar.DATE, 3); // set the next day
		int d3_month = calendar.get(Calendar.MONTH);

		if (d_month != d3_month)
			return true;
		return false;
	}

	public boolean isWeekNumberEven() {
		calendar = new GregorianCalendar();
		int week_number = calendar.get(Calendar.WEEK_OF_YEAR);
		
		if(week_number%2 == 0)
			return true;
		return false;
	}

	public List<Date> lastWeekList(){
		calendar = new GregorianCalendar();
		List<Date> working_days = new ArrayList<Date>();
		for(int i=0;i<7;i++){
			working_days.add(wrapDate(calendar));
			calendar.add(Calendar.DATE, -1);
		}
		
		System.out.println(working_days.toString());
		
		return working_days;
	}

	public List<Date> last2WeeksList() {
		calendar = new GregorianCalendar();
		List<Date> working_days = new ArrayList<Date>();
		
		for(int i=0;i<14;i++){
			working_days.add(wrapDate(calendar));
			calendar.add(Calendar.DATE, -1);
		}
				
		return working_days;
	}
	
	// This method retrieves a list of Date elements
	// corresponding to all last month's working days
	public List<Date> lastMonthList(){
		
		// This is the list of working days which will be 
		// retrieved by the method
		List<Date> working_days = new ArrayList<Date>();
		
		calendar = new GregorianCalendar();
		
		// Get the current month number [0..12] => [January..December]
		int current_month = calendar.get(Calendar.MONTH);
		
		// Since we have set the current month, we will
		// decrease by one day the calendar date until
		// the calendar month is equal to the current_month
		// set before
		while(calendar.get(Calendar.MONTH) == current_month){
			
			int saturday = 7;
			int sunday = 1;
			int calendar_day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
			
			// We will add the Date element to the working_days
			// list only if it is not Saturday or Sunday
			if(calendar_day_of_week != saturday && calendar_day_of_week != sunday)
				working_days.add(wrapDate(calendar));
			
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		
		return working_days;
	}

}
