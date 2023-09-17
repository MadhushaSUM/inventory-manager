package sum.inventory_manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class moveToStoreController {
    private VehicleStock vehicleStock;
    private int vehicleId;
    @FXML
    TextField txtQuantity;
    @FXML
    Button btnClose;

    public void setVehicleStock(VehicleStock vehicleStock, int vehicleId) {
        this.vehicleStock = vehicleStock;
        this.vehicleId = vehicleId;
        txtQuantity.setPromptText("Max : " + vehicleStock.getQuantity());
        btnClose.requestFocus();
    }

    @FXML
    protected void move() {
        try {
            double quantity = Double.parseDouble(txtQuantity.getText());
            Database.moveVehicleStock(vehicleStock, quantity, vehicleId);
            close();

        } catch (NumberFormatException ex) {
            PopUp.showMessage("Enter a valid number");
            return;
        }
    }
    @FXML
    protected void close() {
        txtQuantity.getScene().getWindow().hide();
    }
}
