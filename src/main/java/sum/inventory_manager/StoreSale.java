package sum.inventory_manager;

import java.time.LocalDate;

public class StoreSale {
    private int id;
    private LocalDate date;
    private int vehicleId;
    private double saleTotal;

    public StoreSale(int id, LocalDate date, int vehicleId, double saleTotal) {
        this.id = id;
        this.date = date;
        this.vehicleId = vehicleId;
        this.saleTotal = saleTotal;
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

    public double getSaleTotal() {
        return saleTotal;
    }
}
