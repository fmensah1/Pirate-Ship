package edu.westga.cs3211.pirate_ship_inventory.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory.model.Authenticator;
import edu.westga.cs3211.pirate_ship_inventory.model.User;

class AuthenticatorTest {


    private ArrayList<User> userList;
    private Authenticator authenticator;


	@BeforeEach
    public void setUp() {
        this.userList = new ArrayList<>();
        this.userList.add(new User("alice", "1234"));
        this.authenticator = new Authenticator(this.userList);
    }

    @Test
    public void testConstructorWithNullListThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Authenticator(null));
    }

    @Test
    public void testAddUserSuccessfully() {
        User newUser = new User("bob", "pass");
        this.authenticator.addUser(newUser);
        assertEquals(2, this.userList.size());
        assertEquals("bob", this.userList.get(1).getName());
    }


    @Test
    public void testAddUserAlreadyExistsDoesNotAddDuplicate() {
        User duplicateUser = new User("alice", "differentPass");
        this.authenticator.addUser(duplicateUser);
        assertEquals(1, this.userList.size());
    }

    @Test
    public void testVerifyUserReturnsTrueForValidCredentials() {
        boolean result = this.authenticator.verifyUser("alice", "1234");
        assertTrue(result);
    }

    @Test
    public void testVerifyUserReturnsFalseForInvalidUsername() {
        boolean result = this.authenticator.verifyUser("bob", "1234");
        assertFalse(result);
    }

    @Test
    public void testVerifyUserReturnsFalseForInvalidPassword() {
        boolean result = this.authenticator.verifyUser("alice", "wrong");
        assertFalse(result);
    }
}
