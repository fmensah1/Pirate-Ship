package edu.westga.cs3211.pirate_ship_inventory.test;


import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory.model.Compartment;
import edu.westga.cs3211.pirate_ship_inventory.model.Condition;
import edu.westga.cs3211.pirate_ship_inventory.model.Role;
import edu.westga.cs3211.pirate_ship_inventory.model.Stock;
import edu.westga.cs3211.pirate_ship_inventory.model.StockChange;
import edu.westga.cs3211.pirate_ship_inventory.model.StockLogger;
import edu.westga.cs3211.pirate_ship_inventory.model.User;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class TestStockLogger {

    @Test
    void testLogStockChange() {
        StockLogger logger = new StockLogger();
        User user = new User("Jack", "pass", Role.CREWMATE);
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 10, "liquid", Condition.USABLE, future);
        Compartment comp = new Compartment("Test", new ArrayList<>(), 100, "general");
        
        logger.logStockChange(user, stock, comp);
        
        assertEquals(1, logger.getAllChanges().size());
    }

    @Test
    void testGetAllChanges() {
        StockLogger logger = new StockLogger();
        User user = new User("Jack", "pass", Role.CREWMATE);
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 10, "liquid", Condition.USABLE, future);
        Compartment comp = new Compartment("Test", new ArrayList<>(), 100, "general");
        
        logger.logStockChange(user, stock, comp);
        List<StockChange> changes = logger.getAllChanges();
        
        assertEquals(1, changes.size());
        assertEquals("Jack", changes.get(0).getCrewMate().getName());
    }

    @Test
    void testFilterBySpecialQuality() {
        StockLogger logger = new StockLogger();
        User user = new User("Jack", "pass", Role.CREWMATE);
        Date future = new Date(System.currentTimeMillis() + 1000000);
        
        Stock liquidStock = new Stock("Rum", 10, "liquid", Condition.USABLE, future);
        Stock dryStock = new Stock("Food", 5, "dry", Condition.USABLE, future);
        Compartment comp = new Compartment("Test", new ArrayList<>(), 100, "general");
        
        logger.logStockChange(user, liquidStock, comp);
        logger.logStockChange(user, dryStock, comp);
        
        List<String> qualities = List.of("liquid");
        List<StockChange> filtered = logger.filterBySpecialQuality(qualities);
        
        assertEquals(1, filtered.size());
        assertEquals("Rum", filtered.get(0).getStock().getName());
    }

    @Test
    void testFilterByCrewMate() {
        StockLogger logger = new StockLogger();
        User jack = new User("Jack", "pass", Role.CREWMATE);
        User jill = new User("Jill", "pass", Role.CREWMATE);
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 10, "liquid", Condition.USABLE, future);
        Compartment comp = new Compartment("Test", new ArrayList<>(), 100, "general");
        
        logger.logStockChange(jack, stock, comp);
        logger.logStockChange(jill, stock, comp);
        
        List<User> crewMates = List.of(jack);
        List<StockChange> filtered = logger.filterByCrewMate(crewMates);
        
        assertEquals(1, filtered.size());
        assertEquals("Jack", filtered.get(0).getCrewMate().getName());
    }

    @Test
    void testFilterByTimeRange() {
        StockLogger logger = new StockLogger();
        User user = new User("Jack", "pass", Role.CREWMATE);
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 10, "liquid", Condition.USABLE, future);
        Compartment comp = new Compartment("Test", new ArrayList<>(), 100, "general");
        
        // Log first, then get timestamps
        logger.logStockChange(user, stock, comp);
        
        // Use null for start/end to test edge cases
        List<StockChange> allChanges = logger.filterByTimeRange(null, null);
        assertEquals(1, allChanges.size());
        
        // Test with past start date
        Date pastStart = new Date(System.currentTimeMillis() - 1000000);
        List<StockChange> pastChanges = logger.filterByTimeRange(pastStart, null);
        assertEquals(1, pastChanges.size());
        
        // Test with future end date
        Date futureEnd = new Date(System.currentTimeMillis() + 1000000);
        List<StockChange> futureChanges = logger.filterByTimeRange(null, futureEnd);
        assertEquals(1, futureChanges.size());
    }
}