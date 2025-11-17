package edu.westga.cs3211.pirate_ship_inventory.test;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory.model.Role;
import edu.westga.cs3211.pirate_ship_inventory.model.User;

import static org.junit.jupiter.api.Assertions.*;

class TestUser {

    @Test
    void testUserCreation() {
        User user = new User("name", "pass", Role.CREWMATE);
        
        assertEquals("name", user.getName());
        assertEquals("pass", user.getPassword()); 
        assertEquals(Role.CREWMATE, user.getRole());
    }
}