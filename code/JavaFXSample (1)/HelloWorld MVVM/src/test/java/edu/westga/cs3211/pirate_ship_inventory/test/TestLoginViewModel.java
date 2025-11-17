package edu.westga.cs3211.pirate_ship_inventory.test;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.westga.cs3211.pirate_ship_inventory.model.Role;
import edu.westga.cs3211.pirate_ship_inventory.model.User;
import edu.westga.cs3211.pirate_ship_inventory.viewmodel.LoginViewModel;

class TestLoginViewModel {

    @Test
    void testInitialState() {
        LoginViewModel viewModel = new LoginViewModel();
        
        assertEquals("", viewModel.usernameProperty().get());
        assertEquals("", viewModel.passwordProperty().get());
        assertEquals("", viewModel.loginTextProperty().get());
    }

    @Test
    void testLoginSuccess() {
        LoginViewModel viewModel = new LoginViewModel();
        
        viewModel.usernameProperty().set("Enoch");
        viewModel.passwordProperty().set("1234");
        
        User result = viewModel.login();
        
        assertNotNull(result);
        assertEquals("Enoch", result.getName());
        assertEquals(Role.CREWMATE, result.getRole());
        assertTrue(viewModel.loginTextProperty().get().contains("Login successful"));
    }

    @Test
    void testLoginInvalidCredentials() {
        LoginViewModel viewModel = new LoginViewModel();
        
        viewModel.usernameProperty().set("Enoch");
        viewModel.passwordProperty().set("wrongpassword");
        
        User result = viewModel.login();
        
        assertNull(result);
        assertEquals("Invalid credentials", viewModel.loginTextProperty().get());
        assertEquals("", viewModel.passwordProperty().get()); // Password cleared
    }

    @Test
    void testLoginEmptyUsername() {
        LoginViewModel viewModel = new LoginViewModel();
        
        viewModel.usernameProperty().set("");
        viewModel.passwordProperty().set("1234");
        
        User result = viewModel.login();
        
        assertNull(result);
        assertEquals("Username or password cannot be empty", viewModel.loginTextProperty().get());
    }

    @Test
    void testLoginEmptyPassword() {
        LoginViewModel viewModel = new LoginViewModel();
        
        viewModel.usernameProperty().set("Enoch");
        viewModel.passwordProperty().set("");
        
        User result = viewModel.login();
        
        assertNull(result);
        assertEquals("Username or password cannot be empty", viewModel.loginTextProperty().get());
    }

    @Test
    void testLoginNullUsername() {
        LoginViewModel viewModel = new LoginViewModel();
        
        viewModel.usernameProperty().set(null);
        viewModel.passwordProperty().set("1234");
        
        User result = viewModel.login();
        
        assertNull(result);
        assertEquals("Username or password cannot be empty", viewModel.loginTextProperty().get());
    }

    @Test
    void testLoginNullPassword() {
        LoginViewModel viewModel = new LoginViewModel();
        
        viewModel.usernameProperty().set("Enoch");
        viewModel.passwordProperty().set(null);
        
        User result = viewModel.login();
        
        assertNull(result);
        assertEquals("Username or password cannot be empty", viewModel.loginTextProperty().get());
    }

    @Test
    void testCredentialsClearedOnSuccess() {
        LoginViewModel viewModel = new LoginViewModel();
        
        viewModel.usernameProperty().set("Enoch");
        viewModel.passwordProperty().set("1234");
        
        User result = viewModel.login();
        
        assertNotNull(result);
        assertEquals("", viewModel.usernameProperty().get()); // Username cleared
        assertEquals("", viewModel.passwordProperty().get()); // Password cleared
    }

    @Test
    void testMultipleUsers() {
        LoginViewModel viewModel = new LoginViewModel();
        
        // Test user 1
        viewModel.usernameProperty().set("Felix");
        viewModel.passwordProperty().set("abcd");
        User result1 = viewModel.login();
        assertNotNull(result1);
        assertEquals("Felix", result1.getName());
        
        // Test user 2  
        viewModel.usernameProperty().set("Abdul");
        viewModel.passwordProperty().set("asdf");
        User result2 = viewModel.login();
        assertNotNull(result2);
        assertEquals("Abdul", result2.getName());
    }
}