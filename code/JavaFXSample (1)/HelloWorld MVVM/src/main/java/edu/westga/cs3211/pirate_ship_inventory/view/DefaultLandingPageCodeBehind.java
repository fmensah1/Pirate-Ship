package edu.westga.cs3211.pirate_ship_inventory.view;

import java.io.IOException;

import edu.westga.cs3211.pirate_ship_inventory.model.Inventory;
import edu.westga.cs3211.pirate_ship_inventory.model.Role;
import edu.westga.cs3211.pirate_ship_inventory.model.User;
import edu.westga.cs3211.pirate_ship_inventory.viewmodel.DefaultLandingPageViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DefaultLandingPageCodeBehind {
	
    @FXML
    private Label welcomeText;
    @FXML private Button addStockButton;
    @FXML private Button reviewStockChangesButton;
    @FXML private Button getFoodButton;
    private DefaultLandingPageViewModel vm;
    private User currentUser; 
    private Inventory inventory; 
	
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
	
	 public void setCurrentUser(User user) {
	        this.currentUser = user;
	    }
	    
	    public void setInventory(Inventory inventory) {
	        this.inventory = inventory;
	        System.out.println("Landing received inventory: " + inventory);
	       
	    }
	    
	 @FXML
	    private void handleAddStock() {
		 try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStock.fxml"));
		        Parent addStockRoot = loader.load();
		        
		        AddStockCodeBehind addStockController = loader.getController();
		        addStockController.setCurrentUser(this.currentUser);
		        addStockController.setInventory(this.inventory);
		        
		        // Get the current stage and switch scenes
		        Stage stage = (Stage) addStockButton.getScene().getWindow();
		        stage.setScene(new Scene(addStockRoot));
		        stage.setTitle("Add New Stock");
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		        //showErrorAlert("Could not load Add Stock screen");
		    }
	    }

	    @FXML
	    private void handleReviewStockChanges() {
	        
	    }

	    @FXML
	    private void handleGetFood() {
	       
	    }
}
