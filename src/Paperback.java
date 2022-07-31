
public class Paperback extends Book {
	//Paperback attributes
	private int pages;
	private String condition;
	
	//Paperback constructor
	public Paperback (String isbn, String title, String language, String genre, String release, double price, int quantity, int pages, String condition) {
		super (isbn,title,language,genre,release,price,quantity);
		this.pages = pages;
		this.condition = condition;
		this.type = "paperback";
	}
	
	//Getter Methods
	public int getPages() {
		return this.pages;
	}

	public String getCondition() {
		return this.condition;
	}
}
