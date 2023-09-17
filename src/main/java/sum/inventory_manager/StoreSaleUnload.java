package sum.inventory_manager;

public class StoreSaleUnload {
    private int id;

    private Vehicle vehicle;
    private StoreStock storeStock;
    private double quantity;
    private double unitPrice;
    private double total;

    public StoreSaleUnload(Vehicle vehicle, StoreStock storeStock, double quantity, double unitPrice) {
        this.vehicle = vehicle;
        this.storeStock = storeStock;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = unitPrice * quantity;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public StoreStock getStoreStock() {
        return storeStock;
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
}
