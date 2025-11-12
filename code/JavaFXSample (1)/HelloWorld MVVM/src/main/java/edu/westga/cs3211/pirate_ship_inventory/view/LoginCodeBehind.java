package edu.westga.cs3211.pirate_ship_inventory.view;

import edu.westga.cs3211.pirate_ship_inventory.model.User;
import edu.westga.cs3211.pirate_ship_inventory.viewmodel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginCodeBehind {

    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Button loginButton;
    @FXML private Label loginText;

    private LoginViewModel viewModel;

    public LoginCodeBehind() {
        this.viewModel = new LoginViewModel();
    }

    @FXML
    void initialize() {
        this.bindComponentsToViewModel();
        this.loginButton.disableProperty().bind(
                viewModel.usernameProperty().isEmpty()
                    .or(viewModel.passwordProperty().isEmpty())
            ); 
    }

    // Bind ViewModel to UI components
    private void bindComponentsToViewModel() {
        usernameTextField.textProperty().bindBidirectional(viewModel.usernameProperty());
        passwordTextField.textProperty().bindBidirectional(viewModel.passwordProperty());
        loginText.textProperty().bind(viewModel.loginTextProperty());
    }
    
    @FXML
	void handleLogin() {
    	 User authenticUser = this.viewModel.login();
	    if (authenticUser !=  null) {
            openLanding(authenticUser);
        }
	}
    
    private void openLanding(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/westga/cs3211/pirate_ship_inventory/view/DefaultLandingPage.fxml")
            );
            Parent root = loader.load();

            // Get landing controller and send username
            DefaultLandingPageCodeBehind landing = loader.getController();
            landing.setUsername(user.getName());
            landing.setRole(user.getRole());

            Stage stage = (Stage) this.loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Landing");
            stage.centerOnScreen();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.viewModel.loginTextProperty().set("Failed to open landing page");  // ✅
        }
    }

}
