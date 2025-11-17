package edu.westga.cs3211.pirate_ship_inventory.model;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Stock.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class Stock {
	
	/** The name. */
	private String name;
	
	/** The size. */
	private int size;
	
	/** The special quals. */
	private String specialQuals;
	
	/** The condition. */
	private Condition condition;
	
	/** The expiration date. */
	private Date expirationDate;
	
	/**
	 * Instantiates a new stock.
	 *
	 * @param name the name
	 * @param size the size
	 * @param specialQuals the special quals
	 * @param condition the condition
	 * @param expirationDate the expiration date
	 */
	public Stock(String name, int size, String specialQuals, Condition condition, Date expirationDate) {

	    if (name == null) {
	        throw new IllegalArgumentException("Name cannot be null");
	    }
	    if (name.trim().isEmpty()) {
	        throw new IllegalArgumentException("Name cannot be empty");
	    }

	    if (size <= 0) {
	        throw new IllegalArgumentException("Size must be greater than zero");
	    }

	    if (specialQuals == null) {
	        throw new IllegalArgumentException("Special qualities cannot be null");
	    }

	    if (condition == null) {
	        throw new IllegalArgumentException("Condition cannot be null");
	    }

	    if (specialQuals.toLowerCase().contains("perishable")) {
            if (expirationDate == null) {
                throw new IllegalArgumentException("Perishable stock must have an expiration date");
        }  
            
	    Date today = new Date();
	    if (expirationDate.before(today)) {
	        throw new IllegalArgumentException("Expiration date cannot be in the past");
	    }
 
	    }
	    
	    this.name = name;
	    this.size = size;
	    this.specialQuals = specialQuals.trim();
	    this.condition = condition;
	    this.expirationDate = expirationDate;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Gets the special quals.
	 *
	 * @return the special quals
	 */
	public String getSpecialQuals() {
		return this.specialQuals;
	}

	/**
	 * Sets the special quals.
	 *
	 * @param specialQuals the new special quals
	 */
	public void setSpecialQuals(String specialQuals) {
		this.specialQuals = specialQuals;
	}

	/**
	 * Gets the condition.
	 *
	 * @return the condition
	 */
	public Condition getCondition() {
		return this.condition;
	}

	/**
	 * Sets the condition.
	 *
	 * @param newCondition the new Condition
	 */
	public void setCondition(Condition newCondition) {
	    if (newCondition == null) {
            throw new IllegalArgumentException("Condition cannot be null");
        }
        this.condition = newCondition;
    }

	/**
	 * Gets the expiration date.
	 *
	 * @return the expiration date
	 */
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	/**
	 * Sets the expiration date.
	 *
	 * @param expirationDate the new expiration date
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	  @Override
	    public String toString() {
	        return "Stock item: " + this.name
	                + ", size: " + this.size
	                + ", special qualities: " + this.specialQuals
	                + ", condition: " + this.condition
	                + ", expiration: " + this.expirationDate;
	    }
	
}
