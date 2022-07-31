
public class Audio extends Book {
	//Audio attributes
	private float length;
	private Format_A format;
	
	//Audio constructor
	public Audio (String isbn, String title, String language, String genre, String release, double price, int quantity, float length, Format_A format) {
		super (isbn,title,language,genre,release,price,quantity);
		this.length = length;
		this.format = format;
		this.type = "audiobook";
	}
	
	//Getter methods
	public float getLength() {
		return this.length;
	}

	public Format_A getFormat() {
		return this.format;
	}
}
