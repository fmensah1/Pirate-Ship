package edu.westga.cs3211.pirate_ship_inventory.model;

public class User {
	
	private String name;
	private String password;
	private String roles;
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.roles = "Crewmate";
	}
	
	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return roles;
	}
}
