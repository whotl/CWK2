
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * SQLite class offers the connection between database and project.
 * 
 */
public class SQLite {

	private Connection conn;
	private String url = "jdbc:sqlite:";

	/**
	 * Connect to database
	 */
	public void connect()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			String dburl = url + "database/database.db";
			conn = DriverManager.getConnection(dburl);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * disconnect from database
	 */
	public void disconnect() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * register a new user
	 * @param fName first name of the user
	 * @param sName surname of the user
	 * @param email email of the user
	 * @param password password of the user
	 * @param userType type of the user
	 * @return true if the user registered successfully and otherwise return false
	 */
	public boolean registerNewUser(String fName, String sName, String email, String password, String userType) {
		try {
			connect();
			PreparedStatement pStatement;
			pStatement = conn.prepareStatement("INSERT INTO User(uFirstName, uSurname, uEmail, uPassword, uType) VALUES (?, ?, ?, ?, ?)");
			pStatement.setString(1, fName);
			pStatement.setString(2, sName);
			pStatement.setString(3, email);
			pStatement.setString(4, Encryption.encrypt(password));
			pStatement.setString(5, userType);
			pStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
	}

	/**
	 * Delete a user by his email
	 * @param email email of a user
	 * @return true if the user is deleted and otherwise return false
	 */
	public boolean deleteUser(String email) {
		try {
			connect();
			PreparedStatement pStatement = conn.prepareStatement("DELETE FROM User WHERE uEmail=?");
			pStatement.setString(1, email);
			return pStatement.executeUpdate() != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return false;
	}

	/**
	 * Check if the user can login with correct email and password
	 * @param email email that user entered
	 * @param password password that user entered
	 * @return true if the user entered correct email and password and otherwise return false
	 */
	public User login(String email, String password) {
		User user = null;
		try {
			connect();
			PreparedStatement pStatement = conn.prepareStatement("SELECT * FROM User WHERE uEmail=?");
			pStatement.setString(1, email);
			ResultSet result = pStatement.executeQuery();
			if (result.next()) {
				if (result.getString("uPassword").equals(Encryption.encrypt(password))) {
					Date loginDate = new Date(System.currentTimeMillis());
					PreparedStatement activityStatement = conn.prepareStatement("INSERT INTO Activity_Log(loginDateTime, uEmail) VALUES(?, ?)");
					activityStatement.setDate(1, loginDate);
					activityStatement.setString(2, result.getString("uEmail"));
					activityStatement.execute();
					user = new User(result.getString("uFirstName"), result.getString("uSurname"), email, result.getString("uType").charAt(0));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return user;
	}

	/**
	 * Logout from the system
	 * @param email email of the user who wants to logout
	 */
	public void logout(String email) {
		try {
			connect();
			PreparedStatement pStatement = conn.prepareStatement("SELECT * FROM Activity_Log WHERE uEmail=? and logoutDateTime is null");
			pStatement.setString(1, email);
			ResultSet result = pStatement.executeQuery();
			if (result.next()) {
				Date loginDate = new Date(System.currentTimeMillis());
				PreparedStatement activityStatement = conn.prepareStatement("UPDATE Activity_Log SET logoutDateTime=? WHERE uEmail=? and logoutDateTime is null");
				activityStatement.setDate(1, loginDate);
				activityStatement.setString(2, email);
				activityStatement.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	/**
	 * Get all activity log
	 * @return all activity log
	 */
	public ObservableList<ActivityLog> getAllActivityLog() {
		ObservableList<ActivityLog> data = FXCollections.observableArrayList();
		try {
			connect();
			PreparedStatement pStatement = conn.prepareStatement("SELECT * FROM Activity_Log");
			ResultSet result = pStatement.executeQuery();
			while (result.next()) {
				data.add(new ActivityLog(result.getString("uEmail"), result.getDate("loginDateTime"), result.getDate("logoutDateTime")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * Get all hosts' data
	 * @return all hosts' data
	 */
	public ObservableList<Host> getAllHostsData() {
		ObservableList<Host> data = FXCollections.observableArrayList();
		try {
			connect();
			PreparedStatement pStatement = conn.prepareStatement("SELECT * FROM Host");
			ResultSet result = pStatement.executeQuery();
			while (result.next()) {
				data.add(new Host(result.getString("host_id"), result.getString("name"), result.getString("type"), result.getInt("price"), result.getInt("minimum_nights")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public static void main(String[] args) {
//		SQLite sqLite = new SQLite();
//		sqLite.connect();
//		sqLite.registerNewUser("TestFirstname", "TestSurname", "testemail@email.com", "testpassword", "U");
//		System.out.println(sqLite.login("testemail@email.com", "testpassword"));
//		sqLite.logout("testemail@email.com");
//		sqLite.registerNewUser("Admin", "Admin", "admin@email.com", "admin", "A");
//		System.out.println(sqLite.login("admin@email.com", "admin"));
//		sqLite.logout("admin@email.com");
//		sqLite.disconnect();
	}
}