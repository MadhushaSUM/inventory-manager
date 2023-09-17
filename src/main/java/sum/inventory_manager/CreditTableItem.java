package sum.inventory_manager;

public class CreditTableItem {
    private String nameOrDate;
    private double amount;
    private int customerId = -1;

    public CreditTableItem(String nameOrDate, double amount) {
        this.nameOrDate = nameOrDate;
        this.amount = amount;
    }

    public CreditTableItem(String nameOrDate) {
        this.nameOrDate = nameOrDate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNameOrDate() {
        return nameOrDate;
    }

    public double getAmount() {
        return amount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
