package application;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ReportBuilder {
	/*
	 * TODO:
	 * 		Figure out if this class just takes a report class and work on it,
	 * 		or do something else.
	 *		Create the necessary exception infrastructure
	 */
	
	

	public String reportTimeStamp(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
		String dateStamp = sdf.format(date);
		
		return dateStamp;
	}

}
