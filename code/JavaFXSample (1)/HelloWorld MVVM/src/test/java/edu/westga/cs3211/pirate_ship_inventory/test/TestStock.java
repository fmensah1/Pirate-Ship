package edu.westga.cs3211.pirate_ship_inventory.test;


import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirate_ship_inventory.model.Condition;
import edu.westga.cs3211.pirate_ship_inventory.model.Stock;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

class TestStock {

    @Test
    void testValidStock() {
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 5, "Aged", Condition.USABLE, future);
        
        assertEquals("Rum", stock.getName());
        assertEquals(5, stock.getSize());
        assertEquals("Aged", stock.getSpecialQuals());
        assertEquals(Condition.USABLE, stock.getCondition());
        assertEquals(future, stock.getExpirationDate());
    }

    @Test
    void testNullNameThrows() {
        Date future = new Date(System.currentTimeMillis() + 1000000);
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock(null, 5, "Test", Condition.USABLE, future);
        });
    }

    @Test
    void testEmptyNameThrows() {
        Date future = new Date(System.currentTimeMillis() + 1000000);
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("   ", 5, "Test", Condition.USABLE, future);
        });
    }

    @Test
    void testZeroSizeThrows() {
        Date future = new Date(System.currentTimeMillis() + 1000000);
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Test", 0, "Test", Condition.USABLE, future);
        });
    }

    @Test
    void testPerishableWithoutDateThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fruit", 5, "perishable", Condition.USABLE, null);
        });
    }

    @Test
    void testPastExpirationDateThrows() {
        Date past = new Date(System.currentTimeMillis() - 1000000);
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Fruit", 5, "perishable", Condition.USABLE, past);
        });
    }

    @Test
    void testSettersWork() {
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Test", 1, "Test", Condition.USABLE, future);
        
        stock.setName("NewName");
        stock.setSize(10);
        stock.setSpecialQuals("NewQuals");
        stock.setCondition(Condition.PERFECT);
        stock.setExpirationDate(null);
        
        assertEquals("NewName", stock.getName());
        assertEquals(10, stock.getSize());
        assertEquals("NewQuals", stock.getSpecialQuals());
        assertEquals(Condition.PERFECT, stock.getCondition());
        assertNull(stock.getExpirationDate());
    }

    @Test
    void testSetConditionNullThrows() {
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Test", 1, "Test", Condition.USABLE, future);
        assertThrows(IllegalArgumentException.class, () -> {
            stock.setCondition(null);
        });
    }

    @Test
    void testToString() {
        Date future = new Date(System.currentTimeMillis() + 1000000);
        Stock stock = new Stock("Rum", 5, "Aged", Condition.USABLE, future);
        String result = stock.toString();
        
        assertTrue(result.contains("Rum"));
        assertTrue(result.contains("5"));
        assertTrue(result.contains("Aged"));
        assertTrue(result.contains("USABLE"));
    }
    
    @Test
    void testNonPerishableCanHaveNullExpiration() {
        // This should NOT throw - specialQuals doesn't contain "perishable"
        Stock stock = new Stock("Cannon", 2, "Heavy", Condition.PERFECT, null);
        assertNull(stock.getExpirationDate());
    }

    @Test
    void testNonPerishableCanHavePastExpiration() {
        Date past = new Date(System.currentTimeMillis() - 1000000);
        // This should NOT throw - specialQuals doesn't contain "perishable"  
        Stock stock = new Stock("Cannon", 2, "Heavy", Condition.PERFECT, past);
        assertEquals(past, stock.getExpirationDate());
    }

    @Test
    void testPerishableWithFutureDateWorks() {
        Date future = new Date(System.currentTimeMillis() + 1000000);
        // This should NOT throw - perishable with future date is valid
        Stock stock = new Stock("Fruit", 5, "perishable", Condition.USABLE, future);
        assertEquals(future, stock.getExpirationDate());
    }
    
    @Test
    public void testSpecialQualsNull() {
        // Try creating a stock object with null specialQuals
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Cannonballs", 100, null, Condition.USABLE, new Date());
        });
    }
    
    @Test
    public void testConditionNull() {
        // Try creating a stock object with null condition
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Cannonballs", 100, "flammable", null, new Date());
        });
    }


}
