package sum.inventory_manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ResourceBundle;

public class vehicleController implements Initializable {
    @FXML
    TableView<Vehicle> vehicleTable;
    @FXML
    TableColumn<Vehicle, Integer> colId;
    @FXML
    TableColumn<Vehicle, String> colName;
    @FXML
    TableColumn<Vehicle, String> colDriverName;
    @FXML
    TableColumn<Vehicle, String> colDriverTp;
    @FXML
    TableColumn<Vehicle, String> colRepName;
    @FXML
    TableColumn<Vehicle, String> colRepTp;
    @FXML
    TextField txtName;
    @FXML
    TextField txtDriverName;
    @FXML
    TextField txtDriverTp;
    @FXML
    TextField txtRepName;
    @FXML
    TextField txtRepTp;

    public enum VehicleDetails {
        NAME,
        DRIVER_NAME,
        DRIVER_TP,
        REP_NAME,
        REP_TP
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colDriverName.setCellValueFactory(new PropertyValueFactory<>("DriverName"));
        colDriverTp.setCellValueFactory(new PropertyValueFactory<>("DriverTp"));
        colRepName.setCellValueFactory(new PropertyValueFactory<>("RepName"));
        colRepTp.setCellValueFactory(new PropertyValueFactory<>("RepTp"));

        vehicleTable.setItems(Database.getAllVehicleData());

        vehicleTable.setEditable(true);

        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setOnEditCommit(event -> Database.changeVehicleDetails(VehicleDetails.NAME, event));

        colDriverName.setCellFactory(TextFieldTableCell.forTableColumn());
        colDriverName.setOnEditCommit(event -> Database.changeVehicleDetails(VehicleDetails.DRIVER_NAME, event));

        colDriverTp.setCellFactory(TextFieldTableCell.forTableColumn());
        colDriverTp.setOnEditCommit(event -> Database.changeVehicleDetails(VehicleDetails.DRIVER_TP, event));

        colRepName.setCellFactory(TextFieldTableCell.forTableColumn());
        colRepName.setOnEditCommit(event -> Database.changeVehicleDetails(VehicleDetails.REP_NAME, event));

        colRepTp.setCellFactory(TextFieldTableCell.forTableColumn());
        colRepTp.setOnEditCommit(event -> Database.changeVehicleDetails(VehicleDetails.REP_TP, event));
    }

    @FXML
    protected void addVehicle() {
        if (txtName.getText().isEmpty()) {
            PopUp.showMessage("Name is required!");
            return;
        }
        Database.addVehicle(
                txtName.getText(),
                txtDriverName.getText(),
                txtDriverTp.getText(),
                txtRepName.getText(),
                txtRepTp.getText()
        );
        vehicleTable.setItems(Database.getAllVehicleData());
    }
}
