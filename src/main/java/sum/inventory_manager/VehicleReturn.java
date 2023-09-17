package sum.inventory_manager;

public class VehicleReturn {
    private int id, saleId, productId;
    private Product product;
    private double quantity, unitPrice;
    private boolean isDamaged;

    public VehicleReturn(int id, int saleId, int productId, double quantity, double unitPrice, boolean isDamaged) {
        this.id = id;
        this.saleId = saleId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.isDamaged = isDamaged;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public int getSaleId() {
        return saleId;
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

    public String getDamaged() {
        if (isDamaged) return "Yes";
        return "No";
    }
}
