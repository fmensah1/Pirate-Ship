package edu.westga.cs3211.pirate_ship_inventory.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;

import edu.westga.cs3211.pirate_ship_inventory.model.Condition;

public class AddStockViewModel {

    private final StringProperty itemName = new SimpleStringProperty("");
    private final StringProperty itemSizeText = new SimpleStringProperty("");
    private final ObjectProperty<Condition> condition = new SimpleObjectProperty<>(Condition.USABLE);
    private final BooleanProperty flammable = new SimpleBooleanProperty(false);
    private final BooleanProperty liquid = new SimpleBooleanProperty(false);
    private final BooleanProperty perishable = new SimpleBooleanProperty(false);
    private final ObjectProperty<LocalDate> expiryDate = new SimpleObjectProperty<>(null);

    public StringProperty itemNameProperty() {
        return this.itemName;
    }

    public StringProperty itemSizeTextProperty() {
        return this.itemSizeText;
    }

    public ObjectProperty<Condition> conditionProperty() {
        return this.condition;
    }

    public BooleanProperty liquidProperty() {
        return this.liquid;
    }

    public BooleanProperty perishableProperty() {
        return this.perishable;
    }
    
    public BooleanProperty flammableProperty() {
        return this.flammable;
    }
    
    public ObjectProperty<LocalDate> expiryDateProperty() {
        return this.expiryDate;
    }
    
    /**
     * Parse size text to int. Caller handles NumberFormatException.
     */
    public int parseSize() {
        return Integer.parseInt(this.itemSizeText.get().trim());
    }

    /**
     * Build a comma-separated specialQuals string from the checkboxes
     */
    public String buildSpecialQuals() {
        StringBuilder sb = new StringBuilder();
        if (this.flammable.get()) {
            sb.append("flammable");
        }
        if (this.liquid.get()) {
            if (sb.length() > 0) sb.append(",");
            sb.append("liquid");
        }
        if (this.perishable.get()) {
            if (sb.length() > 0) sb.append(",");
            sb.append("perishable");
        }
        return sb.toString();
    }

    
}
