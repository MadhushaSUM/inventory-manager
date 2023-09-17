package sum.inventory_manager;

import java.time.LocalDate;

public class StoreStockUnloads {
    private int id;
    private LocalDate date;
    private int vehicleId;
    private int stockId;
    private double quantity;
    private double unit_price;
    private boolean isSale;
    private int saleId;
    private boolean isNoMoney;
    private String remarks;

    public StoreStockUnloads(int id, LocalDate date, int vehicleId, int stockId, double quantity, double unit_price) {
        this.id = id;
        this.date = date;
        this.vehicleId = vehicleId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public int getStockId() {
        return stockId;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public boolean isSale() {
        return isSale;
    }

    public void setSale(boolean sale) {
        isSale = sale;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public boolean isNoMoney() {
        return isNoMoney;
    }

    public void setNoMoney(boolean noMoney) {
        isNoMoney = noMoney;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
