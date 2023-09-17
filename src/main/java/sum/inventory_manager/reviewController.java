package sum.inventory_manager;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.AutoCompleteTextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;

public class reviewController implements Initializable {
    AutoCompleteTextField<Vehicle> vehicleAutoCompleteText;
    @FXML
    DatePicker fromDate;
    @FXML
    DatePicker toDate;
    @FXML
    TextField txtSaleTotal;
    @FXML
    TextField txtDiscounts;
    @FXML
    TextField txtReturns;
    @FXML
    TextField txtCredits;
    @FXML
    TextField txtNetTotal;
    @FXML
    TableView<VehicleUnload> vehicleUnloadTable;
    @FXML
    TableColumn<VehicleUnload, Integer> colId;
    @FXML
    TableColumn<VehicleUnload, String> colDate;
    @FXML
    TableColumn<VehicleUnload, String> colStock;
    @FXML
    TableColumn<VehicleUnload, Double> colQuantity;
    @FXML
    TableColumn<VehicleUnload, Double> colUnitPrice;
    @FXML
    TableColumn<VehicleUnload, String> colDestination;
    @FXML
    TableView<VehicleReturn> returnTable;
    @FXML
    TableColumn<VehicleReturn, Integer> colReturnId;
    @FXML
    TableColumn<VehicleReturn, Integer> colReturnProduct;
    @FXML
    TableColumn<VehicleReturn, Integer> colReturnQuantity;
    @FXML
    TableColumn<VehicleReturn, Integer> colReturnUnitPrice;
    @FXML
    TableColumn<VehicleReturn, Integer> colReturnDamaged;
    @FXML
    TableView<VehicleStockLoad> stockLoadTable;
    @FXML
    TableColumn<VehicleStockLoad, Integer> colLoadId;
    @FXML
    TableColumn<VehicleStockLoad, Integer> colLoadDate;
    @FXML
    TableColumn<VehicleStockLoad, Integer> colLoadProduct;
    @FXML
    TableColumn<VehicleStockLoad, Integer> colLoadQuantity;
    @FXML
    TableColumn<VehicleStockLoad, Integer> colLoadUnitPrice;
    @FXML
    TableColumn<VehicleStockLoad, Integer> colLoadOrigin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toDate.setValue(LocalDate.now());

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("VehicleStock"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("Destination"));

        colReturnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colReturnProduct.setCellValueFactory(new PropertyValueFactory<>("Product"));
        colReturnQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colReturnUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colReturnDamaged.setCellValueFactory(new PropertyValueFactory<>("Damaged"));

        colLoadId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colLoadDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colLoadProduct.setCellValueFactory(new PropertyValueFactory<>("Product"));
        colLoadQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colLoadUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colLoadOrigin.setCellValueFactory(new PropertyValueFactory<>("FromWhere"));

        setUpAutoCompleteTextFields();
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

        ((AnchorPane) txtSaleTotal.getParent()).getChildren().add(vehicleAutoCompleteText);
        AnchorPane.setTopAnchor(vehicleAutoCompleteText,20.0);
        AnchorPane.setLeftAnchor(vehicleAutoCompleteText,65.0);
        AnchorPane.setRightAnchor(vehicleAutoCompleteText,465.0);
    }

    @FXML
    protected void search_clicked() {
        if (vehicleAutoCompleteText.getLastSelectedObject() == null) {
            PopUp.showMessage("Select a vehicle");
            return;
        }

        ReviewReturns values = Database.reviewVehicle(fromDate.getValue(), toDate.getValue(),
                vehicleAutoCompleteText.getLastSelectedObject());
        txtSaleTotal.setText(String.valueOf(values.getSaleTotal()));
        txtDiscounts.setText(String.valueOf(values.getDiscounts()));
        txtReturns.setText(String.valueOf(values.getReturns()));
        txtCredits.setText(String.valueOf(values.getCredits()));
        txtNetTotal.setText(String.valueOf(
                values.getSaleTotal() - values.getDiscounts() - values.getReturns()
        ));

        vehicleUnloadTable.setItems(values.getUnloads());
        returnTable.setItems(values.getReturnedItems());
        stockLoadTable.setItems(values.getStockLoads());
    }
}
