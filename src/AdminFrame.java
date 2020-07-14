
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 * AdminFrame offers a GUI for administrator to interact with the system
 *
 * @author created by Haowen Miao
 */
public class AdminFrame {
	private User loggedUser;
	
	private Stage mainStage;
	private BorderPane root;
	
	private TextField emailField;
	private TextField firstnameField;
	private TextField surnameField;
	private PasswordField passwordField;
	private PasswordField passwordConfirmField;
	private Button deleteButton;
	private Button registerButton;
	private TableView<ActivityLog> logTable;
	private Button deleteMenu;
	private Button registerMenu;
	private Button logMenu;
	
	public AdminFrame(User user) {
		this.loggedUser = user;
		mainStage = new Stage();
		mainStage.setTitle("CWK2 Admin Pane");
		root = new BorderPane();
		root.setLeft(setMenu());
		root.setCenter(setLogScreen());
		logMenu.setDisable(true);
		setRegisterScreen();
		setDeleteScreen();
		setActions();
		Scene scene = new Scene(root, 800, 900);
		mainStage.setScene(scene);
		mainStage.show();
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			public void handle(WindowEvent arg0) {
				LoginFrame.sqLite.logout(loggedUser.getEmail());
			}
			
		});
	}
	
	private VBox setMenu() {
		deleteMenu = new Button("Delete User");
		logMenu = new Button("Activity Log");
		registerMenu = new Button("Register User");
		VBox box = new VBox();
		box.setSpacing(30);
		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().addAll(logMenu, registerMenu, deleteMenu);
		box.setStyle("-fx-padding: 30;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
		        + "-fx-border-radius: 5;" + "-fx-border-color: black;");
		return box;
	}
	
	private VBox setLogScreen() {
		VBox vBox = new VBox();
		logTable = new TableView<ActivityLog>();
		TableColumn<ActivityLog, String> loginDateTimeCol = new TableColumn<ActivityLog, String>("LoginDateTime");
        TableColumn<ActivityLog, String> logoutDateTimeCol = new TableColumn<ActivityLog, String>("LogoutDateTime");
        TableColumn<ActivityLog, String> uEmailCol = new TableColumn<ActivityLog, String>("uEmail");
        logTable.getColumns().add(loginDateTimeCol);
        logTable.getColumns().add(logoutDateTimeCol);
        logTable.getColumns().add(uEmailCol);
        loginDateTimeCol.setCellValueFactory(
        	    new PropertyValueFactory<ActivityLog, String>("loginDateTime")
        	);
        loginDateTimeCol.setMinWidth(200);
        logoutDateTimeCol.setCellValueFactory(
        	    new PropertyValueFactory<ActivityLog, String>("logoutDateTime")
        	);
        logoutDateTimeCol.setMinWidth(200);
        uEmailCol.setCellValueFactory(
        	    new PropertyValueFactory<ActivityLog, String>("email")
        	);
        uEmailCol.setMinWidth(200);
		vBox.getChildren().add(logTable);
		logTable.setItems(LoginFrame.sqLite.getAllActivityLog());
        return vBox;
	}
	
	private VBox setDeleteScreen() {
		emailField = new TextField();
		emailField.setPromptText("Enter user email");
		deleteButton = new Button("DELETE");
		deleteButton.setOnAction(new DeleteButtonListener());
		VBox vBox = new VBox();
		vBox.setSpacing(20);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(emailField, deleteButton);
		vBox.setPadding(new Insets(20));
		return vBox;
	}
	
	private VBox setRegisterScreen() {
		firstnameField = new TextField();
		firstnameField.setPromptText("Enter user firstname");
		surnameField= new TextField();
		surnameField.setPromptText("Enter user surname");
		emailField = new TextField();
		emailField.setPromptText("Enter user email");
		passwordField = new PasswordField();
		passwordField.setPromptText("Enter password");
		passwordConfirmField = new PasswordField();
		passwordConfirmField.setPromptText("Enter password again");
		registerButton = new Button("Register");
		registerButton.setOnAction(new RegisterButtonListener());
		VBox vBox = new VBox();
		vBox.setSpacing(20);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(firstnameField, surnameField, emailField, passwordField, passwordConfirmField, registerButton);
		vBox.setPadding(new Insets(20));
		return vBox;
	}
	
	public void setActions() {
		logMenu.setOnAction(new logMenuListener());
		deleteMenu.setOnAction(new deleteMenuListener());
		registerMenu.setOnAction(new RegisterMenuListener());
	}
	
	/**
	 * Button listeners
	 */
	
	
	public class logMenuListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			root.setCenter(setLogScreen());
			registerMenu.setDisable(false);
			logMenu.setDisable(true);
			deleteMenu.setDisable(false);
		}
		
	}
	
	public class RegisterMenuListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			root.setCenter(setRegisterScreen());
			registerMenu.setDisable(true);
			logMenu.setDisable(false);
			deleteMenu.setDisable(false);
		}
		
	}
	
	public class deleteMenuListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			root.setCenter(setDeleteScreen());
			registerMenu.setDisable(false);
			logMenu.setDisable(false);
			deleteMenu.setDisable(true);
		}
		
	}
	
	private class RegisterButtonListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			String fName = firstnameField.getText().trim();
			String sName = surnameField.getText().trim();
			String email = emailField.getText().trim();
			String password = passwordField.getText();
			String passwordConfirm = passwordConfirmField.getText();
			if (fName.length() == 0 || sName.length() == 0 || email.length() == 0 || password.length() == 0) {
				AlertDialog.showAlert("Register Failed", "Empty fields are not accepted");
			} else {
				if (password.equals(passwordConfirm)) {
					boolean result = LoginFrame.sqLite.registerNewUser(fName, sName, email, password, "U");
					if (result) {
						AlertDialog.showAlert("Admin register", "Register New User Succeed");
						firstnameField.setText("");
						surnameField.setText("");
						emailField.setText("");
						passwordField.setText("");
						passwordConfirmField.setText("");
					} else {
						AlertDialog.showAlert("Register Failed", "The email address is already registered.");
					}
				} else {
					AlertDialog.showAlert("Register Failed", "Confirm password is not the same as password");
				}
			}
		}
		
	}
	
	private class DeleteButtonListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			String email = emailField.getText().trim();
			if (email.length() == 0) {
				AlertDialog.showAlert("Delete Failed", "Empty fields are not accepted");
			} else {
				boolean result = LoginFrame.sqLite.deleteUser(email);
				if (result) {
					AlertDialog.showAlert("Admin delete", "Delete user succeed");
					emailField.setText("");
				} else {
					AlertDialog.showAlert("Delete Failed", "The email address does not exist.");
				}
			}
		}
		
	}
}
