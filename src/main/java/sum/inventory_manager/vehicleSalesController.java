package sum.inventory_manager;

import animatefx.animation.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import sample.AutoCompleteTextField;

public class vehicleSalesController implements Initializable {

    AutoCompleteTextField<Customer> customerAutoCompleteText;
    AutoCompleteTextField<Product> productAutoCompleteText;
    AutoCompleteTextField<VehicleStock> vehicleStockAutoCompleteText;
    @FXML
    TextField txtSaleTotal;
    @FXML
    TextField txtSaleQuantity;
    @FXML
    TextField txtSaleUnitPrice;
    @FXML
    DatePicker saleDate;
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
    TextField txtReturnTotal;
    @FXML
    TextField txtReturnQuantity;
    @FXML
    TextField txtReturnUnitPrice;
    @FXML
    CheckBox isReturnDamaged;
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

    private ScrollPane mainPane;
    private Label titleLabel;
    private Vehicle vehicle;

    private SimpleDoubleProperty creditProperty;
    @FXML
    TextField txtDiscounts;
    @FXML
    TextField txtPaid;
    @FXML
    TextField txtCredit;

    private enum Changed {
        SALE_TOTAL,
        RETURN_TOTAL,
        DISCOUNTS,
        PAID
    }

    public void setController(Vehicle vehicle, ScrollPane mainPane, Label titleLabel) {
        this.mainPane = mainPane;
        this.titleLabel = titleLabel;
        this.vehicle = vehicle;

        setUpAutoCompleteTextFields();

        saleDate.setValue(LocalDate.now());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colStock.setCellValueFactory(new PropertyValueFactory<>("StockString"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colSaleTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));

        vehicleSaleTable.setOnKeyPressed(keyEvent -> this.removeSaleItem(keyEvent.getCode()));

        colReturnProduct.setCellValueFactory(new PropertyValueFactory<>("Product"));
        colReturnQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colReturnUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colReturnSaleTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        colIsDamaged.setCellValueFactory(new PropertyValueFactory<>("DamagedString"));

        vehicleReturnTable.setOnKeyPressed(keyEvent -> this.removeReturnItem(keyEvent.getCode()));

        txtReturnTotal.setText("0");
        txtSaleTotal.setText("0");
        txtDiscounts.setText("0");
        txtPaid.setText("0");

        creditProperty = new SimpleDoubleProperty(0);
        txtCredit.textProperty().bind(creditProperty.asString());

        txtDiscounts.textProperty().addListener((observableValue, oldV, newV) -> changedValue(Changed.DISCOUNTS, oldV, newV));
        txtPaid.textProperty().addListener((observableValue, oldV, newV) -> changedValue(Changed.PAID, oldV, newV));
    }

    private void changedValue(Changed changed, String oldV, String newV) {
        if (newV.isEmpty()) newV = "0";

        try {
            double saleTotal = 0, returnTotal = 0, discounts = 0, paid = 0;

            if (changed == Changed.DISCOUNTS) {
                saleTotal = Double.parseDouble(txtSaleTotal.getText());
                returnTotal = Double.parseDouble(txtReturnTotal.getText());
                discounts = Double.parseDouble(newV);
                paid = Double.parseDouble(txtPaid.getText());
            } else if (changed == Changed.RETURN_TOTAL) {
                saleTotal = Double.parseDouble(txtSaleTotal.getText());
                returnTotal = Double.parseDouble(newV);
                discounts = Double.parseDouble(txtDiscounts.getText());
                paid = Double.parseDouble(txtPaid.getText());
            } else if (changed == Changed.SALE_TOTAL) {
                saleTotal = Double.parseDouble(newV);
                returnTotal = Double.parseDouble(txtReturnTotal.getText());
                discounts = Double.parseDouble(txtDiscounts.getText());
                paid = Double.parseDouble(txtPaid.getText());
            } else if (changed == Changed.PAID) {
                saleTotal = Double.parseDouble(txtSaleTotal.getText());
                returnTotal = Double.parseDouble(txtReturnTotal.getText());
                discounts = Double.parseDouble(txtDiscounts.getText());
                paid = Double.parseDouble(newV);
            }

            creditProperty.set(saleTotal - returnTotal - discounts - paid);
        } catch (NumberFormatException e) {
            PopUp.showMessage("Enter valid numbers");
            if (changed == Changed.DISCOUNTS) {
                txtDiscounts.setText(oldV);
                changedValue(Changed.DISCOUNTS, oldV, oldV);
            } else if (changed == Changed.RETURN_TOTAL) {
                txtReturnTotal.setText(oldV);
                changedValue(Changed.RETURN_TOTAL, oldV, oldV);
            } else if (changed == Changed.SALE_TOTAL) {
                txtSaleTotal.setText(oldV);
                changedValue(Changed.SALE_TOTAL, oldV, oldV);
            } else if (changed == Changed.PAID) {
                txtPaid.setText(oldV);
                changedValue(Changed.PAID, oldV, oldV);
            }
        }
    }

