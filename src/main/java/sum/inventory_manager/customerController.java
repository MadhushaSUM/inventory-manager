package sum.inventory_manager;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;

public class customerController implements Initializable {
    sample.AutoCompleteTextField<Vehicle> vehicleAutoCompleteText;
    @FXML
    TableView<Customer> customerTable;
    @FXML
    TableColumn<Customer, Integer> colId;
    @FXML
    TableColumn<Customer, String> colName;
    @FXML
    TableColumn<Customer, String> colVehicleId;
    @FXML
    TableColumn<Customer, String> colAddress;
    @FXML
    TableColumn<Customer, String> colTP;
    @FXML
    TextField txtName;
    @FXML
    TextField txtAddress;
    @FXML
    TextField txtTP;

    public enum CustomerDetail {
        NAME,
        VEHICLE_ID,
        ADDRESS,
        TELEPHONE
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTP.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("VehicleIdString"));

        customerTable.setItems(Database.getAllCustomerData());

        customerTable.setEditable(true);

        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setOnEditCommit(event -> Database.changeCutomerDetails(CustomerDetail.NAME, event));

        colVehicleId.setCellFactory(TextFieldTableCell.forTableColumn());
        colVehicleId.setOnEditCommit(event -> Database.changeCutomerDetails(CustomerDetail.VEHICLE_ID, event));

        colAddress.setCellFactory(TextFieldTableCell.forTableColumn());
        colAddress.setOnEditCommit(event -> Database.changeCutomerDetails(CustomerDetail.ADDRESS, event));

        colTP.setCellFactory(TextFieldTableCell.forTableColumn());
        colTP.setOnEditCommit(event -> Database.changeCutomerDetails(CustomerDetail.TELEPHONE, event));

        setUpAutoCompleteTextFields();
    }

    private void setUpAutoCompleteTextFields() {
        SortedSet<Vehicle> vehicleEntries = new TreeSet<>(Comparator.comparing(Vehicle::getName));
        vehicleEntries.addAll(Database.getAllVehicleData());
        vehicleAutoCompleteText = new sample.AutoCompleteTextField(vehicleEntries);

        vehicleAutoCompleteText.getEntryMenu().setOnAction(e ->
                ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, autoTextEvent ->
                {
                    if (vehicleAutoCompleteText.getLastSelectedObject() != null)
                    {
                        vehicleAutoCompleteText.setText(vehicleAutoCompleteText.getLastSelectedObject().getName());
                    }
                }));

        ((AnchorPane) txtName.getParent()).getChildren().add(vehicleAutoCompleteText);
        AnchorPane.setTopAnchor(vehicleAutoCompleteText,362.0);
        AnchorPane.setLeftAnchor(vehicleAutoCompleteText,222.0);
        AnchorPane.setRightAnchor(vehicleAutoCompleteText,222.0);
    }

    @FXML
    protected void addNewCustomer() {
        //new RubberBand(btnAdd).play();
        if (txtName.getText().isEmpty()) {
            PopUp.showMessage("Name is required!");
            return;
        }
        if (vehicleAutoCompleteText.getLastSelectedObject() == null) {
            PopUp.showMessage("Select a vehicle");
            return;
        }
        Database.addCustomer(txtName.getText(), vehicleAutoCompleteText.getLastSelectedObject().getId(), txtAddress.getText(),txtTP.getText());
        customerTable.setItems(Database.getAllCustomerData());

    }
}
