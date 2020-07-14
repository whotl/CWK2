import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * AlertDialog shows an alert dialog with specific header and message
 * 
 * @author created by Haowen Miao
 */
public class AlertDialog {
	public static void showAlert(String header, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
