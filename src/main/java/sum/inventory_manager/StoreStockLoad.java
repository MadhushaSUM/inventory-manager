package sum.inventory_manager;

import java.time.LocalDate;

public class StoreStockLoad {
    private int id;
    private LocalDate date;
    private int productId;
    private Product product;
    private double quantity;
    private double unitPrice;
    private int isMovedFrom;

    public StoreStockLoad(int id, LocalDate date, int productId, double quantity, double unitPrice, int isMovedFrom) {
        this.id = id;
        this.date = date;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.isMovedFrom = isMovedFrom;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return product.getName();
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

    public int getIsMovedFrom() {
        return isMovedFrom;
    }
}
