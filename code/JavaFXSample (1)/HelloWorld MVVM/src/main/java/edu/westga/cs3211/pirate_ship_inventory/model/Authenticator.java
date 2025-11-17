package edu.westga.cs3211.pirate_ship_inventory.model;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Authenticator.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class Authenticator {
	
	/** The credential store. */
	private ArrayList<User> credentialStore;
	
	/**
	 * Instantiates a new authenticator.
	 *
	 * @param currentUsers the current users
	 */
	public Authenticator(ArrayList<User> currentUsers) {
		if (currentUsers == null) {
			throw new IllegalArgumentException("Cannot be null");
		}
		this.credentialStore = currentUsers;
	}
	
    /**
     * Adds the user.
     *
     * @param newUser the new user
     */
    public void addUser(User newUser) {
        for (User user : this.credentialStore) {
            if (user.getName().equals(newUser.getName())) {
                return;
            }
        }
        this.credentialStore.add(newUser);  
    }
	
	 /**
 	 * Verify user.
 	 *
 	 * @param username the username
 	 * @param password the password
 	 * @return the user
 	 */
 	public User verifyUser(String username, String password) {
	        for (User user : this.credentialStore) {
	            if (user.getName().equals(username) && user.getPassword().equals(password)) {
	                return user;  
	            }
	        }
	        return null; 
	    }
	
}
