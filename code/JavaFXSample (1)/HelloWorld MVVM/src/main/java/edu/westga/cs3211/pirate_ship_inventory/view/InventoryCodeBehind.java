package edu.westga.cs3211.pirate_ship_inventory.view;

import edu.westga.cs3211.pirate_ship_inventory.viewmodel.InventoryViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InventoryCodeBehind {

    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Button loginButton;
    @FXML private Label loginText;

    private InventoryViewModel viewModel;

    public InventoryCodeBehind() {
        this.viewModel = new InventoryViewModel();
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
		this.viewModel.login();

	}

}
