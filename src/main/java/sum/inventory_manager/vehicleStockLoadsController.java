package sum.inventory_manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class vehicleStockLoadsController implements Initializable {
    @FXML
    TableView<VehicleStockLoad> vehicleStockLoadTable;
    @FXML
    TableColumn<VehicleStockLoad, Integer> colId;
    @FXML
    TableColumn<StoreStockLoad, LocalDate> colDate;
    @FXML
    TableColumn<StoreStockLoad, String> colProduct;
    @FXML
    TableColumn<StoreStockLoad, Double> colQuantity;
    @FXML
    TableColumn<StoreStockLoad, Double> colUnitPrice;
    @FXML
    TableColumn<StoreStockLoad, Integer> colOrigin;

    private int vehicleId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colOrigin.setCellValueFactory(new PropertyValueFactory<>("FromWhere"));


    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
        vehicleStockLoadTable.setItems(Database.getAllVehicleStockLoadsData(vehicleId));
    }
}
