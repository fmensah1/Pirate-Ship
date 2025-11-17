package edu.westga.cs3211.pirate_ship_inventory.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class StockLogger.
 * 
 * @author fmensah1
 * @version cs3211
 */
public class StockLogger {
    
    /** The stock changes. */
    private List<StockChange> stockChanges;
    
    /**
     * Instantiates a new stock logger.
     */
    public StockLogger() {
        this.stockChanges = new ArrayList<>();
    }
    
    /**
     * Log stock change.
     *
     * @param crewMate the crew mate
     * @param stock the stock
     * @param compartment the compartment
     */
    public void logStockChange(User crewMate, Stock stock, Compartment compartment) {
        StockChange change = new StockChange(crewMate, stock, compartment.getLabel(), compartment.getFreeSpace());
        this.stockChanges.add(change);
    }
    
    /**
     * Gets the all changes.
     *
     * @return the all changes
     */
    public List<StockChange> getAllChanges() {
        List<StockChange> sorted = new ArrayList<>(this.stockChanges);
        sorted.sort(Comparator.comparing(StockChange::getTimestamp).reversed());
        return Collections.unmodifiableList(sorted);
    }
    
    /**
     * Filter by special quality.
     *
     * @param qualities the qualities
     * @return the list
     */
    public List<StockChange> filterBySpecialQuality(List<String> qualities) {
        return this.stockChanges.stream()
            .filter(change -> qualities.stream().anyMatch(quality -> change.hasSpecialQuality(quality)))
            .sorted(Comparator.comparing(StockChange::getTimestamp).reversed())
            .collect(Collectors.toList());
    }
    
    /**
     * Filter by crew mate.
     *
     * @param crewMates the crew mates
     * @return the list
     */
    public List<StockChange> filterByCrewMate(List<User> crewMates) {
        return this.stockChanges.stream()
            .filter(change -> crewMates.contains(change.getCrewMate()))
            .sorted(Comparator.comparing(StockChange::getTimestamp).reversed())
            .collect(Collectors.toList());
    }
    
    /**
     * Filter by time range.
     *
     * @param start the start
     * @param end the end
     * @return the list
     */
    public List<StockChange> filterByTimeRange(Date start, Date end) {
        return this.stockChanges.stream()
            .filter(change -> {
                boolean afterStart = start == null || change.getTimestamp().after(start);
                boolean beforeEnd = end == null || change.getTimestamp().before(end);
                return afterStart && beforeEnd;
            })
            .sorted(Comparator.comparing(StockChange::getTimestamp).reversed())
            .collect(Collectors.toList());
    }
}