package edu.westga.cs3211.pirate_ship_inventory.view;

import edu.westga.cs3211.pirate_ship_inventory.model.Compartment;
import edu.westga.cs3211.pirate_ship_inventory.model.Condition;
import edu.westga.cs3211.pirate_ship_inventory.model.Inventory;
import edu.westga.cs3211.pirate_ship_inventory.model.Stock;
import edu.westga.cs3211.pirate_ship_inventory.model.User;
import edu.westga.cs3211.pirate_ship_inventory.viewmodel.AddStockViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDate;

public class AddStockCodeBehind {

    // FIXED: Updated UI components
    @FXML private TextField itemNameTextField;
    @FXML private TextField itemSizeTextField;
    @FXML private ComboBox<Condition> conditionComboBox; // NEW: Condition selector
    @FXML private CheckBox flammableCheckBox; // NEW: Flammable checkbox
    @FXML private CheckBox liquidCheckBox;
    @FXML private CheckBox perishableCheckBox;
    @FXML private ComboBox<Compartment> compartmentComboBox;
    @FXML private DatePicker expiryDate;
    @FXML private Button addStockButton;
    @FXML private Label feedbackLabel;
    @FXML private Label capacityLabel;

    private AddStockViewModel vm = new AddStockViewModel();
    private Inventory inventory;
    private User currentUser;

    @FXML
    public void initialize() {
        // Bind UI to viewmodel
        itemNameTextField.textProperty().bindBidirectional(this.vm.itemNameProperty());
        itemSizeTextField.textProperty().bindBidirectional(this.vm.itemSizeTextProperty());
        
        // Bind condition ComboBox
        conditionComboBox.valueProperty().bindBidirectional(this.vm.conditionProperty());
        
        // Bind special qualities checkboxes
        flammableCheckBox.selectedProperty().bindBidirectional(this.vm.flammableProperty());
        liquidCheckBox.selectedProperty().bindBidirectional(this.vm.liquidProperty());
        perishableCheckBox.selectedProperty().bindBidirectional(this.vm.perishableProperty());
        
        expiryDate.valueProperty().bindBidirectional(this.vm.expiryDateProperty());

        // Set up condition ComboBox
        conditionComboBox.setItems(FXCollections.observableArrayList(Condition.values()));
        conditionComboBox.setValue(Condition.USABLE); // Default value

        // Disable submit until basic fields present
        addStockButton.disableProperty().bind(
            this.vm.itemNameProperty().isEmpty()
                .or(this.vm.itemSizeTextProperty().isEmpty())
                .or(this.compartmentComboBox.valueProperty().isNull())
        );

        // Set up compartment combo box with custom display
        setupCompartmentComboBox();
        
        // Initialize capacity label
        this.capacityLabel.setText("Select a compartment to see capacity");
        
        // Show compartments if inventory already set
        if (this.inventory != null) {
            this.populateCompartments();
        }

        this.feedbackLabel.setText("");
    }

