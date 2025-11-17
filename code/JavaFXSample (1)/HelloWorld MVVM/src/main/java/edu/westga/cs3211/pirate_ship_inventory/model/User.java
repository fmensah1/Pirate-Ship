package edu.westga.cs3211.pirate_ship_inventory.model;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class User {
	
	/** The name. */
	private String name;
	
	/** The password. */
	private String password;
	
	/** The role. */
	private Role role;
	
	/**
	 * Instantiates a new user.
	 *
	 * @param name the name
	 * @param password the password
	 * @param role the role
	 */
	public User(String name, String password, Role role) {
		this.name = name;
		this.password = password;
		this.role = role;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public Role getRole() {
		return this.role;
	}
}
