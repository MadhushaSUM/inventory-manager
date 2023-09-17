package sum.inventory_manager;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class storeSaleDetailsController implements Initializable {
    @FXML
    TableView<StoreSaleTableItem> storeSaleTable;
    @FXML
    TableColumn<StoreSaleTableItem, String> colStock;
    @FXML
    TableColumn<StoreSaleTableItem, Double> colQuantity;
    @FXML
    TableColumn<StoreSaleTableItem, Double> colUnitPrice;
    @FXML
    TableColumn<StoreSaleTableItem, Double> colSaleTotal;
    @FXML
    TextField txtVehicle;
    @FXML
    TextField txtDate;
    @FXML
    TextField txtSaleTotal;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colStock.setCellValueFactory(new PropertyValueFactory<>("StoreStockString"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colSaleTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
    }

    public void setData(ObservableList<StoreSaleTableItem> collections, StoreSale selectedItem) {
        storeSaleTable.setItems(collections);

        Vehicle vehicle = Database.getVehicle(selectedItem.getVehicleId());
        txtVehicle.setText(vehicle.getName());
        txtDate.setText(selectedItem.getDate().toString());
        txtSaleTotal.setText(String.valueOf(selectedItem.getSaleTotal()));
    }

    @FXML
    protected void close() {
        txtVehicle.getScene().getWindow().hide();
    }
}