    private void removeReturnItem(KeyCode code) {
        if (vehicleReturnTable.getSelectionModel().getSelectedItem() != null) {
            if (code == KeyCode.DELETE) {
                vehicleReturnTable.getItems().remove(
                        vehicleReturnTable.getSelectionModel().getSelectedItem()
                );

                String oldV = txtReturnTotal.getText();

                if (!vehicleReturnTable.getItems().isEmpty()) {
                    double total = vehicleReturnTable.getItems().stream().map(VehicleReturnTableItem::getTotal).reduce(Double::sum).get();
                    txtReturnTotal.setText(String.valueOf(total));
                }
                else {
                    txtReturnTotal.setText("0");
                }
                changedValue(Changed.RETURN_TOTAL, oldV, txtReturnTotal.getText());
            }
        }
    }

    private void removeSaleItem(KeyCode code) {
        if (vehicleSaleTable.getSelectionModel().getSelectedItem() != null) {
            if (code == KeyCode.DELETE) {
                vehicleSaleTable.getItems().remove(
                        vehicleSaleTable.getSelectionModel().getSelectedItem()
                );

                String oldV = txtSaleTotal.getText();

                if (!vehicleSaleTable.getItems().isEmpty()) {
                    double total = vehicleSaleTable.getItems().stream().map(VehicleSaleTableItem::getTotal).reduce(Double::sum).get();
                    txtSaleTotal.setText(String.valueOf(total));
                }
                else {
                    txtSaleTotal.setText("0");
                }
                changedValue(Changed.SALE_TOTAL, oldV, txtSaleTotal.getText());
            }
        }
    }

    @FXML
    protected void addSaleItem() {
        if (vehicleStockAutoCompleteText.getLastSelectedObject() == null) {
            PopUp.showMessage("Select a stock");
            return;
        }
        double quantity, unitPrice;
        try {
            quantity = Double.parseDouble(txtSaleQuantity.getText());
            unitPrice = Double.parseDouble(txtSaleUnitPrice.getText());
        } catch (NumberFormatException e) {
            PopUp.showMessage("Enter valid numbers");
            return;
        }

        if (quantity > vehicleStockAutoCompleteText.getLastSelectedObject().getQuantity()) {
            PopUp.showMessage("Not enough stocks");
            return;
        }

        vehicleSaleTable.getItems().add(new VehicleSaleTableItem(
                vehicleStockAutoCompleteText.getLastSelectedObject(),
                quantity,
                unitPrice
        ));

        String oldV = txtSaleTotal.getText();

        if (!vehicleSaleTable.getItems().isEmpty()) {
            double total = vehicleSaleTable.getItems().stream().map(VehicleSaleTableItem::getTotal).reduce(Double::sum).get();
            txtSaleTotal.setText(String.valueOf(total));
        }
        else {
            txtSaleTotal.setText("0");
        }
        changedValue(Changed.SALE_TOTAL, oldV, txtSaleTotal.getText());
    }

    @FXML
    protected void addReturnItem() {
        if (productAutoCompleteText.getLastSelectedObject() == null) {
            PopUp.showMessage("Select a product");
            return;
        }
        double quantity, unitPrice;
        try {
            quantity = Double.parseDouble(txtReturnQuantity.getText());
            unitPrice = Double.parseDouble(txtReturnUnitPrice.getText());
        } catch (NumberFormatException e) {
            PopUp.showMessage("Enter valid numbers");
            return;
        }

        vehicleReturnTable.getItems().add(new VehicleReturnTableItem(
                productAutoCompleteText.getLastSelectedObject(),
                quantity,
                unitPrice,
                isReturnDamaged.isSelected()
        ));

        String oldV = txtReturnTotal.getText();

        if (!vehicleReturnTable.getItems().isEmpty()) {
            double total = vehicleReturnTable.getItems().stream().map(VehicleReturnTableItem::getTotal).reduce(Double::sum).get();
            txtReturnTotal.setText(String.valueOf(total));
        }
        else {
            txtReturnTotal.setText("0");
        }
        changedValue(Changed.RETURN_TOTAL, oldV, txtReturnTotal.getText());
    }

    @FXML
    protected void addVehicleSale() {
        if (customerAutoCompleteText.getLastSelectedObject() == null) {
            PopUp.showMessage("Select a customer");
            return;
        }
        if (saleDate.getValue() == null) {
            PopUp.showMessage("Select a date");
            return;
        }
        if (vehicleSaleTable.getItems().isEmpty()) {
            PopUp.showMessage("Add sale items");
            return;
        }
        Database.addVehicleSale(
                vehicle.getId(),
                customerAutoCompleteText.getLastSelectedObject().getCustomerId(),
                saleDate.getValue(),
                vehicleSaleTable.getItems(),
                vehicleReturnTable.getItems(),
                Double.parseDouble(txtSaleTotal.getText()),
                Double.parseDouble(txtReturnTotal.getText()),
                Double.parseDouble(txtDiscounts.getText()),
                Double.parseDouble(txtCredit.getText())
        );

        vehicleSaleTable.getItems().clear();
        vehicleReturnTable.getItems().clear();

        txtReturnTotal.setText("0");
        txtSaleTotal.setText("0");
        txtDiscounts.setText("0");
        txtPaid.setText("0");

        creditProperty.set(0d);
    }

