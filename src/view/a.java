package view;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class a {

	public static void main(String[] args) {
		Date date = new Date();   
		Calendar calendar = GregorianCalendar.getInstance(); 
		calendar.setTime(date);  
		int a = calendar.get(Calendar.HOUR_OF_DAY);
		
		System.out.println(a);
	}

}
