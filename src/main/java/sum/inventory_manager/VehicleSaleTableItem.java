package sum.inventory_manager;

public class VehicleSaleTableItem {
    private VehicleStock vehicleStock;
    private double quantity;
    private double unitPrice;
    private double total;

    public VehicleSaleTableItem(VehicleStock vehicleStock, double quantity, double unitPrice) {
        this.vehicleStock = vehicleStock;
        this.quantity = quantity;
        this.unitPrice = unitPrice;

        this.total = quantity * unitPrice;
    }

    public VehicleStock getVehicleStock() {
        return vehicleStock;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public String getStockString() {
        return vehicleStock.toString();
    }
}
