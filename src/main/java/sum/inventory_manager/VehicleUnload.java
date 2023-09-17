package sum.inventory_manager;

import java.time.LocalDate;

public class VehicleUnload {
    private int id;
    private LocalDate date;
    private int stockId;
    private VehicleStock vehicleStock;
    private double quantity, unitPrice;
    private boolean isSale, isMoved, isDisposed;

    public VehicleUnload(int id, LocalDate date, int stockId, double quantity, double unitPrice, boolean isSale, boolean isMoved, boolean isDisposed) {
        this.id = id;
        this.date = date;
        this.stockId = stockId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.isSale = isSale;
        this.isMoved = isMoved;
        this.isDisposed = isDisposed;
    }

    public void setVehicleStock(VehicleStock vehicleStock) {
        this.vehicleStock = vehicleStock;
    }

    public String getVehicleStock() {
        return vehicleStock.toString();
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getStockId() {
        return stockId;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public boolean isSale() {
        return isSale;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public boolean isDisposed() {
        return isDisposed;
    }

    public String getDestination() {
        if (isSale) return "Sale";
        if (isMoved) return "Moved";
        return "Disposed";
    }
}
