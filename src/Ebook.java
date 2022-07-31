
public class Ebook extends Book {
	//Ebook attributes
	private int pages;
	private Format_E format;
	
	//Ebook constructor
	public Ebook (String isbn, String title, String language, String genre, String release, double price, int quantity, int pages, Format_E format) {
		super (isbn,title,language,genre,release,price,quantity);
		this.pages = pages;
		this.format = format;
		this.type = "ebook";
	}
	
	//Getter Methods
	public int getPages() {
		return this.pages;
	}

	public Format_E getFormat() {
		return this.format;
	}
}
