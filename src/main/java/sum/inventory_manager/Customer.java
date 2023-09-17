package sum.inventory_manager;

public class Customer {
    private int id;
    private String name;
    private int vehicleId;
    private String address;
    private String telephone;

    public Customer(int id, String name,int vehicleId, String address, String telephone) {
        this.id = id;
        this.name = name;
        this.vehicleId = vehicleId;
        this.address = address;
        this.telephone = telephone;
    }

    public int getCustomerId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getVehicleIdString() {
        return String.valueOf(vehicleId);
    }

    @Override
    public String toString() {
        return name + " , " +address;
    }
}