    @FXML
    protected void viewSales_clicked() {
        AnimationFX titleAnimation = new SlideOutRight(titleLabel);
        titleAnimation.setOnFinished(event1 -> titleLabel.setText(String.format("%s SALES",vehicle.getName())));
        titleAnimation.playOnFinished(new SlideInRight(titleLabel));
        titleAnimation.play();

        FXMLLoader vehicleSalesViewSceneLoader = new FXMLLoader(storeStockController.class.getResource("vehicle-sales-view-stage.fxml"));
        AnchorPane vehicleSalesViewScene;
        try {
            vehicleSalesViewScene = vehicleSalesViewSceneLoader.load();
        } catch (IOException ex) {
            ShowError.show(ex);
            throw new RuntimeException(ex);
        }

        vehicleSalesViewController controller = vehicleSalesViewSceneLoader.getController();
        controller.setVehicleId(vehicle.getId());

        AnimationFX mainAnimation = new SlideOutDown(mainPane);
        mainAnimation.setOnFinished(event1 -> mainPane.contentProperty().set(vehicleSalesViewScene));
        mainAnimation.playOnFinished(new SlideInUp(mainPane));
        mainAnimation.play();
    }

    private void setUpAutoCompleteTextFields() {
        SortedSet<Customer> customerEntries = new TreeSet<>(Comparator.comparing(Customer::toString));
        customerEntries.addAll(Database.getVehicleCustomerData(vehicle.getId()));
        customerAutoCompleteText = new AutoCompleteTextField(customerEntries);

        customerAutoCompleteText.getEntryMenu().setOnAction(e ->
                ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, autoTextEvent ->
                {
                    if (customerAutoCompleteText.getLastSelectedObject() != null)
                    {
                        customerAutoCompleteText.setText(customerAutoCompleteText.getLastSelectedObject().toString());
                    }
                }));

        ((AnchorPane) txtSaleTotal.getParent()).getChildren().add(customerAutoCompleteText);
        AnchorPane.setTopAnchor(customerAutoCompleteText,21.0);
        AnchorPane.setLeftAnchor(customerAutoCompleteText,82.0);
        AnchorPane.setRightAnchor(customerAutoCompleteText,394.0);


        SortedSet<VehicleStock> vehicleStockEntries = new TreeSet<>(Comparator.comparing(VehicleStock::toString));
        vehicleStockEntries.addAll(Database.getAllVehicleStockData(vehicle.getId(), true));
        vehicleStockAutoCompleteText = new AutoCompleteTextField(vehicleStockEntries);

        vehicleStockAutoCompleteText.getEntryMenu().setOnAction(e ->
                ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, autoTextEvent ->
                {
                    if (vehicleStockAutoCompleteText.getLastSelectedObject() != null)
                    {
                        vehicleStockAutoCompleteText.setText(vehicleStockAutoCompleteText.getLastSelectedObject().toString());
                        txtSaleUnitPrice.setText(String.valueOf(vehicleStockAutoCompleteText.getLastSelectedObject().getUnitPrice()));
                    }
                }));

        ((AnchorPane) txtSaleTotal.getParent()).getChildren().add(vehicleStockAutoCompleteText);
        AnchorPane.setTopAnchor(vehicleStockAutoCompleteText,87.0);
        AnchorPane.setLeftAnchor(vehicleStockAutoCompleteText,71.0);
        AnchorPane.setRightAnchor(vehicleStockAutoCompleteText,489.0);


        SortedSet<Product> productEntries = new TreeSet<>(Comparator.comparing(Product::toString));
        productEntries.addAll(Database.getAllProductData());
        productAutoCompleteText = new AutoCompleteTextField(productEntries);

        productAutoCompleteText.getEntryMenu().setOnAction(e ->
                ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, autoTextEvent ->
                {
                    if (productAutoCompleteText.getLastSelectedObject() != null)
                    {
                        productAutoCompleteText.setText(productAutoCompleteText.getLastSelectedObject().toString());
                    }
                }));

        ((AnchorPane) txtSaleTotal.getParent()).getChildren().add(productAutoCompleteText);
        AnchorPane.setTopAnchor(productAutoCompleteText,363.0);
        AnchorPane.setLeftAnchor(productAutoCompleteText,71.0);
        AnchorPane.setRightAnchor(productAutoCompleteText,489.0);
    }
}
