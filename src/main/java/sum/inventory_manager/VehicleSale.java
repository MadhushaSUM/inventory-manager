package sum.inventory_manager;

import java.time.LocalDate;

public class VehicleSale {
    private int id;
    private LocalDate saleDate;
    private int customerId;
    private double sale_total;
    private double discounts;
    private double returns;
    private double credits;

    public VehicleSale(int id, LocalDate saleDate, int customerId, double sale_total, double discounts, double returns, double credits) {
        this.id = id;
        this.saleDate = saleDate;
        this.customerId = customerId;
        this.sale_total = sale_total;
        this.discounts = discounts;
        this.returns = returns;
        this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getSale_total() {
        return sale_total;
    }

    public double getDiscounts() {
        return discounts;
    }

    public double getReturns() {
        return returns;
    }

    public double getCredits() {
        return credits;
    }
}
