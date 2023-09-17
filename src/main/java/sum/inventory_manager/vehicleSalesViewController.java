package sum.inventory_manager;

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

public class vehicleSalesViewController implements Initializable {
    @FXML
    TableView<VehicleSale> vehicleSaleTable;
    @FXML
    TableColumn<VehicleSale, Integer> colId;
    @FXML
    TableColumn<VehicleSale, LocalDate> colDate;
    @FXML
    TableColumn<VehicleSale, Integer> colCustomerId;
    @FXML
    TableColumn<VehicleSale, Double> colSaleTotal;
    @FXML
    TableColumn<VehicleSale, Double> colDiscounts;
    @FXML
    TableColumn<VehicleSale, Double> colReturns;
    @FXML
    TableColumn<VehicleSale, Double> colCredits;

    private int vehicleId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("SaleDate"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        colSaleTotal.setCellValueFactory(new PropertyValueFactory<>("Sale_total"));
        colDiscounts.setCellValueFactory(new PropertyValueFactory<>("Discounts"));
        colReturns.setCellValueFactory(new PropertyValueFactory<>("Returns"));
        colCredits.setCellValueFactory(new PropertyValueFactory<>("Credits"));


    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
        vehicleSaleTable.setItems(Database.getAllVehicleSaleData(vehicleId));
    }

    @FXML
    protected void delete_clicked() {
        if (vehicleSaleTable.getSelectionModel().getSelectedItem() == null) {
            PopUp.showMessage("Select a sale");
            return;
        }
        Database.deleteVehicleSale(vehicleId, vehicleSaleTable.getSelectionModel().getSelectedItem());
        vehicleSaleTable.setItems(Database.getAllVehicleSaleData(vehicleId));
    }

    @FXML
    protected  void view_clicked() {
        if (vehicleSaleTable.getSelectionModel().getSelectedItem() == null) {
            PopUp.showMessage("Select a sale");
            return;
        }

        ArrayList<Collection> collections = Database.viewVehicleSale(vehicleId, vehicleSaleTable.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader vehiclesSaleDetailsSceneLoader = new FXMLLoader(mainController.class.getResource("vehicle-sales-details-stage.fxml"));
        AnchorPane parent;

        try {
            parent = vehiclesSaleDetailsSceneLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        parent.getStylesheets().add(String.valueOf(mainController.class.getResource("main-stage-styles.css")));

        vehicleSaleDetailsController controller = vehiclesSaleDetailsSceneLoader.getController();
        controller.setData(collections, vehicleSaleTable.getSelectionModel().getSelectedItem());

        stage.setScene(scene);
        stage.show();
    }
}
