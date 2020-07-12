
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLite {

	private Connection conn;
	private String url = "jdbc:sqlite:";

	public void connect()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			String dburl = url + getClass().getClassLoader().getResource("database.db").getPath();
			conn = DriverManager.getConnection(dburl);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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

	public boolean login(String email, String password) {
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
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return false;
	}

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
	
	public static void main(String[] args) {
		SQLite sqLite = new SQLite();
//		sqLite.connect();
//		sqLite.registerNewUser("TestFirstname", "TestSurname", "testemail@email.com", "testpassword", "U");
//		System.out.println(sqLite.login("testemail@email.com", "testpassword"));
//		sqLite.logout("testemail@email.com");
//		sqLite.registerNewUser("Admin", "Admin", "admin@email.com", "admin", "A");
		System.out.println(sqLite.login("admin@email.com", "admin"));
		sqLite.logout("admin@email.com");
//		sqLite.disconnect();
	}
}