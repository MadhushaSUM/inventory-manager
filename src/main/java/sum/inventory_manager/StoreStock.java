package sum.inventory_manager;

public class StoreStock {
    private int id;
    private Product product;
    private int productId;
    private double quantity;
    private double unitPrice;

    public StoreStock(int id, Product product, double quantity, double unitPrice) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public StoreStock(int id, int productId, double quantity, double unitPrice) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }


    public int getStoreStockId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getProductName() {
        return this.product.getName();
    }

    public int getProductId() {
        return productId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return product.getName() + " Rs. " + unitPrice;
    }
}
