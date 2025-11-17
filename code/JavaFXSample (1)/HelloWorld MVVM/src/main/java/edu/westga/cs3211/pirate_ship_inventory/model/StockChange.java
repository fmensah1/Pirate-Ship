package edu.westga.cs3211.pirate_ship_inventory.model;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class StockChange.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class StockChange {
    
    /** The crew mate. */
    private final User crewMate;
    
    /** The stock. */
    private final Stock stock;
    
    /** The compartment label. */
    private final String compartmentLabel;
    
    /** The available capacity after. */
    private final int availableCapacityAfter;
    
    /** The timestamp. */
    private final Date timestamp;
    
    /**
     * Instantiates a new stock change.
     *
     * @param crewMate the crew mate
     * @param stock the stock
     * @param compartmentLabel the compartment label
     * @param availableCapacityAfter the available capacity after
     */
    public StockChange(User crewMate, Stock stock, String compartmentLabel, int availableCapacityAfter) {
        if (crewMate == null) {
            throw new IllegalArgumentException("CrewMate cannot be null");
        }
        if (stock == null) {
            throw new IllegalArgumentException("Stock cannot be null");
        }
        if (compartmentLabel == null || compartmentLabel.isBlank()) {
            throw new IllegalArgumentException("Compartment label cannot be null or empty");
        }
        
        this.crewMate = crewMate;
        this.stock = stock;
        this.compartmentLabel = compartmentLabel;
        this.availableCapacityAfter = availableCapacityAfter;
        this.timestamp = new Date(); 
    }
    
    /**
     * Gets the crew mate.
     *
     * @return the crew mate
     */
    // Getters
    public User getCrewMate() { 
    	return this.crewMate; 
    	}
    
    /**
     * Gets the stock.
     *
     * @return the stock
     */
    public Stock getStock() { 
    	return this.stock; 
    }
    
    /**
     * Gets the compartment label.
     *
     * @return the compartment label
     */
    public String getCompartmentLabel() { 
    	return this.compartmentLabel; 
    }
    
    /**
     * Gets the available capacity after.
     *
     * @return the available capacity after
     */
    public int getAvailableCapacityAfter() { 
    	return this.availableCapacityAfter; 
    	}
    
    /**
     * Gets the timestamp.
     *
     * @return the timestamp
     */
    public Date getTimestamp() { 
    	return this.timestamp; 
    }
    
    /**
     * Checks for special quality.
     *
     * @param quality the quality
     * @return true, if successful
     */
    public boolean hasSpecialQuality(String quality) {
        return this.stock.getSpecialQuals().toLowerCase().contains(quality.toLowerCase());
    }
    
    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return String.format("[%s] %s added %s to %s (%d units free)", 
        		this.timestamp, this.crewMate.getName(), this.stock.getName(), this.compartmentLabel, this.availableCapacityAfter);
    }
}