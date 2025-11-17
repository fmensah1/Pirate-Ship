package edu.westga.cs3211.pirate_ship_inventory.test;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.westga.cs3211.pirate_ship_inventory.model.Role;
import edu.westga.cs3211.pirate_ship_inventory.viewmodel.DefaultLandingPageViewModel;

class TestDefaultLandingPageViewModel {

    @Test
    void testInitialState() {
        DefaultLandingPageViewModel viewModel = new DefaultLandingPageViewModel();
        
        assertEquals("Welcome", viewModel.welcomeTextProperty().get());
        assertFalse(viewModel.showAddStockProperty().get());
        assertFalse(viewModel.showReviewStockProperty().get());
        assertFalse(viewModel.showGetFoodProperty().get());
    }

    @Test
    void testSetUsername() {
        DefaultLandingPageViewModel viewModel = new DefaultLandingPageViewModel();
        
        viewModel.setUsername("Enoch");
        assertEquals("Welcome, Enoch", viewModel.welcomeTextProperty().get());
    }

    @Test
    void testSetUsernameNull() {
        DefaultLandingPageViewModel viewModel = new DefaultLandingPageViewModel();
        
        viewModel.setUsername(null);
        assertEquals("Welcome", viewModel.welcomeTextProperty().get());
    }

    @Test
    void testSetUsernameEmpty() {
        DefaultLandingPageViewModel viewModel = new DefaultLandingPageViewModel();
        
        viewModel.setUsername("   ");
        assertEquals("Welcome", viewModel.welcomeTextProperty().get());
    }

    @Test
    void testSetRoleCrewmate() {
        DefaultLandingPageViewModel viewModel = new DefaultLandingPageViewModel();
        
        viewModel.setRole(Role.CREWMATE);
        
        assertTrue(viewModel.showAddStockProperty().get());
        assertFalse(viewModel.showReviewStockProperty().get());
        assertFalse(viewModel.showGetFoodProperty().get());
    }

    @Test
    void testSetRoleQuartermaster() {
        DefaultLandingPageViewModel viewModel = new DefaultLandingPageViewModel();
        
        viewModel.setRole(Role.QUARTERMASTER);
        
        assertTrue(viewModel.showAddStockProperty().get());
        assertTrue(viewModel.showReviewStockProperty().get());
        assertFalse(viewModel.showGetFoodProperty().get());
    }

    @Test
    void testSetRoleCook() {
        DefaultLandingPageViewModel viewModel = new DefaultLandingPageViewModel();
        
        viewModel.setRole(Role.COOK);
        
        assertTrue(viewModel.showAddStockProperty().get());
        assertFalse(viewModel.showReviewStockProperty().get());
        assertTrue(viewModel.showGetFoodProperty().get());
    }

    @Test
    void testSetRoleNull() {
        DefaultLandingPageViewModel viewModel = new DefaultLandingPageViewModel();
        
        // Set some values first
        viewModel.setRole(Role.QUARTERMASTER);
        
        // Now set null - should reset to defaults
        viewModel.setRole(null);
        
        assertFalse(viewModel.showAddStockProperty().get());
        assertFalse(viewModel.showReviewStockProperty().get());
        assertFalse(viewModel.showGetFoodProperty().get());
    }

    @Test
    void testRoleTransition() {
        DefaultLandingPageViewModel viewModel = new DefaultLandingPageViewModel();
        
        // Start with Crewmate
        viewModel.setRole(Role.CREWMATE);
        assertTrue(viewModel.showAddStockProperty().get());
        
        // Switch to Cook
        viewModel.setRole(Role.COOK);
        assertTrue(viewModel.showAddStockProperty().get());
        assertTrue(viewModel.showGetFoodProperty().get());
        assertFalse(viewModel.showReviewStockProperty().get());
        
        // Switch to Quartermaster  
        viewModel.setRole(Role.QUARTERMASTER);
        assertTrue(viewModel.showAddStockProperty().get());
        assertTrue(viewModel.showReviewStockProperty().get());
        assertFalse(viewModel.showGetFoodProperty().get());
    }
}