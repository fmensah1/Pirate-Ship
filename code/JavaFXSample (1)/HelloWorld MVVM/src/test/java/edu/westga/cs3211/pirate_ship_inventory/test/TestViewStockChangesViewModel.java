package edu.westga.cs3211.pirate_ship_inventory.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import edu.westga.cs3211.pirate_ship_inventory.model.*;
import edu.westga.cs3211.pirate_ship_inventory.viewmodel.ViewStockChangesViewModel;

class TestViewStockChangesViewModel {

    private Inventory inventory;
    private StockLogger stockLogger;
    private ViewStockChangesViewModel viewModel;

    @BeforeEach
    void setUp() {
        this.inventory = new Inventory();
        this.stockLogger = this.inventory.getStockLogger();
        this.viewModel = new ViewStockChangesViewModel(this.inventory);
    }

    @Test
    void testInitialState() {

        assertNotNull(viewModel.statusMessageProperty().get());
        assertNull(viewModel.startDateProperty().get());
        assertNull(viewModel.endDateProperty().get());
        assertFalse(viewModel.flammableFilterProperty().get());
        assertFalse(viewModel.liquidFilterProperty().get());
        assertFalse(viewModel.perishableFilterProperty().get());
        assertEquals(0, viewModel.totalChangesProperty().get()); 
    }

    @Test
    void testLoadAllChanges() {
        addSampleStockChanges();
        
        viewModel.loadAllChanges();
        
        assertTrue(viewModel.getStockChanges().size() > 0);
        assertTrue(viewModel.statusMessageProperty().get().contains("Loaded"));
        assertEquals(2, viewModel.totalChangesProperty().get());
    }

    @Test
    void testApplyQualityFiltersNoSelection() {
        addSampleStockChanges();
        viewModel.loadAllChanges();
        
        viewModel.applyQualityFilters();
        
        // Should show all changes when no filters selected
        assertEquals(2, viewModel.getStockChanges().size());
        assertTrue(viewModel.statusMessageProperty().get().contains("Showing 2 changes"));
    }

    @Test
    void testApplyQualityFiltersWithLiquid() {
        addSampleStockChanges();
        viewModel.loadAllChanges();
        
        viewModel.liquidFilterProperty().set(true);
        viewModel.applyQualityFilters();
        
        // Should only show liquid items
        assertEquals(1, viewModel.getStockChanges().size());
        assertEquals("Rum", viewModel.getStockChanges().get(0).getStock().getName());
        assertTrue(viewModel.statusMessageProperty().get().contains("Showing 1 changes"));
    }

    @Test
    void testApplyQualityFiltersWithFlammable() {
        addSampleStockChanges();
        viewModel.loadAllChanges();
        
        viewModel.flammableFilterProperty().set(true);
        viewModel.applyQualityFilters();
        
        // Should only show flammable items
        assertEquals(1, viewModel.getStockChanges().size());
        assertEquals("Gunpowder", viewModel.getStockChanges().get(0).getStock().getName());
    }

    @Test
    void testApplyQualityFiltersMultipleSelections() {
        addSampleStockChanges();
        viewModel.loadAllChanges();
        
        viewModel.liquidFilterProperty().set(true);
        viewModel.flammableFilterProperty().set(true);
        viewModel.applyQualityFilters();
        
        // Should show items that are liquid OR flammable
        assertEquals(2, viewModel.getStockChanges().size());
    }

    @Test
    void testApplyTimeFilterValidRange() {
        addSampleStockChanges();
        viewModel.loadAllChanges();
        
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now().plusDays(1);
        
        viewModel.startDateProperty().set(start);
        viewModel.endDateProperty().set(end);
        viewModel.applyTimeFilter();
        
        assertTrue(viewModel.statusMessageProperty().get().contains("Showing"));
    }

    @Test
    void testApplyTimeFilterInvalidRange() {
        addSampleStockChanges();
        viewModel.loadAllChanges();
        
        LocalDate start = LocalDate.now().plusDays(1); // Start in future
        LocalDate end = LocalDate.now().minusDays(1); // End in past
        
        viewModel.startDateProperty().set(start);
        viewModel.endDateProperty().set(end);
        viewModel.applyTimeFilter();
        
        assertTrue(viewModel.statusMessageProperty().get().contains("Error: End date must be after start date"));
    }

    @Test
    void testApplyTimeFilterNullDates() {
        addSampleStockChanges();
        viewModel.loadAllChanges();
        
        // Should work with null dates (no filtering)
        viewModel.applyTimeFilter();
        
        assertTrue(viewModel.statusMessageProperty().get().contains("Showing"));
    }

    @Test
    void testClearFilters() {
        addSampleStockChanges();
        viewModel.loadAllChanges();
        
        // Set some filters
        viewModel.flammableFilterProperty().set(true);
        viewModel.liquidFilterProperty().set(true);
        viewModel.startDateProperty().set(LocalDate.now());
        viewModel.endDateProperty().set(LocalDate.now().plusDays(1));
        
        viewModel.clearFilters();
        
        // Should reset all filters
        assertFalse(viewModel.flammableFilterProperty().get());
        assertFalse(viewModel.liquidFilterProperty().get());
        assertFalse(viewModel.perishableFilterProperty().get());
        assertNull(viewModel.startDateProperty().get());
        assertNull(viewModel.endDateProperty().get());
        assertEquals("All filters cleared", viewModel.statusMessageProperty().get());
    }

    @Test
    void testPropertyBindingsWork() {
        // Test that properties can be set and retrieved
        LocalDate testDate = LocalDate.now();
        viewModel.startDateProperty().set(testDate);
        assertEquals(testDate, viewModel.startDateProperty().get());
        
        viewModel.statusMessageProperty().set("Test message");
        assertEquals("Test message", viewModel.statusMessageProperty().get());
        
        viewModel.flammableFilterProperty().set(true);
        assertTrue(viewModel.flammableFilterProperty().get());
    }

    @Test
    void testViewModelWithNullInventory() {
        // Should handle null inventory gracefully
        ViewStockChangesViewModel nullViewModel = new ViewStockChangesViewModel(null);
        
        nullViewModel.loadAllChanges();
        nullViewModel.applyQualityFilters();
        nullViewModel.applyTimeFilter();
        
        // Should not throw exceptions
        assertNotNull(nullViewModel.getStockChanges());
        assertTrue(nullViewModel.getStockChanges().isEmpty());
    }

    private void addSampleStockChanges() {
        User user1 = new User("Jack", "pass", Role.CREWMATE);
        User user2 = new User("Jill", "pass", Role.QUARTERMASTER);
        
        Stock liquidStock = new Stock("Rum", 10, "liquid", Condition.USABLE, new Date());
        Stock flammableStock = new Stock("Gunpowder", 5, "flammable", Condition.PERFECT, new Date());
        
        Compartment comp1 = new Compartment("Liquid", new ArrayList<>(), 100, "liquid");
        Compartment comp2 = new Compartment("Flammable", new ArrayList<>(), 100, "flammable");
        
        this.stockLogger.logStockChange(user1, liquidStock, comp1);
        this.stockLogger.logStockChange(user2, flammableStock, comp2);
    }
}