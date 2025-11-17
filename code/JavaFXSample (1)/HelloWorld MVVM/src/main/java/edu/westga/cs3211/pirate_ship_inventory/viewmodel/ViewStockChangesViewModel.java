package edu.westga.cs3211.pirate_ship_inventory.viewmodel;

import edu.westga.cs3211.pirate_ship_inventory.model.Inventory;
import edu.westga.cs3211.pirate_ship_inventory.model.StockChange;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ViewStockChangesViewModel.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class ViewStockChangesViewModel {
    
    /** The stock changes. */
    private final ObservableList<StockChange> stockChanges = FXCollections.observableArrayList();
    
    /** The start date. */
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    
    /** The end date. */
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    
    /** The status message. */
    private final StringProperty statusMessage = new SimpleStringProperty("");
    
    /** The flammable filter. */
    private final BooleanProperty flammableFilter = new SimpleBooleanProperty(false);
    
    /** The liquid filter. */
    private final BooleanProperty liquidFilter = new SimpleBooleanProperty(false);
    
    /** The perishable filter. */
    private final BooleanProperty perishableFilter = new SimpleBooleanProperty(false);
    
    /** The total changes. */
    private final IntegerProperty totalChanges = new SimpleIntegerProperty(0);
    
    /** The inventory. */
    private Inventory inventory;
    
    /**
     * Instantiates a new view stock changes view model.
     *
     * @param inventory the inventory
     */
    public ViewStockChangesViewModel(Inventory inventory) {
        this.inventory = inventory;
        this.loadAllChanges();
    }
    
    /**
     * Load all changes.
     */
    public void loadAllChanges() {
        if (this.inventory != null && this.inventory.getStockLogger() != null) {
            List<StockChange> changes = this.inventory.getStockLogger().getAllChanges();
            this.stockChanges.setAll(changes);
            this.totalChanges.set(changes.size());
            this.statusMessage.set("Loaded " + changes.size() + " stock changes");
        }
    }
    
    /**
     * Apply quality filters.
     */
    public void applyQualityFilters() {
        if (this.inventory == null || this.inventory.getStockLogger() == null) {
			return;
		}
        
        List<String> selectedQualities = new ArrayList<>();
        if (this.flammableFilter.get()) {
			selectedQualities.add("flammable");
		}
        if (this.liquidFilter.get()) {
			selectedQualities.add("liquid");
		}
        if (this.perishableFilter.get()) {
			selectedQualities.add("perishable");
		}

        List<StockChange> filteredChanges;
        
        if (selectedQualities.isEmpty()) {
            filteredChanges = this.inventory.getStockLogger().getAllChanges();
        } else {
            filteredChanges = this.inventory.getStockLogger().filterBySpecialQuality(selectedQualities);
        }
        
        this.stockChanges.setAll(filteredChanges);
        this.totalChanges.set(filteredChanges.size());
        this.statusMessage.set("Showing " + filteredChanges.size() + " changes matching selected filters");
    }
    
    /**
     * Apply time filter.
     */
    public void applyTimeFilter() {
        if (this.inventory == null || this.inventory.getStockLogger() == null) {
            return;
        }
        
        LocalDate startLocal = this.startDate.get();
        LocalDate endLocal = this.endDate.get();
        
        if (startLocal != null && endLocal != null && endLocal.isBefore(startLocal)) {
            this.statusMessage.set("Error: End date must be after start date");
            return;
        }
        
        Date startDate = null;
        if (startLocal != null) {
            startDate = Date.from(startLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        
        Date endDate = null;
        if (endLocal != null) {
            endDate = Date.from(endLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
            
        List<StockChange> filteredChanges = this.inventory.getStockLogger().filterByTimeRange(startDate, endDate);
        this.stockChanges.setAll(filteredChanges);
        this.totalChanges.set(filteredChanges.size());
        this.statusMessage.set("Showing " + filteredChanges.size() + " changes within time range");
    }
    
    /**
     * Clear filters.
     */
    public void clearFilters() {
    	this.flammableFilter.set(false);
    	this.liquidFilter.set(false);
    	this.perishableFilter.set(false);
    	this.startDate.set(null);
    	this.endDate.set(null);
    	this.loadAllChanges();
    	this.statusMessage.set("All filters cleared");
    }
    
    /**
     * Gets the stock changes.
     *
     * @return the stock changes
     */
    // Property getters
    public ObservableList<StockChange> getStockChanges() {
        return this.stockChanges;
    }
    
    /**
     * Start date property.
     *
     * @return the object property
     */
    public ObjectProperty<LocalDate> startDateProperty() {
        return this.startDate;
    }
    
    /**
     * End date property.
     *
     * @return the object property
     */
    public ObjectProperty<LocalDate> endDateProperty() {
        return this.endDate;
    }
    
    /**
     * Status message property.
     *
     * @return the string property
     */
    public StringProperty statusMessageProperty() {
        return this.statusMessage;
    }
    
    /**
     * Flammable filter property.
     *
     * @return the boolean property
     */
    public BooleanProperty flammableFilterProperty() {
        return this.flammableFilter;
    }
    
    /**
     * Liquid filter property.
     *
     * @return the boolean property
     */
    public BooleanProperty liquidFilterProperty() {
        return this.liquidFilter;
    }
    
    /**
     * Perishable filter property.
     *
     * @return the boolean property
     */
    public BooleanProperty perishableFilterProperty() {
        return this.perishableFilter;
    }
    
    /**
     * Total changes property.
     *
     * @return the integer property
     */
    public IntegerProperty totalChangesProperty() {
        return this.totalChanges;
    }
}