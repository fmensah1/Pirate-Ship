package edu.westga.cs3211.pirate_ship_inventory.view;

import edu.westga.cs3211.pirate_ship_inventory.model.Inventory;
import edu.westga.cs3211.pirate_ship_inventory.model.StockChange;
import edu.westga.cs3211.pirate_ship_inventory.model.User;
import edu.westga.cs3211.pirate_ship_inventory.viewmodel.ViewStockChangesViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Class ViewStockChangesCodeBehind.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class ViewStockChangesCodeBehind {

    /** The stock changes table. */
    @FXML private TableView<StockChange> stockChangesTable;
    
    /** The timestamp column. */
    @FXML private TableColumn<StockChange, String> timestampColumn;
    
    /** The crew mate column. */
    @FXML private TableColumn<StockChange, String> crewMateColumn;
    
    /** The item name column. */
    @FXML private TableColumn<StockChange, String> itemNameColumn;
    
    /** The size column. */
    @FXML private TableColumn<StockChange, Integer> sizeColumn;
    
    /** The condition column. */
    @FXML private TableColumn<StockChange, String> conditionColumn;
    
    /** The special qualities column. */
    @FXML private TableColumn<StockChange, String> specialQualitiesColumn;
    
    /** The compartment column. */
    @FXML private TableColumn<StockChange, String> compartmentColumn;
    
    /** The free space column. */
    @FXML private TableColumn<StockChange, Integer> freeSpaceColumn;
    
    /** The flammable filter. */
    @FXML private CheckBox flammableFilter;
    
    /** The liquid filter. */
    @FXML private CheckBox liquidFilter;
    
    /** The perishable filter. */
    @FXML private CheckBox perishableFilter;
    
    /** The start date picker. */
    @FXML private DatePicker startDatePicker;
    
    /** The end date picker. */
    @FXML private DatePicker endDatePicker;
    
    /** The apply filters button. */
    @FXML private Button applyFiltersButton;
    
    /** The clear filters button. */
    @FXML private Button clearFiltersButton;
    
    /** The apply time filter. */
    @FXML private Button applyTimeFilter;
    
    /** The back button. */
    @FXML private Button backButton;
    
    /** The status label. */
    @FXML private Label statusLabel;
    
    /** The summary label. */
    @FXML private Label summaryLabel;

    /** The view model. */
    private ViewStockChangesViewModel viewModel;
    
    /** The current user. */
    private User currentUser;
    
    /** The inventory. */
    private Inventory inventory;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
    	this.setupTableColumns();
    }

    /**
     * Setup table columns.
     */
    private void setupTableColumns() {
    	this.timestampColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTimestamp().toString()));
    	this.crewMateColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCrewMate().getName()));
    	this.itemNameColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStock().getName()));
    	this.sizeColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getStock().getSize()).asObject());
    	this.conditionColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStock().getCondition().toString()));
    	this.specialQualitiesColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStock().getSpecialQuals()));
    	this.compartmentColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCompartmentLabel()));
    	this.freeSpaceColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getAvailableCapacityAfter()).asObject());
    }

    /**
     * Sets the inventory.
     *
     * @param inventory the new inventory
     */
    public void setInventory(Inventory inventory) {
    	this.inventory = inventory;
        this.viewModel = new ViewStockChangesViewModel(inventory);
        this.setupBindings();
    }

    /**
     * Setup bindings.
     */
    private void setupBindings() {

    	this.stockChangesTable.setItems(this.viewModel.getStockChanges());

        this.flammableFilter.selectedProperty().bindBidirectional(this.viewModel.flammableFilterProperty());
        this.liquidFilter.selectedProperty().bindBidirectional(this.viewModel.liquidFilterProperty());
        this.perishableFilter.selectedProperty().bindBidirectional(this.viewModel.perishableFilterProperty());
        this.startDatePicker.valueProperty().bindBidirectional(this.viewModel.startDateProperty());

        this.statusLabel.textProperty().bind(this.viewModel.statusMessageProperty());
        this.summaryLabel.textProperty().bind(this.viewModel.totalChangesProperty().asString("Total changes displayed: %d"));
    }

    /**
     * Apply filters.
     */
    @FXML
    private void applyFilters() {
    	this.viewModel.applyQualityFilters();
    }

    /**
     * Apply time filter.
     */
    @FXML
    private void applyTimeFilter() {
    	this.viewModel.applyTimeFilter();
    }

    /**
     * Clear filters.
     */
    @FXML
    private void clearFilters() {
    	this.viewModel.clearFilters();
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
     * Go back to main.
     */
    @FXML
    private void goBackToMain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DefaultLandingPage.fxml"));
            Parent root = loader.load();

            DefaultLandingPageCodeBehind landingController = loader.getController();
            if (this.currentUser != null) {
                landingController.setCurrentUser(this.currentUser);
                landingController.setRole(this.currentUser.getRole());
                landingController.setInventory(this.inventory);
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