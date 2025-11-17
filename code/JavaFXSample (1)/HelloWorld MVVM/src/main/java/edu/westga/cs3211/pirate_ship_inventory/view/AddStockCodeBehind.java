package edu.westga.cs3211.pirate_ship_inventory.view;

import edu.westga.cs3211.pirate_ship_inventory.model.Compartment;
import edu.westga.cs3211.pirate_ship_inventory.model.Condition;
import edu.westga.cs3211.pirate_ship_inventory.model.Inventory;
import edu.westga.cs3211.pirate_ship_inventory.model.Stock;
import edu.westga.cs3211.pirate_ship_inventory.model.User;
import edu.westga.cs3211.pirate_ship_inventory.viewmodel.AddStockViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Date;
import java.io.IOException;
import java.time.LocalDate;

// TODO: Auto-generated Javadoc
/**
 * The Class AddStockCodeBehind.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class AddStockCodeBehind {

    /** The item name text field. */
    // FIXED: Updated UI components
    @FXML private TextField itemNameTextField;
    
    /** The item size text field. */
    @FXML private TextField itemSizeTextField;
    
    /** The condition combo box. */
    @FXML private ComboBox<Condition> conditionComboBox; 
    
    /** The flammable check box. */
    @FXML private CheckBox flammableCheckBox;
    
    /** The liquid check box. */
    @FXML private CheckBox liquidCheckBox;
    
    /** The perishable check box. */
    @FXML private CheckBox perishableCheckBox;
    
    /** The compartment combo box. */
    @FXML private ComboBox<Compartment> compartmentComboBox;
    
    /** The expiry date. */
    @FXML private DatePicker expiryDate;
    
    /** The add stock button. */
    @FXML private Button addStockButton;
    
    /** The back button. */
    @FXML private Button backButton;
    
    /** The feedback label. */
    @FXML private Label feedbackLabel;
    
    /** The capacity label. */
    @FXML private Label capacityLabel;
   
    /** The vm. */
    private AddStockViewModel vm = new AddStockViewModel();
    
    /** The inventory. */
    private Inventory inventory;
    
    /** The current user. */
    private User currentUser;
	
	/** The status label. */
	private Labeled statusLabel;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
    	this.itemNameTextField.textProperty().bindBidirectional(this.vm.itemNameProperty());
    	this.itemSizeTextField.textProperty().bindBidirectional(this.vm.itemSizeTextProperty());

    	this.conditionComboBox.valueProperty().bindBidirectional(this.vm.conditionProperty());

    	this.flammableCheckBox.selectedProperty().bindBidirectional(this.vm.flammableProperty());
    	this.liquidCheckBox.selectedProperty().bindBidirectional(this.vm.liquidProperty());
    	this.perishableCheckBox.selectedProperty().bindBidirectional(this.vm.perishableProperty());
        
    	this.expiryDate.valueProperty().bindBidirectional(this.vm.expiryDateProperty());

    	this.conditionComboBox.setItems(FXCollections.observableArrayList(Condition.values()));
    	this.conditionComboBox.setValue(Condition.USABLE); 

    	this.addStockButton.disableProperty().bind(
            this.vm.itemNameProperty().isEmpty()
                .or(this.vm.itemSizeTextProperty().isEmpty())
                .or(this.compartmentComboBox.valueProperty().isNull())
        );

    	this.setupCompartmentComboBox();

        this.capacityLabel.setText("Select a compartment to see capacity");
  
        if (this.inventory != null) {
            this.populateCompartments();
        }

        this.feedbackLabel.setText("");
    }

    /**
     * Setup compartment combo box.
     */
    private void setupCompartmentComboBox() {
        this.compartmentComboBox.setCellFactory(param -> new ListCell<Compartment>() {
            @Override
            protected void updateItem(Compartment compartment, boolean empty) {
                super.updateItem(compartment, empty);
                if (empty || compartment == null) {
                    setText(null);
                    setStyle("");
                } else {
                	setText(AddStockCodeBehind.this.getCompartmentDisplayText(compartment));
                    
                    // Color code based on free space percentage
                    double freePercentage = (double) compartment.getFreeSpace() / compartment.getCapacity();
                    if (freePercentage < 0.1) {
                        setTextFill(Color.RED); 
                    } else if (freePercentage < 0.3) {
                        setTextFill(Color.ORANGE); 
                    } else {
                        setTextFill(Color.GREEN); 
                    }
                }
            }
        });

        this.compartmentComboBox.setButtonCell(new ListCell<Compartment>() {
            @Override
            protected void updateItem(Compartment compartment, boolean empty) {
                super.updateItem(compartment, empty);
                if (empty || compartment == null) {
                    setText("Select compartment");
                    setStyle("");
                } else {
                    setText(AddStockCodeBehind.this.getCompartmentDisplayText(compartment));
                }
            }
        });

        this.compartmentComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
        	this.updateCapacityInfo(newVal);
        });
    }

    /**
     * Gets the compartment display text.
     *
     * @param compartment the compartment
     * @return the compartment display text
     */
    private String getCompartmentDisplayText(Compartment compartment) {
        int used = compartment.getUsedSpace();
        int total = compartment.getCapacity();
        double usedPercent = (double) used / total * 100;
        
        return String.format("%s - %d/%d (%.0f%% full)", 
            compartment.getLabel(), used, total, usedPercent);
    }

    /**
     * Update capacity info.
     *
     * @param compartment the compartment
     */
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

        if (freePercentage < 10) {
            this.capacityLabel.setTextFill(Color.RED);
        } else if (freePercentage < 30) {
            this.capacityLabel.setTextFill(Color.ORANGE);
        } else {
            this.capacityLabel.setTextFill(Color.GREEN);
        }
    }

    /**
     * Sets the inventory.
     *
     * @param inventory the new inventory
     */
    public void setInventory(Inventory inventory) {
    	System.out.println("AddStock controller received inventory: " + inventory);
        this.inventory = inventory;
        if (this.compartmentComboBox != null) {
            this.populateCompartments();
        }
    }

    /**
     * Sets the current user.
     *
     * @param user the new current user
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Populate compartments.
     */
    private void populateCompartments() {
        if (this.inventory == null) {
            return;
        }
        ObservableList<Compartment> list = FXCollections.observableArrayList(this.inventory.getCompartments());
        this.compartmentComboBox.setItems(list);
    }

    /**
     * Handle add stock.
     */
    @FXML
    private void handleAddStock() {
        this.feedbackLabel.setText("");
        try {
            Stock stock = this.createStockFromInput();
            if (stock == null) {
                return; 
            }
            
            Compartment chosen = this.compartmentComboBox.getSelectionModel().getSelectedItem();
            if (!this.validateCompartmentSelection(chosen, stock)) {
                return;
            }
            
            this.addStockToCompartment(stock, chosen);
            this.resetForm();
            this.updateCapacityInfo(chosen);
            this.populateCompartments(); 
            this.compartmentComboBox.getSelectionModel().select(chosen); 
        } catch (Exception ex) {
            this.feedbackLabel.setText("Error: " + ex.getMessage());
            this.feedbackLabel.setTextFill(Color.RED);
        }
    }

    private Stock createStockFromInput() {
        String name = this.vm.itemNameProperty().get().trim();
        if (name.isEmpty()) {
            this.feedbackLabel.setText("Item name cannot be empty");
            return null;
        }
        
        int size = this.parseItemSize();
        if (size <= 0) {
            return null; 
        }

        Condition condition = this.conditionComboBox.getValue();
        if (condition == null) {
            this.feedbackLabel.setText("Please select a condition");
            return null;
        }
        
        String specialQuals = this.vm.buildSpecialQuals();
        Date expDate = this.getExpirationDate();
        if (expDate == null && this.vm.perishableProperty().get()) {
            return null; 
        }
        
        return new Stock(name, size, specialQuals, condition, expDate);
    }

    private int parseItemSize() {
        try {
            int size = this.vm.parseSize();
            if (size <= 0) {
                this.feedbackLabel.setText("Size must be greater than 0");
                return -1;
            }
            return size;
        } catch (NumberFormatException nfe) {
            this.feedbackLabel.setText("Size must be a valid number");
            return -1;
        }
    }

    private Date getExpirationDate() {
        if (this.vm.perishableProperty().get()) {
            LocalDate local = this.vm.expiryDateProperty().get();
            if (local == null) {
                this.feedbackLabel.setText("Perishable items require an expiration date");
                return null;
            }
            return Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }

    private boolean validateCompartmentSelection(Compartment chosen, Stock stock) {
        if (chosen == null) {
            this.feedbackLabel.setText("Select a compartment");
            return false;
        }
        if (!chosen.canStore(stock)) {
            this.feedbackLabel.setText("Selected compartment cannot store this item (not enough space or incompatible type)");
            return false;
        }
        return true;
    }

    private void addStockToCompartment(Stock stock, Compartment chosen) {
        chosen.addStock(stock);
        this.inventory.getStockLogger().logStockChange(this.currentUser, stock, chosen);
        
        String successMessage = String.format(
            "✓ Stock added successfully!\nItem: %s\nCompartment: %s\nAdded by: %s\nRemaining space: %d units",
            stock.getName(), chosen.getLabel(), this.currentUser.getName(), chosen.getFreeSpace()
        );
        
        this.feedbackLabel.setText(successMessage);
        this.feedbackLabel.setTextFill(Color.GREEN);
    }

    private void resetForm() {
        this.vm.itemNameProperty().set("");
        this.vm.itemSizeTextProperty().set("");
        this.vm.flammableProperty().set(false);
        this.vm.liquidProperty().set(false);
        this.vm.perishableProperty().set(false);
        this.vm.expiryDateProperty().set(null);
        this.conditionComboBox.setValue(Condition.USABLE);
    }
    
    /**
     * Handle back button.
     *
     * @param event the event
     */
    @FXML
    void handleBackButton(ActionEvent event) {
    	this.goBackToMain();
    }
    
    /**
     * Go back to main.
     */
    private void goBackToMain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DefaultLandingPage.fxml"));
            Parent root = loader.load();

            // Get landing controller and pass current user and inventory
            DefaultLandingPageCodeBehind landingController = loader.getController();
            if (this.currentUser != null) {
                landingController.setCurrentUser(this.currentUser);  
                landingController.setRole(this.currentUser.getRole());  
                landingController.setInventory(this.inventory);  
            } else {
                System.out.println("Error: currentUser is null");
            }

            Stage stage = (Stage) this.backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Pirate Ship Inventory");

        } catch (IOException error) {
            error.printStackTrace();
            this.statusLabel.setText("Error returning to main page");
        }
    }
}
