package sum.inventory_manager;

import animatefx.animation.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class vehicleStockSaleController implements Initializable {
    @FXML
    HBox hBox;

    ScrollPane main_pane;
    Label title_label;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hBox.setSpacing(10);

        ObservableList<Vehicle> vehicles = Database.getAllVehicleData();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() != 0) {
                VBox vBox = new VBox();
                Button stockBtn = new Button(String.format("%s Stock", vehicle.getName()));
                Button saleBtn = new Button(String.format("%s Sales", vehicle.getName()));

                stockBtn.setOnAction(event -> vehicleStock_clicked(vehicle, stockBtn));
                saleBtn.setOnAction(event -> vehicleSales_clicked(vehicle, saleBtn));

                stockBtn.setPrefSize(150,50);
                saleBtn.setPrefSize(150,50);

                vBox.setSpacing(10);
                vBox.getChildren().addAll(stockBtn, saleBtn);

                hBox.getChildren().add(vBox);
            }
        }
    }

    public void setNodes(ScrollPane scrollPane, Label label) {
        this.main_pane = scrollPane;
        this.title_label = label;
    }

    private void vehicleStock_clicked(Vehicle vehicle, Button btn) {
        new Flash(btn).play();
        AnimationFX titleAnimation = new SlideOutRight(title_label);
        titleAnimation.setOnFinished(event1 -> title_label.setText(String.format("%s STOCK", vehicle.getName())));
        titleAnimation.playOnFinished(new SlideInRight(title_label));
        titleAnimation.play();

        FXMLLoader vehiclesStockLoader = new FXMLLoader(mainController.class.getResource("vehicle-stock-stage.fxml"));
        AnchorPane vehiclesStockScene;
        try {
            vehiclesStockScene = vehiclesStockLoader.load();
        } catch (IOException ex) {
            ShowError.show(ex);
            throw new RuntimeException(ex);
        }

        vehicleStockController controller = vehiclesStockLoader.getController();
        controller.setController(vehicle, main_pane, title_label);

        AnimationFX mainAnimation = new SlideOutDown(main_pane);
        mainAnimation.setOnFinished(event1 -> main_pane.contentProperty().set(vehiclesStockScene));
        mainAnimation.playOnFinished(new SlideInUp(main_pane));
        mainAnimation.play();
    }
    private void vehicleSales_clicked(Vehicle vehicle, Button btn) {
        new Flash(btn).play();
        AnimationFX titleAnimation = new SlideOutRight(title_label);
        titleAnimation.setOnFinished(event1 -> title_label.setText(String.format("%s SALES", vehicle.getName())));
        titleAnimation.playOnFinished(new SlideInRight(title_label));
        titleAnimation.play();

        FXMLLoader vehiclesSalesLoader = new FXMLLoader(mainController.class.getResource("vehicle-sales-stage.fxml"));
        AnchorPane vehiclesSalesScene;
        try {
            vehiclesSalesScene = vehiclesSalesLoader.load();
        } catch (IOException ex) {
            ShowError.show(ex);
            throw new RuntimeException(ex);
        }

        vehicleSalesController controller = vehiclesSalesLoader.getController();
        controller.setController(vehicle, main_pane, title_label);

        AnimationFX mainAnimation = new SlideOutDown(main_pane);
        mainAnimation.setOnFinished(event1 -> main_pane.contentProperty().set(vehiclesSalesScene));
        mainAnimation.playOnFinished(new SlideInUp(main_pane));
        mainAnimation.play();
    }
}
