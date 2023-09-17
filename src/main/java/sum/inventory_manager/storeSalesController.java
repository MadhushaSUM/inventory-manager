package sum.inventory_manager;

import animatefx.animation.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import sample.AutoCompleteTextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;

public class storeSalesController implements Initializable {
    AutoCompleteTextField<Vehicle> vehicleAutoCompleteText;
    AutoCompleteTextField<StoreStock> stockAutoCompleteText;
    @FXML
    TextField txtQuantity;
    @FXML
    TextField txtUnitPrice;
    @FXML
    DatePicker saleDate;
    @FXML
    TableView<StoreSaleUnload> storeSaleTable;
    @FXML
    TableColumn<StoreSaleUnload, String> colStock;
    @FXML
    TableColumn<StoreSaleUnload, Double> colQuantity;
    @FXML
    TableColumn<StoreSaleUnload, Double> colUnitPrice;
    @FXML
    TableColumn<StoreSaleUnload, Double> colTotal;
    @FXML
    TextField txtTotal;

    ScrollPane main_pane;
    Label title_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saleDate.setValue(LocalDate.now());

        colStock.setCellValueFactory(new PropertyValueFactory<>("StoreStock"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        storeSaleTable.setOnKeyPressed(keyEvent -> {
            if (storeSaleTable.getSelectionModel().getSelectedItem() != null) {
                if (keyEvent.getCode() == KeyCode.DELETE) {
                    storeSaleTable.getItems().remove(storeSaleTable.getSelectionModel().getSelectedItem());

                    if (!storeSaleTable.getItems().isEmpty()) {
                        double total = storeSaleTable.getItems().stream().map(StoreSaleUnload::getTotal).reduce(Double::sum).get();
                        txtTotal.setText(String.valueOf(total));
                    }
                    else {
                        txtTotal.setText("0");
                    }
                }
            }
        });

        setUpAutoCompleteTextFields();
    }

    @FXML
    protected void addToTable() {
        if (stockAutoCompleteText.getLastSelectedObject() == null) {
            PopUp.showMessage("Select a stock");
            return;
        }

        try {
            double quantity = Double.parseDouble(txtQuantity.getText());
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());

            if (stockAutoCompleteText.getLastSelectedObject().getQuantity() < quantity) {
                PopUp.showMessage("Not enough stocks");
                return;
            }

            storeSaleTable.getItems().add(new StoreSaleUnload(
                    vehicleAutoCompleteText.getLastSelectedObject(),
                    stockAutoCompleteText.getLastSelectedObject(),
                    quantity,
                    unitPrice
            ));

            double total = storeSaleTable.getItems().stream().map(StoreSaleUnload::getTotal).reduce(Double::sum).get();
            txtTotal.setText(String.valueOf(total));
        } catch (NumberFormatException e) {
            PopUp.showMessage("Enter valid numbers!");
            return;
        }
    }

    @FXML
    protected void addSale() {
        try {
//            if (vehicleAutoCompleteText.getLastSelectedObject() == null) {
//                PopUp.showMessage("Select a vehicle!");
//                return;
//            }
            if (saleDate.getValue() == null) {
                PopUp.showMessage("Select a date!");
                return;
            }
            if (storeSaleTable.getItems().isEmpty()) {
                PopUp.showMessage("Add items to the table!");
                return;
            }

            double total = Double.parseDouble(txtTotal.getText());

            Database.addStoreSale(
                    vehicleAutoCompleteText.getLastSelectedObject().getId(),
                    saleDate.getValue(),
                    storeSaleTable.getItems(),
                    total
            );


        } catch (Exception e) {
            ShowError.show(e);
            e.printStackTrace();
        }
    }

    public void getSceneNodes(ScrollPane scrollPane, Label label) {
        this.main_pane = scrollPane;
        this.title_label = label;
    }
    @FXML
    protected void viewSales_clicked() {
        AnimationFX titleAnimation = new SlideOutRight(title_label);
        titleAnimation.setOnFinished(event1 -> title_label.setText("STORE SALES"));
        titleAnimation.playOnFinished(new SlideInRight(title_label));
        titleAnimation.play();

        FXMLLoader storeStockLoadsSceneLoader = new FXMLLoader(storeStockController.class.getResource("store-sales-view-stage.fxml"));
        AnchorPane storeStockLoadsScene;
        try {
            storeStockLoadsScene = storeStockLoadsSceneLoader.load();
        } catch (IOException ex) {
            ShowError.show(ex);
            throw new RuntimeException(ex);
        }

        AnimationFX mainAnimation = new SlideOutDown(main_pane);
        mainAnimation.setOnFinished(event1 -> main_pane.contentProperty().set(storeStockLoadsScene));
        mainAnimation.playOnFinished(new SlideInUp(main_pane));
        mainAnimation.play();
    }

    private void setUpAutoCompleteTextFields() {
        SortedSet<Vehicle> vehicleEntries = new TreeSet<>(Comparator.comparing(Vehicle::getName));
        vehicleEntries.addAll(Database.getAllVehicleData());
        vehicleAutoCompleteText = new AutoCompleteTextField(vehicleEntries);

        vehicleAutoCompleteText.getEntryMenu().setOnAction(e ->
                ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, autoTextEvent ->
                {
                    if (vehicleAutoCompleteText.getLastSelectedObject() != null)
                    {
                        vehicleAutoCompleteText.setText(vehicleAutoCompleteText.getLastSelectedObject().getName());
                    }
                }));

        ((AnchorPane) txtQuantity.getParent()).getChildren().add(vehicleAutoCompleteText);
        AnchorPane.setTopAnchor(vehicleAutoCompleteText,21.0);
        AnchorPane.setLeftAnchor(vehicleAutoCompleteText,192.0);
        AnchorPane.setRightAnchor(vehicleAutoCompleteText,252.0);


        SortedSet<StoreStock> stockEntries = new TreeSet<>(Comparator.comparing(StoreStock::toString));
        stockEntries.addAll(Database.getAllStoreStockData(true));
        stockAutoCompleteText = new AutoCompleteTextField(stockEntries);

        stockAutoCompleteText.getEntryMenu().setOnAction(e ->
                ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, autoTextEvent ->
                {
                    if (stockAutoCompleteText.getLastSelectedObject() != null)
                    {
                        stockAutoCompleteText.setText(stockAutoCompleteText.getLastSelectedObject().toString());
                    }
                }));

        ((AnchorPane) txtQuantity.getParent()).getChildren().add(stockAutoCompleteText);
        AnchorPane.setTopAnchor(stockAutoCompleteText,57.0);
        AnchorPane.setLeftAnchor(stockAutoCompleteText,192.0);
        AnchorPane.setRightAnchor(stockAutoCompleteText,252.0);
    }
}
