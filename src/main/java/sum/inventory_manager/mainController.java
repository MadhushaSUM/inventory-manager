package sum.inventory_manager;

import animatefx.animation.*;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainController implements Initializable {
    @FXML
    Button close_button;
    @FXML
    Button product_button;
    @FXML
    Button customer_button;
    @FXML
    Button store_stock_button;
    @FXML
    Button store_sales_button;
    @FXML
    Button vehicles_button;
    @FXML
    Button vehicleStockSale_button;
    @FXML
    Button credit_button;
    @FXML
    Button review_button;
    @FXML
    Label title_label;
    @FXML
    ScrollPane main_pane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        main_pane.setPannable(true);

        close_button.setOnAction(event -> Platform.exit());

        product_button.setOnAction(this::handle_clicks);
        customer_button.setOnAction(this::handle_clicks);
        store_stock_button.setOnAction(this::handle_clicks);
        store_sales_button.setOnAction(this::handle_clicks);
        vehicles_button.setOnAction(this::handle_clicks);
        vehicleStockSale_button.setOnAction(this::handle_clicks);
        credit_button.setOnAction(this::handle_clicks);
        review_button.setOnAction(this::handle_clicks);
    }

    private void handle_clicks(Event e) {
        if (e.getTarget().equals(product_button)) {
            new Flash(product_button).play();
            AnimationFX titleAnimation = new SlideOutRight(title_label);
            titleAnimation.setOnFinished(event1 -> title_label.setText("PRODUCTS"));
            titleAnimation.playOnFinished(new SlideInRight(title_label));
            titleAnimation.play();

            FXMLLoader productSceneLoader = new FXMLLoader(mainController.class.getResource("product-stage.fxml"));
            AnchorPane productScene;
            try {
                productScene = productSceneLoader.load();
            } catch (IOException ex) {
                ShowError.show(ex);
                throw new RuntimeException(ex);
            }

            //productScene.getStylesheets().add(String.valueOf(mainController.class.getResource("main-stage-styles.css")));

            AnimationFX mainAnimation = new SlideOutDown(main_pane);
            mainAnimation.setOnFinished(event1 -> main_pane.contentProperty().set(productScene));
            mainAnimation.playOnFinished(new SlideInUp(main_pane));
            mainAnimation.play();
        }else if (e.getTarget().equals(customer_button)) {
            new Flash(customer_button).play();
            AnimationFX titleAnimation = new SlideOutRight(title_label);
            titleAnimation.setOnFinished(event1 -> title_label.setText("CUSTOMERS"));
            titleAnimation.playOnFinished(new SlideInRight(title_label));
            titleAnimation.play();

            FXMLLoader customerSceneLoader = new FXMLLoader(mainController.class.getResource("customer-stage.fxml"));
            AnchorPane customerScene;
            try {
                customerScene = customerSceneLoader.load();
            } catch (IOException ex) {
                ShowError.show(ex);
                throw new RuntimeException(ex);
            }

            AnimationFX mainAnimation = new SlideOutDown(main_pane);
            mainAnimation.setOnFinished(event1 -> main_pane.contentProperty().set(customerScene));
            mainAnimation.playOnFinished(new SlideInUp(main_pane));
            mainAnimation.play();
        }else if (e.getTarget().equals(store_stock_button)) {
            new Flash(store_stock_button).play();
            AnimationFX titleAnimation = new SlideOutRight(title_label);
            titleAnimation.setOnFinished(event1 -> title_label.setText("STORE STOCKS"));
            titleAnimation.playOnFinished(new SlideInRight(title_label));
            titleAnimation.play();

            FXMLLoader storeStockSceneLoader = new FXMLLoader(mainController.class.getResource("store-stock-stage.fxml"));
            AnchorPane storeStockScene;
            try {
                storeStockScene = storeStockSceneLoader.load();
            } catch (IOException ex) {
                ShowError.show(ex);
                throw new RuntimeException(ex);
            }

            storeStockController controller = storeStockSceneLoader.getController();
            controller.getSceneNodes(main_pane, title_label);


            AnimationFX mainAnimation = new SlideOutDown(main_pane);
            mainAnimation.setOnFinished(event1 -> main_pane.contentProperty().set(storeStockScene));
            mainAnimation.playOnFinished(new SlideInUp(main_pane));
            mainAnimation.play();
        }else if (e.getTarget().equals(store_sales_button)) {
            new Flash(store_sales_button).play();
            AnimationFX titleAnimation = new SlideOutRight(title_label);
            titleAnimation.setOnFinished(event1 -> title_label.setText("STORE SALES"));
            titleAnimation.playOnFinished(new SlideInRight(title_label));
            titleAnimation.play();

            FXMLLoader storeSalesSceneLoader = new FXMLLoader(mainController.class.getResource("store-sales-stage.fxml"));
            AnchorPane storeSalesScene;
            try {
                storeSalesScene = storeSalesSceneLoader.load();
            } catch (IOException ex) {
                ShowError.show(ex);
                throw new RuntimeException(ex);
            }

            storeSalesController controller = storeSalesSceneLoader.getController();
            controller.getSceneNodes(main_pane, title_label);

            AnimationFX mainAnimation = new SlideOutDown(main_pane);
            mainAnimation.setOnFinished(event1 -> main_pane.contentProperty().set(storeSalesScene));
            mainAnimation.playOnFinished(new SlideInUp(main_pane));
            mainAnimation.play();
        }else if (e.getTarget().equals(vehicles_button)) {
            new Flash(vehicles_button).play();
            AnimationFX titleAnimation = new SlideOutRight(title_label);
            titleAnimation.setOnFinished(event1 -> title_label.setText("VEHICLES"));
            titleAnimation.playOnFinished(new SlideInRight(title_label));
            titleAnimation.play();

            FXMLLoader vehiclesSceneLoader = new FXMLLoader(mainController.class.getResource("vehicle-stage.fxml"));
            AnchorPane vehiclesScene;
            try {
                vehiclesScene = vehiclesSceneLoader.load();
            } catch (IOException ex) {
                ShowError.show(ex);
                throw new RuntimeException(ex);
            }

            AnimationFX mainAnimation = new SlideOutDown(main_pane);
            mainAnimation.setOnFinished(event1 -> main_pane.contentProperty().set(vehiclesScene));
            mainAnimation.playOnFinished(new SlideInUp(main_pane));
            mainAnimation.play();
        }else if (e.getTarget().equals(vehicleStockSale_button)) {
            new Flash(vehicleStockSale_button).play();
            AnimationFX titleAnimation = new SlideOutRight(title_label);
            titleAnimation.setOnFinished(event1 -> title_label.setText("VEHICLE STOCKS/SALES"));
            titleAnimation.playOnFinished(new SlideInRight(title_label));
            titleAnimation.play();

            FXMLLoader vehiclesStockSaleSceneLoader = new FXMLLoader(mainController.class.getResource("vehicle-stock-sales-stage.fxml"));
            AnchorPane vehiclesStockSaleScene;
            try {
                vehiclesStockSaleScene = vehiclesStockSaleSceneLoader.load();
            } catch (IOException ex) {
                ShowError.show(ex);
                throw new RuntimeException(ex);
            }

            vehicleStockSaleController controller = vehiclesStockSaleSceneLoader.getController();
            controller.setNodes(main_pane, title_label);

            AnimationFX mainAnimation = new SlideOutDown(main_pane);
            mainAnimation.setOnFinished(event1 -> main_pane.contentProperty().set(vehiclesStockSaleScene));
            mainAnimation.playOnFinished(new SlideInUp(main_pane));
            mainAnimation.play();
        } else if (e.getTarget().equals(credit_button)) {
            new Flash(credit_button).play();
            AnimationFX titleAnimation = new SlideOutRight(title_label);
            titleAnimation.setOnFinished(event1 -> title_label.setText("CREDITS"));
            titleAnimation.playOnFinished(new SlideInRight(title_label));
            titleAnimation.play();

            FXMLLoader creditSceneLoader = new FXMLLoader(mainController.class.getResource("credits-stage.fxml"));
            AnchorPane creditScene;
            try {
                creditScene = creditSceneLoader.load();
            } catch (IOException ex) {
                ShowError.show(ex);
                throw new RuntimeException(ex);
            }

            AnimationFX mainAnimation = new SlideOutDown(main_pane);
            mainAnimation.setOnFinished(event1 -> main_pane.contentProperty().set(creditScene));
            mainAnimation.playOnFinished(new SlideInUp(main_pane));
            mainAnimation.play();
        } else if (e.getTarget().equals(review_button)) {
            new Flash(review_button).play();
            AnimationFX titleAnimation = new SlideOutRight(title_label);
            titleAnimation.setOnFinished(event1 -> title_label.setText("REVIEW"));
            titleAnimation.playOnFinished(new SlideInRight(title_label));
            titleAnimation.play();

            FXMLLoader creditSceneLoader = new FXMLLoader(mainController.class.getResource("review-stage.fxml"));
            AnchorPane creditScene;
            try {
                creditScene = creditSceneLoader.load();
            } catch (IOException ex) {
                ShowError.show(ex);
                throw new RuntimeException(ex);
            }

            AnimationFX mainAnimation = new SlideOutDown(main_pane);
            mainAnimation.setOnFinished(event1 -> main_pane.contentProperty().set(creditScene));
            mainAnimation.playOnFinished(new SlideInUp(main_pane));
            mainAnimation.play();
        }
    }
}