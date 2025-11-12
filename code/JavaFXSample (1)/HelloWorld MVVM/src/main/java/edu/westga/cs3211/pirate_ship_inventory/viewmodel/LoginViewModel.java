package edu.westga.cs3211.pirate_ship_inventory.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

import edu.westga.cs3211.pirate_ship_inventory.model.Authenticator;
import edu.westga.cs3211.pirate_ship_inventory.model.Role;
import edu.westga.cs3211.pirate_ship_inventory.model.User;

public class LoginViewModel {

    private StringProperty usernameProperty;
    private StringProperty passwordProperty;
    private ArrayList<User> currentUsers;
    private StringProperty loginText;
    private final Authenticator authenticator;

    public LoginViewModel() {
        this.usernameProperty = new SimpleStringProperty("");
        this.passwordProperty = new SimpleStringProperty("");
        this.currentUsers = new ArrayList<>();
        this.loginText = new SimpleStringProperty("");

        currentUsers.add(new User("Enoch", "1234", Role.CREWMATE));
        currentUsers.add(new User("Felix", "abcd", Role.QUARTERMASTER));
        currentUsers.add(new User("Abdul", "asdf", Role.COOK));
        
        this.authenticator = new Authenticator(currentUsers);
    }

    public StringProperty usernameProperty() {
        return this.usernameProperty;
    }

    public StringProperty passwordProperty() {
        return this.passwordProperty;
    }

    public StringProperty loginTextProperty() {
        return this.loginText;
    }

    // Login logic
    public User login() {
        String name = this.usernameProperty.getValue();
        String password = this.passwordProperty.getValue();

        if (name == null || name.isEmpty() || password == null || password.isEmpty()) {
            this.loginText.set("Username or password cannot be empty");
            return null;
        }


        User user = this.authenticator.verifyUser(name, password); // see Authenticator change below
        if (user != null) {
            this.loginText.set("Login successful for " + name);
            // Clear sensitive input
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
