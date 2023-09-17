package sum.inventory_manager;

import java.time.LocalDate;

public class Credit {
    private int id;
    private LocalDate date;
    private int customerId;
    private double amount;
    private int saleId;

    public Credit(int id, LocalDate date, int customerId, double amount, int saleId) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.amount = amount;
        this.saleId = saleId;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getAmount() {
        return amount;
    }

    public int getSaleId() {
        return saleId;
    }
}
