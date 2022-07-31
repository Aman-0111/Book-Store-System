
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Admin extends Person {
	//Admin Attribute
	private String role = "Admin";
	
	//Admin constructor
	public Admin (int userID, String username, String surname, int house_no, String postcode, String city) {
		super(userID,username,surname,house_no,postcode,city);
	}
	
	//Getter Method
	public String getRole() {
		return this.role;
	}
	
	//Adds Book to the stock file
	public void addBook(String isbn,String type,String title,String lang,String genre,String date,String price,String quantity,String info1,String info2) throws IOException {
		//Boolean to check if the book exists already in stock file
		boolean bookExist = false;
		//Open the stock file and creates a string buffer to store the contents of the file
		File BookFile = new File("Stock.txt");
		Scanner BookScanner = new Scanner(BookFile);
		StringBuffer buffer = new StringBuffer();
		//Strings to store unchanged and changed lines if the book exists
		String oldline = "";
		String newline = "";
		while (BookScanner.hasNextLine()) {
			//Line in file is obtained, added to string buffer and turned into an array
			String line = BookScanner.nextLine();
			buffer.append(line+System.lineSeparator());
			String[] array = line.split(", ");
			//If Isbn is found then the boolean is changed
			if (array[0].equals(isbn)) {
				bookExist = true;
				//The new quantity is calculated placed in the changed line
				int new_quantity = Integer.parseInt(array[7]) + Integer.parseInt(quantity);
				oldline = line;
				newline = oldline;
				newline = newline.replaceAll(" "+array[7]+",", " "+String.valueOf(new_quantity)+",");
			}		
		}
		BookScanner.close();
		//If the book exists then the unchanged line is replaced and written to the stock file
		if (bookExist) {
			String fileContents = buffer.toString();
			fileContents = fileContents.replaceAll(oldline, newline);
			FileWriter writer = new FileWriter("Stock.txt");
			writer.append(fileContents);
		    writer.close();
		//If the book does not exist the book details are added to the end of the stock file
		} else {
			String fileContents = buffer.toString();
			FileWriter writer = new FileWriter("Stock.txt");
			writer.append(fileContents);
			writer.append(isbn+", "+type.toLowerCase()+", "+title+", "+lang+", "+genre+", "+date+", "+price+", "+quantity+", "+info1+", "+info2+System.lineSeparator());
		    writer.close();
		}
		
	}
	
	//Checks if isbn entered is valid
	public boolean checkIsbn(String Isbn) {
		//Checks length
		if (((Isbn.trim()).length()) != 8 ) {
			return false;
		} else {
			//Checks if it is an integer
			if (checkInt(Isbn)) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	//Checks if the date entered is in a valid format
	public boolean checkDate(String date) {
		//Creates date format
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
		//First checks if date matches the format
		if (date.matches("[0-3][0-9][-][0-1][0-9][-][0-9][0-9][0-9][0-9]")) {
			//Then checks if a valid day is entered
            if(!(date.substring(0,2).matches("[3][2-9]"))) {
            	//Then checks if a valid month is entered
            	if (!(date.substring(3,5).matches("[1][3-9]"))) {
            		//Then checks if the date can be parsed
            		try {
                        format.parse(date);
                        return true;
            		}
                    catch(ParseException e){
                        return false;
                    }
            	}
            }
        }
		return false;
	}
	
	//Checks entered price
	public boolean checkPrice(String price) {
		//First checks if the price entered is a number
		try { 
			Double.parseDouble(price);
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
		//Then checks that if there is a decimal point there is only a maximum of 2 decimal places
		int integerPlaces = price.indexOf('.');
		if (integerPlaces == -1) {
			return true;
		}
		int decimalPlaces = price.length() - integerPlaces - 1;
		if (decimalPlaces > 2) {
			return false;
		} else {
			return true;
		}
	}
	
	//Checks if entered string is an integer
	public boolean checkInt(String string) {
		try { 
	        Integer.parseInt(string);
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
		return true;
	}
	
	//Checks if book length is a number
	public boolean checkLength(String length) {
		try { 
	        Double.parseDouble(length);
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
		return true;
	}

}
