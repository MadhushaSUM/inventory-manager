module sum.inventory_manager {
    requires javafx.controls;
    requires javafx.fxml;
    requires AnimateFX;
    requires java.sql;


    opens sum.inventory_manager to javafx.fxml;
    exports sum.inventory_manager;
}