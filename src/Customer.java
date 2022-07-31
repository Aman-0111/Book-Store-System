import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.table.DefaultTableModel;

public class Customer extends Person {
	//Customer Attributes
	private String role = "Customer";
	private ArrayList<Book> basket = new ArrayList<Book>();
	
	//Customer Constructor
	public Customer (int userID, String username, String surname, int house_no, String postcode, String city) {
		super(userID,username,surname,house_no,postcode,city);
	}
	
	//Getter Method
	public String getRole() {
		return this.role;
	}
	//Clears Basket
	public void clearBasket() {
		basket.clear();
	}
	
	//Fills basket with book objects from table
	public void setBasket(DefaultTableModel dtmcheck) {
		//For Loop is used to get all data in each row and append it to the list
		Object[] rowdata = new Object[10];
		for (int i = 0;i<dtmcheck.getRowCount();i++) {
			for (int c = 0; c<dtmcheck.getColumnCount();c++) {
				rowdata[c] = dtmcheck.getValueAt(i, c);
			}
			//For each row the book type is checked to instantiate the correct class 
			if (rowdata[1].toString().equals("paperback")) {
				Paperback book = new Paperback(rowdata[0].toString(),rowdata[2].toString(),rowdata[3].toString(),rowdata[4].toString(),rowdata[5].toString(),Double.parseDouble(rowdata[6].toString()),Integer.parseInt(rowdata[7].toString()),Integer.parseInt(rowdata[8].toString()),rowdata[9].toString());
				basket.add(book);
			} else if (rowdata[1].toString().equals("audiobook")) {
				Audio book = new Audio(rowdata[0].toString(),rowdata[2].toString(),rowdata[3].toString(),rowdata[4].toString(),rowdata[5].toString(),Double.parseDouble(rowdata[6].toString()),Integer.parseInt(rowdata[7].toString()),Float.parseFloat(rowdata[8].toString()),Format_A.valueOf(rowdata[9].toString()));
				basket.add(book);
			} else {
				Ebook book = new Ebook(rowdata[0].toString(),rowdata[2].toString(),rowdata[3].toString(),rowdata[4].toString(),rowdata[5].toString(),Double.parseDouble(rowdata[6].toString()),Integer.parseInt(rowdata[7].toString()),Integer.parseInt(rowdata[8].toString()),Format_E.valueOf(rowdata[9].toString()));
				basket.add(book);
			}
		}
	}
	
	//Removes quantity from stock file when book is bought
	public void removeStock() throws IOException {
		//Open the stock file and creates a string buffer to store the contents of the file
		File BookFile = new File("Stock.txt");
		Scanner BookScanner = new Scanner(BookFile);
		StringBuffer buffer = new StringBuffer();
		//Array lists are user to store any lines that are changed
		ArrayList<String> updatedlines = new ArrayList<String>();
		ArrayList<String> oldlines = new ArrayList<String>();
		while(BookScanner.hasNextLine()) {
			//Line in file is obtained, added to string buffer and turned into an array
			String line = BookScanner.nextLine();
			String[] array = line.split(", ");
			buffer.append(line+System.lineSeparator());
			//Checks if a book in the basket is equal to the book stored on the line
			for (Book book : basket) {
				//If book is found minus the quantity bought from total quantity
				if (array[0].equals(book.getIsbn())) {
					int quantity = Integer.parseInt(array[7]) - book.getQuantity();
					//Adds unchanged and changed lines to the arraylists
					oldlines.add(line);
					updatedlines.add(line.replace(", "+Integer.parseInt(array[7])+", ", ", "+quantity+", "));
				}
			}
		}
		BookScanner.close();
		//Turns the string buffer into a string and turns ArrayLists into arrays
		String fileContents = buffer.toString();
		String[] Newlines = updatedlines.toArray(new String[0]);
		String[] Oldlines = oldlines.toArray(new String[0]);
		//Loops through arrays to replace each unchanged line in the fileContents string to the changed line
		for (int i = 0; i<Newlines.length;i++) {
			fileContents = fileContents.replaceAll(Oldlines[i], Newlines[i]);
		}
		//FileContents are rewritten into the file with the new data
		FileWriter writer = new FileWriter("Stock.txt");
		writer.append(fileContents);
	    writer.close();
	}
	
	//Updates the activity logs when books are purchased
	public void purchaseActivity(String payment) throws IOException {
		//Date Format and todays date are obtained
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
		Date date = new Date();
		//Activity Log file is opened alongside a string buffer
		File LogFile = new File("ActivityLog.txt");
		Scanner LogScanner = new Scanner(LogFile);
		StringBuffer buffer = new StringBuffer();
		//fileContents are stored in the string buffer
		while(LogScanner.hasNextLine()) {
			buffer.append(LogScanner.nextLine()+System.lineSeparator());
		}
		LogScanner.close();
		String fileContents = buffer.toString();
		//The Books purchased are added to the activity log first and then the file contents are added after
		FileWriter writer = new FileWriter("ActivityLog.txt");
		for (Book book:basket) {
			writer.append(getUserID()+", "+getPostcode()+", "+book.getIsbn()+", "+book.getPrice()+", "+book.getQuantity()+", purchased, "+payment+", "+(formatter.format(date))+System.lineSeparator());
		}
		writer.append(fileContents);
		writer.close();
	}
	
	//Updates the activity logs when purchase is cancelled
	//Method almost identical to the one above
	public void cancelActivity() throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		Date date = new Date();
		File LogFile = new File("ActivityLog.txt");
		Scanner LogScanner = new Scanner(LogFile);
		StringBuffer buffer = new StringBuffer();
		while(LogScanner.hasNextLine()) {
			buffer.append(LogScanner.nextLine()+System.lineSeparator());
		}
		LogScanner.close();
		String fileContents = buffer.toString();
		FileWriter writer = new FileWriter("ActivityLog.txt");
		for (Book book:basket) {
			//The line appended for each book is the only difference
			writer.append(getUserID()+", "+getPostcode()+", "+book.getIsbn()+", "+book.getPrice()+", "+book.getQuantity()+", cancelled, , "+(formatter.format(date))+System.lineSeparator());
		}
		writer.append(fileContents);
		writer.close();
	}
	
	//Checks the users 6 digit card number
	public boolean check6Digit(String digits) {
		//First checks length
		if (digits.length() == 6) {
			//Then checks if its an integer
			try { 
		        Integer.parseInt(digits);
		    } catch(NumberFormatException e) { 
		        return false; 
		    } catch(NullPointerException e) {
		        return false;
		    }
			return true;
		} else {
			return false;
		}
		
	}
	
	//Checks the users 6 digit card number
	//Same as 6 digit checker except for what length is checked
	public boolean check3Digit(String digits) {
		if (digits.length() == 3) {
			try { 
		        Integer.parseInt(digits);
		    } catch(NumberFormatException e) { 
		        return false; 
		    } catch(NullPointerException e) {
		        return false;
		    }
			return true;
		} else {
			return false;
		}
		
	}
	
	//Checks if valid email address is entered
	public boolean checkPayPal(String email) {
		//Uses the pattern entered and matches it to the email entered
		Pattern p = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
		Matcher m = p.matcher(email);
		//Checks if the email matched the pattern
		if (m.matches()) {
			return true;
		}
		return false;
	}
	
}
