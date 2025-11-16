package edu.westga.cs3211.pirate_ship_inventory.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Compartment {
	private String label;
	private List<Stock> items;
	private int capacity;
	private String specialQuality;
	
	public Compartment(String label, ArrayList<Stock> items, int capacity, String specialQuality) { // FIXED: parameter name
	    if (label == null || label.isBlank()) {
	        throw new IllegalArgumentException("Label cannot be null or empty");
	    }
	    if (capacity <= 0) {
	        throw new IllegalArgumentException("Capacity must be positive");
	    }
	    if (specialQuality == null || specialQuality.isBlank()) { // ADDED: null check
	        throw new IllegalArgumentException("Special quality cannot be null or empty");
	    }

	    this.label = label;
	    this.items = (items != null) ? items : new ArrayList<>();
	    this.capacity = capacity;
	    this.specialQuality = specialQuality.toLowerCase(); // NOW this works correctly
	}

	    public String getLabel() {
	        return this.label;
	    }

	    public List<Stock> getItems() {
	        return this.items;
	    }

	    public int getCapacity() {
	        return this.capacity;
	    }

	    public int getUsedSpace() {
	        int total = 0;
	        for (Stock s : this.items) {
	            total += s.getSize();
	        }
	        return total;
	    }

	    public int getFreeSpace() {
	        return this.capacity - getUsedSpace();
	    }

	    // Basic version: just checks space for now
	    public boolean canStore(Stock stock) {
	        if (stock == null) {
	            return false;
	        }
	        
	        if (!this.isCompatibleWith(stock)) {
	            return false;
	        }
	        return stock.getSize() <= getFreeSpace();
	    }
	    
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

	    public void addStock(Stock stock) {
	        if (stock == null) {
	            throw new IllegalArgumentException("Stock cannot be null");
	        }
	        if (!canStore(stock)) {
	            throw new IllegalStateException("Compartment cannot store this stock - either incompatible type or not enough space");
	        }
	        this.items.add(stock);
	    }
	    
	    public String getSpecialQuality() {
	         return this.specialQuality;
	     }
	    
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