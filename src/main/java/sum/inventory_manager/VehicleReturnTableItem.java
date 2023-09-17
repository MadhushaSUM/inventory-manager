package sum.inventory_manager;

public class VehicleReturnTableItem {
    private Product product;
    private double quantity;
    private double unitPrice;
    private boolean isDamaged;
    private double total;

    public VehicleReturnTableItem(Product product, double quantity, double unitPrice, boolean isDamaged) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.isDamaged = isDamaged;

        this.total = quantity * unitPrice;
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

    public boolean isDamaged() {
        return isDamaged;
    }
    public String getDamagedString() {
        if (isDamaged) return "Yes";
        return "No";
    }

    public double getTotal() {
        return total;
    }


}
