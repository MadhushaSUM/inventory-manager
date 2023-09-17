package sum.inventory_manager;

import animatefx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class vehicleStockController implements Initializable {
    @FXML
    TableView<VehicleStock> vehicleStockTable;
    @FXML
    TableColumn<VehicleStock, Integer> colId;
    @FXML
    TableColumn<VehicleStock, String> colProduct;
    @FXML
    TableColumn<VehicleStock, Double> colQuantity;
    @FXML
    TableColumn<VehicleStock, Double> colUnitPrice;

    ScrollPane main_pane;
    Label title_label;
    Vehicle vehicle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));

        vehicleStockTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(VehicleStock stock, boolean empty) {
                super.updateItem(stock, empty);
                if (stock == null) {
                    setStyle("");
                } else if (stock.getQuantity() <= 0) {
                    setStyle("-fx-background-color: #EC7063;");
                }
            }
        });
    }

    public void setController(Vehicle vehicle, ScrollPane scrollPane, Label label) {
        this.vehicle = vehicle;
        vehicleStockTable.setItems(Database.getAllVehicleStockData(vehicle.getId(), false));
        this.main_pane = scrollPane;
        this.title_label = label;
    }

    @FXML
    protected void moveToStore() {
        if (vehicleStockTable.getSelectionModel().isEmpty()) {
            PopUp.showMessage("Select a stock to continue");
            return;
        }
        if (vehicleStockTable.getSelectionModel().getSelectedItem().getQuantity() <= 0) {
            PopUp.showMessage("Empty stock");
            return;
        }

        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);

        FXMLLoader loader = new FXMLLoader(vehicleStockController.class.getResource("move-to-store-stage.fxml"));
        VBox parent;
        try {
            parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        parent.getStylesheets().add(String.valueOf(mainController.class.getResource("main-stage-styles.css")));

        moveToStoreController controller = loader.getController();
        controller.setVehicleStock(vehicleStockTable.getSelectionModel().getSelectedItem(), vehicle.getId());

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        vehicleStockTable.setItems(Database.getAllVehicleStockData(vehicle.getId(), false));
    }

    @FXML
    protected void vehicleStockLoads_clicked() {
        AnimationFX titleAnimation = new SlideOutRight(title_label);
        titleAnimation.setOnFinished(event1 -> title_label.setText(String.format("%s STOCK LOADS", vehicle.getName())));
        titleAnimation.playOnFinished(new SlideInRight(title_label));
        titleAnimation.play();

        FXMLLoader vehiclesStockLoadsLoader = new FXMLLoader(mainController.class.getResource("vehicle-stock-loads-stage.fxml"));
        AnchorPane vehiclesStockLoadsScene;
        try {
            vehiclesStockLoadsScene = vehiclesStockLoadsLoader.load();
        } catch (IOException ex) {
            ShowError.show(ex);
            throw new RuntimeException(ex);
        }

        vehicleStockLoadsController controller = vehiclesStockLoadsLoader.getController();
        controller.setVehicleId(vehicle.getId());

        AnimationFX mainAnimation = new SlideOutDown(main_pane);
        mainAnimation.setOnFinished(event1 -> main_pane.contentProperty().set(vehiclesStockLoadsScene));
        mainAnimation.playOnFinished(new SlideInUp(main_pane));
        mainAnimation.play();
    }
}
