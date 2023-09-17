package sum.inventory_manager;

public class Product {
    private int id;
    private String name;
    private String description;
    private String unit;

    public Product(int id, String name, String description, String unit) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unit = unit;
    }

    public int getProductId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
