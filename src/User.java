
public class User {
	public static final char ADMIN_TYPE = 'A';
	public static final char USER_TYPE = 'U';
	
	private String firstname;
	private String surname;
	private String email;
	private char type;
	
	public User(String firstname, String surname, String email, char type) {
		this.firstname = firstname;
		this.surname = surname;
		this.email = email;
		this.type = type;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public char getType() {
		return type;
	}
}
