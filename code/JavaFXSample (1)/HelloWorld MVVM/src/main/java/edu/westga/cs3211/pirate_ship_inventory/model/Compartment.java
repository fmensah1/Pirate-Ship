package edu.westga.cs3211.pirate_ship_inventory.model;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Compartment.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class Compartment {
	
	/** The label. */
	private String label;
	
	/** The items. */
	private List<Stock> items;
	
	/** The capacity. */
	private int capacity;
	
	/** The special quality. */
	private String specialQuality;
	
	/**
	 * Instantiates a new compartment.
	 *
	 * @param label the label
	 * @param items the items
	 * @param capacity the capacity
	 * @param specialQuality the special quality
	 */
	public Compartment(String label, ArrayList<Stock> items, int capacity, String specialQuality) {
	    if (label == null || label.isBlank()) {
	        throw new IllegalArgumentException("Label cannot be null or empty");
	    }
	    if (capacity <= 0) {
	        throw new IllegalArgumentException("Capacity must be positive");
	    }
	    if (specialQuality == null || specialQuality.isBlank()) {
	        throw new IllegalArgumentException("Special quality cannot be null or empty");
	    }

	    this.label = label;
	    
	    if (items != null) {
	        this.items = items;
	    } else {
	        this.items = new ArrayList<>();
	    }
	    
	    this.capacity = capacity;
	    this.specialQuality = specialQuality.toLowerCase();
	}
	
	    /**
    	 * Gets the label.
    	 *
    	 * @return the label
    	 */
    	public String getLabel() {
	        return this.label;
	    }

	    /**
    	 * Gets the items.
    	 *
    	 * @return the items
    	 */
    	public List<Stock> getItems() {
	        return this.items;
	    }

	    /**
    	 * Gets the capacity.
    	 *
    	 * @return the capacity
    	 */
    	public int getCapacity() {
	        return this.capacity;
	    }

	    /**
    	 * Gets the used space.
    	 *
    	 * @return the used space
    	 */
    	public int getUsedSpace() {
	        int total = 0;
	        for (Stock stock1 : this.items) {
	            total += stock1.getSize();
	        }
	        return total;
	    }

	    /**
    	 * Gets the free space.
    	 *
    	 * @return the free space
    	 */
    	public int getFreeSpace() {
	        return this.capacity - this.getUsedSpace();
	    }

	    /**
    	 * Can store.
    	 *
    	 * @param stock the stock
    	 * @return true, if successful
    	 */
    	public boolean canStore(Stock stock) {
	        if (stock == null) {
	            return false;
	        }
	        
	        if (!this.isCompatibleWith(stock)) {
	            return false;
	        }
	        return stock.getSize() <= this.getFreeSpace();
	    }
	    
	    /**
    	 * Checks if is compatible with.
    	 *
    	 * @param stock the stock
    	 * @return true, if is compatible with
    	 */
    	private boolean isCompatibleWith(Stock stock) {
	        if (this.specialQuality.equals("general")) {
	            return true;
	        }
	 
	        String[] stockQualities = stock.getSpecialQuals().split(",");
	        for (String quality : stockQualities) {
	            quality = quality.trim().toLowerCase();
	            if (!quality.isEmpty() && !this.specialQuality.contains(quality)) {
	                return false;
	            }
	        }
	        return true;
	    }

	    /**
    	 * Adds the stock.
    	 *
    	 * @param stock the stock
    	 */
    	public void addStock(Stock stock) {
	        if (stock == null) {
	            throw new IllegalArgumentException("Stock cannot be null");
	        }
	        if (!this.canStore(stock)) {
	            throw new IllegalStateException("Compartment cannot store this stock - either incompatible type or not enough space");
	        }
	        this.items.add(stock);
	    }
	    
	    /**
    	 * Gets the special quality.
    	 *
    	 * @return the special quality
    	 */
    	public String getSpecialQuality() {
	         return this.specialQuality;
	     }
	    
	    /**
    	 * To string.
    	 *
    	 * @return the string
    	 */
    	@Override
	    public String toString() {
	        return String.format("%s (%s) - %d/%d (%d free)", 
	            this.label, 
	            this.specialQuality, 
	            this.getUsedSpace(), 
	            this.capacity, 
	            this.getFreeSpace());
	    }
	}