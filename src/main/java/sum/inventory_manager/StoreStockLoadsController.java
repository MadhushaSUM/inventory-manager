package sum.inventory_manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StoreStockLoadsController implements Initializable {
    @FXML
    TableView<StoreStockLoad> storeStockLoadTable;
    @FXML
    TableColumn<StoreStockLoad, Integer> colId;
    @FXML
    TableColumn<StoreStockLoad, LocalDate> colDate;
    @FXML
    TableColumn<StoreStockLoad, String> colProduct;
    @FXML
    TableColumn<StoreStockLoad, Double> colQuantity;
    @FXML
    TableColumn<StoreStockLoad, Double> colUnitPrice;
    @FXML
    TableColumn<StoreStockLoad, Integer> colIsMovedFrom;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colIsMovedFrom.setCellValueFactory(new PropertyValueFactory<>("IsMovedFrom"));

        storeStockLoadTable.setItems(Database.getAllStoreStockLoadsData());
    }

    @FXML
    protected void delete_clicked() {
        //TODO: ask for user confirmation again
        if (storeStockLoadTable.getSelectionModel().getSelectedItem() == null) {
            PopUp.showMessage("Select item from the table!");
            return;
        }
        if (storeStockLoadTable.getSelectionModel().getSelectedItem().getIsMovedFrom() != 0) {
            PopUp.showMessage("Deleting this load is restricted");
            return;
        }

        Database.deleteStoreStockLoad(storeStockLoadTable.getSelectionModel().getSelectedItem());
        storeStockLoadTable.setItems(Database.getAllStoreStockLoadsData());
    }
}
