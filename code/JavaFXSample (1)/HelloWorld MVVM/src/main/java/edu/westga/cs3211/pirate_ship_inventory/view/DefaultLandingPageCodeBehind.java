package edu.westga.cs3211.pirate_ship_inventory.view;

import edu.westga.cs3211.pirate_ship_inventory.model.Role;
import edu.westga.cs3211.pirate_ship_inventory.viewmodel.DefaultLandingPageViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DefaultLandingPageCodeBehind {
	
    @FXML
    private Label welcomeText;
    @FXML private Button addStockButton;
    @FXML private Button reviewStockChangesButton;
    @FXML private Button getFoodButton;
    private DefaultLandingPageViewModel vm;
	
@FXML
public void initialize() {
    this.vm = new DefaultLandingPageViewModel();
    this.welcomeText.textProperty().bind(this.vm.welcomeTextProperty());
    this.addStockButton.visibleProperty().bind(this.vm.showAddStockProperty());
    
    this.addStockButton.managedProperty().bind(this.addStockButton.visibleProperty());
    
    this.reviewStockChangesButton.visibleProperty().bind(this.vm.showReviewStockProperty());
    this.reviewStockChangesButton.managedProperty().bind(this.reviewStockChangesButton.visibleProperty());
    
    this.getFoodButton.visibleProperty().bind(this.vm.showGetFoodProperty());
    this.getFoodButton.managedProperty().bind(this.getFoodButton.visibleProperty());
}

	public void setUsername(String username) {
		this.vm.setUsername(username);
	}
	public void setRole(Role role) {
		this.vm.setRole(role);
	}
	
	 @FXML
	    private void handleAddStock() {
	        // open Add Stock page or dialog
	    }

	    @FXML
	    private void handleReviewStockChanges() {
	        
	    }

	    @FXML
	    private void handleGetFood() {
	        // open kitchen view
	    }
}
