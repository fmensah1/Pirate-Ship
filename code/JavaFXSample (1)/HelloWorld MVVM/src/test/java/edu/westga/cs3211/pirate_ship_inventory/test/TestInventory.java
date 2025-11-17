package edu.westga.cs3211.pirate_ship_inventory.test;


import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory.model.Compartment;
import edu.westga.cs3211.pirate_ship_inventory.model.Condition;
import edu.westga.cs3211.pirate_ship_inventory.model.Inventory;
import edu.westga.cs3211.pirate_ship_inventory.model.Stock;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import java.util.List;

class TestInventory {

    @Test
    void testInventoryInitialization() {
        Inventory inventory = new Inventory();
        List<Compartment> compartments = inventory.getCompartments();
        
        assertEquals(4, compartments.size());
        assertEquals("Flammable Storage", compartments.get(0).getLabel());
    }

    @Test
    void testFindCompatibleCompartments() {
        Inventory inventory = new Inventory();
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 10, "liquid", Condition.USABLE, future);
        
        List<Compartment> compatible = inventory.findCompatibleCompartments(stock);
        
        assertTrue(compatible.size() >= 2); // Should find Liquid and General
    }

    @Test
    void testGetCompartmentByLabel() {
        Inventory inventory = new Inventory();
        
        Compartment comp = inventory.getCompartmentByLabel("Liquid Storage");
        assertNotNull(comp);
        assertEquals("Liquid Storage", comp.getLabel());
    }

    @Test
    void testGetCompartmentByLabelNotFound() {
        Inventory inventory = new Inventory();
        
        Compartment comp = inventory.getCompartmentByLabel("Nonexistent");
        assertNull(comp);
    }

    @Test
    void testHasFreeSpace() {
        Inventory inventory = new Inventory();
        Compartment comp = inventory.getCompartmentByLabel("General Storage");
        
        assertTrue(inventory.hasFreeSpace(comp));
    }

    @Test
    void testGetStockLogger() {
        Inventory inventory = new Inventory();
        assertNotNull(inventory.getStockLogger());
    }
}
