package edu.westga.cs3211.pirate_ship_inventory.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

import edu.westga.cs3211.pirate_ship_inventory.model.Authenticator;
import edu.westga.cs3211.pirate_ship_inventory.model.User;

public class InventoryViewModel {

    private StringProperty usernameProperty;
    private StringProperty passwordProperty;
    private ArrayList<User> currentUsers;
    private StringProperty loginText;

    public InventoryViewModel() {
        this.usernameProperty = new SimpleStringProperty("");
        this.passwordProperty = new SimpleStringProperty("");
        this.currentUsers = new ArrayList<>();
        this.loginText = new SimpleStringProperty("");

        currentUsers.add(new User("Enoch", "1234"));
        currentUsers.add(new User("Felix", "abcd"));
        currentUsers.add(new User("Abdul", "asdf"));
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
    public void login() {
        String name = this.usernameProperty.getValue();
        String password = this.passwordProperty.getValue();

        if (name == null || name.isEmpty() || password == null || password.isEmpty()) {
            this.loginText.set("Username or password cannot be empty");
            return;
        }

        Authenticator authenticator = new Authenticator(currentUsers);
        if (authenticator.verifyUser(name, password)) {
            this.loginText.set("Login successful for " + name);
            this.usernameProperty.setValue("");
            this.passwordProperty.setValue("");
        } else {
            this.loginText.set("Invalid credentials");
            this.passwordProperty.setValue(""); 
        }
    }
}
