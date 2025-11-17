package edu.westga.cs3211.pirate_ship_inventory.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;
import java.util.Date;

import edu.westga.cs3211.pirate_ship_inventory.model.Compartment;
import edu.westga.cs3211.pirate_ship_inventory.model.Condition;
import edu.westga.cs3211.pirate_ship_inventory.model.Stock;

// TODO: Auto-generated Javadoc
/**
 * The Class AddStockViewModel.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class AddStockViewModel {

    /** The item name. */
    private final StringProperty itemName = new SimpleStringProperty("");
    
    /** The item size text. */
    private final StringProperty itemSizeText = new SimpleStringProperty("");
    
    /** The condition. */
    private final ObjectProperty<Condition> condition = new SimpleObjectProperty<>(Condition.USABLE);
    
    /** The flammable. */
    private final BooleanProperty flammable = new SimpleBooleanProperty(false);
    
    /** The liquid. */
    private final BooleanProperty liquid = new SimpleBooleanProperty(false);
    
    /** The perishable. */
    private final BooleanProperty perishable = new SimpleBooleanProperty(false);
    
    /** The expiry date. */
    private final ObjectProperty<LocalDate> expiryDate = new SimpleObjectProperty<>(null);

    /**
     * Item name property.
     *
     * @return the string property
     */
    public StringProperty itemNameProperty() {
        return this.itemName;
    }

    /**
     * Item size text property.
     *
     * @return the string property
     */
    public StringProperty itemSizeTextProperty() {
        return this.itemSizeText;
    }

    /**
     * Condition property.
     *
     * @return the object property
     */
    public ObjectProperty<Condition> conditionProperty() {
        return this.condition;
    }

    /**
     * Liquid property.
     *
     * @return the boolean property
     */
    public BooleanProperty liquidProperty() {
        return this.liquid;
    }

    /**
     * Perishable property.
     *
     * @return the boolean property
     */
    public BooleanProperty perishableProperty() {
        return this.perishable;
    }
    
    /**
     * Flammable property.
     *
     * @return the boolean property
     */
    public BooleanProperty flammableProperty() {
        return this.flammable;
    }
    
    /**
     * Expiry date property.
     *
     * @return the object property
     */
    public ObjectProperty<LocalDate> expiryDateProperty() {
        return this.expiryDate;
    }
    
    /**
     * Parse size text to int. Caller handles NumberFormatException.
     *
     * @return the int
     */
    public int parseSize() {
        return Integer.parseInt(this.itemSizeText.get().trim());
    }

    /**
     * Build a comma-separated specialQuals string from the checkboxes.
     *
     * @return the string
     */
    public String buildSpecialQuals() {
        StringBuilder sb = new StringBuilder();
        if (this.flammable.get()) {
            sb.append("flammable");
        }
        if (this.liquid.get()) {
            if (sb.length() > 0) {
				sb.append(",");
			}
            sb.append("liquid");
        }
        if (this.perishable.get()) {
            if (sb.length() > 0) {
				sb.append(",");
			}
            sb.append("perishable");
        }
        return sb.toString();
    }
    
    /**
     * Adds the stock.
     *
     * @param itemName the item name
     * @param size the size
     * @param condition the condition
     * @param specialQuals the special quals
     * @param expDate the exp date
     * @param compartment the compartment
     * @return the string
     */
    public String addStock(String itemName, int size, Condition condition, String specialQuals, Date expDate, Compartment compartment) {
        try {
            if (itemName == null || itemName.isEmpty()) {
                return "Item name cannot be empty";
            }

            if (size <= 0) {
                return "Size must be greater than 0";
            }

            if (compartment == null) {
                return "Select a compartment";
            }

            Stock stock = new Stock(itemName, size, specialQuals, condition, expDate);
            if (!compartment.canStore(stock)) {
                return "Selected compartment cannot store this item (not enough space or incompatible type)";
            }

            compartment.addStock(stock);
            return "✓ Stock added successfully!";
        } catch (Exception ex) {
            return "Error: " + ex.getMessage();
        }
    }
    
}
