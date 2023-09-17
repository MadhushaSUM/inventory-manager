package sum.inventory_manager;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class storeSalesViewController implements Initializable {
    @FXML
    TableView<StoreSale> storeSaleTable;
    @FXML
    TableColumn<StoreSale, Integer> colId;
    @FXML
    TableColumn<StoreSale, LocalDate> colDate;
    @FXML
    TableColumn<StoreSale, Integer> colVehicleId;
    @FXML
    TableColumn<StoreSale, Double> colSaleTotal;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("VehicleId"));
        colSaleTotal.setCellValueFactory(new PropertyValueFactory<>("SaleTotal"));

        storeSaleTable.setItems(Database.getAllStoreSalesData());
    }

    @FXML
    protected void delete_clicked() {
        //TODO: ask for user confirmation again
        if (storeSaleTable.getSelectionModel().getSelectedItem() == null) {
            PopUp.showMessage("Select item from the table!");
            return;
        }

        Database.deleteStoreSale(storeSaleTable.getSelectionModel().getSelectedItem());
        storeSaleTable.setItems(Database.getAllStoreSalesData());
    }

    @FXML
    protected  void view_clicked() {
        if (storeSaleTable.getSelectionModel().getSelectedItem() == null) {
            PopUp.showMessage("Select a sale");
            return;
        }

        ObservableList<StoreSaleTableItem> collections = Database.viewStoreSale(storeSaleTable.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader storeSaleDetailsSceneLoader = new FXMLLoader(mainController.class.getResource("store-sales-details-stage.fxml"));
        AnchorPane parent;

        try {
            parent = storeSaleDetailsSceneLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        parent.getStylesheets().add(String.valueOf(mainController.class.getResource("main-stage-styles.css")));

        storeSaleDetailsController controller = storeSaleDetailsSceneLoader.getController();
        controller.setData(collections, storeSaleTable.getSelectionModel().getSelectedItem());

        stage.setScene(scene);
        stage.show();
    }
}
