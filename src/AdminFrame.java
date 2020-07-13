
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
		root.setCenter(setLogScreen());
		root.setLeft(setMenu());
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
		VBox vBox = new VBox();
		vBox.setSpacing(20);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(emailField, deleteButton);
		vBox.setPadding(new Insets(20));
		deleteButton.requestFocus();
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
		VBox vBox = new VBox();
		vBox.setSpacing(20);
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(firstnameField, surnameField, emailField, passwordField, passwordConfirmField, registerButton);
		vBox.setPadding(new Insets(20));
		registerButton.requestFocus();
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
}
