package edu.westga.cs3211.pirate_ship_inventory.test;



import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory.model.Authenticator;
import edu.westga.cs3211.pirate_ship_inventory.model.Role;
import edu.westga.cs3211.pirate_ship_inventory.model.User;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class TestAuthenticator {

    @Test
    void testConstructorWithNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Authenticator(null);
        });
    }

    @Test
    void testConstructorWithValidList() {
        ArrayList<User> users = new ArrayList<>();
        Authenticator auth = new Authenticator(users);
        assertNotNull(auth);
    }

    @Test
    void testAddNewUser() {
        ArrayList<User> users = new ArrayList<>();
        Authenticator auth = new Authenticator(users);
        User newUser = new User("Jack", "pass", Role.COOK);
        
        auth.addUser(newUser);
        
        assertEquals(1, users.size());
        assertEquals("Jack", users.get(0).getName());
    }

    @Test
    void testAddDuplicateUser() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Jack", "pass1", Role.CREWMATE));
        Authenticator auth = new Authenticator(users);
        User duplicateUser = new User("Jack", "pass2", Role.QUARTERMASTER);
        
        auth.addUser(duplicateUser);
        
        assertEquals(1, users.size()); // Size shouldn't change
        assertEquals("pass1", users.get(0).getPassword()); // Original user unchanged
    }

    @Test
    void testVerifyUserValidCredentials() {
        ArrayList<User> users = new ArrayList<>();
        User expectedUser = new User("Jack", "pass", Role.QUARTERMASTER);
        users.add(expectedUser);
        Authenticator auth = new Authenticator(users);
        
        User result = auth.verifyUser("Jack", "pass");
        
        assertEquals(expectedUser, result);
    }

    @Test
    void testVerifyUserWrongPassword() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Jack", "pass", Role.CREWMATE));
        Authenticator auth = new Authenticator(users);
        
        User result = auth.verifyUser("Jack", "wrongpass");
        
        assertNull(result);
    }

    @Test
    void testVerifyUserNotFound() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Jack", "pass", Role.COOK));
        Authenticator auth = new Authenticator(users);
        
        User result = auth.verifyUser("Unknown", "pass");
        
        assertNull(result);
    }
}