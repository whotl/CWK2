
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
import javafx.stage.Window;

public class RegisterFrame extends Window {
	
	private Stage mainStage;
	private TextField emailField;
	private TextField fNameField;
	private TextField sNameField;
	private PasswordField passwordField;
	private PasswordField passwordConfirmField;
	private Button backButton;
	private Button registerButton;	
	
	public RegisterFrame() {
		mainStage = new Stage();
		mainStage.setTitle("CWK2");
		setLoginScree();
		setActions();
		Label title = new Label("Welcome Register");
		title.setFont(new Font("Consolas", 30));
		VBox box = new VBox();
		box.setPadding(new Insets(20));
		box.setSpacing(20);
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(title, fNameField, sNameField, emailField, passwordField, passwordConfirmField, registerButton, backButton);
		BorderPane root = new BorderPane();
		root.setCenter(box);
		Scene scene = new Scene(root, 400, 450);
		mainStage.setScene(scene);
		mainStage.show();
	}
	
	public void setLoginScree() {
		fNameField = new TextField();
		fNameField.setPromptText("enter firstname");
		sNameField = new TextField();
		sNameField.setPromptText("enter surname");
		emailField = new TextField();
		emailField.setPromptText("enter email");
		passwordField = new PasswordField();
		passwordField.setPromptText("enter password");
		passwordConfirmField = new PasswordField();
		passwordConfirmField.setPromptText("enter password again");
		backButton = new Button("Back to login");
		registerButton = new Button("Register");
	}
	
	public void setActions() {
		backButton.setOnAction(new BackButtonListener());
	}

	/**
	 * Button listeners
	 */
	
	
	private class BackButtonListener implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			mainStage.close();
			LoginFrame loginFrame = new LoginFrame();
			loginFrame.start(new Stage());
		}
		
	}
}
