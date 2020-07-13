/**
 * User class stores a user detail as an object
 *
 */
public class User {
	public static final char ADMIN_TYPE = 'A';
	public static final char USER_TYPE = 'U';
	
	/**
	 * first name of the user
	 */
	private String firstname;
	/**
	 * surname of the user
	 */
	private String surname;
	/**
	 * email of the user
	 */
	private String email;
	/**
	 * type of the user
	 */
	private char type;
	
	public User(String firstname, String surname, String email, char type) {
		this.firstname = firstname;
		this.surname = surname;
		this.email = email;
		this.type = type;
	}
	
	/**
	 * Getter for firstname
	 * @return first name of the user
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * Getter for surname
	 * @return surname of the user
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 * Getter for email
	 * @return email of the user
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Getter for type
	 * @return type of the user
	 */
	public char getType() {
		return type;
	}
}
