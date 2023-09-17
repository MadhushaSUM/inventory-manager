package sum.inventory_manager;

public class StoreSaleTableItem {
    private StoreStock storeStock;
    private double quantity;
    private double unitPrice;
    private double total;

    public StoreSaleTableItem(StoreStock storeStock, double quantity, double unitPrice) {
        this.storeStock = storeStock;
        this.quantity = quantity;
        this.unitPrice = unitPrice;

        this.total = quantity * unitPrice;
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
    public String getStoreStockString() {
        return storeStock.getProductName();
    }
}
