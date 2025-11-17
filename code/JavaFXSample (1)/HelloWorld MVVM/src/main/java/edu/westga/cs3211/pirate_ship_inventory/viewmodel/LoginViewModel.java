package edu.westga.cs3211.pirate_ship_inventory.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

import edu.westga.cs3211.pirate_ship_inventory.model.Authenticator;
import edu.westga.cs3211.pirate_ship_inventory.model.Role;
import edu.westga.cs3211.pirate_ship_inventory.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginViewModel.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class LoginViewModel {

    /** The username property. */
    private StringProperty usernameProperty;
    
    /** The password property. */
    private StringProperty passwordProperty;
    
    /** The current users. */
    private ArrayList<User> currentUsers;
    
    /** The login text. */
    private StringProperty loginText;
    
    /** The authenticator. */
    private final Authenticator authenticator;

    /**
     * Instantiates a new login view model.
     */
    public LoginViewModel() {
        this.usernameProperty = new SimpleStringProperty("");
        this.passwordProperty = new SimpleStringProperty("");
        this.currentUsers = new ArrayList<>();
        this.loginText = new SimpleStringProperty("");

        this.currentUsers.add(new User("Enoch", "1234", Role.CREWMATE));
        this.currentUsers.add(new User("Felix", "abcd", Role.QUARTERMASTER));
        this.currentUsers.add(new User("Abdul", "asdf", Role.COOK));
        
        this.authenticator = new Authenticator(this.currentUsers);
    }

    /**
     * Username property.
     *
     * @return the string property
     */
    public StringProperty usernameProperty() {
        return this.usernameProperty;
    }

    /**
     * Password property.
     *
     * @return the string property
     */
    public StringProperty passwordProperty() {
        return this.passwordProperty;
    }

    /**
     * Login text property.
     *
     * @return the string property
     */
    public StringProperty loginTextProperty() {
        return this.loginText;
    }

    /**
     * Login.
     *
     * @return the user
     */
    public User login() {
        String name = this.usernameProperty.getValue();
        String password = this.passwordProperty.getValue();

        if (name == null || name.isEmpty() || password == null || password.isEmpty()) {
            this.loginText.set("Username or password cannot be empty");
            return null;
        }

        User user = this.authenticator.verifyUser(name, password); 
        if (user != null) {
            this.loginText.set("Login successful for " + name);
            this.usernameProperty.setValue("");
            this.passwordProperty.setValue("");
            return user;
        } else {
            this.loginText.set("Invalid credentials");
            this.passwordProperty.setValue("");
            return null;
        }
    }
}