    private void setupCompartmentComboBox() {
        // Custom cell factory to show compartment info with color coding
        this.compartmentComboBox.setCellFactory(param -> new ListCell<Compartment>() {
            @Override
            protected void updateItem(Compartment compartment, boolean empty) {
                super.updateItem(compartment, empty);
                if (empty || compartment == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(getCompartmentDisplayText(compartment));
                    
                    // Color code based on free space percentage
                    double freePercentage = (double) compartment.getFreeSpace() / compartment.getCapacity();
                    if (freePercentage < 0.1) {
                        setTextFill(Color.RED); // Almost full
                    } else if (freePercentage < 0.3) {
                        setTextFill(Color.ORANGE); // Getting full
                    } else {
                        setTextFill(Color.GREEN); // Plenty of space
                    }
                }
            }
        });

        // Custom button cell for the selected value display
        this.compartmentComboBox.setButtonCell(new ListCell<Compartment>() {
            @Override
            protected void updateItem(Compartment compartment, boolean empty) {
                super.updateItem(compartment, empty);
                if (empty || compartment == null) {
                    setText("Select compartment");
                    setStyle("");
                } else {
                    setText(getCompartmentDisplayText(compartment));
                }
            }
        });

        // Listen for selection changes to update capacity label
        this.compartmentComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateCapacityInfo(newVal);
        });
    }

    private String getCompartmentDisplayText(Compartment compartment) {
        int used = compartment.getUsedSpace();
        int free = compartment.getFreeSpace();
        int total = compartment.getCapacity();
        double usedPercent = (double) used / total * 100;
        
        return String.format("%s - %d/%d (%.0f%% full)", 
            compartment.getLabel(), used, total, usedPercent);
    }

    private void updateCapacityInfo(Compartment compartment) {
        if (compartment == null) {
            this.capacityLabel.setText("Select a compartment to see capacity");
            return;
        }

        int used = compartment.getUsedSpace();
        int free = compartment.getFreeSpace();
        int total = compartment.getCapacity();
        double freePercentage = (double) free / total * 100;

        String capacityText = String.format(
            "Capacity: %d/%d units used, %d units free (%.0f%% available)",
            used, total, free, freePercentage
        );
        
        this.capacityLabel.setText(capacityText);
        
        // Color code the label based on available space
        if (freePercentage < 10) {
            this.capacityLabel.setTextFill(Color.RED);
        } else if (freePercentage < 30) {
            this.capacityLabel.setTextFill(Color.ORANGE);
        } else {
            this.capacityLabel.setTextFill(Color.GREEN);
        }
    }

    public void setInventory(Inventory inventory) {
    	System.out.println("AddStock controller received inventory: " + inventory);
        this.inventory = inventory;
        if (this.compartmentComboBox != null) {
            this.populateCompartments();
        }
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    private void populateCompartments() {
        if (this.inventory == null) {
            return;
        }
        ObservableList<Compartment> list = FXCollections.observableArrayList(this.inventory.getCompartments());
        this.compartmentComboBox.setItems(list);
    }

    @FXML
    private void handleAddStock() {
        this.feedbackLabel.setText("");
        try {
            String name = this.vm.itemNameProperty().get().trim();
            if (name.isEmpty()) {
                this.feedbackLabel.setText("Item name cannot be empty");
                return;
            }

            int size;
            try {
                size = this.vm.parseSize();
                if (size <= 0) {
                    this.feedbackLabel.setText("Size must be greater than 0");
                    return;
                }
            } catch (NumberFormatException nfe) {
                this.feedbackLabel.setText("Size must be a valid number");
                return;
            }

            // Get condition from ComboBox
            Condition condition = this.conditionComboBox.getValue();
            if (condition == null) {
                this.feedbackLabel.setText("Please select a condition");
                return;
            }

            // Build special qualities from checkboxes
            String specialQuals = this.vm.buildSpecialQuals();

            // Handle expiration date - only required for perishable items
            Date expDate = null;
            if (this.vm.perishableProperty().get()) {
                LocalDate local = this.vm.expiryDateProperty().get();
                if (local == null) {
                    this.feedbackLabel.setText("Perishable items require an expiration date");
                    return;
                }
                expDate = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }

            Stock stock = new Stock(name, size, specialQuals, condition, expDate);

            Compartment chosen = this.compartmentComboBox.getSelectionModel().getSelectedItem();
            if (chosen == null) {
                this.feedbackLabel.setText("Select a compartment");
                return;
            }

            if (!chosen.canStore(stock)) {
                this.feedbackLabel.setText("Selected compartment cannot store this item (not enough space or incompatible type)");
                return;
            }

            // Add the stock
            chosen.addStock(stock);

            // Success feedback and reset
            this.feedbackLabel.setText("✓ Stock added to " + chosen.getLabel());
            this.feedbackLabel.setTextFill(Color.GREEN);
            
            // Reset form but keep compartment selection updated
            this.vm.itemNameProperty().set("");
            this.vm.itemSizeTextProperty().set("");
            this.vm.flammableProperty().set(false);
            this.vm.liquidProperty().set(false);
            this.vm.perishableProperty().set(false);
            this.vm.expiryDateProperty().set(null);
            this.conditionComboBox.setValue(Condition.USABLE);
            
            // Refresh compartment display to show updated capacity
            this.updateCapacityInfo(chosen);
            this.populateCompartments(); // Refresh the combo box items
            this.compartmentComboBox.getSelectionModel().select(chosen); // Keep same compartment selected

        } catch (Exception ex) {
            this.feedbackLabel.setText("Error: " + ex.getMessage());
            this.feedbackLabel.setTextFill(Color.RED);
        }
    }
}
