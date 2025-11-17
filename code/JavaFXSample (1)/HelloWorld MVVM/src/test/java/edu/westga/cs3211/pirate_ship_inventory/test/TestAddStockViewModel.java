package edu.westga.cs3211.pirate_ship_inventory.test;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Date;
import edu.westga.cs3211.pirate_ship_inventory.model.Compartment;
import edu.westga.cs3211.pirate_ship_inventory.model.Condition;
import edu.westga.cs3211.pirate_ship_inventory.viewmodel.AddStockViewModel;

class TestAddStockViewModel {

    @Test
    void testInitialState() {
        AddStockViewModel viewModel = new AddStockViewModel();
        
        assertEquals("", viewModel.itemNameProperty().get());
        assertEquals("", viewModel.itemSizeTextProperty().get());
        assertEquals(Condition.USABLE, viewModel.conditionProperty().get());
        assertFalse(viewModel.flammableProperty().get());
        assertFalse(viewModel.liquidProperty().get());
        assertFalse(viewModel.perishableProperty().get());
        assertNull(viewModel.expiryDateProperty().get());
    }

    @Test
    void testParseSizeValid() {
        AddStockViewModel viewModel = new AddStockViewModel();
        viewModel.itemSizeTextProperty().set("42");
        
        int result = viewModel.parseSize();
        assertEquals(42, result);
    }

    @Test
    void testParseSizeWithSpaces() {
        AddStockViewModel viewModel = new AddStockViewModel();
        viewModel.itemSizeTextProperty().set("  100  ");
        
        int result = viewModel.parseSize();
        assertEquals(100, result);
    }

    @Test
    void testBuildSpecialQualsEmpty() {
        AddStockViewModel viewModel = new AddStockViewModel();
        
        String result = viewModel.buildSpecialQuals();
        assertEquals("", result);
    }

    @Test
    void testBuildSpecialQualsFlammable() {
        AddStockViewModel viewModel = new AddStockViewModel();
        viewModel.flammableProperty().set(true);
        
        String result = viewModel.buildSpecialQuals();
        assertEquals("flammable", result);
    }

    @Test
    void testBuildSpecialQualsLiquid() {
        AddStockViewModel viewModel = new AddStockViewModel();
        viewModel.liquidProperty().set(true);
        
        String result = viewModel.buildSpecialQuals();
        assertEquals("liquid", result);
    }

    @Test
    void testBuildSpecialQualsPerishable() {
        AddStockViewModel viewModel = new AddStockViewModel();
        viewModel.perishableProperty().set(true);
        
        String result = viewModel.buildSpecialQuals();
        assertEquals("perishable", result);
    }

    @Test
    void testBuildSpecialQualsAll() {
        AddStockViewModel viewModel = new AddStockViewModel();
        viewModel.flammableProperty().set(true);
        viewModel.liquidProperty().set(true);
        viewModel.perishableProperty().set(true);
        
        String result = viewModel.buildSpecialQuals();
        assertEquals("flammable,liquid,perishable", result);
    }

    @Test
    void testAddStockSuccess() {
        AddStockViewModel viewModel = new AddStockViewModel();
        Compartment comp = new Compartment("Test", new ArrayList<>(), 100, "general");
        Date future = new Date(System.currentTimeMillis() + 1000000);
        
        String result = viewModel.addStock("Rum", 10, Condition.USABLE, "liquid", future, comp);
        
        assertEquals("✓ Stock added successfully!", result);
        assertEquals(1, comp.getItems().size());
    }

    @Test
    void testAddStockEmptyName() {
        AddStockViewModel viewModel = new AddStockViewModel();
        Compartment comp = new Compartment("Test", new ArrayList<>(), 100, "general");
        Date future = new Date(System.currentTimeMillis() + 1000000);
        
        String result = viewModel.addStock("", 10, Condition.USABLE, "liquid", future, comp);
        
        assertEquals("Item name cannot be empty", result);
    }

    @Test
    void testAddStockNullName() {
        AddStockViewModel viewModel = new AddStockViewModel();
        Compartment comp = new Compartment("Test", new ArrayList<>(), 100, "general");
        Date future = new Date(System.currentTimeMillis() + 1000000);
        
        String result = viewModel.addStock(null, 10, Condition.USABLE, "liquid", future, comp);
        
        assertEquals("Item name cannot be empty", result);
    }

    @Test
    void testAddStockInvalidSize() {
        AddStockViewModel viewModel = new AddStockViewModel();
        Compartment comp = new Compartment("Test", new ArrayList<>(), 100, "general");
        Date future = new Date(System.currentTimeMillis() + 1000000);
        
        String result = viewModel.addStock("Rum", 0, Condition.USABLE, "liquid", future, comp);
        
        assertEquals("Size must be greater than 0", result);
    }

    @Test
    void testAddStockNullCompartment() {
        AddStockViewModel viewModel = new AddStockViewModel();
        Date future = new Date(System.currentTimeMillis() + 1000000);
        
        String result = viewModel.addStock("Rum", 10, Condition.USABLE, "liquid", future, null);
        
        assertEquals("Select a compartment", result);
    }

    @Test
    void testAddStockIncompatibleCompartment() {
        AddStockViewModel viewModel = new AddStockViewModel();
        Compartment comp = new Compartment("Test", new ArrayList<>(), 5, "general"); // Only 5 capacity
        Date future = new Date(System.currentTimeMillis() + 1000000);
        
        String result = viewModel.addStock("Rum", 10, Condition.USABLE, "liquid", future, comp);
        
        assertTrue(result.contains("cannot store this item"));
    }

    @Test
    void testAddStockExceptionHandling() {
        AddStockViewModel viewModel = new AddStockViewModel();
        Compartment comp = new Compartment("Test", new ArrayList<>(), 100, "general");

        String result = viewModel.addStock("Fruit", 10, Condition.USABLE, "perishable", null, comp);
        
        assertTrue(result.startsWith("Error:"));
    }
}