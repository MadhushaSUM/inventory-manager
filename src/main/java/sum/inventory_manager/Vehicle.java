package sum.inventory_manager;


public class Vehicle {
    private int id;
    private String name;
    private String driverName;
    private String driverTp;
    private String repName;
    private String repTp;

    public Vehicle(int id, String name, String driverName, String driverTp, String repName, String repTp) {
        this.id = id;
        this.name = name;
        this.driverName = driverName;
        this.driverTp = driverTp;
        this.repName = repName;
        this.repTp = repTp;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverTp() {
        return driverTp;
    }

    public String getRepName() {
        return repName;
    }

    public String getRepTp() {
        return repTp;
    }

    @Override
    public String toString() {
        return name;
    }
}
