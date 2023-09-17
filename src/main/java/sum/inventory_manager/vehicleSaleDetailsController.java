package sum.inventory_manager;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class vehicleSaleDetailsController implements Initializable {
    @FXML
    TableView<VehicleSaleTableItem> vehicleSaleTable;
    @FXML
    TableColumn<VehicleSaleTableItem, String> colStock;
    @FXML
    TableColumn<VehicleSaleTableItem, Double> colQuantity;
    @FXML
    TableColumn<VehicleSaleTableItem, Double> colUnitPrice;
    @FXML
    TableColumn<VehicleSaleTableItem, Double> colSaleTotal;
    @FXML
    TableView<VehicleReturnTableItem> vehicleReturnTable;
    @FXML
    TableColumn<VehicleReturnTableItem, String> colReturnProduct;
    @FXML
    TableColumn<VehicleReturnTableItem, Double> colReturnQuantity;
    @FXML
    TableColumn<VehicleReturnTableItem, Double> colReturnUnitPrice;
    @FXML
    TableColumn<VehicleReturnTableItem, Double> colReturnSaleTotal;
    @FXML
    TableColumn<VehicleReturnTableItem, String> colIsDamaged;
    @FXML
    TextField txtSaleTotal;
    @FXML
    TextField txtDiscounts;
    @FXML
    TextField txtReturns;
    @FXML
    TextField txtCredits;
    @FXML
    TextField txtPaid;
    @FXML
    TextField txtCustomer;
    @FXML
    TextField txtDate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colStock.setCellValueFactory(new PropertyValueFactory<>("StockString"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colSaleTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));

        colReturnProduct.setCellValueFactory(new PropertyValueFactory<>("Product"));
        colReturnQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colReturnUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colReturnSaleTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        colIsDamaged.setCellValueFactory(new PropertyValueFactory<>("DamagedString"));
    }

    @FXML
    protected void close() {
        txtCustomer.getScene().getWindow().hide();
    }

    public void setData(ArrayList<Collection> collections, VehicleSale selectedItem) {
        vehicleSaleTable.setItems((ObservableList<VehicleSaleTableItem>) collections.get(0));
        if (selectedItem.getReturns() != 0) {
            vehicleReturnTable.setItems((ObservableList<VehicleReturnTableItem>) collections.get(1));
        }

        Customer customer = Database.getCustomer(selectedItem.getCustomerId());
        txtCustomer.setText(customer.getName());
        txtDate.setText(selectedItem.getSaleDate().toString());
        txtSaleTotal.setText(String.valueOf(selectedItem.getSale_total()));
        txtDiscounts.setText(String.valueOf(selectedItem.getDiscounts()));
        txtReturns.setText(String.valueOf(selectedItem.getReturns()));
        txtCredits.setText(String.valueOf(selectedItem.getCredits()));
        txtPaid.setText(String.valueOf(
            selectedItem.getSale_total() - selectedItem.getDiscounts() - selectedItem.getReturns()
                - selectedItem.getCredits()
        ));
    }
}
