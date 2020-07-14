import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * ActivityLog class store one activity log as an object
 *
 * @author created by Haowen Miao
 */
public class ActivityLog {
	/**
	 * login date time
	 */
	private String loginDateTime;
	/**
	 * logout date time (could be null if the user is still login during the query)
	 */
	private String logoutDateTime;
	/**
	 * user email
	 */
	private String email;
	
	public ActivityLog(String email, Date loginDateTime, Date logoutDateTime) {
		this.email = email;
		if (loginDateTime != null)
			this.loginDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loginDateTime);
		if (logoutDateTime != null)
			this.logoutDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logoutDateTime);
	}
	
	/**
	 * Getter for loginDateTime
	 * @return login date time
	 */
	public String getLoginDateTime() {
		return loginDateTime;
	}
	
	/**
	 * Getter for logoutDateTime
	 * @return logout date time
	 */
	public String getLogoutDateTime() {
		return logoutDateTime;
	}
	
	/**
	 * Getter for user email
	 * @return user email
	 */
	public String getEmail() {
		return email;
	}
}
