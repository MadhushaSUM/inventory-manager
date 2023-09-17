package sum.inventory_manager;

public class VehicleStock {
    private int id;
    private int productId;
    private Product product;
    private double quantity;
    private double unitPrice;

    public VehicleStock(int id, int productId, double quantity, double unitPrice) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
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

    @Override
    public String toString() {
        return product.getName() +" Rs. "+ unitPrice;
    }
}
