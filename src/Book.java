import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Book {
	//Book Attributes
	protected String isbn;
	protected String type;
	protected String title;
	protected String language;
	protected String genre;
	protected String release;
	protected double price;
	protected int quantity;
	
	//Book Constructor
	public Book (String isbn, String title, String language, String genre, String release, double price, int quantity) {
		this.isbn = isbn;
		this.title = title;
		this.language = language;
		this.genre = genre;
		this.release = release;
		this.price = price;
		this.quantity = quantity;
	}
	
	//Getter Methods
	public String getType() {
		return type;
	}
	public String getIsbn() {
		return isbn;
	}
	public String getGenre() {
		return genre;
	}
	public String getLanguage() {
		return language;
	}
	public double getPrice() {
		return price;
	}
	public int getQuantity() {
		return quantity;
	}
	public String getRelease() {
		return release;
	}
	public String getTitle() {
		return title;
	}
	
	//Returns an ArrayList of the books in the stock file
	public static ArrayList<Book> getBooks() throws FileNotFoundException {
		//Creates ArrayList
		ArrayList<Book> Books = new ArrayList<Book>();
		//Opens Stock file
		File BookFile = new File("Stock.txt");
		Scanner BookScanner;
		
		BookScanner = new Scanner(BookFile);
		while (BookScanner.hasNextLine()) {
			String line = BookScanner.nextLine();
			String[] array = line.split(", ");
			//For each line the book type is checked to instantiate the correct class and add it to the ArrayList
			if (array[1].equals("paperback")) {
				Paperback book = new Paperback(array[0],array[2],array[3],array[4],array[5],Double.parseDouble(array[6]),Integer.parseInt(array[7]),Integer.parseInt(array[8]),array[9]);
				Books.add(book);
			} else if (array[1].equals("audiobook")) {
				Audio book = new Audio(array[0],array[2],array[3],array[4],array[5],Double.parseDouble(array[6]),Integer.parseInt(array[7]),Float.parseFloat(array[8]),Format_A.valueOf(array[9]));
				Books.add(book);
			} else {
				Ebook book = new Ebook(array[0],array[2],array[3],array[4],array[5],Double.parseDouble(array[6]),Integer.parseInt(array[7]),Integer.parseInt(array[8]),Format_E.valueOf(array[9]));
				Books.add(book);
			}
		}
		BookScanner.close();
		return Books;
	}
}
