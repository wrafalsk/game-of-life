package application;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;

public class ReportDriver {

	/* TODO:
	 * 		Create GUI
	 * 		Write functions to:
	 * 			- create a new file
	 * 			- delete a file
	 * 			- edit a previous file
	 * 		Create the necessary exception infrastructure
	 */
	
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	/*
	public static void main(String[] args) throws FileNotFoundException {
		
		Report r1 = new Report("JWR", "No comments");

		
		
		File file = new File("C:/Users/Rafalskw/workspace/BCR-Report-Builder/reports/bcr_report_test.csv");
		file.getParentFile().mkdirs();
	
		PrintWriter pw = new PrintWriter(file);
		
		pw.write(r1.getComments() + "," + r1.getInitials());
		pw.close();
		System.out.println("Success");
	}*/

}
