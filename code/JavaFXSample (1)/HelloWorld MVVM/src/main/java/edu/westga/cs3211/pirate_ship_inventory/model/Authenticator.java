package edu.westga.cs3211.pirate_ship_inventory.model;

import java.util.ArrayList;

public class Authenticator {
	private ArrayList<User> credentialStore;
	
	public Authenticator(ArrayList<User> currentUsers) {
		if (currentUsers == null) {
			throw new IllegalArgumentException("Cannot be null");
		}
		this.credentialStore = currentUsers;
	}
	
    public void addUser(User newUser) {
        for (User user : this.credentialStore) {
            if (user.getName().equals(newUser.getName())) {
                return;
            }
        }
        this.credentialStore.add(newUser);  // Add user if they don't exist already
    }
	
	 public boolean verifyUser(String username, String password) {
	        for (User user : credentialStore) {
	            if (user.getName().equals(username) && user.getPassword().equals(password)) {
	                return true;  
	            }
	        }
	        return false;  // Authentication failed
	    }
	
}
