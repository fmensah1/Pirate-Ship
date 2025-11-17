package edu.westga.cs3211.pirate_ship_inventory.test;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory.model.Compartment;
import edu.westga.cs3211.pirate_ship_inventory.model.Condition;
import edu.westga.cs3211.pirate_ship_inventory.model.Stock;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Date;

class TestCompartment {

    @Test
    void testValidCompartment() {
        ArrayList<Stock> items = new ArrayList<>();
        Compartment comp = new Compartment("A1", items, 100, "general");
        
        assertEquals("A1", comp.getLabel());
        assertEquals(100, comp.getCapacity());
        assertEquals("general", comp.getSpecialQuality());
    }

    @Test
    void testConstructorNullLabelThrows() {
        ArrayList<Stock> items = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Compartment(null, items, 100, "general");
        });
    }

    @Test
    void testConstructorEmptyLabelThrows() {
        ArrayList<Stock> items = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Compartment("   ", items, 100, "general");
        });
    }

    @Test
    void testConstructorZeroCapacityThrows() {
        ArrayList<Stock> items = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Compartment("A1", items, 0, "general");
        });
    }

    @Test
    void testConstructorNullSpecialQualityThrows() {
        ArrayList<Stock> items = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Compartment("A1", items, 100, null);
        });
    }

    @Test
    void testConstructorEmptySpecialQualityThrows() {
        ArrayList<Stock> items = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Compartment("A1", items, 100, "   ");
        });
    }

    @Test
    void testSpaceCalculations() {
        ArrayList<Stock> items = new ArrayList<>();
        Date future = new Date(System.currentTimeMillis() + 1000000);
        items.add(new Stock("Rum", 20, "liquid", Condition.USABLE, future));
        items.add(new Stock("Food", 30, "dry", Condition.USABLE, future));
        
        Compartment comp = new Compartment("A1", items, 100, "general");
        
        assertEquals(50, comp.getUsedSpace());
        assertEquals(50, comp.getFreeSpace());
    }

    @Test
    void testCanStoreWithNullStock() {
        ArrayList<Stock> items = new ArrayList<>();
        Compartment comp = new Compartment("A1", items, 100, "general");
        assertFalse(comp.canStore(null));
    }

    @Test
    void testCanStoreWithEnoughSpace() {
        ArrayList<Stock> items = new ArrayList<>();
        Compartment comp = new Compartment("A1", items, 100, "general");
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 50, "liquid", Condition.USABLE, future);
        
        assertTrue(comp.canStore(stock));
    }

    @Test
    void testCanStoreWithNotEnoughSpace() {
        ArrayList<Stock> items = new ArrayList<>();
        Date future = new Date(System.currentTimeMillis() + 1000000);
        items.add(new Stock("Rum", 80, "liquid", Condition.USABLE, future));
        Compartment comp = new Compartment("A1", items, 100, "general");
        Stock stock = new Stock("Food", 30, "dry", Condition.USABLE, future);
        
        assertFalse(comp.canStore(stock));
    }

    @Test
    void testCompatibleGeneralStorage() {
        ArrayList<Stock> items = new ArrayList<>();
        Compartment comp = new Compartment("A1", items, 100, "general");
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 10, "liquid", Condition.USABLE, future);
        
        assertTrue(comp.canStore(stock));
    }

    @Test
    void testCompatibleSpecificStorage() {
        ArrayList<Stock> items = new ArrayList<>();
        Compartment comp = new Compartment("A1", items, 100, "liquid");
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 10, "liquid", Condition.USABLE, future);
        
        assertTrue(comp.canStore(stock));
    }

    @Test
    void testIncompatibleStorage() {
        ArrayList<Stock> items = new ArrayList<>();
        Compartment comp = new Compartment("A1", items, 100, "liquid");
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Food", 10, "dry", Condition.USABLE, future);
        
        assertFalse(comp.canStore(stock));
    }

    @Test
    void testAddStockValid() {
        ArrayList<Stock> items = new ArrayList<>();
        Compartment comp = new Compartment("A1", items, 100, "general");
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 10, "liquid", Condition.USABLE, future);
        
        comp.addStock(stock);
        
        assertEquals(1, comp.getItems().size());
        assertEquals("Rum", comp.getItems().get(0).getName());
    }

    @Test
    void testAddStockNullThrows() {
        ArrayList<Stock> items = new ArrayList<>();
        Compartment comp = new Compartment("A1", items, 100, "general");
        
        assertThrows(IllegalArgumentException.class, () -> {
            comp.addStock(null);
        });
    }

    @Test
    void testAddStockIncompatibleThrows() {
        ArrayList<Stock> items = new ArrayList<>();
        Compartment comp = new Compartment("A1", items, 100, "liquid");
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Food", 10, "dry", Condition.USABLE, future);
        
        assertThrows(IllegalStateException.class, () -> {
            comp.addStock(stock);
        });
    }

    @Test
    void testToString() {
        ArrayList<Stock> items = new ArrayList<>();
        Compartment comp = new Compartment("A1", items, 100, "liquid");
        String result = comp.toString();
        
        assertTrue(result.contains("A1"));
        assertTrue(result.contains("liquid"));
    }
}