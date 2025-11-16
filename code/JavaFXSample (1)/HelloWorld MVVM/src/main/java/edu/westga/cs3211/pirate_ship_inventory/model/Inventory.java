package edu.westga.cs3211.pirate_ship_inventory.model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
	private List<Compartment> compartments;
	
	public Inventory() {
		this.compartments = new ArrayList<>();
		 this.compartments.add(new Compartment("Flammable Storage", new ArrayList<>(), 50, "flammable"));
	        this.compartments.add(new Compartment("Liquid Storage", new ArrayList<>(), 80, "liquid"));
	        this.compartments.add(new Compartment("Perishable Storage", new ArrayList<>(), 60, "perishable"));
	        this.compartments.add(new Compartment("General Storage", new ArrayList<>(), 100, "general"));
	    }
	
	 public List<Compartment> getCompartments() {
	        return new ArrayList<>(this.compartments);
	    }
	 
	 public List<Compartment> findCompatibleCompartments(Stock stock) {
	        List<Compartment> compatible = new ArrayList<>();
	        for (Compartment compartment : this.compartments) {
	            if (compartment.canStore(stock)) {
	                compatible.add(compartment);
	            }
	        }
	        return compatible;
	    }
	 
	 public Compartment getCompartmentByLabel(String label) {
	        for (Compartment compartment : this.compartments) {
	            if (compartment.getLabel().equals(label)) {
	                return compartment;
	            }
	        }
	        return null;
	    }
	 
	public boolean hasFreeSpace(Compartment compartment) {
		return compartment.getFreeSpace() > 0;
		
	}

}
