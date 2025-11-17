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

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultLandingPageCodeBehind.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class DefaultLandingPageCodeBehind {
	
    /** The welcome text. */
    @FXML
    private Label welcomeText;
    
    /** The add stock button. */
    @FXML private Button addStockButton;
    
    /** The review stock changes button. */
    @FXML private Button reviewStockChangesButton;
    
    /** The get food button. */
    @FXML private Button getFoodButton;
    
    /** The vm. */
    private DefaultLandingPageViewModel vm;
    
    /** The current user. */
    private User currentUser; 
    
    /** The inventory. */
    private Inventory inventory; 
	
/**
 * Initialize.
 */
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

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.vm.setUsername(username);
	}
	
	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(Role role) {
		this.vm.setRole(role);
	}
	
	 /**
 	 * Sets the current user.
 	 *
 	 * @param user the new current user
 	 */
 	public void setCurrentUser(User user) {
	        this.currentUser = user;
	    }
	    
	    /**
    	 * Sets the inventory.
    	 *
    	 * @param inventory the new inventory
    	 */
    	public void setInventory(Inventory inventory) {
	        this.inventory = inventory;
	        System.out.println("Landing received inventory: " + inventory);
	       
	    }
	    
	 /**
 	 * Handle add stock.
 	 */
 	@FXML
	    private void handleAddStock() {
		 try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStock.fxml"));
		        Parent addStockRoot = loader.load();
		        
		        AddStockCodeBehind addStockController = loader.getController();
		        addStockController.setCurrentUser(this.currentUser);
		        addStockController.setInventory(this.inventory);
		        
		        // Get the current stage and switch scenes
		        Stage stage = (Stage) this.addStockButton.getScene().getWindow();
		        stage.setScene(new Scene(addStockRoot));
		        stage.setTitle("Add New Stock");
		        
		    } catch (IOException error) {
		        error.printStackTrace();
		    }
	    }

	    /**
    	 * Handle review stock changes.
    	 */
    	@FXML
	    private void handleReviewStockChanges() {
	    	   try {
	               FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewStockChanges.fxml"));
	               Parent reviewStockRoot = loader.load();
	               
	               ViewStockChangesCodeBehind reviewController = loader.getController();
	               reviewController.setCurrentUser(this.currentUser);  
	               reviewController.setInventory(this.inventory);
	               
	               Stage stage = (Stage) this.reviewStockChangesButton.getScene().getWindow();
	               stage.setScene(new Scene(reviewStockRoot));
	               stage.setTitle("Review Stock Changes");
	               
	           } catch (IOException error) {
	               error.printStackTrace();
	           }
	        
	    }

	    /**
    	 * Handle get food.
    	 */
    	@FXML
	    private void handleGetFood() {
	       
	    }
}
