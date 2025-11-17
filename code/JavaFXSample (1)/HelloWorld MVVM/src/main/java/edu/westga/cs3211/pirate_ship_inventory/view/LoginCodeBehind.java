package edu.westga.cs3211.pirate_ship_inventory.view;

import edu.westga.cs3211.pirate_ship_inventory.model.Inventory;
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

// TODO: Auto-generated Javadoc
/**
 * The Class LoginCodeBehind.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class LoginCodeBehind {

    /** The username text field. */
    @FXML private TextField usernameTextField;
    
    /** The password text field. */
    @FXML private TextField passwordTextField;
    
    /** The login button. */
    @FXML private Button loginButton;
    
    /** The login text. */
    @FXML private Label loginText;

    /** The view model. */
    private LoginViewModel viewModel;

    /**
     * Instantiates a new login code behind.
     */
    public LoginCodeBehind() {
        this.viewModel = new LoginViewModel();
    }

    /**
     * Initialize.
     */
    @FXML
    void initialize() {
        this.bindComponentsToViewModel();
        this.loginButton.disableProperty().bind(
        		this.viewModel.usernameProperty().isEmpty()
                    .or(this.viewModel.passwordProperty().isEmpty())
            ); 
    }

    /**
     * Bind components to view model.
     */
    private void bindComponentsToViewModel() {
        this.usernameTextField.textProperty().bindBidirectional(this.viewModel.usernameProperty());
        this.passwordTextField.textProperty().bindBidirectional(this.viewModel.passwordProperty());
        this.loginText.textProperty().bind(this.viewModel.loginTextProperty());
    }
    
    /**
     * Handle login.
     */
    @FXML
	void handleLogin() {
    	 User authenticUser = this.viewModel.login();
	    if (authenticUser !=  null) {
	    	this.openLanding(authenticUser);
        }
	}
    
    /**
     * Open landing.
     *
     * @param user the user
     */
    private void openLanding(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/westga/cs3211/pirate_ship_inventory/view/DefaultLandingPage.fxml")
            );
            Parent root = loader.load();

            DefaultLandingPageCodeBehind landing = loader.getController();
            landing.setUsername(user.getName());
            landing.setRole(user.getRole());
            landing.setCurrentUser(user);
            landing.setInventory(new Inventory()); 

            Stage stage = (Stage) this.loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("HomePage");
            stage.centerOnScreen();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.viewModel.loginTextProperty().set("Failed to open landing page");  
        }
    }

}
