
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginFrame extends Application {
	
	private Stage mainStage;
	private TextField emailField;
	private PasswordField passwordField;
	private Button loginButton;
	private Button registerButton;
	private SQLite sqLite;
	
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * start() method creates the opening screen with menus. It also creates the
	 * screenGrid by invoking setupScreen() method of PTViewer class but doesn't
	 * attach it to the root yet *
	 */
	@Override
	public void start(Stage primaryStage) {
		mainStage = primaryStage;
		mainStage.setTitle("CWK2");
		sqLite = new SQLite();
		setLoginScree();
		setActions();
		Label title = new Label("Welcome Login");
		title.setFont(new Font("Consolas", 30));
		VBox box = new VBox();
		box.setPadding(new Insets(20));
		box.setSpacing(20);
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(title, emailField, passwordField, loginButton, registerButton);
		BorderPane root = new BorderPane();
		root.setCenter(box);
		Scene scene = new Scene(root, 300, 300);
		mainStage.setScene(scene);
		mainStage.show();
	}
	
	public void setLoginScree() {
		emailField = new TextField();
		emailField.setPromptText("enter email");
		passwordField = new PasswordField();
		passwordField.setPromptText("enter password");
		loginButton = new Button("Login");
		registerButton = new Button("Register");
	}
	
	public void setActions() {
		loginButton.setOnAction(new LoginButtonListener());
		registerButton.setOnAction(new RegisterButtonListener());
	}

	/**
	 * Button listeners
	 */
	

	private class LoginButtonListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			String email = emailField.getText().trim();
			String password = passwordField.getText();
			System.out.println(email + " " + password);
			if (sqLite.login(email, password)) {
				System.out.println("true");
			} else {
				System.out.println("false");
			}
		}
		
	}

	private class RegisterButtonListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			mainStage.close();
			new RegisterFrame();
		}
		
	}
}
