package edu.westga.cs3211.pirate_ship_inventory.test;



import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory.model.Condition;
import edu.westga.cs3211.pirate_ship_inventory.model.Role;
import edu.westga.cs3211.pirate_ship_inventory.model.Stock;
import edu.westga.cs3211.pirate_ship_inventory.model.StockChange;
import edu.westga.cs3211.pirate_ship_inventory.model.User;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

class TestStockChange {

    @Test
    void testValidStockChange() {
        User user = new User("Jack", "pass", Role.CREWMATE);
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 10, "liquid", Condition.USABLE, future);

        
        StockChange change = new StockChange(user, stock, "Test", 90);
        
        assertEquals(user, change.getCrewMate());
        assertEquals(stock, change.getStock());
        assertEquals("Test", change.getCompartmentLabel());
        assertEquals(90, change.getAvailableCapacityAfter());
        assertNotNull(change.getTimestamp());
    }

    @Test
    void testStockChangeNullCrewMateThrows() {
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 10, "liquid", Condition.USABLE, future);
        
        assertThrows(IllegalArgumentException.class, () -> {
            new StockChange(null, stock, "Test", 90);
        });
    }

    @Test
    void testStockChangeNullStockThrows() {
        User user = new User("Jack", "pass", Role.CREWMATE);
        
        assertThrows(IllegalArgumentException.class, () -> {
            new StockChange(user, null, "Test", 90);
        });
    }

    @Test
    void testStockChangeEmptyLabelThrows() {
        User user = new User("Jack", "pass", Role.CREWMATE);
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 10, "liquid", Condition.USABLE, future);
        
        assertThrows(IllegalArgumentException.class, () -> {
            new StockChange(user, stock, "   ", 90);
        });
    }

    @Test
    void testHasSpecialQuality() {
        User user = new User("Jack", "pass", Role.CREWMATE);
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 10, "liquid", Condition.USABLE, future);

        
        StockChange change = new StockChange(user, stock, "Test", 90);
        
        assertTrue(change.hasSpecialQuality("liquid"));
        assertFalse(change.hasSpecialQuality("flammable"));
    }

    @Test
    void testToString() {
        User user = new User("Jack", "pass", Role.CREWMATE);
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 10, "liquid", Condition.USABLE, future);
        
        StockChange change = new StockChange(user, stock, "Test", 90);
        String result = change.toString();
        
        assertTrue(result.contains("Jack"));
        assertTrue(result.contains("Rum"));
        assertTrue(result.contains("Test"));
    }
}