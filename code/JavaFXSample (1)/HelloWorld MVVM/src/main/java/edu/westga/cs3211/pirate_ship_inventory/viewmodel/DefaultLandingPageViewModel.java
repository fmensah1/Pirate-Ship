package edu.westga.cs3211.pirate_ship_inventory.viewmodel;

import edu.westga.cs3211.pirate_ship_inventory.model.Role;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DefaultLandingPageViewModel {

    private final StringProperty welcomeText;
    private final BooleanProperty showAddStock = new SimpleBooleanProperty(false);
    private final BooleanProperty showReviewStockChanges = new SimpleBooleanProperty(false);
    private final BooleanProperty showGetFood = new SimpleBooleanProperty(false);


    public DefaultLandingPageViewModel() {
        this.welcomeText = new SimpleStringProperty("Welcome");   
        
    }
    

    public BooleanProperty showReviewStockProperty() {
    	return this.showReviewStockChanges; 
    }
    
    public BooleanProperty showAddStockProperty() {
    	return this.showAddStock; 
    }
    
    public BooleanProperty showGetFoodProperty() {
    	return this.showGetFood; 
    }


    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            this.welcomeText.set("Welcome");
        } else {
            this.welcomeText.set("Welcome, " + username);
        }
    }

    public StringProperty welcomeTextProperty() {
        return this.welcomeText;
    }

    public void setRole(Role role) {
        // Reset to defaults
        this.showAddStock.set(false);
        this.showReviewStockChanges.set(false);
        this.showGetFood.set(false); 

        if (role == null) {
            return;
        }
        switch (role) {
            case CREWMATE:
            	this.showAddStock.set(true);
                break;
            case QUARTERMASTER:
                this.showAddStock.set(true);   // example: quartermaster can add stock
                this.showReviewStockChanges.set(true); // example: manage crew inventory/assignments
                break;
            case COOK:
                this.showGetFood.set(true);    // cook-specific actions
                this.showAddStock.set(true);   // maybe cook can add kitchen stock
                break;
        }
    }
    
    
}
