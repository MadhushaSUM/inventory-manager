package sum.inventory_manager;

import javafx.collections.ObservableList;

public class ReviewReturns {
    private double saleTotal, discounts, returns, credits;
    private ObservableList<VehicleUnload> unloads;
    private ObservableList<VehicleReturn> returnedItems;
    private ObservableList<VehicleStockLoad> stockLoads;

    public double getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(double saleTotal) {
        this.saleTotal = saleTotal;
    }

    public double getDiscounts() {
        return discounts;
    }

    public void setDiscounts(double discounts) {
        this.discounts = discounts;
    }

    public double getReturns() {
        return returns;
    }

    public void setReturns(double returns) {
        this.returns = returns;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public ObservableList<VehicleUnload> getUnloads() {
        return unloads;
    }

    public void setUnloads(ObservableList<VehicleUnload> unloads) {
        this.unloads = unloads;
    }

    public ObservableList<VehicleReturn> getReturnedItems() {
        return returnedItems;
    }

    public void setReturnedItems(ObservableList<VehicleReturn> returnedItems) {
        this.returnedItems = returnedItems;
    }

    public ObservableList<VehicleStockLoad> getStockLoads() {
        return stockLoads;
    }

    public void setStockLoads(ObservableList<VehicleStockLoad> stockLoads) {
        this.stockLoads = stockLoads;
    }
}
