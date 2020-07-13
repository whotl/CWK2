import java.sql.Date;
import java.text.SimpleDateFormat;

public class ActivityLog {
	private String loginDateTime;
	private String logoutDateTime;
	private String email;
	
	public ActivityLog(String email, Date loginDateTime, Date logoutDateTime) {
		this.email = email;
		if (loginDateTime != null)
			this.loginDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(loginDateTime);
		if (logoutDateTime != null)
			this.logoutDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logoutDateTime);
	}
	
	public String getLoginDateTime() {
		return loginDateTime;
	}
	
	public String getLogoutDateTime() {
		return logoutDateTime;
	}
	
	public String getEmail() {
		return email;
	}
}
