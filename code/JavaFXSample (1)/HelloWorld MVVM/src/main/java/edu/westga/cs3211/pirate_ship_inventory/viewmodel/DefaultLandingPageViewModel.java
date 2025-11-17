package edu.westga.cs3211.pirate_ship_inventory.viewmodel;

import edu.westga.cs3211.pirate_ship_inventory.model.Role;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultLandingPageViewModel.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class DefaultLandingPageViewModel {

    /** The welcome text. */
    private final StringProperty welcomeText;
    
    /** The show add stock. */
    private final BooleanProperty showAddStock = new SimpleBooleanProperty(false);
    
    /** The show review stock changes. */
    private final BooleanProperty showReviewStockChanges = new SimpleBooleanProperty(false);
    
    /** The show get food. */
    private final BooleanProperty showGetFood = new SimpleBooleanProperty(false);

    /**
     * Instantiates a new default landing page view model.
     */
    public DefaultLandingPageViewModel() {
        this.welcomeText = new SimpleStringProperty("Welcome");   
        
    }
    
    /**
     * Show review stock property.
     *
     * @return the boolean property
     */
    public BooleanProperty showReviewStockProperty() {
    	return this.showReviewStockChanges; 
    }
    
    /**
     * Show add stock property.
     *
     * @return the boolean property
     */
    public BooleanProperty showAddStockProperty() {
    	return this.showAddStock; 
    }
    
    /**
     * Show get food property.
     *
     * @return the boolean property
     */
    public BooleanProperty showGetFoodProperty() {
    	return this.showGetFood; 
    }

    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            this.welcomeText.set("Welcome");
        } else {
            this.welcomeText.set("Welcome, " + username);
        }
    }

    /**
     * Welcome text property.
     *
     * @return the string property
     */
    public StringProperty welcomeTextProperty() {
        return this.welcomeText;
    }

    /**
     * Sets the role.
     *
     * @param role the new role
     */
    public void setRole(Role role) {
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
                this.showAddStock.set(true);  
                this.showReviewStockChanges.set(true); 
                break;
            case COOK:
                this.showGetFood.set(true);    
                this.showAddStock.set(true);   
                break;
		default:
			    break;
        }
        System.out.println("Role set to: " + role);
        System.out.println("showAddStock: " + this.showAddStock.get());
        System.out.println("showReviewStockChanges: " + this.showReviewStockChanges.get());
        System.out.println("showGetFood: " + this.showGetFood.get());
    }  
}
