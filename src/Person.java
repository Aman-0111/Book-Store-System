
public abstract class Person {
	//Person Attributes
	protected int userID;
	protected String username;
	protected String surname;
	protected int house_no;
	protected String postcode;
	protected String city;
	
	//Person Constructor
	public Person (int userID, String username, String surname, int house_no, String postcode, String city) {
		this.userID = userID;
		this.username = username;
		this.surname = surname;
		this.house_no = house_no;
		this.postcode = postcode;
		this.city = city;
	}
	
	//Getter Methods
	public String getCity() {
		return city;
	}
	public int getHouse_no() {
		return house_no;
	}
	public String getPostcode() {
		return postcode;
	}
	public String getSurname() {
		return surname;
	}
	public int getUserID() {
		return userID;
	}
	public String getUsername() {
		return username;
	}

}
