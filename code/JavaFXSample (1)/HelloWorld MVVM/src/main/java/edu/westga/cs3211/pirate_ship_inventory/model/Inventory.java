package edu.westga.cs3211.pirate_ship_inventory.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Inventory.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class Inventory {
	
	/** The compartments. */
	private List<Compartment> compartments;
	
	/** The stock logger. */
	private StockLogger stockLogger;
	
	/**
	 * Instantiates a new inventory.
	 */
	public Inventory() {
		this.compartments = new ArrayList<>();
		 this.compartments.add(new Compartment("Flammable Storage", new ArrayList<>(), 50, "flammable"));
	        this.compartments.add(new Compartment("Liquid Storage", new ArrayList<>(), 80, "liquid"));
	        this.compartments.add(new Compartment("Perishable Storage", new ArrayList<>(), 60, "perishable"));
	        this.compartments.add(new Compartment("General Storage", new ArrayList<>(), 100, "general"));
	        this.stockLogger = new StockLogger();
	    }
	
	 /**
 	 * Gets the compartments.
 	 *
 	 * @return the compartments
 	 */
 	public List<Compartment> getCompartments() {
	        return new ArrayList<>(this.compartments);
	    }
	 
	 /**
 	 * Find compatible compartments.
 	 *
 	 * @param stock the stock
 	 * @return the list
 	 */
 	public List<Compartment> findCompatibleCompartments(Stock stock) {
	        List<Compartment> compatible = new ArrayList<>();
	        for (Compartment compartment : this.compartments) {
	            if (compartment.canStore(stock)) {
	                compatible.add(compartment);
	            }
	        }
	        return compatible;
	    }
	 
	 /**
 	 * Gets the compartment by label.
 	 *
 	 * @param label the label
 	 * @return the compartment by label
 	 */
 	public Compartment getCompartmentByLabel(String label) {
	        for (Compartment compartment : this.compartments) {
	            if (compartment.getLabel().equals(label)) {
	                return compartment;
	            }
	        }
	        return null;
	    }
	 
	/**
	 * Checks for free space.
	 *
	 * @param compartment the compartment
	 * @return true, if successful
	 */
	public boolean hasFreeSpace(Compartment compartment) {
		return compartment.getFreeSpace() > 0;
		
	}
	
	/**
	 * Gets the stock logger.
	 *
	 * @return the stock logger
	 */
	public StockLogger getStockLogger() {
		return this.stockLogger;
	}
}
